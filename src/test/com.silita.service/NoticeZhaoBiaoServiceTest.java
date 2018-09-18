package com.silita.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
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
        String requestBody = "{\"source\":\"hunan\", \"proviceCode\":\"hunan\", \"ntStatus\":\"5\", \"ntCategory\":\"1\", \"title\":\"招标\", \"currentPage\":\"1\", \"pageSize\":\"5\"}";
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
        String requestBody = "{\"ntCategory\":\"2\", \"ntStatus\":\"1\", \"pkid\":\"1\", \"source\":\"hunan\"}";
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
        String requestBody = "{\"ntId\":\"1\", \"segment\":\"3\", \"proSum\":\"1000.0\", \"enrollAddr\":\"测试报名修改地点\", \"openingAddr\":\"测试开修改标地点\", " +
                "\"enrollEndTime\":\"2018-08-12\", \"bidEndTime\":\"2018-08-12\", \"pbMode\":\"测试评标办法\", \"bidBonds\":\"222\", \"bidBondsEndTime\":\"2018-08-12\", " +
                "\"keepBonds\":\"222\", \"otherBonds\":\"222\", \"auditTime\":\"2018-08-12\", \"openingPerson\":\"测试开标人\", \"openingFileFee\":\"222.2\", " +
                "\"otherFee\":\"444.2\", \"tenderee\":\"6666\", \"tenderContactPerson\":\"测试修改\", \"tenderContactInfo\":\"1876666\", \"proxyContactPerson\":\"修改代理联系人\", " +
                "\"proxyContactInfo\":\"1674444\", \"proPerson\":\"修改项目人要求\", \"socialSecurity\":\"社保有修改要球\", \"achievement\":\"修改业绩要求\", \"enrollMethod\":\"1\", " +
                "\"proDuration\":\"180\", \"completionTime\":\"2018-08-12\", \"isFlow\":\"true\", \"fundsProvid\":\"修改资金来源\", \"enrollMethod\":\"1\", " +
                "\"source\":\"hunan\", \"binessType\":\"2\", \"certAuditAddr\":\"资格审查地点\", \"filingPfm\":\"备案平台\", \"controllSum\":\"678\", " +
                "\"title\":\"我要更新\", \"pubDate\":\"2018-09-04\", \"cityCode\":\"hengyang\", \"countyCode\":\"hengyangxian\", \"ntTdStatus\":\"7\", \"proType\":\"1\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/saveNtTenders").characterEncoding("UTF-8")
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
    public void testController8()throws Exception{
        String requestBody = "{\"ntId\":\"1\", \"segment\":\"2\", \"proSum\":\"1000.0\", \"enrollAddr\":\"测试报名修改地点\", \"openingAddr\":\"测试开修改标地点\", " +
                "\"enrollEndTime\":\"2018-08-12\", \"bidEndTime\":\"2018-08-12\", \"pbMode\":\"测试评标办法\", \"bidBonds\":\"222\", \"bidBondsEndTime\":\"2018-08-12\", " +
                "\"keepBonds\":\"222\", \"otherBonds\":\"222\", \"auditTime\":\"2018-08-12\", \"openingPerson\":\"测试开标人\", \"openingFileFee\":\"222.2\", " +
                "\"otherFee\":\"444.2\", \"tenderee\":\"6666\", \"tenderContactPerson\":\"测试修改\", \"tenderContactInfo\":\"1876666\", \"proxyContactPerson\":\"修改代理联系人\", " +
                "\"proxyContactInfo\":\"1674444\", \"proPerson\":\"修改项目人要求\", \"socialSecurity\":\"社保有修改要球\", \"achievement\":\"修改业绩要求\", \"enrollMethod\":\"1\", " +
                "\"proDuration\":\"180\", \"completionTime\":\"2018-08-12\", \"isFlow\":\"true\", \"fundsProvid\":\"修改资金来源\", \"enrollMethod\":\"1\", " +
                "\"source\":\"hunan\", \"binessType\":\"2\", \"certAuditAddr\":\"资格审查地点\", \"filingPfm\":\"备案平台\", \"controllSum\":\"678\", " +
                "\"title\":\"我要更新\", \"pubDate\":\"2018-09-04\", \"cityCode\":\"hengyang\", \"countyCode\":\"hengyangxian\", \"ntTdStatus\":\"7\", \"proType\":\"1\"," +
                "\"pkid\":\"df02df31d1d64f1bbbae3e6b2b19fb02\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/saveNtTenders").characterEncoding("UTF-8")
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
    public void testController9()throws Exception{
        String requestBody = "{\"source\":\"hunan\", \"proviceCode\":\"hunan\", \"ntCategory\":\"1\", \"title\":\"招标\", \"ntStatus\":\"3\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/exportTendersExcel").characterEncoding("UTF-8")
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
    public void testController10()throws Exception{
        String requestBody = "{\"ntId\":\"1\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/listNtTenders").characterEncoding("UTF-8")
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
    public void testController33()throws Exception{
        String requestBody = "{\"ntId\":\"1\", \"source\":\"hunan\", \"pkid\":\"41311819ab2e41b88d080e57d739429b\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/getNtTenders").characterEncoding("UTF-8")
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
    public void testController11()throws Exception{
        String requestBody = "{\"idsStr\":\"9b83f3ca231c4375a5fc2972c43a53d8\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/deleteNtTendersByPkId").characterEncoding("UTF-8")
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
    public void testController12()throws Exception{
        String requestBody = "{\"ntId\":\"1\", \"ntEditId\":\"9b83f3ca231c4375a5fc2972c43a53d8\", \"ntCategory\":\"1\", \"source\":\"hunan\", \"fieldFrom\":\"测试评标办法\", " +
                "\"fieldName\":\"pbMode\", \"fieldValue\":\"更新时，一起修改的\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/insertTbNtChange").characterEncoding("UTF-8")
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
    public void testController13()throws Exception{
        String requestBody = "{\"ntId\":\"1\", \"ntEditId\":\"9b83f3ca231c4375a5fc2972c43a53d8\", \"ntCategory\":\"1\", \"source\":\"hunan\", \"fieldFrom\":\"测试评标办法\", " +
                "\"fieldName\":\"pb_mode\", \"fieldValue\":\"再次修改评标办法\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/insertTbNtChange").characterEncoding("UTF-8")
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
    public void testController14()throws Exception{
        String requestBody = "{\"bizId\":\"1\", \"type\":\"9b83f3ca231c4375a5fc2972c43a53d8\", \"fileName\":\"1\", \"source\":\"hunan\", \"fieldFrom\":\"测试评标办法\", " +
                "\"fieldName\":\"pb_mode\", \"fieldValue\":\"再次修改评标办法\", \"pkid\":\"f592d72506094d399d95e06547f3e512\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/insertSysFiles").characterEncoding("UTF-8")
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
    public void testController15()throws Exception{
        String requestBody = "{\"bizId\":\"1\", \"type\":\"4\", \"fileName\":\"测试上传招标文件路径2\", \"filePath\":\"www.baidu.com\", \"ossObj\":\"不知道是什么\", \"orderNo\":\"2\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/insertZhaoBiaoFilePath").characterEncoding("UTF-8")
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
    public void testController16()throws Exception{
        String requestBody = "{\"bizId\":\"1\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/listZhaoBiaoFiles").characterEncoding("UTF-8")
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
    public void testController17()throws Exception{
        String requestBody = "{\"idsStr\":\"1c931b141b9341448cf1c7f8ca9ded13|1c931b141b9341448cf1c7f8ca9ded14\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/deleteZhaoBiaoFile").characterEncoding("UTF-8")
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
    public void testController18()throws Exception{
        MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain", "some xml".getBytes());

        String responseString = mockMvc.perform(fileUpload("/upload/uploadZhaoBiaoFile").file(firstFile)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController19()throws Exception{
        String requestBody = "{\"idsStr\":\"02321b3e2fd740b4a2d408eb120918fc|09695d60a0aa4ab8af79115cb946ac2e\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/deleteZhaoBiaoFile").characterEncoding("UTF-8")
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
    public void testController20()throws Exception{
        String requestBody = "{\"idsStr\":\"1|2\", \"source\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/insertNtAssociateGp").characterEncoding("UTF-8")
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
    public void testController21()throws Exception{
        JSONArray array = new JSONArray(2);
        JSONObject object;
        for (int i = 0; i < 2; i++) {
            object = new JSONObject();
            object.put("ntId", String.valueOf(i));
            object.put("relGp", "时间戳");
            object.put("source", "hunan");
            array.add(object);
        }
//        String requestBody = array.toJSONString();
        String requestBody = "{\"list\":[{\"ntId\":\"1\",\"relGp\":\"时间戳\",\"source\":\"hunan\"},{\"ntId\":\"2\",\"relGp\":\"时间戳\",\"source\":\"hunan\"}]}";
        String responseString = mockMvc.perform(post("/zhaobiao/deleteNtAssociateGp").characterEncoding("UTF-8")
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
    public void testController22()throws Exception{
//        String requestBody = "{\"title\":\"测试\", \"source\":\"hunan\", \"pubDate\":\"2018-08-10\", \"pubEndDate\":\"2018-08-30\"}";
        String requestBody = "{\"ntId\":\"1\", \"source\":\"hunan\", \"currentPage\":\"1\", \"pageSize\":\"30\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/listNtAssociateGp").characterEncoding("UTF-8")
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
    public void testController23()throws Exception{
//        String requestBody = "{\"source\":\"hunan\", \"proviceCode\":\"hunan\", \"cityCode\":\"changsha\", \"ntStatus\":\"\", \"ntCategory\":\"1\", \"title\":\"测试\", \"pubDate\":\"2018-08-10\", \"pubEndDate\":\"2018-08-20\", \"currentPage\":\"1\", \"pageSize\":\"5\"}";
        String requestBody = "{\"source\":\"hunan\", \"title\":\"更新\", \"currentPage\":\"1\", \"pageSize\":\"5\"}";
        String responseString = mockMvc.perform(post("/common/listRelevantNotice").characterEncoding("UTF-8")
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
    public void testController24()throws Exception{
        String requestBody = "{\"source\":\"hunan\", \"pkid\":\"1\", \"content\":\"哈哈哈哈哈哈哈哈\"}";
        String responseString = mockMvc.perform(post("/zhaobiao/updateNtText").characterEncoding("UTF-8")
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
