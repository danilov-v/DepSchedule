package com.varb.schedule.config.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ModelMapperCustomize extends ModelMapper {

    public <D> List<D> mapToList(Object source, Class<D> destinationType) {
        if (source == null)
            return Collections.emptyList();
        Type type = Array.newInstance(destinationType, 0).getClass();
        return Arrays.asList(super.map(source, type));
    }
}
