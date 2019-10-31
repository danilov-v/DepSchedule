package com.varb.schedule.integration;

import com.varb.schedule.buisness.logic.controllers.apiimpl.UnitApiImpl;
import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.logic.repository.UnitRepository;
import com.varb.schedule.buisness.models.dto.*;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.exception.WebApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testing {@link UnitApiImpl}
 */
public class UnitApiTest extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/unit";
    private static final String UNIT_TREE = "/api/unit/tree";

    @Autowired
    private UnitRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("Получение списка подразделений")
    @Sql("/db/scripts/unit/InsertUnitData.sql")
    public void testGetUnitList() throws Exception {
        var entities = repository.findAll();

        //validate data has been initialized correctly
        assertTrue(entities.size() > 0);

        MvcResult mvcResult = mockMvc.perform(
                get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        final String responseStr = mvcResult.getResponse().getContentAsString();

        var dtoList = asObjectList(responseStr, UnitResponseDto.class);
        assertEquals(entities.size(), dtoList.size());

        for (var entity : entities) {
            var dto = dtoList.stream()
                    .filter(d -> d.getUnitId().equals(entity.getUnitId()))
                    .findFirst().get();

            //assertion according to the initialization data of sql scripts above
            assertAll("Has to return initialized before the test data",
                    //first entity
                    () -> assertEquals(entity.getUnitId(), dto.getUnitId()),
                    () -> assertEquals(entity.getCalendarId(), dto.getCalendarId()),
                    () -> assertEquals(entity.getParentId(), dto.getParentId()),
                    () -> assertEquals(entity.getTitle(), dto.getTitle()),
                    () -> assertEquals(entity.getFlag(), dto.getFlag()),
                    () -> assertEquals(entity.getPlanned(), dto.getPlanned())
            );
        }
    }

    @Nested
    @DisplayName("Получение древовидного списка подразделений")
    class GetUnitThree extends AbstractIntegrationTest {

        @Test
        @Sql({
                "/db/scripts/unit/InsertUnitData.sql",
                "/db/scripts/unit/UnitTestRequiredData.sql"
        })
        public void testGetUnitThree() throws Exception {
            final String dateFrom = "2019-09-01";
            final String calendarId = "2";

            final MvcResult mvcResult = mockMvc.perform(
                    get(UNIT_TREE)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .param("calendarId", calendarId)
                            .param("dateFrom", dateFrom))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();

            final String responseStr = mvcResult.getResponse().getContentAsString();

            var dtoList = asObjectList(responseStr, UnitResponseTreeDto.class);

            validateUnits(dtoList, calendarId);
            validateEvents(dtoList, calendarId, dateFrom);
        }

        private void validateUnits(List<UnitResponseTreeDto> dtoList, String calendarId) {
            List<Unit> entities = repository.findAll().stream()
                    .filter(u -> u.getCalendarId().toString().equals(calendarId))
                    .collect(Collectors.toList());

            //validate data has been initialized correctly
            assertTrue(entities.size() > 0);


            List<UnitResponseTreeDto> unitsFromResponse = unitStreamFromUnitThreeList(dtoList).collect(Collectors.toList());
            assertEquals(entities.size(), unitsFromResponse.size());

            for (var entity : entities) {
                var dto = unitsFromResponse.stream()
                        .filter(d -> d.getUnitId().equals(entity.getUnitId()))
                        .findFirst().get();

                //assertion according to the initialization data of sql scripts above
                assertAll("Has to return initialized before the test data",
                        //first entity
                        () -> assertEquals(entity.getUnitId(), dto.getUnitId()),
                        () -> assertEquals(entity.getCalendarId(), dto.getCalendarId()),
                        () -> assertEquals(entity.getParentId(), dto.getParentId()),
                        () -> assertEquals(entity.getTitle(), dto.getTitle()),
                        () -> assertEquals(entity.getFlag(), dto.getFlag()),
                        () -> assertEquals(entity.getPlanned(), dto.getPlanned())
                );
            }
        }

        private void validateEvents(List<UnitResponseTreeDto> dtoList, String calendarId, String dateFrom) {
            List<Event> entities = eventRepository.findAll().stream()
                    .filter(e -> e.getCalendarId().toString().equals(calendarId) &&
                            e.getDateFrom().compareTo(LocalDate.parse(dateFrom)) > 0)
                    .collect(Collectors.toList());

            //validate data has been initialized correctly
            assertTrue(entities.size() > 0);

            List<EventResponseDto> eventsFromResponse = eventStreamFromUnitThreeList(dtoList).collect(Collectors.toList());
            assertEquals(entities.size(), eventsFromResponse.size());

            for (var entity : entities) {
                var dto = eventsFromResponse.stream()
                        .filter(d -> d.getUnitId().equals(entity.getUnitId()))
                        .findFirst().get();

                //assertion according to the initialization data of sql scripts above
                assertAll("Has to return initialized before the test data",
                        //first entity
                        () -> assertEquals(entity.getCalendarId(), dto.getCalendarId()),
                        () -> assertEquals(entity.getCalendarId(), dto.getCalendarId())
                );
            }
        }

        private Stream<EventResponseDto> eventStreamFromUnitThreeList(List<UnitResponseTreeDto> unitList) {
            return unitList.stream()
                    .flatMap(this::streamOfUnit)
                    .flatMap(u -> u.getEvents().stream());
        }

        private Stream<UnitResponseTreeDto> unitStreamFromUnitThreeList(List<UnitResponseTreeDto> unitList) {
            return unitList.stream().flatMap(this::streamOfUnit);
        }

        private Stream<UnitResponseTreeDto> streamOfUnit(UnitResponseTreeDto unit) {
            if (unit.getChildUnit().isEmpty()) {
                return Stream.of(unit);
            } else {
                return Stream.concat(Stream.of(unit),
                        unit.getChildUnit().stream().flatMap(this::streamOfUnit));
            }
        }
    }


    @Nested
    @DisplayName("Создание нового подразделения")
    class PostUnit extends AbstractIntegrationTest {


        @Test
        @Sql("/db/scripts/unit/InsertUnitData.sql")
        @DisplayName("корректное создание")
        public void testPostUnit() throws Exception {
            long calendarId = 2L;
            long parentId = 210L;

            var unitSizeBefore = repository.findAll().size();

            var actualDto = new UnitPostDto()
                    .calendarId(calendarId)
                    .flag("flag")
                    .parentId(parentId)
                    .planned(true)
                    .title("2.1.3");

            MvcResult mvcResult = mockMvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(actualDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.unitId").isNumber())
                    .andExpect(jsonPath("$.title").value(actualDto.getTitle()))
                    .andExpect(jsonPath("$.flag").value(actualDto.getFlag()))
                    .andExpect(jsonPath("$.planned").value(actualDto.getPlanned()))
                    .andExpect(jsonPath("$.calendarId").value(actualDto.getCalendarId()))
                    .andExpect(jsonPath("$.parentId").value(actualDto.getParentId()))
                    .andReturn();

            //clear hibernate cache
            entityManager.flush();
            entityManager.clear();

            final String responseStr = mvcResult.getResponse().getContentAsString();

            Long unitId = asObject(responseStr, UnitResponseDto.class).getUnitId();

            //second level validation
            var entityOpt = repository.findById(unitId);
            assertTrue(entityOpt.isPresent());
            var entity = entityOpt.get();
            assertAll("Assertion of just added entity",
                    () -> assertEquals(unitId, entity.getUnitId()),
                    () -> assertEquals(actualDto.getTitle(), entity.getTitle()),
                    () -> assertEquals(actualDto.getFlag(), entity.getFlag()),
                    () -> assertEquals(actualDto.getPlanned(), entity.getPlanned()),
                    () -> assertEquals(actualDto.getCalendarId(), entity.getCalendarId()),
                    () -> assertEquals(actualDto.getParentId(), entity.getParentId()),
                    () -> assertEquals(0, entity.getEvents().size())
            );
        }


        @Test
        @Sql("/db/scripts/unit/InsertUnitData.sql")
        @DisplayName("... unit ссылается на unit другого календаря")
        public void testPostUnitWithIncorrectParentId() throws Exception {
            long calendarId = 2L;
            long parentId = 110L;

            var unitSizeBefore = repository.findAll().size();

            var actualDto = new UnitPostDto()
                    .calendarId(calendarId)
                    .flag("flag")
                    .parentId(parentId)
                    .planned(true)
                    .title("2.1.3");

            MvcResult mvcResult = mockMvc.perform(post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(actualDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();

            //we expect exception here
            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
        }

    }


    @Nested
    @DisplayName("Изменение существующего подразделения")
    class PutUnit extends AbstractIntegrationTest {

        @Test
        @DisplayName("корректное изменение")
        @Sql({
                "/db/scripts/unit/InsertUnitData.sql",
                "/db/scripts/unit/UnitTestRequiredData.sql"
        })
        public void testPutUnit() throws Exception {
            long unitIdToChange = 211;
            long parentId = 200L;

            var entityBeforeUpdateOpt = repository.findById(unitIdToChange);
            //validate data has been initialized correctly
            assertTrue(entityBeforeUpdateOpt.isPresent());
            var entityBeforeUpdate = entityBeforeUpdateOpt.get();
            //Lazy loading events
            entityBeforeUpdate.getEvents().forEach(Event::getEventId);
            entityManager.detach(entityBeforeUpdate);

            var actualDto = new UnitPutDto()
                    .flag("changed")
                    .planned(true)
                    .parentId(parentId)
                    .title("changed");

            mockMvc.perform(put(BASE_URL + "/" + unitIdToChange)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(actualDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.unitId").value(entityBeforeUpdate.getUnitId()))
                    .andExpect(jsonPath("$.title").value(actualDto.getTitle()))
                    .andExpect(jsonPath("$.flag").value(actualDto.getFlag()))
                    .andExpect(jsonPath("$.planned").value(actualDto.getPlanned()))
                    .andExpect(jsonPath("$.calendarId").value(entityBeforeUpdate.getCalendarId()))
                    .andExpect(jsonPath("$.parentId").value(actualDto.getParentId()));

            var entityAfterUpdate = repository.findById(entityBeforeUpdate.getUnitId()).get();
            //second level validation
            assertAll("Assertion of just updated entityBeforeUpdate",
                    () -> assertEquals(unitIdToChange, entityAfterUpdate.getUnitId().longValue()),
                    () -> assertEquals(actualDto.getTitle(), entityAfterUpdate.getTitle()),
                    () -> assertEquals(actualDto.getFlag(), entityAfterUpdate.getFlag()),
                    () -> assertEquals(actualDto.getPlanned(), entityAfterUpdate.getPlanned()),
                    () -> assertEquals(entityBeforeUpdate.getCalendarId(), entityAfterUpdate.getCalendarId()),
                    () -> assertEquals(actualDto.getParentId(), entityAfterUpdate.getParentId()),
                    () -> assertEquals(
                            entityBeforeUpdate.getEvents(),
                            entityAfterUpdate.getEvents()
                    )
            );
        }

        @Test
        @DisplayName("... unit ссылается на unit другого календаря")
        @Sql({
                "/db/scripts/unit/InsertUnitData.sql",
                "/db/scripts/unit/UnitTestRequiredData.sql"
        })
        public void testPutUnitWithIncorrectParentId() throws Exception {
            long unitIdToChange = 211;
            long parentId = 110L;

            var entityBeforeUpdateOpt = repository.findById(unitIdToChange);
            //validate data has been initialized correctly
            assertTrue(entityBeforeUpdateOpt.isPresent());

            var actualDto = new UnitPutDto()
                    .flag("changed")
                    .planned(true)
                    .parentId(parentId)
                    .title("changed");

            MvcResult mvcResult = mockMvc.perform(put(BASE_URL + "/" + unitIdToChange)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(asJsonString(actualDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andReturn();

            //we expect exception here
            assertTrue(mvcResult.getResolvedException() instanceof WebApiException);
        }
    }

    @Test
    @DisplayName("Удаление существующего подразделения")
    @Sql({
            "/db/scripts/unit/InsertUnitData.sql",
            "/db/scripts/unit/UnitTestRequiredData.sql"
    })
    public void testDeleteEvent() throws Exception {
        long unitIdToDelete = 212;
        var entitiesFromDb = repository.findAll();
        //validate data has been initialized correctly
        assertTrue(entitiesFromDb.size() > 0);

        int rowsNum = entitiesFromDb.size();
        var entityToDeleteOpt = entitiesFromDb.stream()
                .filter(u -> u.getUnitId().equals(unitIdToDelete))
                .findFirst();

        assertTrue(entityToDeleteOpt.isPresent());
        var entityToDelete = entityToDeleteOpt.get();

        Optional<Event> eventOpt = eventRepository.findAll().stream().filter(e -> e.getUnit().getUnitId().equals(unitIdToDelete)).findFirst();
        assertTrue(eventOpt.isPresent());
        Event event = eventOpt.get();

        mockMvc.perform(delete(BASE_URL + "/" + entityToDelete.getUnitId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //assert after deleting one unit
        var afterDeleteList = repository.findAll();
        assertEquals(rowsNum - 1, afterDeleteList.size());
        assertFalse(afterDeleteList.contains(entityToDelete));

        assertAll("Assertion of cascade delete",
                () -> assertEquals(0, eventRepository.findAll().stream().filter(e -> e.getUnit().getUnitId().equals(unitIdToDelete)).count()),
                () -> assertFalse(eventRepository.findById(event.getEventId()).isPresent())
        );
    }
}
