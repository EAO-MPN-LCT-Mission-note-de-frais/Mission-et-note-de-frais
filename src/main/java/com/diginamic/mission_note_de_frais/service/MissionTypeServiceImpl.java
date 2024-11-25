package com.diginamic.mission_note_de_frais.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.repository.MissionTypeRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implémentation du service pour la gestion des natures de mission.
 * <p>
 * Cette classe fournit des méthodes pour récupérer, créer, mettre à jour et
 * supprimer des natures de mission.
 */
@Service
public class MissionTypeServiceImpl implements MissionTypeService {

	@Autowired
	private MissionTypeRepository missionTypeRepository;

	/**
	 * Récupère toutes les natures de mission enregistrées.
	 *
	 * @return une liste de {@link MissionTypeDTO} représentant les natures de
	 *         mission
	 */
	@Override
	public List<MissionTypeDTO> getAllMissionTypes() {
		return missionTypeRepository.findAll().stream().map(this::mapToDTO).toList();
	}

	/**
	 * Crée une nouvelle nature de mission.
	 * <p>
	 * La nature de mission est considérée comme active à partir de la date
	 * actuelle. Des contrôles sont effectués pour s'assurer qu'aucune autre nature
	 * active avec le même libellé n'existe, que les règles métier pour
	 * {@code bonusPercentage} et {@code averageDailyRate} sont respectées.
	 *
	 * @param missionTypeDTO les données de la nature de mission à créer
	 * @return un {@link MissionTypeDTO} représentant la nature de mission créée
	 * @throws IllegalArgumentException si une nature active avec le même libellé
	 *                                  existe déjà ou si les données fournies
	 *                                  violent les règles métier
	 */
	@Override
	public MissionTypeDTO createMissionType(MissionTypeDTO missionTypeDTO) {
		// Vérifier si une nature active avec le même libellé existe déjà
		if (missionTypeRepository.findByLabelAndEndDateIsNull(missionTypeDTO.getLabel()).isPresent()) {
			throw new IllegalArgumentException("Une nature active avec ce libellé existe déjà.");
		}

		// Validation de la règle métier pour bonusPercentage
		if (missionTypeDTO.getIsBonus() != null && missionTypeDTO.getIsBonus()) {
			if (missionTypeDTO.getBonusPercentage() == null || missionTypeDTO.getBonusPercentage() <= 0) {
				throw new IllegalArgumentException(
						"Le pourcentage de prime (bonusPercentage) est obligatoire et doit être supérieur à 0 si isBonus est vrai.");
			}
		} else if (missionTypeDTO.getBonusPercentage() != null) {
			throw new IllegalArgumentException(
					"Le champ bonusPercentage ne doit pas être renseigné si isBonus est false.");
		}

		// Validation de la règle métier pour averageDailyRate
		if (missionTypeDTO.getIsCharged() != null && missionTypeDTO.getIsCharged()) {
			if (missionTypeDTO.getAverageDailyRate() == null || missionTypeDTO.getAverageDailyRate() <= 0) {
				throw new IllegalArgumentException(
						"Le tarif journalier moyen (averageDailyRate) est obligatoire et doit être supérieur à 0 si isCharged est vrai.");
			}
		} else if (missionTypeDTO.getAverageDailyRate() != null) {
			throw new IllegalArgumentException(
					"Le champ averageDailyRate ne doit pas être renseigné si isCharged est false.");
		}

		// Mapper le DTO en entité et définir la date de début
		MissionType missionType = mapToEntity(missionTypeDTO);
		missionType.setStartDate(LocalDate.now());

		// Sauvegarder et retourner le DTO
		missionTypeRepository.save(missionType);
		return mapToDTO(missionType);
	}

