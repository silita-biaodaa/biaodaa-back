package com.silita.dao;

import com.silita.model.CountryHighway;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全国公路接口
 *
 * @author Antoneo
 * @create 2020-09-07 17:41
 **/
public interface CountryHighwayMapper {

    //按列表查询
    List<CountryHighway> findByPage(@Param("province")String province, @Param("isOpt")int isOpt, @Param("nameKey")String nameKey, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize);

    //统计总数
    int countTotal(@Param("province")String province, @Param("isOpt")int isOpt, @Param("nameKey")String nameKey);

    //根据查询记录
    CountryHighway findById(@Param("pkid")String pkid,@Param("type")String type);

    //锁住记录
    void lock(@Param("pkid")String pkid,@Param("type")String type);

    //释放
    int release(@Param("pkid")String pkid,@Param("type")String type);

    //恢复已审核
    int reset(@Param("pkid")String pkid,@Param("type")String type);

    //更新记录
    int update(@Param("pkid")String pkid,@Param("type")String type,@Param("mileageMan")String mileageMan,@Param("tunnelLen")String tunnelLen,@Param("bridgeLen")String bridgeLen,@Param("bridgeSpan")String bridgeSpan,@Param("bridgeWidth")String bridgeWidth);

    long countByIsOpt(@Param("isOpt") int isOpt);

    List<String> allProvinces();
}
