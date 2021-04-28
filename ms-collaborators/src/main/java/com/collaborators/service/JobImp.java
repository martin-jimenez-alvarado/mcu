package com.collaborators.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.collaborators.dto.CollaboratorDTO;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class JobImp {
	private CollaboratorImp collaborator;
	
	public JobImp(CollaboratorImp collaborator) {
		this.collaborator = collaborator;
	}
	
	@Scheduled(cron = "* 1 * * * *")
	public  void updateCollaborators() {		
		collaborator.getAllCollaborator()
			.flatMap(s -> 
				collaborator.fetchCollaborator(collaborator.fetchAll(s.getSuperhero()))
						.flatMap(u -> {	
									if(s.getLast_sync()!=null) {
										return 	collaborator.createCollaborator(s.getSuperhero(),
												CollaboratorDTO.builder()
												.last_sync( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) )
												.editors(s.getEditors().stream().collect(Collectors.toList()))
												.writers(s.getWriters().stream().collect(Collectors.toList()))
												.colorists(s.getColorists().stream().collect(Collectors.toList()))
												.build());
									}
									else return Mono.empty();
						})
				)
			.subscribe(data -> log.info("\n " + data.toString() + "\n" ));		
	}
	

}










