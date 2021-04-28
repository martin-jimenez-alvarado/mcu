package com.characters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextObjectDTO{
    public String type;
    public String language;
    public String text;
}
