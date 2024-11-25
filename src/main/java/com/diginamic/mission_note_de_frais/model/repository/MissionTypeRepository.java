package com.diginamic.mission_note_de_frais.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diginamic.mission_note_de_frais.model.entity.MissionType;

/**
 * Interface de gestion des opérations de persistence pour l'entité
 * {@link MissionType}.
 * <p>
 * Hérite de {@link JpaRepository} pour bénéficier des fonctionnalités standards
 * CRUD. Fournit également des méthodes personnalisées pour des cas d'usage
 * spécifiques liés à la nature de mission.
 */
public interface MissionTypeRepository extends JpaRepository<MissionType, Long> {

	/**
	 * Récupère toutes les natures de mission dont la date de fin est nulle.
	 * <p>
	 * Cette méthode identifie les natures de mission actuellement actives.
	 *
	 * @return une liste de {@link MissionType} actives, ou une liste vide si aucune
	 *         nature active n'est trouvée.
	 */
	List<MissionType> findByEndDateIsNull();

	/**
	 * Récupère une nature de mission par son libellé, uniquement si elle est
	 * active.
	 * <p>
	 * Cette méthode vérifie que la date de fin est nulle, ce qui indique une nature
	 * active.
	 *
	 * @param label le libellé de la nature de mission recherchée.
	 * @return un {@link Optional} contenant la nature de mission si elle est
	 *         trouvée et active, ou un {@link Optional#empty()} si aucune nature
	 *         correspondante n'est trouvée.
	 */
	Optional<MissionType> findByLabelAndEndDateIsNull(String label);
}
