package com.collaborators.error;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
	
	private String timestamp;
	private String path;
	private String message;
	private List<Map<String, String>> error;
	private Integer status;

	public ErrorResponse(String timestamp, String path, String message, List<Map<String, String>> error, Integer status) {
		super();
		this.timestamp = timestamp;
		this.path = path;
		this.status = status;
		this.message = message;
		this.error = error;
	}
}
