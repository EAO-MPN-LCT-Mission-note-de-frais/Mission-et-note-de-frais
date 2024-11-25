package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.StatusDto;
import com.diginamic.mission_note_de_frais.model.entity.Status;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StatusMapper implements Function<Status, StatusDto> {

  /**
   * Converts a {@link Status} to a {@link StatusDto}.
   */
  @Override
  public StatusDto apply(Status entity) {
    var status = new StatusDto();
    status.setId(entity.getId());
    status.setName(entity.getName());
    status.setDescription(entity.getDescription());
    return status;
  }
}
