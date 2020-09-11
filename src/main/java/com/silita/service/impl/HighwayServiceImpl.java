package com.silita.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.silita.dao.CountryHighwayMapper;
import com.silita.model.CountryHighway;
import com.silita.model.HighwayEditVo;
import com.silita.model.HighwayVo;
import com.silita.model.HunanHighway;
import com.silita.service.IHighwayService;
import com.silita.service.mongodb.HunanHighwayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antoneo
 * @create 2020-09-09 9:30
 **/
@Service
public class HighwayServiceImpl implements IHighwayService {

    @Resource
    CountryHighwayMapper countryHighwayMapper;

    @Resource
    HunanHighwayService hunanHighwayServicel;


    @Override
    public Map<String, Object> list(int pageNo, int pageSize, int sourceType, String nameKey, String province, int isOpt) {
        Map<String, Object> result=new HashMap<>();
        try {
            switch (sourceType) {
                case 0:
                    //全国公路
                    List<CountryHighway> highways = countryHighwayMapper.findByPage(province, isOpt, nameKey, pageNo, pageSize);
                    List<HighwayVo> highwayVoList = new ArrayList<>();
                    highways.forEach(highway -> {
                        HighwayVo highwayVo = new HighwayVo();
                        BeanUtil.copyProperties(highway, highwayVo);
                        highwayVo.setSource("全国公路建设信用信息平台");
                        highwayVoList.add(highwayVo);
                    });
                    result.put("code", 1);
                    result.put("msg", "请求成功!");
                    result.put("data", highwayVoList);
                    int total=countryHighwayMapper.countTotal(province, isOpt, nameKey);
                    result.put("total", total);
                    result.put("pages", total/pageSize+1);
                    break;
                case 1:
                    //湖南公路
                    result=hunanHighwayServicel.list(pageNo,pageSize,nameKey,isOpt);
                    break;
            }
        }catch (Exception e){
            result.put("code", 0);
            result.put("msg", "请求异常!");
        }
        return result;
    }

    @Override
    public Map<String, Object> show(String pkid,String type) {
        Map<String, Object> result=new HashMap<>();
        if("build".equals(type)||"design".equals(type)){
            //全国公路 在建或设计
            CountryHighway countryHighway=countryHighwayMapper.findById(pkid, type);
            HighwayEditVo highwayEditVo=new HighwayEditVo();
            switch (countryHighway.getIsOpt()){
                case 0:
                    result.put("code",1);
                    result.put("msg","获取成功！");
                    BeanUtil.copyProperties(countryHighway,highwayEditVo);
                    result.put("data",highwayEditVo);
                    countryHighwayMapper.lock(pkid,type);
                    break;
                case 1:
                    result.put("code",0);
                    result.put("msg","该记录已被锁定！");
                    break;
                case 2:
//                    result.put("code",0);
//                    result.put("msg","该记录已被人工解析！");
                    result.put("code",1);
                    result.put("msg","获取成功！");
                    BeanUtil.copyProperties(countryHighway,highwayEditVo);
                    result.put("data",highwayEditVo);
                    countryHighwayMapper.lock(pkid,type);
                    break;
            }
        }else if("hunan".equals(type)){
            //湖南公路
            HunanHighway hunanHighway=hunanHighwayServicel.findById(pkid);
            switch (hunanHighway.getIsOpt()){
                case 0:
                    result.put("code",1);
                    result.put("msg","获取成功！");
                    HighwayEditVo highwayEditVo=new HighwayEditVo();
                    highwayEditVo.setIsOpt(hunanHighway.getIsOpt());
                    highwayEditVo.setMileage(hunanHighway.getMileageMan());
                    highwayEditVo.setMileageMan(hunanHighway.getMileageMan());
                    highwayEditVo.setPkid(hunanHighway.getId());
                    highwayEditVo.setProjName(hunanHighway.getProjectName());
                    highwayEditVo.setSection(hunanHighway.getContractName());
                    highwayEditVo.setRemark(hunanHighway.getMainWorks());
                    highwayEditVo.setSectionStart(hunanHighway.getContractBeginNo());
                    highwayEditVo.setSectionEnd(hunanHighway.getContractFinishNo());
                    highwayEditVo.setType("hunan");
                    hunanHighwayServicel.lock(pkid);
                    result.put("data",highwayEditVo);
                    break;
                case 1:
                    result.put("code",0);
                    result.put("msg","该记录已被锁定！");
                    break;
                case 2:
                    result.put("code",0);
                    result.put("msg","该记录已被人工解析！");
                    break;
            }


        }
        return result;
    }

    @Override
    public Map<String, Object> release(String pkid, String type) {
        Map<String, Object> result=new HashMap<>();
        int validrows=0;
        if("build".equals(type)||"design".equals(type)){
            validrows=countryHighwayMapper.release(pkid,type);
        }else if("hunan".equals(type)){
            validrows=hunanHighwayServicel.release(pkid);
        }
        result.put("code",1);
        result.put("msg",validrows==0?"释放失败！":"释放成功！");
        return result;
    }

    @Override
    public Map<String, Object> update(String pkid, String type, String mileageMan) {
        Map<String, Object> result=new HashMap<>(2);
        int validRows=-1;
        try {
            if ("build".equals(type) || "design".equals(type)) {
                //全国公路 在建或设计
                validRows = countryHighwayMapper.update(pkid, type, mileageMan);
            }else if("hunan".equals(type)){
                //湖南公路
                validRows = hunanHighwayServicel.updateData(pkid,mileageMan);
            }
            if (validRows > 0) {
                result.put("code", 1);
                result.put("msg", "更新成功！");
            } else {
                result.put("code", 0);
                result.put("msg", "更新失败！");
            }
        }catch (Exception e){
            result.put("code", 0);
            result.put("msg", "更新失败！");
        }finally {
            return result;
        }
    }

    @Override
    public Map<String, Object> count() {
        Map<String,Object> result=new HashMap<>(3);
        long finish=countryHighwayMapper.countByIsOpt(2)+hunanHighwayServicel.countByIsOpt(2);
        long unFinish=countryHighwayMapper.countByIsOpt(0)+hunanHighwayServicel.countByIsOpt(0);
        long total=countryHighwayMapper.countByIsOpt(-1)+hunanHighwayServicel.countByIsOpt(-1);
        result.put("finish",finish);
        result.put("unFinish",unFinish);
        result.put("total",total);
        return result;
    }

    @Override
    public Map<String, Object> provinces() {
        Map<String,Object> result=new HashMap<>(3);
        List<String> provinces=countryHighwayMapper.allProvinces();
        result.put("code",1);
        result.put("msg","请求成功！");
        result.put("data",provinces);
        return result;
    }
}
