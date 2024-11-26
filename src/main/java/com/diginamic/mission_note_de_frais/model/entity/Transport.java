package com.diginamic.mission_note_de_frais.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente un moyen de transport utilisé dans le cadre des missions. Un
 * transport est associé à un ou plusieurs types de missions, et chaque
 * transport possède un nom unique.
 * <p>
 * La classe est mappée à une table de base de données via JPA avec une relation
 * {@link ManyToMany} vers la classe {@link Mission}.
 */
@Entity
public class Transport {

	/**
	 * Identifiant unique du transport. Généré automatiquement par la base de
	 * données.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nom du moyen de transport. Ce champ est unique dans la base de données et ne
	 * peut être nul.
	 */
	@Column(nullable = false, unique = true)
	private String name;

	/**
	 * Liste des missions auxquelles ce moyen de transport est associé. Relation
	 * Many-to-Many avec la classe {@link Mission}.
	 */
	@ManyToMany(mappedBy = "transports")
	private Set<Mission> missions = new HashSet<>();

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

	/**
	 * Récupère la liste des missions associées à ce moyen de transport.
	 *
	 * @return la liste des missions associées
	 */
	public Set<Mission> getMissions() {
		return missions;
	}

	/**
	 * Définit la liste des missions associées à ce moyen de transport.
	 *
	 * @param missions la liste des missions à associer
	 */
	public void setMissions(Set<Mission> missions) {
		this.missions = missions;
	}
}
