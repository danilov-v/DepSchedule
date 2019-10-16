package com.varb.schedule.integration;

import com.varb.schedule.buisness.logic.repository.CalendarRepository;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.dto.CalendarResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CalendarApiTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/calendar";

    @Autowired
    private CalendarRepository repository;

    @Test
    @DisplayName("Получение списка календарей")
    @Sql("/db/scripts/spring/InsertCalendarData.sql")
    public void testGetCalendarList() throws Exception {
        var entities = repository.findAll();

        //validate data has been initialized correctly
        assertTrue(entities.size() > 0);

        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        final String responseStr = mvcResult.getResponse().getContentAsString();

        var dtoList = asObjectList(responseStr, CalendarResponseDto.class);
        assertEquals(entities.size(), dtoList.size());

        for (var entity : entities) {
            var dto = dtoList.stream()
                    .filter(d -> d.getCalendarId().equals(entity.getCalendarId())).findFirst().get();

            //assertion according to the initialization data of sql scripts above
            assertAll("Has to return initialized before the test data",
                    //first entity
                    () -> assertEquals(entity.getCalendarId(), dto.getCalendarId()),
                    () -> assertEquals(entity.getName(), dto.getName()),
                    () -> assertEquals(entity.isAstronomical(), dto.getIsAstronomical()),
                    () -> assertEquals(entity.getShift(), dto.getShift().intValue())
            );
        }
    }

    @Test
    @DisplayName("Получение конкретного календаря")
    @Sql("/db/scripts/spring/InsertCalendarData.sql")
    public void testGetCalendar() throws Exception {
        var entities = repository.findAll();

        //validate data has been initialized correctly
        assertTrue(entities.size() > 0);
        var entity = entities.get(0);

        final MvcResult mvcResult = mockMvc.perform(get(baseUrl + "/" + entity.getCalendarId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        final String responseStr = mvcResult.getResponse().getContentAsString();

        var dto = asObject(responseStr, CalendarResponseDto.class);

        //assertion according to the initialization data of sql scripts above
        assertAll("Has to return initialized before the test data",
                //first entity
                () -> assertEquals(entity.getCalendarId(), dto.getCalendarId()),
                () -> assertEquals(entity.getName(), dto.getName()),
                () -> assertEquals(entity.isAstronomical(), dto.getIsAstronomical()),
                () -> assertEquals(entity.getShift(), dto.getShift().intValue())
        );
    }

    @Test
    @DisplayName("Создание нового календаря")
    public void testPostCalendar() throws Exception {
        var actualDto = new CalendarBaseReqDto()
                .isAstronomical(false)
                .name("test calendar")
                .shift(5);

        mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(actualDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.calendarId").isNumber())
                .andExpect(jsonPath("$.name").value(actualDto.getName()))
                .andExpect(jsonPath("$.shift").value(actualDto.getShift()))
                .andExpect(jsonPath("$.isAstronomical").value(actualDto.getIsAstronomical()))
        ;

        //second level validation
        var entities = repository.findAll();
        assertEquals(1, entities.size());
        var entity = entities.get(0);
        assertAll("Assertion of just added period",
                () -> assertNotNull(entity.getCalendarId()),
                () -> assertEquals(actualDto.getName(), entity.getName()),
                () -> assertEquals(actualDto.getShift().intValue(), entity.getShift()),
                () -> assertEquals(actualDto.getIsAstronomical(), entity.isAstronomical())
        );
    }

    @Test
    @DisplayName("Изменение существующего календаря")
    @Sql("/db/scripts/spring/InsertCalendarData.sql")
    public void testPutCalendar() throws Exception {

        var entitiesBeforeUpdate = repository.findAll();
        //validate data has been initialized correctly
        assertTrue(entitiesBeforeUpdate.size() > 0);

        var entityBeforeUpdate = entitiesBeforeUpdate.get(0);
        entityManager.detach(entityBeforeUpdate);

        var actualDto = new CalendarBaseDto()
                .name("newName")
                .shift(12);

        mockMvc.perform(put(baseUrl + "/" + entityBeforeUpdate.getCalendarId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(actualDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.calendarId").value(entityBeforeUpdate.getCalendarId()))
                .andExpect(jsonPath("$.name").value(actualDto.getName()))
                .andExpect(jsonPath("$.shift").value(actualDto.getShift()))
                .andExpect(jsonPath("$.isAstronomical").value(entityBeforeUpdate.isAstronomical()));

        var entityAfterUpdate = repository.findById(entityBeforeUpdate.getCalendarId()).get();
        //second level validation
        assertAll("Assertion of just updated entityBeforeUpdate",
                () -> assertEquals(entityBeforeUpdate.getCalendarId(), entityAfterUpdate.getCalendarId()),
                () -> assertEquals(actualDto.getName(), entityAfterUpdate.getName()),
                () -> assertEquals(actualDto.getShift().intValue(), entityAfterUpdate.getShift()),
                () -> assertEquals(entityBeforeUpdate.isAstronomical(), entityAfterUpdate.isAstronomical())
        );
    }

    @Test
    @DisplayName("Удаление существующего календаря")
    @Sql("/db/scripts/spring/InsertCalendarData.sql")
    public void testDeleteEvent() throws Exception {
        var entitiesFromDb = repository.findAll();
        //validate data has been initialized correctly
        assertTrue(entitiesFromDb.size() > 0);

        int rowsNum = entitiesFromDb.size();
        var calendarToDelete = entitiesFromDb.get(0);

        mockMvc.perform(delete(baseUrl + "/" + calendarToDelete.getCalendarId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());


        //assert after deleting one event
        var afterDeleteList = repository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(calendarToDelete));
    }
}
