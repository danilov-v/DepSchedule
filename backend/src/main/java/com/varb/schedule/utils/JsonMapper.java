package com.varb.schedule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varb.schedule.exception.MapperException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonMapper {
    private final ObjectMapper objectMapper;

    public <T> T toObject(String json, Class<T> valueType) {
        if (json == null)
            return null;
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new MapperException(e, json);
        }
    }

    public <T> List<T> toObjectList(String json, Class<T> valueType) {
        if (json == null)
            return Collections.emptyList();
        try {

            return objectMapper.readValue(json, new ObjectMapper().getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (IOException e) {
            throw new MapperException(e, json);
        }
    }

    public String toString(Object object) {
        if (object == null)
            return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new MapperException(e, object.toString());
        }
    }
}
