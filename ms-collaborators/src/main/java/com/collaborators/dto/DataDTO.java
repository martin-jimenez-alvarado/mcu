package com.collaborators.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data   @AllArgsConstructor  @NoArgsConstructor @Builder
public class DataDTO {
	public int offset;
	public int limit;
	public int total;
	public int count;
	public List<ResultDTO> results;
}