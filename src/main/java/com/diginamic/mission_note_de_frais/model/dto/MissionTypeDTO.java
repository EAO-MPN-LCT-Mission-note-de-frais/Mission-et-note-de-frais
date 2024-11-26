package com.diginamic.mission_note_de_frais.model.dto;

import java.time.LocalDate;

/**
 * Représente un Data Transfer Object (DTO) pour la nature de mission. Utilisé
 * pour transférer les données entre les couches sans exposer directement
 * l'entité.
 */
public class MissionTypeDTO {

	/**
	 * Identifiant unique de la nature de mission.
	 */
	private Long id;

	/**
	 * Nom de la nature de mission.
	 */
	private String label;

	/**
	 * Indique si la nature de mission est facturée.
	 */
	private Boolean isCharged;

	/**
	 * Indique si la nature de mission inclut une prime.
	 */
	private Boolean isBonus;

	/**
	 * Taux Journalier Moyen (TJM) de la nature de mission.
	 */
	private Double averageDailyRate;

	/**
	 * Pourcentage de la prime de la nature de mission.
	 */
	private Double bonusPercentage;

	/**
	 * Date de début de validité de la nature de mission.
	 */
	private LocalDate startDate;

	/**
	 * Date de fin de validité de la nature de mission.
	 */
	private LocalDate endDate;

	/**
	 * Constructeur par défaut.
	 */
	public MissionTypeDTO() {
	}

	/**
	 * Constructeur avec paramètres.
	 *
	 * @param id               l'identifiant unique de la nature de mission
	 * @param label            le nom de la nature de mission
	 * @param isCharged        indique si la nature de mission est facturée
	 * @param isBonus          indique si la nature de mission inclut une prime
	 * @param averageDailyRate le TJM de la nature de mission
	 * @param bonusPercentage  le pourcentage de la prime
	 * @param startDate        la date de début de validité
	 * @param endDate          la date de fin de validité
	 */
	public MissionTypeDTO(Long id, String label, Boolean isCharged, Boolean isBonus, Double averageDailyRate,
			Double bonusPercentage, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.label = label;
		this.isCharged = isCharged;
		this.isBonus = isBonus;
		this.averageDailyRate = averageDailyRate;
		this.bonusPercentage = bonusPercentage;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return l'identifiant unique de la nature de mission
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return le nom de la nature de mission
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label le nom de la nature de mission
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return vrai si la nature de mission est facturée
	 */
	public Boolean getIsCharged() {
		return isCharged;
	}

	/**
	 * @param isCharged indique si la nature de mission est facturée
	 */
	public void setIsCharged(Boolean isCharged) {
		this.isCharged = isCharged;
	}

	/**
	 * @return vrai si la nature de mission inclut une prime
	 */
	public Boolean getIsBonus() {
		return isBonus;
	}

	/**
	 * @param isBonus indique si la nature de mission inclut une prime
	 */
	public void setIsBonus(Boolean isBonus) {
		this.isBonus = isBonus;
	}

	/**
	 * @return le TJM de la nature de mission
	 */
	public Double getAverageDailyRate() {
		return averageDailyRate;
	}

	/**
	 * @param averageDailyRate le TJM de la nature de mission
	 */
	public void setAverageDailyRate(Double averageDailyRate) {
		this.averageDailyRate = averageDailyRate;
	}

	/**
	 * @return le pourcentage de la prime de la nature de mission
	 */
	public Double getBonusPercentage() {
		return bonusPercentage;
	}

	/**
	 * @param bonusPercentage le pourcentage de la prime de la nature de mission
	 */
	public void setBonusPercentage(Double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}

	/**
	 * @return la date de début de validité de la nature de mission
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate la date de début de validité de la nature de mission
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return la date de fin de validité de la nature de mission
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate la date de fin de validité de la nature de mission
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
