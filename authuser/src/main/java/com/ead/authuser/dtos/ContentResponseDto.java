package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class ContentResponseDto<T>  {
    public List<T> content;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ContentResponseDto(
            @JsonProperty("content") List<T> content
    ) {
        this.content = content;
    }
}
