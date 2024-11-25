package com.diginamic.mission_note_de_frais.model.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing user information.
 */
@Entity(name = "users")
public class User {
  /**
   * The unique identifier for the mission.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  /**
   * The user's first name.
   */
  @Column(name = "first_name")
  private String firstName;

  /**
   * The user's last name.
   */
  @Column(name = "last_name")
  private String lastName;

  /**
   * The user's email.
   */
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  /**
   * The roles assigned to the user.
   */

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id")
  )
  @Column(name = "role")
  private Set<Role> roles = new HashSet<>();

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", roles=" + roles +
        '}';
  }

  /**
   * Gets the ID of the user.
   *
   * @return the ID of the user
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the ID of the user.
   *
   * @param id the ID of the user
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Gets the first name of the user.
   *
   * @return the first name of the user
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name of the user.
   *
   * @param firstName the first name of the user
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the last name of the user.
   *
   * @return the last name of the user
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name of the user.
   *
   * @param lastName the last name of the user
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the email of the user.
   *
   * @return the email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email the email of the user
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the roles assigned to the user.
   *
   * @return the roles assigned to the user
   */
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * Sets the roles assigned to the user.
   *
   * @param roles the roles assigned to the user
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * Enum representing the roles that can be assigned to a user in the system.
   * Each role defines the level of access and permissions a user has.
   */
  public enum Role {
    /**
     * Represents a collaborator role with standard permissions to create, and update missions.
     */
    COLLABORATOR,

    /**
     * Represents a manager role with permissions to manage and approve missions.
     */
    MANAGER,

    /**
     * Represents an administrator role with the highest level of access and system control.
     */
    ADMINISTRATOR
  }
}
