package com.varb.schedule.integration;

import com.varb.schedule.buisness.logic.repository.EventDurationRepository;
import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.logic.service.EventService;
import com.varb.schedule.buisness.logic.service.ValidationService;
import com.varb.schedule.buisness.models.dto.*;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.exception.WebApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventApiTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/event";
    private final String eventDurationUrl = "/api/eventDuration";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventDurationRepository durationRepository;

    @Nested
    @DisplayName("Получение списка событий")
    @SqlGroup({@Sql("/db/scripts/event/EventTestRequiredData.sql"),
            @Sql("/db/scripts/event/InsertEventData.sql")})
    class GetEvent extends AbstractIntegrationTest {

        @Test
        @DisplayName("Только с обязательными параметрами")
        public void withOnlyDateFromParam() throws Exception {
            Long calendarId = 1L;

            MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                    .param("calendarId", calendarId.toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();

            var dtoList = asObjectList(mvcResult.getResponse().getContentAsString(), EventResponseDto.class);
            var entities = eventRepository.findByCalendarId(calendarId);

            assertList(entities, dtoList);
        }

        @Test
        @DisplayName("Без обязательных параметров - исключение")
        public void withoutMandatoryParam() throws Exception {
            //request without mandatory calendarId param
            MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();

            //we expect exception here
            assertTrue(mvcResult.getResolvedException() instanceof MissingServletRequestParameterException);
        }

        @Test
        @DisplayName("Список последних событий")
        public void getEventRecentList() throws Exception {
            Long calendarId = 1L;
            int count = 2;

            MvcResult mvcResult = mockMvc.perform(get(baseUrl +"/recentList")
                    .param("count", String.valueOf(count))
                    .param("calendarId", calendarId.toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();

            var dtoList = asObjectList(mvcResult.getResponse().getContentAsString(), EventRecentResponseDto.class);

            assertEquals(count, dtoList.size());
            for(var dto : dtoList) {
                assertEquals(calendarId, dto.getCalendarId());
            }
        }

    }

    @Nested
    @DisplayName("Добавление события")
    @Sql(value = "/db/scripts/event/EventTestRequiredData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    class PostEvent extends AbstractIntegrationTest {

        //share post body parameters for all tests
        final Long calendarId = 1L;
        final Long unitId = 200L;
        final Long eventTypeId = 1L;
        final LocalDate dateFrom = LocalDate.of(2019, 5, 20);
        final LocalDate dateTo = LocalDate.of(2019, 5, 25);
        final String note = "Note here";
        final LocationDto location = new LocationDto().name("Minsk").type(LocationDto.TypeEnum.STATICAL);
        final Boolean plannedDefault = false;

        @Test
        @DisplayName("Успешно - с обязатльными параметрами")
        public void postEventSuccess() throws Exception {

            //don't set planned param - it's false by default
            EventReqDto postDto = (EventReqDto) new EventReqDto()
                    .calendarId(calendarId)
                    .eventTypeId(eventTypeId)
                    .unitId(unitId)
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
                    .note(note)
                    .location(location);

            MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(postDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.eventId").isNumber())
                    .andExpect(jsonPath("$.calendarId").value(calendarId))
                    .andExpect(jsonPath("$.unitId").value(unitId))
                    .andExpect(jsonPath("$.eventTypeId").value(eventTypeId))
                    .andExpect(jsonPath("$.dateFrom").value(dateFrom.toString()))
                    .andExpect(jsonPath("$.dateTo").value(dateTo.toString()))
                    .andExpect(jsonPath("$.note").value(note))
                    .andExpect(jsonPath("$.location.name").value(location.getName()))
                    .andExpect(jsonPath("$.location.type").value(location.getType().toString()))
                    .andExpect(jsonPath("$.planned").value(plannedDefault))
                    .andReturn();

            //second level validation
            var dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EventResponseDto.class);
            var entity = eventRepository.findById(dto.getEventId()).orElse(null);

            assertEntityWithDto(entity, dto);
        }

        @Test
        @DisplayName("Исключение - календарь события отличается от календаря подразделения")
        public void calendarDiffers() throws Exception {
            Long calendarId = 2L;

            EventReqDto postDto = new EventReqDto()
                    .calendarId(calendarId)
                    .eventTypeId(eventTypeId)
                    .unitId(unitId)
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
                    .note(note)
                    .location(location);

            MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(postDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            //we expect exception here
            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
        }

        @Test
        @DisplayName("Исключение - calendarId не указан")
        public void noCalendarIdParam() throws Exception {

            EventReqDto postDto = new EventReqDto()
                    .eventTypeId(eventTypeId)
                    .unitId(unitId)
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
                    .note(note)
                    .location(location);

            MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(postDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            //we expect exception here
            assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException);
        }

        //TODO SqlMergeMode - replace with
        @Test
        @DisplayName("Исключение - событие пересекается с существующим")
        @SqlGroup({@Sql("/db/scripts/event/EventTestRequiredData.sql"),
                @Sql("/db/scripts/event/InsertEventData.sql")})
        public void intersections() throws Exception {
            //set dateFrom and dateTo to have intersection
            LocalDate dateFrom = LocalDate.of(2019, 9, 20);
            LocalDate dateTo = LocalDate.of(2019, 9, 27);

            EventReqDto postDto = new EventReqDto()
                    .calendarId(calendarId)
                    .eventTypeId(eventTypeId)
                    .unitId(unitId)
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
                    .note(note)
                    .location(location);

            MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(postDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
            assertEquals(EventService.INTERSECTION_OF_EVENTS, ((WebApiException) mvcResult.getResolvedException()).getCode());

        }
    }

    @Nested
    @DisplayName("Изменение события")
    @SqlGroup({@Sql("/db/scripts/event/EventTestRequiredData.sql"),
            @Sql("/db/scripts/event/InsertEventData.sql")})
    class patchEvent extends AbstractIntegrationTest {

        //новые значения для всех разрешенных для изменения параметров - на основе первой записи в БД
        final Long newUnitId = 300L;
        final LocalDate newDateFrom = LocalDate.of(2019, 9, 27);
        final LocalDate newDateTo = LocalDate.of(2019, 9, 29);
        final LocationDto newLocationDto = new LocationDto().name("Minsk 2").type(LocationDto.TypeEnum.DISTRICT);
        final Boolean newPlanned = true;
        final String newNote = "Changed note";
        final Long newEventTypeId = 2L;

        @Test
        @DisplayName("Успешно - изменяем все доступные параметры")
        public void patchEventSuccess() throws Exception {
            var initializedData = eventRepository.findAll();
            //validate data has been initialized correctly
            assertTrue(initializedData.size() > 0);

            var entityBase = initializedData.get(0);
            final Long eventId = entityBase.getEventId();

            EventDto patchDto = new EventDto()
                    .unitId(newUnitId)
                    .dateFrom(newDateFrom)
                    .dateTo(newDateTo)
                    .location(newLocationDto)
                    .planned(newPlanned)
                    .note(newNote)
                    .eventTypeId(newEventTypeId);

            MvcResult mvcResult = mockMvc.perform(patch(baseUrl + "/" + eventId)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(patchDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.eventId").value(eventId))
                    .andExpect(jsonPath("$.calendarId").value(entityBase.getCalendarId()))
                    .andExpect(jsonPath("$.unitId").value(newUnitId))
                    .andExpect(jsonPath("$.eventTypeId").value(newEventTypeId))
                    .andExpect(jsonPath("$.dateFrom").value(newDateFrom.toString()))
                    .andExpect(jsonPath("$.dateTo").value(newDateTo.toString()))
                    .andExpect(jsonPath("$.note").value(newNote))
                    .andExpect(jsonPath("$.location.name").value(newLocationDto.getName()))
                    .andExpect(jsonPath("$.location.type").value(newLocationDto.getType().toString()))
                    .andExpect(jsonPath("$.planned").value(newPlanned))
                    .andReturn();

            var dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EventResponseDto.class);
            var entityResult = eventRepository.findById(eventId).orElse(null);

            //second level validation
            assertEntityWithDto(entityResult, dto);
        }

        @Test
        @DisplayName("Успешно - проверяем, что eventDuration обновился")
        public void checkEventDurationIsUpdated() throws Exception {
            var initializedData = eventRepository.findAll();
            //validate data has been initialized correctly
            assertTrue(initializedData.size() > 0);

            var entityBase = initializedData.get(0);
            final Long eventId = entityBase.getEventId();
            EventDto patchDto = new EventDto()
                    .dateFrom(newDateFrom)
                    .dateTo(newDateTo);

            //perform update
            mockMvc.perform(patch(baseUrl + "/" + eventId)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(patchDto)))
                    .andExpect(status().isOk());

            MvcResult mvcResult = mockMvc.perform(get(eventDurationUrl)
                    .param("unitId", entityBase.getUnitId().toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andReturn();

            var dtoList = asObjectList(mvcResult.getResponse().getContentAsString(), EventDurationResponseDto.class);
            //take corresponding eventDuration
            var dto = dtoList.stream()
                    .filter(d -> d.getEventTypeId().equals(entityBase.getEventTypeId()))
                    .findFirst().get();

            //check that event duration is set
            assertNotNull(dto);
            assertEquals((int) ChronoUnit.DAYS.between(newDateFrom, newDateTo), dto.getDuration().intValue());
        }

        @Test
        @DisplayName("Исключение - выбираем UnitId из другого календаря")
        public void changeUnitException() throws Exception {
            var initializedData = eventRepository.findAll();
            //validate data has been initialized correctly
            assertTrue(initializedData.size() > 0);

            var entityBase = initializedData.get(0);
            final Long eventId = entityBase.getEventId();
            final Long newUnitId = 400L;
            EventDto patchDto = new EventDto()
                    .unitId(newUnitId);

            MvcResult mvcResult = mockMvc.perform(patch(baseUrl + "/" + eventId)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(patchDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            //we expect exception here
            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);

        }

        @Test
        @DisplayName("Исключение - событие пересекается с существующим")
        public void intersections() throws Exception {
            var initializedData = eventRepository.findAll();
            //validate data has been initialized correctly
            assertTrue(initializedData.size() > 0);

            var entityBase = initializedData.get(0);
            final Long eventId = entityBase.getEventId();
            //set dateTo to have intersection
            LocalDate dateTo = LocalDate.of(2019, 10, 4);
            EventDto patchDto = new EventDto()
                    .dateTo(dateTo);

            MvcResult mvcResult = mockMvc.perform(patch(baseUrl + "/" + eventId)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(patchDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
            assertEquals(EventService.INTERSECTION_OF_EVENTS, ((WebApiException) mvcResult.getResolvedException()).getCode());

        }

        //TODO SET DURATION NOT NEGATIVE
        @Test
        @DisplayName("Исключение - дата начала события больше даты его окончания")
        public void dateFromGreaterThanDateTo() throws Exception {
            var initializedData = eventRepository.findAll();
            //validate data has been initialized correctly
            assertTrue(initializedData.size() > 0);

            var entityBase = initializedData.get(0);
            final Long eventId = entityBase.getEventId();
            //set dateTo to have intersection
            LocalDate dateTo = LocalDate.of(2019, 9, 25);
            EventDto patchDto = new EventDto()
                    .dateTo(dateTo);

            MvcResult mvcResult = mockMvc.perform(patch(baseUrl + "/" + eventId)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(patchDto)))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
            assertEquals(ValidationService.WRONG_DATES, ((WebApiException) mvcResult.getResolvedException()).getCode());

        }
    }

    @Test
    @SqlGroup({@Sql("/db/scripts/event/EventTestRequiredData.sql"),
            @Sql("/db/scripts/event/InsertEventData.sql")})
    public void testDeleteEvent() throws Exception {
        var initializedData = eventRepository.findAll();
        int rowsNum = initializedData.size();
        assertTrue(rowsNum > 0);
        var entityToDelete = initializedData.get(0);

        mockMvc.perform(delete(baseUrl + "/" + entityToDelete.getEventId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //assert after deleting one event
        var afterDeleteList = eventRepository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(entityToDelete));
    }

    private void assertList(List<Event> entities, List<EventResponseDto> dtoList) {
        assertEquals(entities.size(), dtoList.size());

        for (var entity : entities) {
            var dto = dtoList.stream()
                    .filter(d -> d.getEventId().equals(entity.getEventId()))
                    .findFirst().get();

            assertEntityWithDto(entity, dto);
        }
    }

    //TODO sometimes expected result is DTO params, not entity
    private void assertEntityWithDto(final Event entity, final EventResponseDto dto) {
        assertNotNull(entity);
        assertNotNull(dto);

        assertAll("Compare entity with dto",
                () -> assertEquals(entity.getEventId(), dto.getEventId()),
                () -> assertEquals(entity.getEventTypeId(), dto.getEventTypeId()),
                () -> assertEquals(entity.getUnitId(), dto.getUnitId()),
                () -> assertEquals(entity.getDateFrom(), dto.getDateFrom()),
                () -> assertEquals(entity.getDateTo(), dto.getDateTo()),
                () -> assertEquals(entity.getNote(), dto.getNote()),
                () -> assertEquals(entity.getPlanned(), dto.getPlanned()),
                () -> assertEquals(entity.getLocationName(), dto.getLocation().getName()),
                () -> assertEquals(entity.getLocationType().toString(), dto.getLocation().getType().toString()),
                () -> assertEquals(entity.getCalendarId(), dto.getCalendarId())
        );
    }

}
