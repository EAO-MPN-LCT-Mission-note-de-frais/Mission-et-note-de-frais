package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import com.diginamic.mission_note_de_frais.service.TransportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MissionMapper implements Function<Mission, MissionDTO> {

	@Autowired
	private TransportServiceImpl transportService;

	private final StatusMapper statusMapper;
	private final SimpleExpenseReportMapper expenseReportMapper;

	public MissionMapper(StatusMapper statusMapper, SimpleExpenseReportMapper expenseReportMapper) {
		this.statusMapper = statusMapper;
		this.expenseReportMapper = expenseReportMapper;
	}

	/**
	 * Converts a {@link Mission} to a {@link MissionDTO}.
	 */
	@Override
	public MissionDTO apply(Mission entity) {
		var mission = new MissionDTO();
		mission.setId(entity.getId());
		mission.setStartDate(entity.getStartDate());
		mission.setEndDate(entity.getEndDate());
		mission.setStartTown(entity.getStartTown());
		mission.setEndTown(entity.getEndTown());

		Optional.ofNullable(entity.getStatus()).map(statusMapper).ifPresent(mission::setStatus);

		Optional.ofNullable(entity.getExpenseReport()).map(expenseReportMapper).ifPresent(mission::setExpenseReport);

		mission.setTransportIds(entity.getTransports().stream().map(Transport::getId).collect(Collectors.toSet()));

		Optional.ofNullable(entity.getMissionType()).map(MissionType::getId) 
				.ifPresent(mission::setMissionTypeId);

		return mission;
	}

	/**
	 * Converts a {@link MissionDTO} to a {@link Mission}.
	 */
	public Mission toEntity(MissionDTO dto) {
		var mission = new Mission();
		mission.setId(dto.getId());
		mission.setStartDate(dto.getStartDate());
		mission.setEndDate(dto.getEndDate());
		mission.setStartTown(dto.getStartTown());
		mission.setEndTown(dto.getEndTown());

		Optional.ofNullable(dto.getStatus()).ifPresent(status -> mission.setStatus(statusMapper.toEntity(status)));

		Optional.ofNullable(dto.getExpenseReport())
				.ifPresent(expenseReport -> mission.setExpenseReport(expenseReportMapper.toEntity(expenseReport)));

		mission.setTransports(dto.getTransportIds().stream().map(id -> transportService.getTransportById(id))
				.collect(Collectors.toSet()));

		if (dto.getMissionTypeId() != null) {
			MissionType missionType = new MissionType();
			missionType.setId(dto.getMissionTypeId());
			mission.setMissionType(missionType); 
		}

		return mission;
	}

}
