package com.characters.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.characters.dto.CharacterDTO;
import com.characters.dto.ResultDTO;
import reactor.core.publisher.Mono;

@Service
public class ComicImp implements IComic{
	private WebClient.Builder webClientComic;
	private ReactiveResilience4JCircuitBreakerFactory cbFactory;
	@Value("${api-marvel}")
	private String APIMARVEL;
	@Value("${MD5-key}")
	private String key;
	
	public ComicImp(WebClient.Builder webClient, ReactiveResilience4JCircuitBreakerFactory cbFactory){
		this.webClientComic = webClient;
		this.cbFactory = cbFactory;
	}
	
	
	@Override
	public Mono<List<ResultDTO>> getComicsByCharacterId(Mono<Integer> id) {
		return id
				.flatMap(c -> {
					if(!String.valueOf(c).contains("-1")) {
						return this.webClientComic.build()
									.get()
									.uri(APIMARVEL + "/v1/public/characters/{id}/comics?" + key, c)
									.retrieve()
									.bodyToMono(CharacterDTO.class)
									.map(m -> m.data.results)
									.transform( it -> cbFactory.create("/marvel/characters/")
											.run( it, throwabble -> comicDTOFallback())
									);
					}else return Mono.empty();	
				});
	}
	
	
	@Override
	public Mono<List<String>> getComicsTitleByCharacter(Mono<List<ResultDTO>> comics){
		return comics
				.map(t -> t.stream()
						.map(n -> n.title)
						.collect(Collectors.toList())
				);
		
	}
	
	
	private Mono<List<ResultDTO>> comicDTOFallback() {
		return Mono.just(new ArrayList<>());
	}


}