	/**
	 * Met à jour une nature de mission.
	 * <p>
	 * Si la nature de mission est active (non expirée), elle est marquée comme
	 * expirée (avec une date de fin). Une nouvelle version est créée avec les
	 * nouvelles données fournies. Des contrôles sont effectués pour s'assurer que
	 * les règles métier pour {@code bonusPercentage} et {@code averageDailyRate}
	 * sont respectées.
	 *
	 * @param id             l'identifiant de la nature de mission à mettre à jour
	 * @param missionTypeDTO les nouvelles données pour la nature de mission
	 * @return un {@link MissionTypeDTO} représentant la nouvelle nature de mission
	 * @throws EntityNotFoundException  si aucune nature de mission avec
	 *                                  l'identifiant donné n'est trouvée
	 * @throws IllegalArgumentException si la nature de mission est déjà expirée ou
	 *                                  si les données fournies violent les règles
	 *                                  métier
	 */
	@Override
	public MissionTypeDTO updateMissionType(Long id, MissionTypeDTO missionTypeDTO) {
		MissionType missionType = missionTypeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

		// Validation de la règle métier pour bonusPercentage
		if (missionTypeDTO.getIsBonus() != null && missionTypeDTO.getIsBonus()) {
			if (missionTypeDTO.getBonusPercentage() == null || missionTypeDTO.getBonusPercentage() <= 0) {
				throw new IllegalArgumentException(
						"Le pourcentage de prime (bonusPercentage) est obligatoire et doit être supérieur à 0 si isBonus est vrai.");
			}
		} else if (missionTypeDTO.getBonusPercentage() != null) {
			throw new IllegalArgumentException(
					"Le champ bonusPercentage ne doit pas être renseigné si isBonus est false.");
		}

		// Validation de la règle métier pour averageDailyRate
		if (missionTypeDTO.getIsCharged() != null && missionTypeDTO.getIsCharged()) {
			if (missionTypeDTO.getAverageDailyRate() == null || missionTypeDTO.getAverageDailyRate() <= 0) {
				throw new IllegalArgumentException(
						"Le tarif journalier moyen (averageDailyRate) est obligatoire et doit être supérieur à 0 si isCharged est vrai.");
			}
		} else if (missionTypeDTO.getAverageDailyRate() != null) {
			throw new IllegalArgumentException(
					"Le champ averageDailyRate ne doit pas être renseigné si isCharged est false.");
		}

		if (missionType.getEndDate() == null) {
			// Marquer la nature existante comme expirée
			missionType.setEndDate(LocalDate.now());
			missionTypeRepository.save(missionType);

			// Créer une nouvelle version
			MissionType newMissionType = mapToEntity(missionTypeDTO);
			newMissionType.setStartDate(LocalDate.now().plusDays(1));
			missionTypeRepository.save(newMissionType);

			return mapToDTO(newMissionType);
		} else {
			throw new IllegalArgumentException("Impossible de modifier une nature expirée.");
		}
	}

	/**
	 * Supprime une nature de mission.
	 * <p>
	 * Si la nature de mission est active (non expirée), elle est marquée comme
	 * expirée avec une date de fin. Sinon, elle est complètement supprimée de la
	 * base de données.
	 *
	 * @param id l'identifiant de la nature de mission à supprimer
	 * @throws EntityNotFoundException si aucune nature de mission avec
	 *                                 l'identifiant donné n'est trouvée
	 */
	@Override
	public void deleteMissionType(Long id) {
		MissionType missionType = missionTypeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

		if (missionType.getEndDate() == null) {
			missionType.setEndDate(LocalDate.now());
			missionTypeRepository.save(missionType);
		} else {
			missionTypeRepository.delete(missionType);
		}
	}

	/**
	 * Convertit une entité {@link MissionType} en DTO {@link MissionTypeDTO}.
	 *
	 * @param missionType l'entité à convertir
	 * @return le DTO correspondant
	 */
	private MissionTypeDTO mapToDTO(MissionType missionType) {
		return new MissionTypeDTO(missionType.getId(), missionType.getLabel(), missionType.getIsCharged(),
				missionType.getIsBonus(), missionType.getAverageDailyRate(), missionType.getBonusPercentage(),
				missionType.getStartDate(), missionType.getEndDate());
	}

	/**
	 * Convertit un DTO {@link MissionTypeDTO} en entité {@link MissionType}.
	 *
	 * @param missionTypeDTO le DTO à convertir
	 * @return l'entité correspondante
	 */
	private MissionType mapToEntity(MissionTypeDTO missionTypeDTO) {
		MissionType missionType = new MissionType();
		missionType.setLabel(missionTypeDTO.getLabel());
		missionType.setIsCharged(missionTypeDTO.getIsCharged());
		missionType.setIsBonus(missionTypeDTO.getIsBonus());
		missionType.setAverageDailyRate(missionTypeDTO.getAverageDailyRate());
		missionType.setBonusPercentage(missionTypeDTO.getBonusPercentage());
		return missionType;
	}
}
