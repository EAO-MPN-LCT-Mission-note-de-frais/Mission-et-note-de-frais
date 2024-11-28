package com.diginamic.mission_note_de_frais.model.dto;

/**
 * Data Transfer Object (DTO) représentant un moyen de transport.
 * <p>
 * Ce DTO est utilisé pour transférer les données d'un moyen de transport entre
 * les couches de l'application, généralement entre le contrôleur et le service
 * ou entre le service et la couche d'accès aux données.
 */
public class TransportDTO {

	/**
	 * Identifiant unique du transport.
	 */
	private Long id;

	/**
	 * Nom du moyen de transport.
	 */
	private String name;

	/**
	 * Constructeur par défaut.
	 */
	public TransportDTO() {
	}

	/**
	 * Constructeur avec paramètres pour initialiser les attributs du DTO.
	 *
	 * @param id   l'identifiant unique du transport
	 * @param name le nom du moyen de transport
	 */
	public TransportDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Récupère l'identifiant unique du transport.
	 *
	 * @return l'identifiant unique du transport
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Définit l'identifiant unique du transport.
	 *
	 * @param id l'identifiant unique à définir
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Récupère le nom du moyen de transport.
	 *
	 * @return le nom du transport
	 */
	public String getName() {
		return name;
	}

	/**
	 * Définit le nom du moyen de transport.
	 *
	 * @param name le nom à définir
	 */
	public void setName(String name) {
		this.name = name;
	}

}
