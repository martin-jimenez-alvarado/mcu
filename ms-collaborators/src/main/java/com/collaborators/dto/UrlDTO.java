package com.collaborators.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data   @AllArgsConstructor  @NoArgsConstructor @Builder
public class UrlDTO {
	public String type;
	public String url;
}