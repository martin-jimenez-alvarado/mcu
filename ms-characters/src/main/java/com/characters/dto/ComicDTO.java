package com.characters.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data   @AllArgsConstructor  @NoArgsConstructor @Builder
public class ComicDTO {
	public String comic;
    public List<String> characters;
}