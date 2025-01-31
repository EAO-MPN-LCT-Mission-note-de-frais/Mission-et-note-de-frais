package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;

    /**
     * Returns a list of all transports available in the system.
     */
    @GetMapping("/transports")
    public List<TransportDTO> getTransports() {
        return transportService.getAllTransports();
    }
}