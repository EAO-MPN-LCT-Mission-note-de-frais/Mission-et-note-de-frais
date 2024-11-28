package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import com.diginamic.mission_note_de_frais.model.mapper.TransportMapper;
import com.diginamic.mission_note_de_frais.model.repository.TransportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository;

    @Mock
    private TransportMapper transportMapper;

    @InjectMocks
    private TransportServiceImpl transportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransports() {
        // Arrange
        Transport transport = new Transport();
        transport.setId(1L);
        transport.setName("Bus");
        when(transportRepository.findAll()).thenReturn(List.of(transport));
        when(transportMapper.apply(transport)).thenReturn(new TransportDTO(1L, "Bus"));

        // Act
        List<TransportDTO> result = transportService.getAllTransports();

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Bus");
        verify(transportRepository, times(1)).findAll();
    }

    @Test
    void testCreateTransport_Success() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Train");
        Transport transport = new Transport();
        transport.setName("Train");
        Transport savedTransport = new Transport();
        savedTransport.setId(2L);
        savedTransport.setName("Train");

        when(transportRepository.existsByName("Train")).thenReturn(false);
        when(transportRepository.save(any(Transport.class))).thenReturn(savedTransport);

        // Act
        TransportDTO result = transportService.createTransport(transportDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Train");
        verify(transportRepository, times(1)).existsByName("Train");
        verify(transportRepository, times(1)).save(any(Transport.class));
    }

    @Test
    void testCreateTransport_Fails_WhenTransportExists() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Bus");
        when(transportRepository.existsByName("Bus")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transportService.createTransport(transportDTO));
        verify(transportRepository, times(1)).existsByName("Bus");
        verify(transportRepository, never()).save(any(Transport.class));
    }

    @Test
    void testUpdateTransport_Success() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Updated Bus");
        Transport existingTransport = new Transport();
        existingTransport.setId(1L);
        existingTransport.setName("Bus");
        when(transportRepository.findById(1L)).thenReturn(Optional.of(existingTransport));
        when(transportRepository.save(any(Transport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TransportDTO result = transportService.updateTransport(1L, transportDTO);

        // Assert
        assertThat(result.getName()).isEqualTo("Updated Bus");
        verify(transportRepository, times(1)).findById(1L);
        verify(transportRepository, times(1)).save(any(Transport.class));
    }

    @Test
    void testUpdateTransport_Fails_WhenTransportNotFound() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Updated Train");
        when(transportRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transportService.updateTransport(1L, transportDTO));
        verify(transportRepository, times(1)).findById(1L);
        verify(transportRepository, never()).save(any(Transport.class));
    }

    @Test
    void testDeleteTransport_Success() {
        // Arrange
        when(transportRepository.existsById(1L)).thenReturn(true);

        // Act
        transportService.deleteTransport(1L);

        // Assert
        verify(transportRepository, times(1)).existsById(1L);
        verify(transportRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTransport_Fails_WhenTransportNotFound() {
        // Arrange
        when(transportRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transportService.deleteTransport(1L));
        verify(transportRepository, times(1)).existsById(1L);
        verify(transportRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetMissionsForTransport_Success() {
        // Arrange
        Transport transport = new Transport();
        transport.setId(1L);
        transport.setName("Bus");
        transport.setMissions(Collections.emptySet());

        when(transportRepository.findById(1L)).thenReturn(Optional.of(transport));

        // Act
        Set<MissionDTO> result = transportService.getMissionsForTransport(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(transportRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMissionsForTransport_Fails_WhenTransportNotFound() {
        // Arrange
        when(transportRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transportService.getMissionsForTransport(1L));
        verify(transportRepository, times(1)).findById(1L);
    }
}
