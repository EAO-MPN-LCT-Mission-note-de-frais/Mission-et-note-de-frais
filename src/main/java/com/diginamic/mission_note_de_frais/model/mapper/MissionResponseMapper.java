package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.MissionResponse;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MissionResponseMapper implements Function<Mission, MissionResponse> {

    private final StatusMapper statusMapper;
    private final ExpenseReportMapper expenseReportMapper;
    private final MissionTypeMapper missionTypeMapper;

    /**
     * Converts a {@link Mission} to a {@link MissionResponse}.
     */
    @Override
    public MissionResponse apply(Mission entity) {
        var mission = new MissionResponse().setId(entity.getId())
                .setStartDate(entity.getStartDate())
                .setEndDate(entity.getEndDate())
                .setStartTown(entity.getStartTown())
                .setEndTown(entity.getEndTown());

        Optional.ofNullable(entity.getStatus())
                .map(statusMapper)
                .ifPresent(mission::setStatus);

        Optional.ofNullable(entity.getExpenseReport())
                .map(expenseReportMapper)
                .ifPresent(mission::setExpenseReport);

        Optional.ofNullable(entity.getMissionType())
                .map(missionTypeMapper::toDTO)
                .ifPresent(mission::setMissionType);

        mission.setTransportIds(
                entity.getTransports()
                        .stream()
                        .map(Transport::getId)
                        .collect(Collectors.toSet())
        );

        return mission;
    }
}
