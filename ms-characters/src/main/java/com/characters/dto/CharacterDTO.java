package com.characters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO{
    public int code;
    public String status;
    public String copyright;
    public String attributionText;
    public String attributionHTML;
    public String etag;
    public DataDTO data;
}

