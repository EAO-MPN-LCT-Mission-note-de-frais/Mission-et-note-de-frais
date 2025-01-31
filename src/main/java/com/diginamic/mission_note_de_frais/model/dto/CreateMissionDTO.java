package com.diginamic.mission_note_de_frais.model.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
public class CreateMissionDTO {
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
     * The transports of the mission
     */
    private Set<Long> transportIds;

    /**
     * The identifier for the MissionType
     */
    private Long missionTypeId;
}
