package com.silita.dao;

import com.silita.model.HunanHighwayTmp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志
 *
 * @author Antoneo
 * @create 2020-09-17 16:57
 **/
public interface LogParseMapper {

    int insert(@Param("dataId")String dataId,@Param("type")String type, @Param("optUid")String optUid);

    int countByDataId(@Param("dataId")String dataId);

    List<HunanHighwayTmp> selectHunan(@Param("startDate") String startDate, @Param("endDate")String endDate, @Param("optUid")String optUid);
}
