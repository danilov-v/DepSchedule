package com.varb.schedule.springtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varb.schedule.buisness.models.dto.EventTypeResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class EventTypeSpringBootTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/eventType";

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testPostEventType() {
    }
}
