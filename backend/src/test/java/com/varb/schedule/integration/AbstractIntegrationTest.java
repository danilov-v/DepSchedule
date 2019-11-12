package com.varb.schedule.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.varb.schedule.config.RootDirInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(initializers = RootDirInitializer.class)
@Transactional
public abstract class AbstractIntegrationTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String asJsonString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    protected <D> D asObject(String source, Class<D> destinationType) throws IOException {
        return objectMapper.readValue(source, destinationType);
    }

    protected <D> List<D> asListOfObjects(MvcResult mvcResult, Class<D> destinationType) throws IOException {
        String source = mvcResult.getResponse().getContentAsString();

        if (source == null)
            return Collections.emptyList();

        final CollectionType destinationCollectionType = new ObjectMapper()
                .getTypeFactory()
                .constructCollectionType(List.class, destinationType);

        return objectMapper.readValue(source, destinationCollectionType);
    }

}
