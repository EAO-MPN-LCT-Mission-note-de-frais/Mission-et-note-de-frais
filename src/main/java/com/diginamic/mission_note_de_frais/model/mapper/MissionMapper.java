package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MissionMapper implements Function<Mission, MissionDTO> {

  private final StatusMapper statusMapper;
  private final TransportMapper transportMapper;

  public MissionMapper(StatusMapper statusMapper, TransportMapper transportMapper) {
    this.statusMapper = statusMapper;
    this.transportMapper = transportMapper;
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

      Optional.ofNullable(entity.getStatus())
          .map(statusMapper)
          .ifPresent(mission::setStatus);

      mission.setTransportIds(
          entity.getTransports().stream()
              .map(transport -> transport.getId())
              .collect(Collectors.toSet())
      );

      return mission;
  }
}
