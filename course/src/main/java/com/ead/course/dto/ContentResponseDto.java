package com.ead.course.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ContentResponseDto<T>  {
    private List<T> content;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ContentResponseDto(
            @JsonProperty("content") List<T> content
    ) {
        this.content = content;
    }
}


