package com.collaborators.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data   @AllArgsConstructor  @NoArgsConstructor @Builder
public class CollaboratorsDTO {
	public int code;
	public String status;
	public String copyright;
	public String attributionText;
	public String attributionHTML;
	public String etag;
	public DataDTO data;
}