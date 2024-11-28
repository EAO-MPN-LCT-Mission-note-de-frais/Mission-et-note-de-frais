package com.diginamic.mission_note_de_frais.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant une note de frais contenant des lignes de frais.
 * <p>
 *
 * @author Marjory PRIN
 */
@Entity(name = "EXPENSE_REPORT")
@Data
public class ExpenseReport {

  /**
   * Identifiant unique de la note de frais
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Liste des lignes de frais associées
   */
  @NonNull
  @OneToMany(mappedBy = "expenseReport", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Expense> expenses = new ArrayList<>();

  /**
   * Mission rattachée à la note de frais
   */
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "mission_id")
  private Mission mission;

  /**
   * Statut de la note de frais
   */
  @ManyToOne
  @JoinColumn(name = "status_id")
  Status status;
}
