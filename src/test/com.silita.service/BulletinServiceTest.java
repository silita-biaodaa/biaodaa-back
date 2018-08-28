package com.silita.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 17:26
 */

@WebAppConfiguration
public class BulletinServiceTest extends ConfigTest {

    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext wac;

    @Before()
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Test
    public void testController1()throws Exception{
        String responseString = mockMvc.perform(post("/bulletin/listFixedEditData").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController2()throws Exception{
        String requestBody = "{\"type\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/bulletin/listPbMode").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }
}
