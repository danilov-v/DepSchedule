package com.varb.schedule.springtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.varb.schedule.buisness.logic.repository.EventTypeRepository;
import com.varb.schedule.buisness.models.dto.EventTypePostDto;
import com.varb.schedule.buisness.models.dto.EventTypeResponseDto;
import com.varb.schedule.buisness.models.entity.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class EventTypeSpringBootTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/eventType";

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Test
    @Sql("/db/scripts/spring/InsertEventTypeData.sql")
    public void testGetEventType() throws Exception {

        MvcResult mvcResult =  mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        List<EventTypeResponseDto> dtoList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<EventTypeResponseDto>>() {});
        dtoList.toString();

        //assertion according to the initialization data of InsertEventTypeData.sql
        assertAll("Has to return two event types",
                () -> assertEquals("red", dtoList.get(0).getColor()),
                () -> assertEquals("mobilization", dtoList.get(0).getDescription()),
                () -> assertEquals("green", dtoList.get(1).getColor()),
                () -> assertEquals("deployment", dtoList.get(1).getDescription())
        );
    }

    @Test
    public void testPostEventType() throws Exception {
        String color = "Purple", description = "Type Description";
        Long expectedTypeId = 1L;
        EventTypePostDto postDto = new EventTypePostDto();
        postDto.setColor(color);
        postDto.setDescription(description);

        mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.typeId").value(expectedTypeId))
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.description").value(description));

        //second-level validation
        Optional<EventType> eventTypeOpt = eventTypeRepository.findById(expectedTypeId);
        assertTrue(!eventTypeOpt.isEmpty());

        EventType eventType = eventTypeOpt.get();
        assertAll("Assertion of added event type",
                () -> assertEquals(expectedTypeId, eventType.getTypeId()),
                () -> assertEquals(color, eventType.getColor()),
                () -> assertEquals(description, eventType.getDescription())
        );
    }
}
