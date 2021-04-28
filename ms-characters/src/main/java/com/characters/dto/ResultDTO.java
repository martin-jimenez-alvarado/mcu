package com.characters.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDTO{
    public int id;
    public int digitalId;
    public String title;
    public String name;
    public Object modified;
    public int issueNumber;
    public String variantDescription;
    public String description;
    public String isbn;
    public String upc;
    public String diamondCode;
    public String ean;
    public String issn;
    public String format;
    public int pageCount;
    public List<TextObjectDTO> textObjects;
    public String resourceURI;
    public ComicsDTO comics;
    public List<UrlDTO> urls;
    public SeriesDTO series;
    public List<VariantDTO> variants;
    public List<Object> collections;
    public List<CollectedIssueDTO> collectedIssues;
    public List<DateDTO> dates;
    public List<PriceDTO> prices;
    public ThumbnailDTO thumbnail;
    public List<ImageDTO> images;
    public CreatorsDTO creators;
    public CharactersDTO characters;
    public StoriesDTO stories;
    public EventsDTO events;
}
