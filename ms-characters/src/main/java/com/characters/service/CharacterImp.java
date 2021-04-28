package com.characters.service;

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
public class CharacterImp implements ICharacter{

	private WebClient.Builder webClientCharacter;
	private ReactiveResilience4JCircuitBreakerFactory cbFactory;
	@Value("${api-marvel}")
	private String APIMARVEL;
	@Value("${MD5-key}")
	private String key;
	
	public CharacterImp(WebClient.Builder webClient, ReactiveResilience4JCircuitBreakerFactory cbFactory){
		this.webClientCharacter = webClient;
		this.cbFactory = cbFactory;
		
	}
	
	@Override
	public Mono<Integer> getId(String superhero) {
		return this.webClientCharacter.build()
				.get()
				.uri(APIMARVEL + "/v1/public/characters?name=" + superhero + key)
				.retrieve()
				.bodyToFlux(CharacterDTO.class)
				.map(c -> c.data.results.stream()
						.map(r -> r.id)
						.collect(Collectors.toList())
				)
				.collect(Collectors.toList())
				.map(a -> a.stream()
						.flatMap(List::stream)
						.collect(Collectors.toList())
				)
				.map(d -> d.get(0))
				.transform( it -> cbFactory.create("/marvel/characters/")
						.run( it, throwabble -> charactersDTOFallback())
				);
				
	}

	
	public  Mono<List<List<String>>> getCharactersInvolvedByComic(Mono<List<ResultDTO>> comics){
		return comics
				.map(t -> t.stream()
						.map(n -> n.characters.items.stream()
								.map(c -> c.name)
								.collect(Collectors.toList())
						).collect(Collectors.toList())
				);
		
	}

	private Mono<Integer> charactersDTOFallback() {
		return Mono.just(-1);
	}

}
