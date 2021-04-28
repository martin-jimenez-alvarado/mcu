package com.characters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO{
    public String resourceURI;
    public String name;
    public String role;
    public String type;
}