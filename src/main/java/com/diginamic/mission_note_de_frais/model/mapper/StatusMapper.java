package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.StatusDTO;
import com.diginamic.mission_note_de_frais.model.entity.Status;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StatusMapper implements Function<Status, StatusDTO> {

  /**
   * Converts a {@link Status} to a {@link StatusDTO}.
   */
  @Override
  public StatusDTO apply(Status entity) {
    var status = new StatusDTO();
    status.setId(entity.getId());
    status.setName(entity.getName());
    return status;
  }

  /**
   * Converts a {@link StatusDTO} to a {@link Status}.
   */
  public Status toEntity(StatusDTO dto) {
    var status = new Status();
    status.setId(dto.getId());
    status.setName(dto.getName());
    return status;
  }
}
