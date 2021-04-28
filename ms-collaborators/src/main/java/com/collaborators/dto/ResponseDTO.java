package com.collaborators.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data   @AllArgsConstructor  @NoArgsConstructor @Builder
public class ResponseDTO{
	private String last_sync;
    private List<String> editors;
    private List<String> writers;
    private List<String> colorists;
}
