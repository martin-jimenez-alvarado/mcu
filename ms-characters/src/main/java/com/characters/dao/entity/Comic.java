package com.characters.dao.entity;

import java.util.List;
import lombok.Data;

@Data
public class Comic {
	public String comic;
    public List<String> characters;
}
