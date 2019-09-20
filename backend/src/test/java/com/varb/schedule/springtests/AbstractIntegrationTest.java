package com.varb.schedule.springtests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    //protected final String appJsonMediaType = "application/json;charset=UTF-8";
    //protected final String appJsonContentType = "application/json;charset=UTF-8";

}
