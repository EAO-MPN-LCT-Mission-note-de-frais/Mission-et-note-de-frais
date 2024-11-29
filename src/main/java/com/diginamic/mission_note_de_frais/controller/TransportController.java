package com.diginamic.mission_note_de_frais.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.service.TransportService;

import java.util.List;

/**
 * Contrôleur pour gérer les opérations liées aux moyens de transport.
 * <p>
 * Ce contrôleur expose des endpoints REST pour permettre la création, la mise à
 * jour, la suppression et la récupération des moyens de transport. Il interagit
 * avec le {@link TransportService} pour effectuer les opérations métier.
 */
@RestController
@RequestMapping("/transports")
public class TransportController {

	private final TransportService transportService;

	/**
	 * Constructeur du contrôleur Transport.
	 *
	 * @param transportService le service pour gérer les moyens de transport
	 */
	public TransportController(TransportService transportService) {
		this.transportService = transportService;
	}

	/**
	 * Récupère tous les moyens de transport.
	 * <p>
	 * Cette méthode permet de récupérer la liste complète des transports
	 * disponibles sous forme de {@link TransportDTO}.
	 *
	 * @return une réponse HTTP contenant une liste de {@link TransportDTO}
	 */
	@GetMapping
	public ResponseEntity<List<TransportDTO>> getAllTransports() {
		List<TransportDTO> transports = transportService.getAllTransports();
		return new ResponseEntity<>(transports, HttpStatus.OK);
	}

	/**
	 * Crée un nouveau moyen de transport.
	 * <p>
	 * Cette méthode permet à un utilisateur de créer un nouveau moyen de transport
	 * en envoyant un objet {@link TransportDTO}.
	 *
	 * @param transportDTO les données du transport à créer
	 * @return une réponse HTTP contenant le {@link TransportDTO} du transport créé
	 *         avec un statut 201 (Created)
	 */
	@PostMapping
	public ResponseEntity<TransportDTO> createTransport(@RequestBody TransportDTO transportDTO) {
		TransportDTO createdTransport = transportService.createTransport(transportDTO);
		return new ResponseEntity<>(createdTransport, HttpStatus.CREATED);
	}

	/**
	 * Met à jour un moyen de transport existant.
	 * <p>
	 * Cette méthode permet de mettre à jour un transport en fonction de son
	 * identifiant. Elle attend un {@link TransportDTO} avec les nouvelles données
	 * du transport.
	 *
	 * @param id           l'identifiant du transport à mettre à jour
	 * @param transportDTO les nouvelles données du transport
	 * @return une réponse HTTP contenant le {@link TransportDTO} mis à jour
	 */
	@PutMapping("/{id}")
	public ResponseEntity<TransportDTO> updateTransport(@PathVariable Long id, @RequestBody TransportDTO transportDTO) {
		TransportDTO updatedTransport = transportService.updateTransport(id, transportDTO);
		return new ResponseEntity<>(updatedTransport, HttpStatus.OK);
	}

	/**
	 * Supprime un moyen de transport.
	 * <p>
	 * Cette méthode permet de supprimer un transport en fonction de son
	 * identifiant.
	 *
	 * @param id l'identifiant du transport à supprimer
	 * @return une réponse HTTP vide avec un statut 204 (No Content)
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
		transportService.deleteTransport(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
