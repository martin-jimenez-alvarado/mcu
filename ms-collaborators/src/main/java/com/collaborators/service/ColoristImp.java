package com.collaborators.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.collaborators.dto.ItemDTO;
import reactor.core.publisher.Mono;

@Service
public class ColoristImp implements IColorist{

	@Override
	public Mono<List<String>> fetchAll(Mono<List<ItemDTO>> collaborators) {			
		return collaborators
				.map( w -> w.stream()
						.distinct()
						.filter(r -> r.role.contains("colorist"))
						.collect(Collectors.toList())
				)
				.map(a -> a.stream()
						.map(ItemDTO::getName)
						.collect(Collectors.toList())
				);
	}
	
}
