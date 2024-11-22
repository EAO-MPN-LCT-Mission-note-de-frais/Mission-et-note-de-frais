package com.diginamic.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MissionType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date startDate;
	private Date endDate;
	private String label;
	private Boolean isCharged;
	private Boolean isBonus;
	private Float averageDailyRate;
	private Float bonusPercentage;
	private Float bonusAmount;
	
}
