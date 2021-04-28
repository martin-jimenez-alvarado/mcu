package com.collaborators.dao.entity;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data   @AllArgsConstructor  @NoArgsConstructor @Builder
public class Collaborator {
	@Indexed(name = "superheroIndex", unique = true)
	@Id
	private String superhero;
	@NotEmpty
	private String last_sync;
    private Set<String> editors;
    private Set<String> writers;
    private Set<String> colorists;
}