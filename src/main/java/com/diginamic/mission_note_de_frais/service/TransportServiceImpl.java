package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.dto.TransportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Transport;
import com.diginamic.mission_note_de_frais.model.repository.TransportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des moyens de transport.
 * <p>
 * Cette classe implémente les opérations définies dans l'interface
 * {@link TransportService}. Elle gère la création, la mise à jour, la
 * suppression et la récupération des moyens de transport. Elle utilise le
 * {@link TransportRepository} pour interagir avec la base de données.
 */
@Service
public class TransportServiceImpl implements TransportService {

	private final TransportRepository transportRepository;

	/**
	 * Constructeur de {@link TransportServiceImpl}.
	 * <p>
	 * Le constructeur prend un {@link TransportRepository} pour interagir avec les
	 * données de transport.
	 *
	 * @param transportRepository le repository pour accéder aux données de
	 *                            transport
	 */
	public TransportServiceImpl(TransportRepository transportRepository) {
		this.transportRepository = transportRepository;
	}

	/**
	 * Récupère tous les moyens de transport.
	 * <p>
	 * Cette méthode utilise le {@link TransportRepository} pour obtenir tous les
	 * moyens de transport dans la base de données et les retourne sous forme de
	 * liste de {@link TransportDTO}.
	 *
	 * @return une liste de {@link TransportDTO} représentant tous les moyens de
	 *         transport
	 */
	@Override
	public List<TransportDTO> getAllTransports() {
		List<Transport> transports = transportRepository.findAll();
		return transports.stream().map(transport -> new TransportDTO(transport.getId(), transport.getName()))
				.collect(Collectors.toList());
	}

	/**
	 * Crée un nouveau moyen de transport.
	 * <p>
	 * Cette méthode vérifie d'abord si un moyen de transport avec le même nom
	 * existe déjà dans la base de données. Si c'est le cas, une exception
	 * {@link IllegalArgumentException} est levée. Sinon, un nouveau transport est
	 * créé, enregistré dans la base de données et retourné sous forme de
	 * {@link TransportDTO}.
	 *
	 * @param transportDTO les données du transport à créer
	 * @return un {@link TransportDTO} représentant le transport créé
	 * @throws IllegalArgumentException si un transport avec le même nom existe déjà
	 */
	@Override
	@Transactional
	public TransportDTO createTransport(TransportDTO transportDTO) {
		if (transportRepository.existsByName(transportDTO.getName())) {
			throw new IllegalArgumentException("Un transport avec ce nom existe déjà.");
		}

		Transport transport = new Transport();
		transport.setName(transportDTO.getName());

		Transport savedTransport = transportRepository.save(transport);

		return new TransportDTO(savedTransport.getId(), savedTransport.getName());
	}

	/**
	 * Met à jour un moyen de transport existant.
	 * <p>
	 * Cette méthode vérifie si un transport avec l'identifiant fourni existe dans
	 * la base de données. Si le transport n'est pas trouvé, une exception
	 * {@link IllegalArgumentException} est levée. Sinon, le transport est mis à
	 * jour avec les nouvelles données et retourné sous forme de
	 * {@link TransportDTO}.
	 *
	 * @param id           l'identifiant du transport à mettre à jour
	 * @param transportDTO les nouvelles données du transport
	 * @return un {@link TransportDTO} représentant le transport mis à jour
	 * @throws IllegalArgumentException si le transport n'existe pas
	 */
	@Override
	@Transactional
	public TransportDTO updateTransport(Long id, TransportDTO transportDTO) {
		Optional<Transport> optionalTransport = transportRepository.findById(id);

		if (optionalTransport.isEmpty()) {
			throw new IllegalArgumentException("Transport introuvable.");
		}

		Transport transport = optionalTransport.get();
		transport.setName(transportDTO.getName());

		Transport updatedTransport = transportRepository.save(transport);

		return new TransportDTO(updatedTransport.getId(), updatedTransport.getName());
	}

	/**
	 * Supprime un moyen de transport.
	 * <p>
	 * Cette méthode vérifie d'abord si un transport avec l'identifiant fourni
	 * existe dans la base de données. Si le transport n'existe pas, une exception
	 * {@link IllegalArgumentException} est levée. Sinon, le transport est supprimé
	 * de la base de données.
	 *
	 * @param id l'identifiant du transport à supprimer
	 * @throws IllegalArgumentException si le transport n'existe pas
	 */
	@Override
	@Transactional
	public void deleteTransport(Long id) {
		if (!transportRepository.existsById(id)) {
			throw new IllegalArgumentException("Transport introuvable.");
		}

		transportRepository.deleteById(id);
	}
}
