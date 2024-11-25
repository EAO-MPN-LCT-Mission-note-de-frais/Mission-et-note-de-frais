package com.diginamic.mission_note_de_frais.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Entity class representing user credentials.
 */
@Entity(name = "credentials")
@EntityListeners(AuditingEntityListener.class)
public class Credential {
  /**
   * The unique identifier for the mission.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  /**
   * The password associated with the credential.
   */
  @Column(name = "password")
  private String password;

  /**
   * The user associated with the credential.
   */
  @OneToOne()
  @JoinColumn(name = "user_id")
  private User user;

  /**
   * The timestamp when the credential was created.
   */
  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  /**
   * The timestamp when the credential was last updated.
   */
  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  /**
   * Gets the ID of the credential.
   *
   * @return the ID of the credential
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the ID of the credential.
   *
   * @param id the ID to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Gets the password associated with the credential.
   *
   * @return the password associated with the credential
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password associated with the credential.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the user associated with the credential.
   *
   * @return the user associated with the credential
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with the credential.
   *
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the timestamp when the credential was created.
   *
   * @return the timestamp when the credential was created
   */
  public Instant getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the timestamp when the credential was created.
   *
   * @param createdAt the timestamp to set
   */
  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Gets the timestamp when the credential was last updated.
   *
   * @return the timestamp when the credential was last updated
   */
  public Instant getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the timestamp when the credential was last updated.
   *
   * @param updatedAt the timestamp to set
   */
  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
