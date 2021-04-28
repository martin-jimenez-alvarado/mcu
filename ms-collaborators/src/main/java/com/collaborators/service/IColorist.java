package com.collaborators.service;

import java.util.List;
import com.collaborators.dto.ItemDTO;
import reactor.core.publisher.Mono;

public interface IColorist {
	Mono<List<String>> fetchAll(Mono<List<ItemDTO>> collaborators);
}
