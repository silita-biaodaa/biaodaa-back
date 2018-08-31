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
public class NoticeZhaoBiaoServiceTest extends ConfigTest {

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
        String responseString = mockMvc.perform(post("/zhaobiao/listFixedEditData").characterEncoding("UTF-8")
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
        String responseString = mockMvc.perform(post("/zhaobiao/listPbMode").characterEncoding("UTF-8")
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
        String requestBody = "{\"areaParentId\":\"0\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/listSysArea").characterEncoding("UTF-8")
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
    public void testController5()throws Exception{
        String responseString = mockMvc.perform(post("/zhaobiao/listNoticeStatus").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM1NDA1OTU2fQ.pcCP9aQedZ5hTnK9n3FzDNtzK4lUxRoxE6lxuHfPArw")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController4()throws Exception{
//        String requestBody = "{\"source\":\"hunan\", \"proviceCode\":\"hunan\", \"cityCode\":\"changsha\", \"ntStatus\":\"\", \"ntCategory\":\"1\", \"title\":\"测试\", \"pubDate\":\"2018-08-10\", \"pubEndDate\":\"2018-08-20\", \"currentPage\":\"1\", \"pageSize\":\"5\"}";
        String requestBody = "{\"source\":\"hunan\", \"proviceCode\":\"hunan\", \"cityCode\":\"changsha\", \"ntStatus\":\"\", \"ntCategory\":\"1\", \"title\":\"测试\", \"currentPage\":\"1\", \"pageSize\":\"5\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/listNtMain").characterEncoding("UTF-8")
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
    public void testController6()throws Exception{
        String requestBody = "{\"ntCategory\":\"2\", \"ntStatus\":\"1\", \"pkid\":\"9\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/updateNtMainStatus").characterEncoding("UTF-8")
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
    public void testController7()throws Exception{
        String requestBody = "{\"ntId\":\"1\", \"segment\":\"1\", \"proSum\":\"1000.0\", \"enrollAddr\":\"测试报名地点\", \"openingAddr\":\"测试开标地点\", " +
                "\"enrollEndTime\":\"2018-08-12\", \"bidEndTime\":\"2018-08-12\", \"pbMode\":\"测试评标办法\", \"bidBonds\":\"222\", \"bidBondsEndTime\":\"2018-08-12\", " +
                "\"keepBonds\":\"222\", \"otherBonds\":\"222\", \"auditTime\":\"2018-08-12\", \"openingPerson\":\"测试开标人\", \"openingFileFee\":\"222.2\", " +
                "\"otherFee\":\"444.2\", \"tenderee\":\"6666\", \"tenderContactPerson\":\"测试\", \"tenderContactInfo\":\"1876666\", \"proxyContactPerson\":\"代理联系人\", " +
                "\"proxyContactInfo\":\"1674444\", \"proPerson\":\"项目人要求\", \"socialSecurity\":\"社保有要球\", \"achievement\":\"业绩要求\", \"enrollMethod\":\"1\", " +
                "\"proDuration\":\"180\", \"completionTime\":\"2018-08-12\", \"isFlow\":\"true\", \"fundsProvid\":\"资金来源\", \"enrollMethod\":\"1\", " +
                "\"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/insertNtTenders").characterEncoding("UTF-8")
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
