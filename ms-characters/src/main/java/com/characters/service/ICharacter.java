package com.characters.service;

import reactor.core.publisher.Mono;

public interface ICharacter {
	public Mono<Integer> getId(String superhero);
}
