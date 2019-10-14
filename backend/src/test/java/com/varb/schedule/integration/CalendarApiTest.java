package com.varb.schedule.integration;

import com.varb.schedule.buisness.logic.repository.CalendarRepository;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.dto.CalendarResponseDto;
import com.varb.schedule.buisness.models.entity.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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
        List<Calendar> entitiesFromDb = repository.findAll();

        //validate data has been initialized correctly
        assertTrue(entitiesFromDb.size() > 0);

        final String responseStr = mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();

        List<CalendarResponseDto> actualEntities = asObjectList(responseStr, CalendarResponseDto.class);
        assertEquals(entitiesFromDb.size(), actualEntities.size());

        for (Calendar entityFromDb : entitiesFromDb) {
            CalendarResponseDto actualEntity = actualEntities.stream()
                    .filter(dto -> dto.getCalendarId().equals(entityFromDb.getCalendarId())).findFirst().get();

            //assertion according to the initialization data of sql scripts above
            assertAll("Has to return initialized before the test data",
                    //first entity
                    () -> assertEquals(entityFromDb.getCalendarId(), actualEntity.getCalendarId()),
                    () -> assertEquals(entityFromDb.getName(), actualEntity.getName()),
                    () -> assertEquals(entityFromDb.isAstronomical(), actualEntity.getIsAstronomical()),
                    () -> assertEquals(entityFromDb.getShift(), actualEntity.getShift().intValue())
            );
        }
    }

    @Test
    @DisplayName("Получение конкретного календаря")
    @Sql("/db/scripts/spring/InsertCalendarData.sql")
    public void testGetCalendar() throws Exception {
        List<Calendar> entitiesFromDb = repository.findAll();

        //validate data has been initialized correctly
        assertTrue(entitiesFromDb.size() > 0);
        Calendar entityFromDb = entitiesFromDb.get(0);

        final String responseStr = mockMvc.perform(get(baseUrl + "/" + entityFromDb.getCalendarId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                .getResponse().getContentAsString();

        CalendarResponseDto actualEntity = asObject(responseStr, CalendarResponseDto.class);

        //assertion according to the initialization data of sql scripts above
        assertAll("Has to return initialized before the test data",
                //first entity
                () -> assertEquals(entityFromDb.getCalendarId(), actualEntity.getCalendarId()),
                () -> assertEquals(entityFromDb.getName(), actualEntity.getName()),
                () -> assertEquals(entityFromDb.isAstronomical(), actualEntity.getIsAstronomical()),
                () -> assertEquals(entityFromDb.getShift(), actualEntity.getShift().intValue())
        );
    }

    @Test
    @DisplayName("Создание нового календаря")
    public void testPostCalendar() throws Exception {
        CalendarBaseReqDto actualDto = new CalendarBaseReqDto()
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
        List<Calendar> afterAdd = repository.findAll();
        assertEquals(1, afterAdd.size());
        Calendar entityFromDb = afterAdd.get(0);
        assertAll("Assertion of just added period",
                () -> assertNotNull(entityFromDb.getCalendarId()),
                () -> assertEquals(actualDto.getName(), entityFromDb.getName()),
                () -> assertEquals(actualDto.getShift().intValue(), entityFromDb.getShift()),
                () -> assertEquals(actualDto.getIsAstronomical(), entityFromDb.isAstronomical())
        );
    }

    @Test
    @DisplayName("Изменение существующего календаря")
    @Sql("/db/scripts/spring/InsertCalendarData.sql")
    public void testPutCalendar() throws Exception {

        List<Calendar> entitiesFromDb = repository.findAll();
        //validate data has been initialized correctly
        assertTrue(entitiesFromDb.size() > 0);

        Calendar entityBeforeUpdate = entitiesFromDb.get(0);
        entityManager.detach(entityBeforeUpdate);

        String newNote = "Changed note";
        CalendarBaseDto actualDto = new CalendarBaseDto()
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

        Calendar entityAfterUpdate = repository.findById(entityBeforeUpdate.getCalendarId()).get();
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
        List<Calendar> entitiesFromDb = repository.findAll();
        //validate data has been initialized correctly
        assertTrue(entitiesFromDb.size() > 0);

        int rowsNum = entitiesFromDb.size();
        Calendar calendarToDelete = entitiesFromDb.get(0);

        mockMvc.perform(delete(baseUrl + "/" + calendarToDelete.getCalendarId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());


        //assert after deleting one event
        List<Calendar> afterDeleteList = repository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(calendarToDelete));
    }
}
