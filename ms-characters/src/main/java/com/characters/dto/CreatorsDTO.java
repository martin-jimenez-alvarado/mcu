package com.characters.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatorsDTO{
    public int available;
    public String collectionURI;
    public List<ItemDTO> items;
    public int returned;
}
