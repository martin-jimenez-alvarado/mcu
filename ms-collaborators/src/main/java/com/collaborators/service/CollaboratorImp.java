package com.collaborators.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.collaborators.dao.entity.Collaborator;
import com.collaborators.dto.CollaboratorDTO;
import com.collaborators.dto.CollaboratorsDTO;
import com.collaborators.dto.ItemDTO;
import com.collaborators.repository.CollaboratorRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class CollaboratorImp implements ICollaborator{ 
	private final WebClient.Builder webClientCollaborator;
	private ReactiveResilience4JCircuitBreakerFactory cbFactory;
	private CollaboratorRepository collaboratorRepository;
	private IWriter writer;
	private IEditor editor;
	private IColorist colorist;
	
	@Value("${api-marvel}")
	private String APIMARVEL;
	@Value("${MD5-key}")
	private String key;
	
	
	public CollaboratorImp(WebClient.Builder webClient, 
			ReactiveResilience4JCircuitBreakerFactory cbFactory, 
			CollaboratorRepository collaboratorRepository, 
			IWriter writer,
			IEditor editor,
			IColorist colorist){
		
		this.webClientCollaborator = webClient;
		this.cbFactory = cbFactory;
		this.writer = writer;
		this.editor = editor;
		this.colorist = colorist;
		this.collaboratorRepository = collaboratorRepository;
	}
	
	
	@Override
	public Mono<List<ItemDTO>> fetchAll(String superhero) {		
		return this.webClientCollaborator.build()
				.get()
				.uri(APIMARVEL + "/v1/public/comics?title=" + superhero + key)
				.retrieve()
				.bodyToFlux(CollaboratorsDTO.class)
				.map(c -> c.data.results.stream()
						  .map(r -> 
							  r.creators.items.stream()
							  .filter(w -> w.role.contains("writer")  || w.role.contains("colorist") || w.role.contains("editor"))
							  .collect(Collectors.toList())
						  )
						  .flatMap(List::stream)
						  .collect(Collectors.toList())
				)
				.collect(Collectors.toList())
				.map(a -> a.stream()
						.flatMap(List::stream)
						.collect(Collectors.toList())
				)
				.transform( it -> cbFactory.create("/marvel/collaborators/")
						.run( it, throwabble -> collaboratorDTOFallback())
				);
	}
	
	
	@Override
	public Mono<CollaboratorDTO> getCollaborators(Mono<List<String>> writer, Mono<List<String>> editor, Mono<List<String>> colorist){
		return Mono.zip(editor,	writer,	colorist)
				.map(d -> CollaboratorDTO.builder()
							.last_sync( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
							.editors(d.getT1())
							.writers(d.getT2())
							.colorists(d.getT3())
							.build()
				);
	}
	
	
	private Mono<List<ItemDTO>> collaboratorDTOFallback() {	
		return Mono.just(new ArrayList());
	}


	@Override
	public Flux<Collaborator> getAllCollaborator() {
		return collaboratorRepository.findAll();
	}


	@Override
	public Mono<CollaboratorDTO> createCollaborator(String superhero, CollaboratorDTO collaboratorDTO) {
		return collaboratorRepository.save(Collaborator.builder()
				.superhero(superhero)
				.last_sync( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) )
				.editors(collaboratorDTO.getEditors().stream().collect(Collectors.toSet()))
				.writers(collaboratorDTO.getWriters().stream().collect(Collectors.toSet()))
				.colorists(collaboratorDTO.getColorists().stream().collect(Collectors.toSet()))
				.build())
				.map(c -> CollaboratorDTO.builder()
						.last_sync(c.getLast_sync())
						.editors(c.getEditors().stream().collect(Collectors.toList()))
						.writers(c.getWriters().stream().collect(Collectors.toList()))
						.colorists(c.getColorists().stream().collect(Collectors.toList()))
						.build());
		
	}


	@Override
	public Mono<CollaboratorDTO> findById(String superhero) {
		return collaboratorRepository.findById(superhero)
				.map(c -> CollaboratorDTO.builder()
						.last_sync( c.getLast_sync() )
						.editors(c.getEditors().stream().collect(Collectors.toList()))
						.writers(c.getEditors().stream().collect(Collectors.toList()))
						.colorists(c.getColorists().stream().collect(Collectors.toList()))
						.build()
				);
	}
	
	
	
	public String firstCharUppercase(String str) {
	    if (str == null || str.isEmpty()) return str;            
	    else return str.substring(0, 1).toUpperCase() + str.substring(1); 
	}
	
	
	public Mono<CollaboratorDTO> fetchCollaborator( Mono<List<ItemDTO>> collaborators ){
		return getCollaborators( writer.fetchAll(collaborators), editor.fetchAll(collaborators), colorist.fetchAll(collaborators))
				.map(a -> {
					if(!a.getColorists().isEmpty() && !a.getEditors().isEmpty() && !a.getWriters().isEmpty()) {
						return a;
					}else return CollaboratorDTO.builder().build();
				});
	}
		
}
