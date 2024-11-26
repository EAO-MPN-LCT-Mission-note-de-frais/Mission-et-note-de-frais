package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MissionMapper implements Function<Mission, MissionDTO> {

  private final StatusMapper statusMapper;
  private final SimpleExpenseReportMapper expenseReportMapper;

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

    Optional.ofNullable(entity.getStatus())
        .map(statusMapper)
        .ifPresent(mission::setStatus);

    Optional.ofNullable(entity.getExpenseReport())
        .map(expenseReportMapper)
        .ifPresent(mission::setExpenseReport);

    return mission;
  }
}
