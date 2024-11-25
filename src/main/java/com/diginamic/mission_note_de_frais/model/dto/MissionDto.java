package com.diginamic.mission_note_de_frais.model.dto;


import java.time.LocalDate;

public class MissionDto {
  /**
   * The unique identifier for the mission.
   */
  private Integer id;

  /**
   * The date the mission started.
   */
  private LocalDate startDate;

  /**
   * The date the mission ended.
   */
  private LocalDate endDate;

  /**
   * The town where the mission started.
   */
  private String startTown;

  /**
   * The town where the mission ended.
   */
  private String endTown;

  /**
   * The current status of the mission.
   */
  private StatusDto status;

  @Override
  public String toString() {
    return "MissionDto{" +
        "id=" + id +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", startTown='" + startTown + '\'' +
        ", endTown='" + endTown + '\'' +
        ", status=" + status +
        '}';
  }

  /**
   * Gets the ID of the mission.
   *
   * @return the ID of the mission
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the ID of the mission.
   *
   * @param id the ID to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Gets the start date of the mission.
   *
   * @return the start date of the mission
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Sets the start date of the mission.
   *
   * @param startDate the start date to set
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Gets the end date of the mission.
   *
   * @return the end date of the mission
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Sets the end date of the mission.
   *
   * @param endDate the end date to set
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Gets the start town of the mission.
   *
   * @return the start town of the mission
   */
  public String getStartTown() {
    return startTown;
  }

  /**
   * Sets the start town of the mission.
   *
   * @param startTown the start town to set
   */
  public void setStartTown(String startTown) {
    this.startTown = startTown;
  }

  /**
   * Gets the end town of the mission.
   *
   * @return the end town of the mission
   */
  public String getEndTown() {
    return endTown;
  }

  /**
   * Sets the end town of the mission.
   *
   * @param endTown the end town to set
   */
  public void setEndTown(String endTown) {
    this.endTown = endTown;
  }

  /**
   * Gets the status of the mission.
   *
   * @return the status of the mission
   */
  public StatusDto getStatus() {
    return status;
  }

  /**
   * Sets the status of the mission.
   *
   * @param status the status to set
   */
  public void setStatus(StatusDto status) {
    this.status = status;
  }
}
