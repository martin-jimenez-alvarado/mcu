package com.characters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectedIssueDTO{
    public String resourceURI;
    public String name;
}
