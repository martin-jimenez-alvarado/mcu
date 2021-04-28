package com.characters.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDTO{
    public int offset;
    public int limit;
    public int total;
    public int count;
    public List<ResultDTO> results;
}