package com.diginamic.mission_note_de_frais.model.dto;

import java.time.LocalDate;

public class MissionTypeDTO {

	private Long id;
	private String label;
	private Boolean isCharged;
	private Boolean isBonus;
	private Float averageDailyRate;
	private Float bonusPercentage;
	private LocalDate startDate;
	private LocalDate endDate;

	public MissionTypeDTO() {
	}

	public MissionTypeDTO(Long id, String label, Boolean isCharged, Boolean isBonus, Float averageDailyRate,
			Float bonusPercentage, LocalDate startDate, LocalDate endDate) {
		super();
		this.id = id;
		this.label = label;
		this.isCharged = isCharged;
		this.isBonus = isBonus;
		this.averageDailyRate = averageDailyRate;
		this.bonusPercentage = bonusPercentage;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
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

}
