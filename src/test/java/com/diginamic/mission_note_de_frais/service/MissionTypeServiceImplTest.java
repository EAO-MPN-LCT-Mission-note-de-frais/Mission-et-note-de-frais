package com.diginamic.mission_note_de_frais.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.repository.MissionTypeRepository;

class MissionTypeServiceImplTest {

    @Mock
    private MissionTypeRepository missionTypeRepository;

    @InjectMocks
    private MissionTypeServiceImpl missionTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMissionType_Success() {
        // Arrange
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO(null, "Mission 1", true, true, 500.0, 10.0, null, null);
        MissionType missionType = new MissionType();
        missionType.setLabel("Mission 1");
        missionType.setIsCharged(true);
        missionType.setIsBonus(true);
        missionType.setAverageDailyRate(500.0);
        missionType.setBonusPercentage(10.0);
        missionType.setStartDate(LocalDate.now());

        when(missionTypeRepository.findByLabelAndEndDateIsNull("Mission 1")).thenReturn(Optional.empty());
        when(missionTypeRepository.save(any(MissionType.class))).thenReturn(missionType);

        // Act
        MissionTypeDTO result = missionTypeService.createMissionType(missionTypeDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLabel()).isEqualTo("Mission 1");
        verify(missionTypeRepository, times(1)).save(any(MissionType.class));
    }

    @Test
    void testCreateMissionType_ThrowsException_WhenLabelExists() {
        // Arrange
        MissionTypeDTO missionTypeDTO = new MissionTypeDTO(null, "Mission 1", true, true, 500.0, 10.0, null, null);
        MissionType existingMissionType = new MissionType();
        existingMissionType.setLabel("Mission 1");

        when(missionTypeRepository.findByLabelAndEndDateIsNull("Mission 1")).thenReturn(Optional.of(existingMissionType));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            missionTypeService.createMissionType(missionTypeDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("Une nature active avec ce libellé existe déjà.");
        verify(missionTypeRepository, never()).save(any(MissionType.class));
    }
}
