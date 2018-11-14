package com.silita.controller;

import com.silita.model.TbNtMian;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.Map;

@RequestMapping("/notic")
@Controller
public class NoticController {

    @ResponseBody
    @RequestMapping("/notic")
    public Map<String, Object> addNotice(@RequestBody TbNtMian tbNtMian, ServletRequest request) {
        return null;
    }
}
