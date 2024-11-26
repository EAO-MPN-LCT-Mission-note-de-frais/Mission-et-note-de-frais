package com.diginamic.mission_note_de_frais.model.entity;

import com.diginamic.mission_note_de_frais.model.mapper.Mappable;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * Entity class representing a mission.
 */
@Entity(name = "missions")
public class Mission implements Mappable<Mission, MissionDTO> {
  /**
   * The unique identifier for the mission.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  /**
   * The date the mission started.
   */
  @Column(name = "start_date")
  private LocalDate startDate;

  /**
   * The date the mission ended.
   */
  @Column(name = "end_date")
  private LocalDate endDate;

  /**
   * The town where the mission started.
   */
  @Column(name = "start_town")
  private String startTown;

  /**
   * The town where the mission ended.
   */
  @Column(name = "end_town")
  private String endTown;

  /**
   * The current status of the mission.
   */
  @ManyToOne()
  @JoinColumn(name = "status_id")
  Status status;

  @Override
  public MissionDTO map(Function<? super Mission, ? extends MissionDTO> mapper) {
    return mapper.apply(this);
  }

  @Override
  public String toString() {
    return "Mission{" +
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
   * Gets the town where the mission started.
   *
   * @return the town where the mission started
   */
  public String getStartTown() {
    return startTown;
  }

  /**
   * Sets the town where the mission started.
   *
   * @param startTown the town where the mission started
   */
  public void setStartTown(String startTown) {
    this.startTown = startTown;
  }

  /**
   * Gets the town where the mission ended.
   *
   * @return the town where the mission ended
   */
  public String getEndTown() {
    return endTown;
  }

  /**
   * Sets the town where the mission ended.
   *
   * @param endTown the town where the mission ended
   */
  public void setEndTown(String endTown) {
    this.endTown = endTown;
  }

  /**
   * Gets the status of the mission.
   *
   * @return the status of the mission
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets the status of the mission.
   *
   * @param status the status of the mission
   */
  public void setStatus(Status status) {
    this.status = status;
  }
}
