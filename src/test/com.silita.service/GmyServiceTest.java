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
 * Created by 91567 on 2018/4/12.
 */
@WebAppConfiguration
public class GmyServiceTest extends ConfigTest {

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
        String requestBody = "{\"name\":\"开始看书法看看理论上\", \"type\":\"hunan\", \"orderNo\":\"1\", \"desc\":\"这是描述\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/insertPbMode").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM0MTM0NjMxfQ.Y84sVsK7A7krMCNDGVBoOUbwe_vVgIqYQ_qzcWxvUa0")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController2()throws Exception{
        String requestBody = "{\"id\":\"8adf1e38038c40ee9062c4ab83b13cb7\", \"name\":\"测试低价法\", \"type\":\"hunan\", \"orderNo\":\"2\", \"desc\":\"这是描述\", \"updateBy\":\"test\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/updatePbMode").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM0MTM0NjMxfQ.Y84sVsK7A7krMCNDGVBoOUbwe_vVgIqYQ_qzcWxvUa0")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController3()throws Exception{
        String requestBody = "{\"type\":\"hunan\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/listPbMode").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController4()throws Exception{
        String requestBody = "{\"idsStr\":\"8adf1e38038c40ee9062c4ab83b13cb1\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/deletePbMode").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    //-----------------------------------------------

    @Test
    public void testController5()throws Exception{
        String requestBody = "{\"name\":\"更新综合评标法的别名2\", \"stdCode\":\"jiangx_pdmode_zhpbf_1534134583021\", \"desc\":\"别名描述\", \"remark\":\"备用字段\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/insertPbModeAlias").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController6()throws Exception{
        //jiangx_pdmode_zhpbf_1534134583021
        String requestBody = "{\"name\":\"更新综合评标法的别名2\", \"id\":\"04b28793f65c4a6d9e1e9493640ea043\", \"stdCode\":\"jiangx_pdmode_zhpbf_1534134583021\", \"desc\":\"别名描述更新\", \"remark\":\"备用字段更新\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/updatePbModeAlias").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImdlbWluZ3lpIiwiZXhwIjoxNTM0MTM0NjMxfQ.Y84sVsK7A7krMCNDGVBoOUbwe_vVgIqYQ_qzcWxvUa0")
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController7()throws Exception{
        String requestBody = "{\"stdCode\":\"jiangx_pdmode_zhpbf_1534134583021\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/listPbModeAlias").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController8()throws Exception{
        String requestBody = "{\"idsStr\":\"29129b19c9794e4392b72803f26bd3d7\"}";
        String responseString = mockMvc.perform(post("/dataMaintain/deletePbModeAlias").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController9()throws Exception{
        String responseString = mockMvc.perform(post("/dataMaintain/listProvince").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }

    @Test
    public void testController10()throws Exception{
        String requestBody = "{\"userName\":\"gemingyi\", \"password\":\"123456\"}";
        String responseString = mockMvc.perform(post("/authorize/login").characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.getBytes())
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }
}
