package com.ead.course.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ResponsePagedDto<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePagedDto(
            @JsonProperty("total") long total,
            @JsonProperty("content") List content,
            @JsonProperty("pageable") JsonNode pageable,
            @JsonProperty("last") boolean last,
            @JsonProperty("totalElements") Integer totalElements,
            @JsonProperty("totalPages") Integer totalPages,
            @JsonProperty("size") Integer size,
            @JsonProperty("number") Integer number,
            @JsonProperty("sort") JsonNode sort
    ) {
        super(content, PageRequest.of(number,size), total);
    }
}


