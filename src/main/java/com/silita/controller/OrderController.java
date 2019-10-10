package com.silita.controller;

import com.silita.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    private IOrderService orderService;
    /**
     * 订单列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String,Object> list(@RequestBody Map<String,Object> param){
        return orderService.getOrderListMap(param);
    }
}
