package com.varb.schedule.integration;

import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.logic.service.PeriodService;
import com.varb.schedule.buisness.logic.service.ValidationService;
import com.varb.schedule.buisness.models.dto.PeriodDto;
import com.varb.schedule.buisness.models.dto.PeriodReqDto;
import com.varb.schedule.buisness.models.dto.PeriodResponseDto;
import com.varb.schedule.exception.WebApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PeriodApiTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/period";

    @Autowired
    private PeriodRepository periodRepository;

    @Test
    @Sql("/db/scripts/period/InsertPeriodData.sql")
    public void testGetPeriod() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        var dtoList = asListOfObjects(mvcResult, PeriodResponseDto.class);

        var entityList = periodRepository.findAll();
        //assertion according to the initialization data of InsertEventTypeData.sql

        //TODO replace with loop
        assertAll("Has to return initialized before the test periods",
                () -> assertEquals(entityList.size(), dtoList.size()),
                () -> assertEquals(entityList.get(0).getPeriodId(), dtoList.get(0).getPeriodId()),
                () -> assertEquals(entityList.get(0).getCalendarId(), dtoList.get(0).getCalendarId()),
                () -> assertEquals(entityList.get(0).getName(), dtoList.get(0).getName()),
                () -> assertEquals(entityList.get(0).getStartDate(), dtoList.get(0).getStartDate()),
                () -> assertEquals(entityList.get(0).getEndDate(), dtoList.get(0).getEndDate()),
                //Assert another period
                () -> assertEquals(entityList.get(1).getPeriodId(), dtoList.get(1).getPeriodId()),
                () -> assertEquals(entityList.get(1).getCalendarId(), dtoList.get(1).getCalendarId()),
                () -> assertEquals(entityList.get(1).getName(), dtoList.get(1).getName()),
                () -> assertEquals(entityList.get(1).getStartDate(), dtoList.get(1).getStartDate()),
                () -> assertEquals(entityList.get(1).getEndDate(), dtoList.get(1).getEndDate())
        );
    }

    @Test
    @Sql("/db/scripts/period/InsertPeriodData.sql")
    public void testPostPeriod() throws Exception {
        int beforeAddSize = periodRepository.findAll().size();

        final LocalDate startDate = LocalDate.of(2019, 10, 17);
        final LocalDate endDate = LocalDate.of(2020, 5, 27);
        String name = "Just added period";
        final Long calendarId = 1L;
        var postDto = new PeriodReqDto();
        postDto.calendarId(calendarId)
                .name(name)
                .startDate(startDate)
                .endDate(endDate);

        mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.periodId").isNumber())
                .andExpect(jsonPath("$.calendarId").value(calendarId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.endDate").value(endDate.toString()));

        //second level validation
        var afterAdd = periodRepository.findAll();
        assertTrue(afterAdd.size() == beforeAddSize + 1);
        //take the last entity from list. We expect it is the one we added
        var entity = afterAdd.get(beforeAddSize);
        assertAll("Assertion of just added period",
                () -> assertNotNull(entity.getPeriodId()),
                () -> assertEquals(calendarId, entity.getCalendarId()),
                () -> assertEquals(name, entity.getName()),
                () -> assertEquals(startDate, entity.getStartDate()),
                () -> assertEquals(endDate, entity.getEndDate())
        );
    }

    @Test
    @Sql("/db/scripts/period/InsertPeriodData.sql")
    public void testpatchPeriod() throws Exception {
        var initializedData = periodRepository.findAll();
        //validate data has been initialized correctly
        assertTrue(initializedData.size() > 0);
        var baseEntity = initializedData.get(0);

        final Long periodId = baseEntity.getPeriodId();
        final Long calendarId = baseEntity.getCalendarId();
        String newName = "Changed name";
        final LocalDate startDate = baseEntity.getStartDate();
        final LocalDate endDate = baseEntity.getEndDate();
        var patchDto = new PeriodDto();
        patchDto.setName(newName);

        mockMvc.perform(patch(baseUrl + "/" + periodId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.periodId").value(periodId))
                .andExpect(jsonPath("$.calendarId").value(calendarId))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.endDate").value(endDate.toString()));

        assertTrue(periodRepository.findById(periodId).isPresent());
        var resultEntity = periodRepository.findById(periodId).get();
        //second level validation
        assertAll("Assertion of just updated period",
                () -> assertEquals(periodId, resultEntity.getPeriodId()),
                () -> assertEquals(calendarId, resultEntity.getCalendarId()),
                () -> assertEquals(newName, resultEntity.getName()),
                () -> assertEquals(startDate, resultEntity.getStartDate()),
                () -> assertEquals(endDate, resultEntity.getEndDate())
        );
    }

    @Test
    @Sql("/db/scripts/period/InsertPeriodData.sql")
    public void testDeletePeriod() throws Exception {
        var initializedData = periodRepository.findAll();
        int rowsNum = initializedData.size();
        assertTrue(rowsNum > 0);
        var entityToDelete = initializedData.get(0);

        mockMvc.perform(delete(baseUrl + "/" + entityToDelete.getPeriodId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //assert after deleting one period
        var afterDeleteList = periodRepository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(entityToDelete));
    }

    @Test
    @Sql("/db/scripts/period/InsertPeriodData.sql")
    public void testWrongDatesAndIntersections() throws Exception {
        var postDto = new PeriodReqDto();
        postDto.calendarId(1L)
                .name("Intersection")
                .startDate(LocalDate.of(2019, 9, 29))
                .endDate(LocalDate.of(2019, 10, 03));

        MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isBadRequest()).andReturn();

        //we expect intersection of periods in the first calendar
        assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
        assertEquals(PeriodService.DATES_INTERSECTION, ((WebApiException)mvcResult.getResolvedException()).getCode());

        postDto.setEndDate(LocalDate.of(2019, 9, 28));

        mvcResult = mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
        assertEquals(ValidationService.WRONG_DATES, ((WebApiException)mvcResult.getResolvedException()).getCode());
    }
}
