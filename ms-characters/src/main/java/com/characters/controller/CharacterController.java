package com.characters.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.characters.dto.ResultDTO;
import com.characters.dto.CharacterssDTO;
import com.characters.dto.ComicDTO;
import com.characters.service.CharacterImp;
import com.characters.service.ComicImp;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;


@RequestMapping("/marvel/characters")
@AllArgsConstructor
public @RestController class CharacterController {
	private CharacterImp character;
	private ComicImp comic;
	
	@GetMapping(path = "/{superhero}")
	public  Mono<CharacterssDTO> fetchCharacters(@Parameter(description="superhero", required=true,example = "Magneto")
	@PathVariable("superhero") String superhero) {
		
		Mono<List<ResultDTO>> tmp = comic.getComicsByCharacterId(character.getId(superhero));
		return Mono
				.zip( comic.getComicsTitleByCharacter(tmp), character.getCharactersInvolvedByComic(tmp))
				.map(t -> {
			        List<ComicDTO> comicDTO = new ArrayList();
			        final AtomicInteger count = new AtomicInteger();
			        t.getT1().forEach(title -> {
			        							ComicDTO comic = ComicDTO.builder()
			        							.comic(title.toString())		
								        		.characters( t.getT2().get(count.getAndIncrement())) 
								        		.build();
			        							comicDTO.add(comic);
			        });
			        return CharacterssDTO.builder()
			        		.last_sync( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) )
			        		.info(comicDTO)
			        		.build();
			    });
				
	}
}
