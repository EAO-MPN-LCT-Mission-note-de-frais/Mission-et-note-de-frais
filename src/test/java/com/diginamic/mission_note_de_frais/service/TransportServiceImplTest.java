package com.diginamic.mission_note_de_frais.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import com.diginamic.mission_note_de_frais.model.repository.TransportRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

public class TransportServiceImplTest {

    @Mock
    private TransportRepository transportRepository; 

    @InjectMocks
    private TransportServiceImpl transportService; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransport_Success() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Bus");
        Mockito.when(transportRepository.existsByName("Bus")).thenReturn(false);
        Mockito.when(transportRepository.save(any(Transport.class))).thenAnswer(invocation -> {
            Transport transport = invocation.getArgument(0);
            transport.setId(1L);
            return transport;
        });

        // Act
        TransportDTO result = transportService.createTransport(transportDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Bus");
        assertThat(result.getId()).isNotNull();
    }
    
    @Test
    void testCreateTransport_Fails_WhenNameAlreadyExists() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Bus");
        Mockito.when(transportRepository.existsByName("Bus")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transportService.createTransport(transportDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("Un transport avec ce nom existe déjà.");
    }

    @Test
    void testUpdateTransport_Success() {
        // Arrange
        TransportDTO transportDTO = new TransportDTO(null, "Car");
        Transport existingTransport = new Transport();
        existingTransport.setId(1L);
        existingTransport.setName("Bus");
        Mockito.when(transportRepository.findById(1L)).thenReturn(Optional.of(existingTransport));
        Mockito.when(transportRepository.save(any(Transport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TransportDTO result = transportService.updateTransport(1L, transportDTO);

        // Assert
        assertThat(result.getName()).isEqualTo("Car");
    }

    @Test
    void testDeleteTransport_Success() {
        // Arrange
        Mockito.when(transportRepository.existsById(1L)).thenReturn(true);

        // Act
        transportService.deleteTransport(1L);

        // Assert
        Mockito.verify(transportRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTransport_Fails_WhenNotFound() {
        // Arrange
        Mockito.when(transportRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            transportService.deleteTransport(1L);
        });
    }
}
