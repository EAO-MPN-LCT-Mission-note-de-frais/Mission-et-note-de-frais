package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Mapper pour convertir un {@link Transport} en {@link TransportDTO}.
 */
@Component
public class TransportMapper implements Function<Transport, TransportDTO> {

    /**
     * Convertit un {@link Transport} en {@link TransportDTO}.
     *
     * @param entity l'entité à convertir
     * @return le DTO correspondant
     */
    @Override
    public TransportDTO apply(Transport entity) {
        if (entity == null) {
            return null;
        }
        return new TransportDTO(entity.getId(), entity.getName());
    }

    /**
     * Convertit un {@link TransportDTO} en {@link Transport}.
     *
     * @param dto le DTO à convertir
     * @return l'entité correspondante
     */
    public Transport mapToEntity(TransportDTO dto) {
        if (dto == null) {
            return null;
        }
        Transport transport = new Transport();
        transport.setId(dto.getId());
        transport.setName(dto.getName());
        return transport;
    }
}
