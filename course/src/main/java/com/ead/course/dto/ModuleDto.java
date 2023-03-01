package com.ead.course.dto;

import com.ead.course.models.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {
    private BigInteger moduleId;

    private String title;
    private String description;

    public final static ModuleDto of(Module module) {
        var moduleDto = new ModelMapper().map(module, ModuleDto.class);
        return moduleDto;
    }
}
