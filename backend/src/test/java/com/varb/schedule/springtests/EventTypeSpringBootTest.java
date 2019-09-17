package com.varb.schedule.springtests;

import com.varb.schedule.buisness.models.dto.EventTypePostDto;
import com.varb.schedule.buisness.models.dto.EventTypeResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@Sql(scripts = {"/db/scripts/cleanup.sql", "/db/scripts/initEventTypeTest.sql"})
public class EventTypeSpringBootTest extends AbstractIntegrationTest {

    @Test
    public void testGetEventType() throws Exception {
        //List<EventTypeResponseDto> response = this.restTemplate.getForObject(this.getBaseUrl(), List.class);
        //assert response.size() == 2;

        ResponseEntity<List<EventTypeResponseDto>> dtoList = restTemplate.exchange(
                this.getBaseUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EventTypeResponseDto>>(){}
        );

        assertEquals(2, dtoList.getBody().size());
    }

    @Test
    public void testPostEventType() {
        EventTypePostDto eventTypePost = new EventTypePostDto();
        eventTypePost.setColor("Purple");
        eventTypePost.setDescription("Description");
        ResponseEntity<EventTypeResponseDto> response = restTemplate.postForEntity(
                this.getBaseUrl(), eventTypePost, EventTypeResponseDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(3, response.getBody().getTypeId());

    }

    private String getBaseUrl() {
        return getRootUrl() + "/api/eventType";
    }
}
