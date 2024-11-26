package com.diginamic.mission_note_de_frais.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Représente une entité "Nature de Mission" utilisée pour définir les
 * caractéristiques d'une mission.
 * <p>
 * Cette entité est persistée dans la base de données.
 */
@Entity
public class MissionType {

	/**
	 * Identifiant unique de la nature de mission, généré automatiquement.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Date de début de validité de la nature de mission.
	 * <p>
	 * Ce champ est obligatoire.
	 */
	@Column(nullable = false)
	private LocalDate startDate;

	/**
	 * Date de fin de validité de la nature de mission.
	 * <p>
	 * Ce champ est optionnel, indiquant une validité illimitée si absent.
	 */
	private LocalDate endDate;

	/**
	 * Nom ou libellé de la nature de mission.
	 * <p>
	 * Ce champ est obligatoire et doit être unique.
	 */
	@Column(nullable = false, unique = true)
	private String label;

	/**
	 * Indique si la nature de mission est facturable.
	 * <p>
	 * Ce champ est obligatoire.
	 */
	@Column(nullable = false)
	private Boolean isCharged;

	/**
	 * Indique si la nature de mission inclut une prime.
	 * <p>
	 * Ce champ est obligatoire.
	 */
	@Column(nullable = false)
	private Boolean isBonus;

	/**
	 * Taux Journalier Moyen (TJM) associé à la nature de mission.
	 * <p>
	 * Ce champ est obligatoire uniquement si {@code isCharged} est vrai.
	 */
	private Double averageDailyRate;

	/**
	 * Pourcentage de prime applicable à la nature de mission.
	 * <p>
	 * Ce champ est obligatoire uniquement si {@code isBonus} est vrai.
	 */
	private Double bonusPercentage;

	/**
	 * Montant de la prime associé à la nature de mission.
	 * <p>
	 * Ce champ est optionnel et peut être utilisé pour des calculs futurs.
	 */
	private Double bonusAmount;

	// Getters et setters

	/**
	 * Retourne l'identifiant unique de la nature de mission.
	 *
	 * @return l'identifiant de la nature de mission
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Définit l'identifiant unique de la nature de mission.
	 *
	 * @param id l'identifiant de la nature de mission
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retourne la date de début de validité de la nature de mission.
	 *
	 * @return la date de début de validité
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Définit la date de début de validité de la nature de mission.
	 *
	 * @param startDate la date de début de validité
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Retourne la date de fin de validité de la nature de mission.
	 *
	 * @return la date de fin de validité ou {@code null} si illimitée
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * Définit la date de fin de validité de la nature de mission.
	 *
	 * @param endDate la date de fin de validité
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * Retourne le libellé ou nom de la nature de mission.
	 *
	 * @return le libellé de la nature de mission
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Définit le libellé ou nom de la nature de mission.
	 *
	 * @param label le libellé de la nature de mission
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Indique si la nature de mission est facturable.
	 *
	 * @return {@code true} si la mission est facturable, sinon {@code false}
	 */
	public Boolean getIsCharged() {
		return isCharged;
	}

	/**
	 * Définit si la nature de mission est facturable.
	 *
	 * @param isCharged {@code true} si la mission est facturable, sinon
	 *                  {@code false}
	 */
	public void setIsCharged(Boolean isCharged) {
		this.isCharged = isCharged;
	}

	/**
	 * Indique si la nature de mission inclut une prime.
	 *
	 * @return {@code true} si une prime est incluse, sinon {@code false}
	 */
	public Boolean getIsBonus() {
		return isBonus;
	}

	/**
	 * Définit si la nature de mission inclut une prime.
	 *
	 * @param isBonus {@code true} si une prime est incluse, sinon {@code false}
	 */
	public void setIsBonus(Boolean isBonus) {
		this.isBonus = isBonus;
	}

	/**
	 * Retourne le Taux Journalier Moyen (TJM) de la nature de mission.
	 *
	 * @return le TJM de la mission ou {@code null} si non applicable
	 */
	public Double getAverageDailyRate() {
		return averageDailyRate;
	}

	/**
	 * Définit le Taux Journalier Moyen (TJM) de la nature de mission.
	 *
	 * @param averageDailyRate le TJM de la mission
	 */
	public void setAverageDailyRate(Double averageDailyRate) {
		this.averageDailyRate = averageDailyRate;
	}

	/**
	 * Retourne le pourcentage de prime de la nature de mission.
	 *
	 * @return le pourcentage de prime ou {@code null} si non applicable
	 */
	public Double getBonusPercentage() {
		return bonusPercentage;
	}

	/**
	 * Définit le pourcentage de prime de la nature de mission.
	 *
	 * @param bonusPercentage le pourcentage de prime
	 */
	public void setBonusPercentage(Double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}

	/**
	 * Retourne le montant de la prime associé à la nature de mission.
	 *
	 * @return le montant de la prime ou {@code null} si non défini
	 */
	public Double getBonusAmount() {
		return bonusAmount;
	}

	/**
	 * Définit le montant de la prime associé à la nature de mission.
	 *
	 * @param bonusAmount le montant de la prime
	 */
	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
}
