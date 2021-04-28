package com.characters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateDTO{
    public String type;
    public Object date;
}
