package com.varb.schedule.springtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.logic.service.EventService;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class EventSpringBootTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/event";

    @Autowired
    private EventRepository eventRepository;

    @Test
    @SqlGroup({@Sql("/db/scripts/spring/EventTestRequiredData.sql"),
            @Sql("/db/scripts/spring/InsertEventData.sql")})
    public void testGetEvent() throws Exception {
        getWithOnlyDateFromParam();
        getWithBothDateFromAndDateToParams();
        getWithoutDateFromParam();

    }

    private void getWithOnlyDateFromParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                //set dateFrom here before any other in init scripts so that retrieve all entities
                .param("dateFrom", "2019-09-25")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        List<EventResponseDto> dtoList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<EventResponseDto>>() {});

        //assertion according to the initialization data of sql scripts above
        assertAll("Has to return initialized before the test events",
                //first entity
                () -> assertEquals(1L, dtoList.get(0).getEventId().longValue()),
                () -> assertEquals(1L, dtoList.get(0).getEventTypeId().longValue()),
                () -> assertEquals(200L, dtoList.get(0).getUnitId().longValue()),
                () -> assertEquals(LocalDate.of(2019, 9, 26), dtoList.get(0).getDateFrom()),
                () -> assertEquals(4, dtoList.get(0).getDuration().intValue()),
                () -> assertEquals("NOT NULL", dtoList.get(0).getNote()),
                //second entity
                () -> assertEquals(2L, dtoList.get(1).getEventId().longValue()),
                () -> assertEquals(2L, dtoList.get(1).getEventTypeId().longValue()),
                () -> assertEquals(300L, dtoList.get(1).getUnitId().longValue()),
                () -> assertEquals(LocalDate.of(2019, 10, 3), dtoList.get(1).getDateFrom()),
                () -> assertEquals(2, dtoList.get(1).getDuration().intValue()),
                () -> assertNull(dtoList.get(1).getNote())
        );
    }

    private void getWithBothDateFromAndDateToParams() throws Exception {
        //request with dateFrom and dateTo params
        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                //arrange params so that retrieve only first entity
                .param("dateFrom", "2019-09-25")
                .param("dateTo", "2019-10-01")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        List<EventResponseDto> dtoList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<EventResponseDto>>() {});

        //we expect only one event here
        assertTrue(dtoList.size() == 1);
        EventResponseDto firstEvent = dtoList.get(0);

        assertAll("Has to return first event initialized before the test",
                //first entity
                () -> assertEquals(1L, firstEvent.getEventId().longValue()),
                () -> assertEquals(1L, firstEvent.getEventTypeId().longValue()),
                () -> assertEquals(200L, firstEvent.getUnitId().longValue()),
                () -> assertEquals(LocalDate.of(2019, 9, 26), firstEvent.getDateFrom()),
                () -> assertEquals(4, firstEvent.getDuration().intValue()),
                () -> assertEquals("NOT NULL", firstEvent.getNote())
        );
    }

    private void getWithoutDateFromParam() throws Exception {
        //request without mandatory dateFrom param
        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        //we expect exception here
        assertAll(
                () -> assertTrue(mvcResult.getResolvedException() instanceof MissingServletRequestParameterException),
                () -> assertEquals("dateFrom",
                        ((MissingServletRequestParameterException)mvcResult.getResolvedException()).getParameterName())
        );
    }

    @Test
    @Sql("/db/scripts/spring/EventTestRequiredData.sql")
    public void testPostEvent() throws Exception {
        final Long unitId = 200L;
        final Long eventTypeId = 1L;
        final LocalDate dateFrom = LocalDate.of(2019, 5, 28);
        final Integer duration = 5;
        final String note = "Note here";
        EventPostDto postDto = new EventPostDto();
        postDto.setEventTypeId(eventTypeId);
        postDto.setUnitId(unitId);
        postDto.setDateFrom(dateFrom);
        postDto.setDuration(duration);
        postDto.setNote(note);

        mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.eventId").isNumber())
                .andExpect(jsonPath("$.unitId").value(unitId))
                .andExpect(jsonPath("$.eventTypeId").value(eventTypeId))
                .andExpect(jsonPath("$.dateFrom").value(dateFrom.toString()))
                .andExpect(jsonPath("$.duration").value(duration))
                .andExpect(jsonPath("$.note").value(note));

        //second level validation
        List<Event> afterAdd = eventRepository.findAll();
        assertTrue(afterAdd.size() == 1);
        Event event = afterAdd.get(0);
        assertAll("Assertion of just added event",
                () -> assertNotNull(event.getEventId()),
                () -> assertEquals(unitId, event.getUnitId()),
                () -> assertEquals(eventTypeId, event.getEventTypeId()),
                () -> assertEquals(dateFrom, event.getDateFrom()),
                () -> assertEquals(duration, event.getDuration()),
                () -> assertEquals(note, event.getNote())
        );
    }

    @Test
    @SqlGroup({@Sql("/db/scripts/spring/EventTestRequiredData.sql"),
            @Sql("/db/scripts/spring/InsertEventData.sql")})
    public void testPutEvent() throws Exception {
        /*TODO refactor after implementation of right logic - Duration
         duration from EventDuration is supposed to be used only for usability
         so it mustn't affect the logic during update or add operations. Exclude it from calculation
         */

        List<Event> initializedData = eventRepository.findAll();
        //validate data has been initialized correctly
        assertTrue(initializedData.size() > 0);

        Event event = initializedData.get(0);
        final Long eventId = event.getEventId();
        final LocalDate dateFrom = event.getDateFrom();
        final Long eventTypeId = event.getEventTypeId();
        final Long unitId = event.getUnitId();

        String newNote = "Changed note";
        final Integer newDuration = 10;
        EventPutDto putDto = new EventPutDto();
        putDto.setNote(newNote);
        putDto.setDuration(newDuration);

        mockMvc.perform(put(baseUrl + "/" + eventId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(putDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.eventId").value(eventId))
                .andExpect(jsonPath("$.unitId").value(unitId))
                .andExpect(jsonPath("$.eventTypeId").value(eventTypeId))
                .andExpect(jsonPath("$.dateFrom").value(dateFrom.toString()))
                .andExpect(jsonPath("$.duration").value(newDuration))
                .andExpect(jsonPath("$.note").value(newNote));

        //second level validation
        assertAll("Assertion of just updated event",
                () -> assertTrue(eventRepository.findById(eventId).isPresent()),
                () -> assertSame(event, eventRepository.findById(eventId).get()),
                () -> assertEquals(eventId, event.getEventId()),
                () -> assertEquals(unitId, event.getUnitId()),
                () -> assertEquals(eventTypeId, event.getEventTypeId()),
                () -> assertEquals(dateFrom, event.getDateFrom()),
                () -> assertEquals(newDuration, event.getDuration()),
                () -> assertEquals(newNote, event.getNote())
        );
    }

    @Test
    @SqlGroup({@Sql("/db/scripts/spring/EventTestRequiredData.sql"),
            @Sql("/db/scripts/spring/InsertEventData.sql")})
    public void testDeleteEvent() throws Exception {
        List<Event> initializedData = eventRepository.findAll();
        int rowsNum = initializedData.size();
        assertTrue(rowsNum > 0);
        Event eventToDelete = initializedData.get(0);

        mockMvc.perform(delete(baseUrl + "/" + eventToDelete.getEventId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //assert after deleting one event
        List<Event> afterDeleteList = eventRepository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(eventToDelete));
    }

    @Test
    @SqlGroup({@Sql("/db/scripts/spring/EventTestRequiredData.sql"),
            @Sql("/db/scripts/spring/InsertEventData.sql")})
    public void testIntersections() throws Exception {
        //set dateFrom and duration to have intersection
        LocalDate dateFrom = LocalDate.of(2019, 9, 30);
        Integer duration = 5;
        EventPostDto postDto = new EventPostDto();
        postDto.setEventTypeId(1L);
        postDto.setUnitId(200L);
        postDto.setDateFrom(dateFrom);
        postDto.setDuration(duration);
        postDto.setNote("Intersection");

        MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(mvcResult.getResolvedException() instanceof ServiceException);
        assertEquals(EventService.INTERSECTION_OF_EVENTS, ((ServiceException)mvcResult.getResolvedException()).getCode());

    }
}
