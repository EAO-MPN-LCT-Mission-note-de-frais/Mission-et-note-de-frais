package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.mapper.MissionTypeMapper;
import com.diginamic.mission_note_de_frais.model.repository.MissionTypeRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissionTypeServiceImplTest {

	@Mock
	private MissionTypeRepository missionTypeRepository;

	@Mock
	private MissionTypeMapper missionTypeMapper;

	@InjectMocks
	private MissionTypeServiceImpl missionTypeService;

	private MissionTypeDTO missionTypeDTO;
	private MissionType missionType;

	@BeforeEach
	void setUp() {
		missionTypeDTO = new MissionTypeDTO(1L, "Type 1", true, true, 500.0, 10.0, LocalDate.now(), null, null);

		missionType = new MissionType();
		missionType.setId(1L);
		missionType.setLabel("Type 1");
		missionType.setIsCharged(true);
		missionType.setIsBonus(true);
		missionType.setAverageDailyRate(500.0);
		missionType.setBonusPercentage(10.0);
		missionType.setStartDate(LocalDate.now());
		missionType.setEndDate(null);
	}

	@Test
	void getAllMissionTypes_shouldReturnList() {
		// Arrange
		when(missionTypeRepository.findAll()).thenReturn(List.of(missionType));
		when(missionTypeMapper.toDTO(any(MissionType.class))).thenReturn(missionTypeDTO);

		// Act
		List<MissionTypeDTO> result = missionTypeService.getAllMissionTypes();

		// Assert
		assertEquals(1, result.size());
		assertEquals("Type 1", result.get(0).getLabel());
		verify(missionTypeRepository).findAll();
		verify(missionTypeMapper).toDTO(any(MissionType.class));
	}

	@Test
	void createMissionType_shouldCreateNewMissionType() {
		// Arrange
		when(missionTypeRepository.findByLabelAndEndDateIsNull(missionTypeDTO.getLabel())).thenReturn(Optional.empty());
		when(missionTypeMapper.toEntity(any(MissionTypeDTO.class))).thenReturn(missionType);
		when(missionTypeRepository.save(any(MissionType.class))).thenReturn(missionType);
		when(missionTypeMapper.toDTO(any(MissionType.class))).thenReturn(missionTypeDTO);

		// Act
		MissionTypeDTO result = missionTypeService.createMissionType(missionTypeDTO);

		// Assert
		assertNotNull(result);
		assertEquals("Type 1", result.getLabel());
		verify(missionTypeRepository).save(any(MissionType.class));
		verify(missionTypeMapper).toEntity(missionTypeDTO);
	}

	@Test
	void createMissionType_shouldThrowExceptionIfLabelExists() {
		// Arrange
		when(missionTypeRepository.findByLabelAndEndDateIsNull(missionTypeDTO.getLabel()))
				.thenReturn(Optional.of(missionType));

		// Act & Assert
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			missionTypeService.createMissionType(missionTypeDTO);
		});
		assertEquals("Une nature active avec ce libellé existe déjà.", exception.getMessage());
	}

	@Test
	void updateMissionType_shouldUpdateMissionType() {
		// Arrange
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.of(missionType));
		when(missionTypeMapper.toEntity(any(MissionTypeDTO.class))).thenReturn(missionType);
		when(missionTypeRepository.save(any(MissionType.class))).thenReturn(missionType);
		when(missionTypeMapper.toDTO(any(MissionType.class))).thenReturn(missionTypeDTO);

		// Act
		MissionTypeDTO result = missionTypeService.updateMissionType(1L, missionTypeDTO);

		// Assert
		assertNotNull(result);
		assertEquals("Type 1", result.getLabel());
		verify(missionTypeRepository, times(2)).save(any(MissionType.class));
		verify(missionTypeRepository).findById(1L);
	}

	@Test
	void updateMissionType_shouldThrowExceptionIfNotFound() {
		// Arrange
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			missionTypeService.updateMissionType(1L, missionTypeDTO);
		});
		assertEquals("Nature de mission non trouvée", exception.getMessage());
	}

	@Test
	void deleteMissionType_shouldDeleteMissionTypeWhenEndDateIsSet() {
		// Arrange
		missionType.setEndDate(LocalDate.now());
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.of(missionType));

		// Act
		missionTypeService.deleteMissionType(1L);

		// Assert
		verify(missionTypeRepository).delete(missionType);
		verify(missionTypeRepository, never()).save(any(MissionType.class)); 
	}

	@Test
	void deleteMissionType_shouldMarkMissionAsExpiredWhenEndDateIsNull() {
		// Arrange
		missionType.setEndDate(null);
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.of(missionType));
		when(missionTypeRepository.save(any(MissionType.class))).thenReturn(missionType);

		// Act
		missionTypeService.deleteMissionType(1L);

		// Assert
		assertNotNull(missionType.getEndDate());
		assertEquals(LocalDate.now(), missionType.getEndDate());
		verify(missionTypeRepository).save(missionType);
		verify(missionTypeRepository, never()).delete(missionType);
	}

	@Test
	void deleteMissionType_shouldThrowExceptionIfNotFound() {
		// Arrange
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			missionTypeService.deleteMissionType(1L);
		});
		assertEquals("Nature de mission non trouvée", exception.getMessage());
	}
}
