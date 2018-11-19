package com.silita.controller;

import com.silita.controller.base.BaseController;
import com.silita.model.TbFinService;
import com.silita.service.IFinService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-11-19 10:34
 */
@RestController
@RequestMapping("/FinService")
public class FinServiceController extends BaseController {

    @Autowired
    private IFinService finService;

    @RequestMapping(value = "/listFinService",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    public Map<String,Object> listFinService(@RequestBody TbFinService tbFinService, HttpServletResponse response) {
        return super.successMap(finService.listFinService(tbFinService));
    }

    @RequestMapping(value = "/exportFinService",method = RequestMethod.POST,produces="application/json;charset=utf-8")
    public void exportBidsDetail(@RequestBody TbFinService tbFinService, HttpServletResponse response) {
        try{
            HSSFWorkbook work = finService.exportFinService(tbFinService);
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ System.currentTimeMillis());
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream out=response.getOutputStream();
            work.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
