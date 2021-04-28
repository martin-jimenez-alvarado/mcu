package com.collaborators.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.collaborators.dto.CollaboratorDTO;
import com.collaborators.service.CollaboratorImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;


@RequestMapping("/marvel/collaborators")
@AllArgsConstructor
public @RestController class CollaboratorController {
	private CollaboratorImp collaborator;
	
	@Operation(summary = "Fetch Superhero", description = "Fetch a list of editors, colorists, and writers, involved in the character's comics")
	@GetMapping(path = "/{superhero}")
	public  Mono<CollaboratorDTO> fetchCollaborators(@Parameter(description="superhero", required=true,example = "Magneto")
	@PathVariable(name = "superhero") String superhero){
		
		return collaborator.findById(collaborator.firstCharUppercase(superhero))
				.switchIfEmpty( collaborator.fetchCollaborator(collaborator.fetchAll(superhero))
						.flatMap( r -> {
							if(r.getLast_sync() != null) {
								return collaborator.createCollaborator(collaborator.firstCharUppercase(superhero), r);
							}else return Mono.empty();
						})
				);	
	}
	
	
}














