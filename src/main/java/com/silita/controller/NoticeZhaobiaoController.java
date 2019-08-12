package com.silita.controller;

import com.silita.commons.shiro.utils.JWTUtil;
import com.silita.controller.base.BaseController;
import com.silita.model.*;
import com.silita.service.INoticeZhaoBiaoService;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:49
 * 招标公告接口
 */

@Controller
@RequestMapping("/zhaobiao")
public class NoticeZhaobiaoController extends BaseController {

    @Autowired
    INoticeZhaoBiaoService noticeZhaoBiaoService;


    @RequestMapping(value = "/listFixedEditData", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listProvince() {
        return super.successMap(noticeZhaoBiaoService.listFixedEditData());
    }

    @RequestMapping(value = "/listNoticeStatus", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listNoticeStatus() {
        return super.successMap(noticeZhaoBiaoService.listBulletinStatus());
    }

    @RequestMapping(value = "/listPbMode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listPbMode(@RequestBody DicCommon dicCommon) {
        return super.successMap(noticeZhaoBiaoService.listDicCommonNameByType(dicCommon));
    }

    @RequestMapping(value = "/listSysArea", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listSysArea(@RequestBody SysArea sysArea) {
        return super.successMap(noticeZhaoBiaoService.listSysAreaByParentId(sysArea));
    }

    //

    @RequestMapping(value = "/listNtMain", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listNtMain(@RequestBody TbNtMian tbNtMian) {
        return super.successMap(noticeZhaoBiaoService.listNtMain(tbNtMian));
    }

    @RequestMapping(value = "/exportTendersExcel",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public void listTendersDetail(@RequestBody Map<String,Object> param, HttpServletResponse response) {
        String storeAddress = MapUtils.getString(param, "storeAddress");
        try {
            //long curr_time = System.currentTimeMillis();
            SXSSFWorkbook wb = noticeZhaoBiaoService.listTendersDetail(param);
            /*写数据到文件中*/
            FileOutputStream os = new FileOutputStream(storeAddress);
            wb.write(os);
            os.flush();
            os.close();
            /*计算耗时*/
            //System.out.println("耗时:" + (System.currentTimeMillis() - curr_time) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/updateNtMainStatus", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateNtMainStatus(@RequestBody TbNtMian tbNtMian) {
        noticeZhaoBiaoService.updateNtMainStatus(tbNtMian);
        return successMap(null);
    }

    //#####################招标标段信息######################

    @RequestMapping(value = "/saveNtTenders", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveNtTenders(@RequestBody TbNtTenders tbNtTenders, ServletRequest request) {
        Map result = new HashMap<String, Object>();
        result.put("code", 1);
        try {
            String userName = JWTUtil.getUsername(request);
            tbNtTenders.setCreateBy(userName);
            String msg = noticeZhaoBiaoService.saveNtTenders(tbNtTenders);
            result.put("msg", msg);
        } catch (Exception e) {
            result.put("code", 0);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/listNtTenders", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listNtTenders(@RequestBody TbNtTenders tbNtTenders) {
        return super.successMap(noticeZhaoBiaoService.listNtTenders(tbNtTenders));
    }

    @RequestMapping(value = "/getNtTenders", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> getNtTenders(@RequestBody TbNtTenders tbNtTenders) {
        return super.successMap(noticeZhaoBiaoService.getNtTendersByNtIdByPkId(tbNtTenders));
    }

    @RequestMapping(value = "/deleteNtTendersByPkId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteNtTendersByPkId(@RequestBody Map<String, Object> param) {
        noticeZhaoBiaoService.deleteNtTendersByPkId(param);
        return super.successMap(null);
    }

    //#####################变更标段########################

    @RequestMapping(value = "/insertTbNtChange", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> insertTbNtChange(@RequestBody TbNtChange tbNtChange, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtChange.setCreateBy(userName);
        noticeZhaoBiaoService.saveTbNtChange(tbNtChange);
        return successMap(null);
    }

    //#####################招标文件######################

    @RequestMapping(value = "/insertZhaoBiaoFilePath", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> insertZhaoBiaoFilePath(@RequestBody SysFiles sysFiles, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        sysFiles.setCreateBy(userName);
        noticeZhaoBiaoService.insertZhaoBiaoFiles(sysFiles);
        return successMap(null);
    }

    @RequestMapping(value = "/listZhaoBiaoFiles", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listSysFilesByPkid(@RequestBody SysFiles sysFiles) {
        return super.successMap(noticeZhaoBiaoService.listZhaoBiaoFilesByPkid(sysFiles));
    }

    @RequestMapping(value = "/deleteZhaoBiaoFile", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteSysFilesByPkid(@RequestBody Map<String, Object> param) {
        noticeZhaoBiaoService.deleteZhaoBiaoFilesByPkid(param);
        return super.successMap(null);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> del(@RequestBody TbNtMian tbNtMian, ServletRequest request) {
        noticeZhaoBiaoService.delNtMainInfo(tbNtMian, JWTUtil.getUsername(request));
        return super.successMap(null);
    }

    @RequestMapping(value = "/updateNtText", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateNtText(@RequestBody TbNtText tbNtText) {
        noticeZhaoBiaoService.updateNtText(tbNtText);
        return successMap(null);
    }

    @RequestMapping(value = "/insertNtAssociateGp", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> insertNtAssociateGp(@RequestBody Map<String, Object> param, ServletRequest request) {
        Map result = new HashMap<String, Object>();
        result.put("code", 1);
        try {
            param.put("createBy", JWTUtil.getUsername(request));
            String msg = noticeZhaoBiaoService.insertNtAssociateGp(param);
            result.put("msg", msg);
        } catch (Exception e) {
            result.put("code", 0);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteNtAssociateGp", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteNtAssociateGp(@RequestBody Map params) {
        List<Map<String, Object>> tbNtAssociateGps = (List<Map<String, Object>>) params.get("list");
        noticeZhaoBiaoService.deleteNtAssociateGp(tbNtAssociateGps);
        return super.successMap(null);
    }

    @RequestMapping(value = "/listNtAssociateGp", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listNtAssociateGp(@RequestBody TbNtAssociateGp tbNtAssociateGp) {
        return super.successMap(noticeZhaoBiaoService.listNtAssociateGp(tbNtAssociateGp));
    }

    @RequestMapping(value = "/insertNtRegexQua", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> insertNtRegexQua(@RequestBody TbNtRegexQua tbNtRegexQua, ServletRequest request) {
        String userName = JWTUtil.getUsername(request);
        tbNtRegexQua.setCreateBy(userName);
        noticeZhaoBiaoService.insertNtRegexQua(tbNtRegexQua);
        return super.successMap(null);
    }

    @RequestMapping(value = "/saveTbNtRegexGroup", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveTbNtRegexGroup(@RequestBody Map<String, Object> test) {
        List<Map<String, Object>> regexGroupslist = (List<Map<String, Object>>) test.get("tbNtRegexGroups");
        Map temp;
        TbNtRegexGroup tbNtRegexGroup;
        List<TbNtRegexGroup> tbNtRegexGroups = new ArrayList<>();
        for (int i = 0; i < regexGroupslist.size(); i++) {
            temp = regexGroupslist.get(i);
            tbNtRegexGroup = new TbNtRegexGroup();
//            tbNtRegexGroup.setTbNtQuaGroups((List<TbNtQuaGroup>) temp.get("tbNtQuaGroups"));
            List<Map<String, Object>> quaGroupsList = (List<Map<String, Object>>) temp.get("tbNtQuaGroups");
            List<TbNtQuaGroup> tbNtQuaGroups = new ArrayList<>();
            for (int j = 0; j < quaGroupsList.size(); j++) {
                TbNtQuaGroup tbNtQuaGroup = new TbNtQuaGroup();
//                tbNtQuaGroup.setQuaId((String) quaGroupsList.get(j).get("quaId"));
                tbNtQuaGroup.setQualIds((List<String>) quaGroupsList.get(j).get("qualIds"));
                tbNtQuaGroup.setRelType((String) quaGroupsList.get(j).get("relType"));
                tbNtQuaGroups.add(tbNtQuaGroup);
            }
            tbNtRegexGroup.setTbNtQuaGroups(tbNtQuaGroups);
//            tbNtRegexGroup.setQuaId((String) temp.get("quaId"));
            tbNtRegexGroup.setQualIds((List<String>) temp.get("qualIds"));
            tbNtRegexGroup.setRelType((String) temp.get("relType"));
            tbNtRegexGroups.add(tbNtRegexGroup);
        }
        Map params = (Map) test.get("params");
        noticeZhaoBiaoService.saveTbNtRegexGroup(tbNtRegexGroups, params);
        return super.successMap(null);
    }

    @RequestMapping(value = "/listTbQuaGroup", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> listTbQuaGroup(@RequestBody TbNtRegexGroup tbNtRegexGroup) {
        return super.successMap(noticeZhaoBiaoService.listTbQuaGroup(tbNtRegexGroup));
    }
}
