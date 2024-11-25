package com.diginamic.mission_note_de_frais.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MissionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate startDate;

	private LocalDate endDate;

	@Column(nullable = false, unique = true)
	private String label;

	@Column(nullable = false)
	private Boolean isCharged;

	@Column(nullable = false)
	private Boolean isBonus;

	private Float averageDailyRate; // Nullable only if isCharged is false

	private Float bonusPercentage; // Nullable only if isBonus is false

	private Float bonusAmount; // Optionnel pour des calculs futurs

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getIsCharged() {
		return isCharged;
	}

	public void setIsCharged(Boolean isCharged) {
		this.isCharged = isCharged;
	}

	public Boolean getIsBonus() {
		return isBonus;
	}

	public void setIsBonus(Boolean isBonus) {
		this.isBonus = isBonus;
	}

	public Float getAverageDailyRate() {
		return averageDailyRate;
	}

	public void setAverageDailyRate(Float averageDailyRate) {
		this.averageDailyRate = averageDailyRate;
	}

	public Float getBonusPercentage() {
		return bonusPercentage;
	}

	public void setBonusPercentage(Float bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}

	public Float getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Float bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

}
