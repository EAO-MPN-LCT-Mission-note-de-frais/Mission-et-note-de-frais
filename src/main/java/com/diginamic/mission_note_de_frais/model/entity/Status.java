package com.diginamic.mission_note_de_frais.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class representing mission status.
 */
@Entity(name = "status")
public class Status {
  /**
   * The unique identifier for the mission.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  /**
   * The name of the status.
   */
  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private MissionStatus name;

  /**
   * The short description of the status.
   */
  @Column(name = "description")

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

  /**
   * The enum representing the status.
   */
  public enum MissionStatus {

    /**
     * The mission has just been created.
     */
    INITIALE,

    /**
     * The mission is waiting for validation.
     */
    EN_ATTENTE_VALIDATION,

    /**
     * The mission has been validated.
     */
    VALIDEE,

    /**
     * The mission has been rejected.
     */
    REJETEE,

    /**
     * The mission has been cancelled.
     */
    ANNULER
  }
}
