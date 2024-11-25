package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.MissionDto;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class MissionMapper implements Function<Mission, MissionDto> {

  private final StatusMapper statusMapper;

  public MissionMapper(StatusMapper statusMapper) {
    this.statusMapper = statusMapper;
  }

  /**
   * Converts a {@link Mission} to a {@link MissionDto}.
   */
  @Override
  public MissionDto apply(Mission entity) {
    var mission = new MissionDto();
    mission.setId(entity.getId());
    mission.setStartDate(entity.getStartDate());
    mission.setEndDate(entity.getEndDate());
    mission.setStartTown(entity.getStartTown());
    mission.setEndTown(entity.getEndTown());

    Optional.ofNullable(entity.getStatus())
        .map(statusMapper)
        .ifPresent(mission::setStatus);

    return mission;
  }
}