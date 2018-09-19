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
 * Date: 2018-09-17 17:02
 */
@WebAppConfiguration
public class NoticeZhongBiaoServiceTest extends ConfigTest {

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
        String requestBody = "{\"pkid\":\"2\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhongbiao/listNtTenders").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController2()throws Exception{
        String requestBody = "{\"idsStr\":\"d52aad232faa44608d5367de81658bbb\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhongbiao/deleteNtTenders").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController3()throws Exception{
        String requestBody = "{\"pkid\":\"2\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhongbiao/listNtAssociateGp").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController4()throws Exception{
        String requestBody = "{\"pkid\":\"2\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhongbiao/listZhaoBiaoFiles").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController5()throws Exception {
        String requestBody = "{\n" +
                "\"ntId\":\"1\", \"tdEditCode\":\"td153629015755017\", \"segment\":\"1\", \"controllSum\":200, \"proSum\":210, \"proType\":\"3\", \"proDuration\":\"666\", \"pbMode\":\"中标更新评标办法\", \"cityCode\":\"ceshi\", \"countyCode\":\"ceshi\", \"source\":\"hunan\", \"binessType\":\"02\",\n" +
                "\"bidsCands\":\n" +
                "\t\t\t[\n" +
                "\t\t\t\t{\"ntId\":\"123456\",\"fCandidate\":\"测试中标候选人1\",\"fQuote\":310,\"fProLeader\":\"测试项目负责人1\",\"fTechLeader\":\"测试技术负责人1\"\n" +
                "\t\t\t\t,\"fBuilder\":\"测试施工员1\",\"fSafety\":\"测试安全员1\",\"fQuality\":\"测试质量员1\",\"number\":1,\"source\":\"hunan\"},\n" +
                "\t\t\t\t{\"ntId\":\"123456\",\"fCandidate\":\"测试中标候选人2\",\"fQuote\":410,\"fProLeader\":\"测试项目负责人2\",\"fTechLeader\":\"测试技术负责人2\"\n" +
                "\t\t\t\t,\"fBuilder\":\"测试施工员2\",\"fSafety\":\"测试安全员2\",\"fQuality\":\"测试质量员2\",\"number\":2,\"source\":\"hunan\"}\n" +
                "\t\t\t]\n" +
                "} ";

        String responseString = mockMvc.perform(post("/zhongbiao/saveTbNtBids").characterEncoding("UTF-8")
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