package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.MissionTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.MissionType;
import com.diginamic.mission_note_de_frais.model.mapper.MissionTypeMapper;
import com.diginamic.mission_note_de_frais.model.repository.MissionTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implémentation du service pour la gestion des natures de mission.
 * <p>
 * Cette classe gère la logique métier associée aux natures de mission
 * (MissionType) et fournit des méthodes pour :
 * <ul>
 *   <li>Récupérer toutes les natures de mission</li>
 *   <li>Créer une nouvelle nature de mission</li>
 *   <li>Mettre à jour une nature de mission existante</li>
 *   <li>Supprimer une nature de mission</li>
 * </ul>
 * Elle utilise le {@link MissionTypeRepository} pour accéder aux données
 * persistantes et le {@link MissionTypeMapper} pour convertir entre
 * entités et DTOs.
 */
@Service
public class MissionTypeServiceImpl implements MissionTypeService {

    private final MissionTypeRepository missionTypeRepository;
    private final MissionTypeMapper missionTypeMapper;

    /**
     * Constructeur de {@link MissionTypeServiceImpl}.
     *
     * @param missionTypeRepository le repository pour interagir avec les données des natures de mission
     * @param missionTypeMapper      le mapper pour convertir les entités en DTOs et vice-versa
     */
    public MissionTypeServiceImpl(MissionTypeRepository missionTypeRepository, MissionTypeMapper missionTypeMapper) {
        this.missionTypeRepository = missionTypeRepository;
        this.missionTypeMapper = missionTypeMapper;
    }

    /**
     * Récupère toutes les natures de mission enregistrées.
     *
     * @return une liste de {@link MissionTypeDTO} représentant toutes les natures de mission
     */
    @Override
    public List<MissionTypeDTO> getAllMissionTypes() {
        return missionTypeRepository.findAll()
                .stream()
                .map(missionTypeMapper::toDTO)
                .toList();
    }

    /**
     * Crée une nouvelle nature de mission.
     * <p>
     * La nature de mission est créée avec une date de début correspondant à la
     * date actuelle. Avant la création, des contrôles métier sont effectués pour :
     * <ul>
     *   <li>Vérifier qu'aucune autre nature active avec le même libellé n'existe</li>
     *   <li>Valider les champs de la nature de mission selon les règles métier</li>
     * </ul>
     *
     * @param missionTypeDTO les données de la nature de mission à créer
     * @return un {@link MissionTypeDTO} représentant la nature de mission créée
     * @throws IllegalArgumentException si les règles métier ne sont pas respectées ou si une nature
     *                                  active avec le même libellé existe déjà
     */
    @Override
    public MissionTypeDTO createMissionType(MissionTypeDTO missionTypeDTO) {
        if (missionTypeRepository.findByLabelAndEndDateIsNull(missionTypeDTO.getLabel()).isPresent()) {
            throw new IllegalArgumentException("Une nature active avec ce libellé existe déjà.");
        }

        validateMissionTypeDTO(missionTypeDTO);

        MissionType missionType = missionTypeMapper.toEntity(missionTypeDTO);
        missionType.setStartDate(LocalDate.now());

        return missionTypeMapper.toDTO(missionTypeRepository.save(missionType));
    }

    /**
     * Met à jour une nature de mission existante.
     * <p>
     * Si la nature de mission est active (non expirée), elle est marquée comme
     * expirée (en ajoutant une date de fin) et une nouvelle version est créée
     * avec les données mises à jour. Avant la mise à jour, des contrôles métier
     * sont effectués.
     *
     * @param id             l'identifiant de la nature de mission à mettre à jour
     * @param missionTypeDTO les nouvelles données de la nature de mission
     * @return un {@link MissionTypeDTO} représentant la nouvelle nature de mission
     * @throws EntityNotFoundException  si aucune nature de mission avec l'identifiant donné n'est trouvée
     * @throws IllegalArgumentException si les règles métier ne sont pas respectées ou si la nature
     *                                  de mission est déjà expirée
     */
    @Override
    public MissionTypeDTO updateMissionType(Long id, MissionTypeDTO missionTypeDTO) {
        MissionType missionType = missionTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

        validateMissionTypeDTO(missionTypeDTO);

        // 🛠️ Si `isCharged` est false, on met `averageDailyRate` à null
        if (!missionTypeDTO.getIsCharged()) {
            missionTypeDTO.setAverageDailyRate(null);
        }

        // 🛠️ Si `isBonus` est false, on met `bonusPercentage` à null
        if (!missionTypeDTO.getIsBonus()) {
            missionTypeDTO.setBonusPercentage(null);
        }

        if (missionType.getEndDate() == null) {
            missionType.setEndDate(LocalDate.now());
            missionTypeRepository.save(missionType);

            MissionType newMissionType = missionTypeMapper.toEntity(missionTypeDTO);
            newMissionType.setStartDate(LocalDate.now().plusDays(1));
            return missionTypeMapper.toDTO(missionTypeRepository.save(newMissionType));
        } else {
            throw new IllegalArgumentException("Impossible de modifier une nature expirée.");
        }
    }

