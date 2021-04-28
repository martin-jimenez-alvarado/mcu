package com.characters.service;

import java.util.List;
import com.characters.dto.ResultDTO;
import reactor.core.publisher.Mono;


public interface IComic {
	public Mono<List<ResultDTO>> getComicsByCharacterId(Mono<Integer> id);
	public Mono<List<String>> getComicsTitleByCharacter(Mono<List<ResultDTO>> comics);
}
