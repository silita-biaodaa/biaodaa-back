package com.silita.service.mongodb;

import cn.hutool.core.util.StrUtil;
import com.mongodb.WriteResult;
import com.silita.dao.LogParseMapper;
import com.silita.model.HighwayVo;
import com.silita.model.HunanHighway;
import com.silita.model.HunanHighwayTmp;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 湖南公路  mongodb
 *
 * @author Antoneo
 * @create 2020-09-09 15:03
 **/
@Service
public class HunanHighwayService {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    LogParseMapper logParseMapper;

    /**
     * 列表查询
     * @param pageNo
     * @param pageSize
     * @param nameKey
     * @param isOpt
     * @return
     */
    public Map<String,Object> list(int pageNo, int pageSize, String nameKey, int isOpt,String startDate, String endDate, String optUid){
        Map<String,Object> result=new HashMap<>();
        Query query = new Query();
        Criteria criteria=new Criteria();
        if(StrUtil.isNotEmpty(nameKey)){
            Pattern pattern = Pattern.compile(String.format("^.*%s.*$",nameKey), Pattern.CASE_INSENSITIVE);
            criteria.andOperator(Criteria.where("projectName").regex(pattern));
        }
        if(isOpt>=0&&isOpt<=2){
            criteria.andOperator(Criteria.where("isOpt").is(isOpt));
        }
        query.addCriteria(criteria);
        query.skip((pageNo-1)*pageSize);
        query.limit(pageSize);
        Sort sort = new Sort(Sort.Direction.ASC,"isOpt");
        query.with(sort);
        List<HunanHighway> hunanHighways=mongoTemplate.find(query,HunanHighway.class);
        List<HighwayVo> highwayVoList = new ArrayList<>();
        long total=0L;
        switch(isOpt){
            case 0:
                hunanHighways.forEach(hunanHighway -> {
                    HighwayVo highwayVo = new HighwayVo();
                    highwayVo.setSource("湖南省公路建设信用信息平台");
                    highwayVo.setType("hunan");
                    highwayVo.setPkid(hunanHighway.getId());
                    highwayVo.setIsOpt(hunanHighway.getIsOpt());
                    highwayVo.setProjName(hunanHighway.getProjectName());
                    highwayVo.setSection(hunanHighway.getContractName());
                    highwayVo.setProvince(hunanHighway.getLocation());
                    highwayVoList.add(highwayVo);
                });
                total=mongoTemplate.count(query,HunanHighway.class);
                break;
            case 2:
                Map<String, HunanHighway> mongoDataMap = new HashMap<>();
                hunanHighways.forEach(optData -> {
                    mongoDataMap.put(optData.getId(), optData);
                });
                List<HunanHighwayTmp> optDatas = logParseMapper.selectHunan(startDate, endDate, optUid);
                optDatas.forEach(optData -> {
                    if (mongoDataMap.containsKey(optData.getDataId())) {
                        HunanHighway hunanHighway = mongoDataMap.get(optData.getDataId());
                        HighwayVo highwayVo = new HighwayVo();
                        highwayVo.setSource("湖南省公路建设信用信息平台");
                        highwayVo.setType("hunan");
                        highwayVo.setPkid(hunanHighway.getId());
                        highwayVo.setIsOpt(hunanHighway.getIsOpt());
                        highwayVo.setProjName(hunanHighway.getProjectName());
                        highwayVo.setSection(hunanHighway.getContractName());
                        highwayVo.setProvince(hunanHighway.getLocation());
                        highwayVo.setOptDate(optData.getOptDate());
                        highwayVo.setOptUser(optData.getOptUser());
                        highwayVoList.add(highwayVo);
                    }
                });
                total=optDatas.size();
                break;
        }
        result.put("code",1);
        result.put("msg","请求成功！");
        result.put("data",highwayVoList);
        result.put("total",total);
        result.put("pages",total/pageSize-1);
        return result;
    }


    /**
     * 锁住记录
     */
    public void lock(String pkid){
        updateOpt(pkid,1);
    }

    /**
     * 释放
     * @param pkid
     */
    public int release(String pkid){
        return updateOpt(pkid,0);
    }

    /**
     * 恢复已审核
     * @param pkid
     */
    public int reset(String pkid){
        return updateOpt(pkid,2);
    }

    /**
     * 根据主键  更新状态
     * @param pkid
     * @param status
     * @return
     */
    public int updateOpt(String pkid,int status){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(pkid));
        Update update = Update.update("isOpt", status);
        WriteResult upsert = mongoTemplate.upsert(query, update, "hunan_highway_achieve");
        return upsert.getN();
    }

    /**
     * 通过主键查找isOpt
     * @param pkid
     * @return
     */
    public int findOptByPkid(String pkid){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(pkid));
        HunanHighway hunanHighway=mongoTemplate.findById(pkid,HunanHighway.class);
        return hunanHighway.getIsOpt();
    }

    /**
     * 根据主键  更新数据
     * @param pkid
     * @param mileageMan
     * @return
     */
    public int updateData(String pkid,String mileageMan,String tunnelLen,String bridgeLen,String bridgeSpan,String bridgeWidth){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(pkid));
        Update update = new Update();
        update.set("isOpt", 2);
        update.set("mileageMan", mileageMan);
        update.set("tunnelLen", tunnelLen);
        update.set("bridgeLen", bridgeLen);
        update.set("bridgeSpan", bridgeSpan);
        update.set("bridgeWidth", bridgeWidth);
        WriteResult upsert = mongoTemplate.upsert(query, update, "hunan_highway_achieve");
        return upsert.getN();
    }

    //通过id找到数据
    public HunanHighway findById(String pkid){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(pkid));
        return mongoTemplate.findById(pkid,HunanHighway.class);
    }


    /**
     * 统计数量
     *
     * @param isOpt
     * @return
     */
    public long countByIsOpt(int isOpt){
        Query query = new Query();
        Criteria criteria=new Criteria();
        if(isOpt>=0&&isOpt<=2) {
            criteria.andOperator(Criteria.where("isOpt").is(isOpt));
        }
        query.addCriteria(criteria);
        long total=mongoTemplate.count(query,HunanHighway.class);
        return total;
    }

}