    /**
     * Echue une nature de mission existante.
     * <p>
     * Si la nature de mission est active (non expirée), elle est marquée comme
     * expirée (en ajoutant une date de fin). 
     * Avant la mise à jour, des contrôles métier sont effectués.
     *
     * @param id             l'identifiant de la nature de mission à mettre à jour
     * @param missionTypeDTO les nouvelles données de la nature de mission
     * @return un {@link MissionTypeDTO} représentant la nature de mission expiré
     * @throws EntityNotFoundException  si aucune nature de mission avec l'identifiant donné n'est trouvée
     * @throws IllegalArgumentException si les règles métier ne sont pas respectées ou si la nature
     *                                  de mission est déjà expirée
     */
    @Override
    public MissionTypeDTO fadeMissionType(Long id, MissionTypeDTO missionTypeDTO) {
        MissionType missionType = missionTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

        validateMissionTypeDTO(missionTypeDTO);

        if (missionType.getEndDate() == null) {
            missionType.setEndDate(LocalDate.now());
            missionTypeRepository.save(missionType);

            return missionTypeMapper.toDTO(missionTypeRepository.save(missionType));
                
        } else {
            throw new IllegalArgumentException("Impossible de modifier une nature expirée.");
        }
    }

    /**
     * Supprime une nature de mission.
     * <p>
     * Si la nature de mission est active (non expirée), elle est marquée comme
     * expirée (en ajoutant une date de fin). Sinon, elle est complètement supprimée
     * de la base de données.
     *
     * @param id l'identifiant de la nature de mission à supprimer
     * @throws EntityNotFoundException si aucune nature de mission avec l'identifiant donné n'est trouvée
     */
    @Override
    public void deleteMissionType(Long id) {
        MissionType missionType = missionTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nature de mission non trouvée"));

        // Si la nature de mission n'est pas encore échue, on met à jour la date de fin
        if (missionType.getEndDate() == null) {
            missionType.setEndDate(LocalDate.now());
            missionTypeRepository.save(missionType);
            return;
        }

        // Vérifier si des missions sont encore liées
        if (!missionType.getMissions().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer cette nature de mission car des missions lui sont encore rattachées.");
        }

        // Suppression de la nature de mission si elle n'a plus de missions associées
        missionTypeRepository.delete(missionType);
    }


    /**
     * Valide les règles métier d'un {@link MissionTypeDTO}.
     * <p>
     * Cette méthode effectue les validations suivantes :
     * <ul>
     *   <li>Si {@code isBonus} est vrai, {@code bonusPercentage} doit être renseigné et supérieur à 0</li>
     *   <li>Si {@code isBonus} est faux, {@code bonusPercentage} ne doit pas être renseigné</li>
     *   <li>Si {@code isCharged} est vrai, {@code averageDailyRate} doit être renseigné et supérieur à 0</li>
     *   <li>Si {@code isCharged} est faux, {@code averageDailyRate} ne doit pas être renseigné</li>
     * </ul>
     *
     * @param missionTypeDTO le DTO à valider
     * @throws IllegalArgumentException si les règles métier ne sont pas respectées
     */
    private void validateMissionTypeDTO(MissionTypeDTO missionTypeDTO) {
        if (Boolean.TRUE.equals(missionTypeDTO.getIsBonus()) &&
                (missionTypeDTO.getBonusPercentage() == null || missionTypeDTO.getBonusPercentage() <= 0)) {
            throw new IllegalArgumentException(
                    "Le pourcentage de prime (bonusPercentage) est obligatoire et doit être supérieur à 0 si isBonus est vrai.");
        }

        if (Boolean.FALSE.equals(missionTypeDTO.getIsBonus()) && missionTypeDTO.getBonusPercentage() != null) {
            throw new IllegalArgumentException(
                    "Le champ bonusPercentage ne doit pas être renseigné si isBonus est false.");
        }

        if (Boolean.TRUE.equals(missionTypeDTO.getIsCharged()) &&
                (missionTypeDTO.getAverageDailyRate() == null || missionTypeDTO.getAverageDailyRate() <= 0)) {
            throw new IllegalArgumentException(
                    "Le tarif journalier moyen (averageDailyRate) est obligatoire et doit être supérieur à 0 si isCharged est vrai.");
        }

        if (Boolean.FALSE.equals(missionTypeDTO.getIsCharged()) && missionTypeDTO.getAverageDailyRate() != null) {
            throw new IllegalArgumentException(
                    "Le champ averageDailyRate ne doit pas être renseigné si isCharged est false.");
        }
    }
}