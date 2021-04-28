package com.collaborators.service;

import java.util.List;
import com.collaborators.dao.entity.Collaborator;
import com.collaborators.dto.CollaboratorDTO;
import com.collaborators.dto.ItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICollaborator {
	public Mono<List<ItemDTO>> fetchAll(String superhero);
	public Mono<CollaboratorDTO> getCollaborators(Mono<List<String>> writer, Mono<List<String>> editor, Mono<List<String>> colorist);
	
	public Flux<Collaborator> getAllCollaborator();
	public Mono<CollaboratorDTO> createCollaborator(String superhero, CollaboratorDTO aCollaborator);
	public Mono<CollaboratorDTO> findById(String superhero);
}
