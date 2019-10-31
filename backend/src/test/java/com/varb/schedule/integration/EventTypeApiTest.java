package com.varb.schedule.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.varb.schedule.buisness.logic.repository.EventTypeRepository;
import com.varb.schedule.buisness.models.dto.EventTypeDto;
import com.varb.schedule.buisness.models.dto.EventTypeResponseDto;
import com.varb.schedule.buisness.models.entity.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventTypeApiTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/eventType";

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Test
    @Sql("/db/scripts/eventType/InsertEventTypeData.sql")
    public void testGetEventType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        List<EventTypeResponseDto> dtoList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<EventTypeResponseDto>>() {});

        //assertion according to the initialization data of InsertEventTypeData.sql
        assertAll("Has to return initialized before the test event types",
                () -> assertEquals("red", dtoList.get(0).getColor()),
                () -> assertEquals("mobilization", dtoList.get(0).getDescription()),
                () -> assertEquals("green", dtoList.get(1).getColor()),
                () -> assertEquals("deployment", dtoList.get(1).getDescription())
        );
    }

    @Test
    public void testPostEventType() throws Exception {
        String color = "Purple", description = "Type Description";
        EventTypeDto postDto = new EventTypeDto();
        postDto.setColor(color);
        postDto.setDescription(description);

        mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.typeId").isNumber())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.description").value(description));

        //second-level validation
        assertTrue(eventTypeRepository.findAll().size() == 1);
        EventType eventType = eventTypeRepository.findAll().get(0);
        assertAll("Assertion of added event type",
                () -> assertNotNull(eventType.getTypeId()),
                () -> assertEquals(color, eventType.getColor()),
                () -> assertEquals(description, eventType.getDescription())
        );
    }

    @Test
    @Sql("/db/scripts/eventType/InsertEventTypeData.sql")
    public void testPutEventType() throws Exception {
        //Get event type to update
        List<EventType> initializedData = eventTypeRepository.findAll();
        assertTrue(initializedData.size() > 0);
        EventType eventType = initializedData.get(0);
        final long typeId = eventType.getTypeId();
        //set new color - to be updated
        String newColor = "Purple";
        String newDescription = "newDesc";

        EventTypeDto putDto = new EventTypeDto();
        putDto.color(newColor)
        .description(newDescription);

        mockMvc.perform(put(baseUrl + "/" + typeId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(putDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeId").value(typeId))
                .andExpect(jsonPath("$.color").value(newColor))
                .andExpect(jsonPath("$.description").value(newDescription));

        //second-level validation
        assertAll("Assertion of just updated event type",
                () -> assertTrue(eventTypeRepository.findById(typeId).isPresent()),
                () -> assertTrue(eventType == eventTypeRepository.findById(typeId).get()),
                () -> assertEquals(typeId, eventType.getTypeId().longValue()),
                () -> assertEquals(newColor, eventType.getColor()),
                () -> assertEquals(newDescription, eventType.getDescription())
        );
    }

    @Test
    @Sql("/db/scripts/eventType/InsertEventTypeData.sql")
    public void testDeleteEventType() throws Exception {
        List<EventType> initializedData = eventTypeRepository.findAll();
        int rowsNum = initializedData.size();
        assertTrue(rowsNum > 0);

        mockMvc.perform(delete(baseUrl + "/" + initializedData.get(0).getTypeId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //assert after deleting one event type
        List<EventType> afterDeleteList = eventTypeRepository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(initializedData.get(0)));
    }
}
