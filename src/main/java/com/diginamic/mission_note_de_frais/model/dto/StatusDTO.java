package com.diginamic.mission_note_de_frais.model.dto;


import com.diginamic.mission_note_de_frais.model.entity.Status.MissionStatus;

public class StatusDTO {
  /**
   * The unique identifier for the mission.
   */
  private Integer id;

  /**
   * The name of the status.
   */
  private MissionStatus name;

  /**
   * The short description of the status.
   */
  private String description;


  /**
   * Gets the ID of the status.
   *
   * @return the ID of the status
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the ID of the status.
   *
   * @param id the ID of the status
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Gets the name of the status.
   *
   * @return the name of the status
   */
  public MissionStatus getName() {
    return name;
  }

  /**
   * Sets the name of the status.
   *
   * @param name the name of the status
   */
  public void setName(MissionStatus name) {
    this.name = name;
  }

  /**
   * Gets the description of the status.
   *
   * @return the description of the status
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the status.
   *
   * @param description the description of the status
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
