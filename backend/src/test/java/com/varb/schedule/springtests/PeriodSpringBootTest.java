package com.varb.schedule.springtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.logic.service.PeriodService;
import com.varb.schedule.buisness.logic.service.ValidationService;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.dto.PeriodResponseDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PeriodSpringBootTest extends AbstractIntegrationTest {

    private final String baseUrl = "/api/period";

    @Autowired
    private PeriodRepository periodRepository;

    @Test
    @Sql("/db/scripts/spring/InsertPeriodData.sql")
    public void testGetPeriod() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        List<PeriodResponseDto> dtoList = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<PeriodResponseDto>>() {});

        //assertion according to the initialization data of InsertEventTypeData.sql
        assertAll("Has to return initialized before the test periods",
                () -> assertNotNull(dtoList.get(0).getPeriodId()),
                () -> assertEquals("Period 1", dtoList.get(0).getName()),
                () -> assertEquals(LocalDate.of(2019, 9, 26), dtoList.get(0).getStartDate()),
                () -> assertEquals(LocalDate.of(2019, 9, 30), dtoList.get(0).getEndDate()),
                () -> assertNotNull(dtoList.get(1).getPeriodId()),
                () -> assertEquals("Period 2", dtoList.get(1).getName()),
                () -> assertEquals(LocalDate.of(2019, 10, 03), dtoList.get(1).getStartDate()),
                () -> assertEquals(LocalDate.of(2019, 10, 05), dtoList.get(1).getEndDate())
        );
    }

    @Test
    public void testPostPeriod() throws Exception {
        final LocalDate startDate = LocalDate.of(2019, 5, 28);
        final LocalDate endDate = LocalDate.of(2020, 5, 27);
        String name = "Just added period";
        PeriodPostDto postDto = new PeriodPostDto();
        postDto.setName(name);
        postDto.setStartDate(startDate);
        postDto.setEndDate(endDate);

        mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.periodId").isNumber())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.endDate").value(endDate.toString()));

        //second level validation
        List<Period> afterAdd = periodRepository.findAll();
        assertTrue(afterAdd.size() == 1);
        Period period = afterAdd.get(0);
        assertAll("Assertion of just added period",
                () -> assertNotNull(period.getPeriodId()),
                () -> assertEquals(name, period.getName()),
                () -> assertEquals(startDate, period.getStartDate()),
                () -> assertEquals(endDate, period.getEndDate())
        );
    }

    @Test
    @Sql("/db/scripts/spring/InsertPeriodData.sql")
    public void testPutPeriod() throws Exception {
        List<Period> initializedData = periodRepository.findAll();
        //validate data has been initialized correctly
        assertTrue(initializedData.size() > 0);
        Period period = initializedData.get(0);

        long periodId = period.getPeriodId();
        String newName = "Changed name";
        final LocalDate startDate = period.getStartDate();
        final LocalDate endDate = period.getEndDate();
        PeriodPutDto putDto = new PeriodPutDto();
        putDto.setName(newName);

        mockMvc.perform(put(baseUrl + "/" + period.getPeriodId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(putDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.periodId").value(periodId))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.endDate").value(endDate.toString()));

        //second level validation
        assertAll("Assertion of just updated period",
                () -> assertTrue(periodRepository.findById(periodId).isPresent()),
                () -> assertTrue(period == periodRepository.findById(periodId).get()),
                () -> assertEquals(periodId, period.getPeriodId().longValue()),
                () -> assertEquals(newName, period.getName()),
                //It is correct here. As LocalDate is immutable and was not changed, links are supposed to be the same
                () -> assertTrue(startDate == period.getStartDate()),
                () -> assertTrue(endDate == period.getEndDate())
        );
    }

    @Test
    @Sql("/db/scripts/spring/InsertPeriodData.sql")
    public void testDeletePeriod() throws Exception {
        List<Period> initializedData = periodRepository.findAll();
        int rowsNum = initializedData.size();
        assertTrue(rowsNum > 0);
        Period periodToDelete = initializedData.get(0);

        mockMvc.perform(delete(baseUrl + "/" + periodToDelete.getPeriodId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //assert after deleting one event type
        List<Period> afterDeleteList = periodRepository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(periodToDelete));
    }

    @Test
    @Sql("/db/scripts/spring/InsertPeriodData.sql")
    public void testWrongDatesAndIntersections() throws Exception {
        PeriodPostDto postDto = new PeriodPostDto();
        postDto.setName("Intersection");
        postDto.setStartDate(LocalDate.of(2019, 9, 29));
        postDto.setEndDate(LocalDate.of(2019, 10, 03));

        MvcResult mvcResult = mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(mvcResult.getResolvedException() instanceof ServiceException);
        assertEquals(PeriodService.DATES_INTERSECTION, ((ServiceException)mvcResult.getResolvedException()).getCode());

        postDto.setEndDate(LocalDate.of(2019, 9, 28));

        mvcResult = mockMvc.perform(post(baseUrl)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(postDto)))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(mvcResult.getResolvedException() instanceof ServiceException);
        assertEquals(ValidationService.WRONG_DATES, ((ServiceException)mvcResult.getResolvedException()).getCode());
    }
}
