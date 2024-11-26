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
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * Entity class representing a mission.
 */
@Entity(name = "missions")
@Data
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

  /**
   * The expense report associated with the mission.
   */
  @OneToOne(mappedBy = "mission")
  private ExpenseReport expenseReport;

  @Override
  public MissionDTO map(Function<? super Mission, ? extends MissionDTO> mapper) {
    return mapper.apply(this);
  }
}
