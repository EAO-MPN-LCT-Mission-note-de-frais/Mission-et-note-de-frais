package com.diginamic.mission_note_de_frais.model;

import lombok.Getter;

import java.time.Instant;

/**
 * A report is the result or outcome achieved through the execution of an action.
 */
@Getter
public class Report {

    /**
     * The message or information associated with the report.
     */
    private final String message;

    /**
     * The timestamp indicating when the report was generated.
     */
    private final Instant timestamp;

    public Report(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }
}
