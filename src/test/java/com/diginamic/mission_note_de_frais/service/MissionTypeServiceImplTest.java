package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.mapper.MissionTypeMapper;
import com.diginamic.mission_note_de_frais.model.repository.MissionTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MissionTypeServiceImplTest {

	@Mock
	private MissionTypeRepository missionTypeRepository;

	@Mock
	private MissionTypeMapper missionTypeMapper;

	@InjectMocks
	private MissionTypeServiceImpl missionTypeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllMissionTypes() {
		// Arrange
		MissionType missionType = new MissionType();
		missionType.setId(1L);
		missionType.setLabel("Test");
		when(missionTypeRepository.findAll()).thenReturn(List.of(missionType));
		when(missionTypeMapper.toDTO(missionType))
				.thenReturn(new MissionTypeDTO(1L, "Test", true, false, 500.0, null, null, null));

		// Act
		List<MissionTypeDTO> result = missionTypeService.getAllMissionTypes();

		// Assert
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getLabel()).isEqualTo("Test");
		verify(missionTypeRepository, times(1)).findAll();
	}

	@Test
	void testCreateMissionType_Success() {
		// Arrange
		MissionTypeDTO missionTypeDTO = new MissionTypeDTO(null, "Test", true, false, 500.0, null, null, null);
		MissionType missionType = new MissionType();
		missionType.setLabel("Test");
		when(missionTypeRepository.findByLabelAndEndDateIsNull("Test")).thenReturn(Optional.empty());
		when(missionTypeMapper.toEntity(missionTypeDTO)).thenReturn(missionType);
		when(missionTypeRepository.save(any(MissionType.class))).thenReturn(missionType);
		when(missionTypeMapper.toDTO(missionType)).thenReturn(missionTypeDTO);

		// Act
		MissionTypeDTO result = missionTypeService.createMissionType(missionTypeDTO);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getLabel()).isEqualTo("Test");
		verify(missionTypeRepository, times(1)).save(any(MissionType.class));
	}

	@Test
	void testCreateMissionType_ThrowsException_WhenLabelExists() {
		// Arrange
		MissionTypeDTO missionTypeDTO = new MissionTypeDTO(null, "Test", true, false, 500.0, null, null, null);
		when(missionTypeRepository.findByLabelAndEndDateIsNull("Test")).thenReturn(Optional.of(new MissionType()));

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> missionTypeService.createMissionType(missionTypeDTO));
		verify(missionTypeRepository, never()).save(any(MissionType.class));
	}

	@Test
	void testUpdateMissionType_Success() {
		// Arrange
		MissionTypeDTO missionTypeDTO = new MissionTypeDTO(null, "Updated", true, false, 500.0, null, null, null);
		MissionType existingMissionType = new MissionType();
		existingMissionType.setEndDate(null);
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.of(existingMissionType));
		when(missionTypeMapper.toEntity(missionTypeDTO)).thenReturn(new MissionType());
		when(missionTypeRepository.save(any(MissionType.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(missionTypeMapper.toDTO(any(MissionType.class))).thenReturn(missionTypeDTO);

		// Act
		MissionTypeDTO result = missionTypeService.updateMissionType(1L, missionTypeDTO);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getLabel()).isEqualTo("Updated");
		verify(missionTypeRepository, times(2)).save(any(MissionType.class));
	}

	@Test
	void testUpdateMissionType_ThrowsException_WhenNotFound() {
		// Arrange
		MissionTypeDTO missionTypeDTO = new MissionTypeDTO(null, "Updated", true, false, 500.0, null, null, null);
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> missionTypeService.updateMissionType(1L, missionTypeDTO));
		verify(missionTypeRepository, never()).save(any(MissionType.class));
	}

	@Test
	void testDeleteMissionType_Success_WhenActive() {
		// Arrange: Nature de mission active (non expirée)
		MissionType missionType = new MissionType();
		missionType.setEndDate(null);
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.of(missionType));

		// Act
		missionTypeService.deleteMissionType(1L);

		// Assert: Vérifiez que la nature de mission est marquée comme expirée
		assertThat(missionType.getEndDate()).isNotNull();
		verify(missionTypeRepository, times(1)).save(missionType);
		verify(missionTypeRepository, never()).delete(any(MissionType.class));
	}

	@Test
	void testDeleteMissionType_Success_WhenExpired() {
		// Arrange: Nature de mission expirée
		MissionType missionType = new MissionType();
		missionType.setEndDate(LocalDate.now());
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.of(missionType));

		// Act
		missionTypeService.deleteMissionType(1L);

		// Assert: Vérifiez que la nature de mission est supprimée
		verify(missionTypeRepository, times(1)).delete(missionType);
		verify(missionTypeRepository, never()).save(any(MissionType.class));
	}

	@Test
	void testDeleteMissionType_ThrowsException_WhenNotFound() {
		// Arrange
		when(missionTypeRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> missionTypeService.deleteMissionType(1L));
		verify(missionTypeRepository, never()).delete(any(MissionType.class));
	}
}
