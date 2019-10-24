package com.silita.task;

import com.silita.utils.dateUtils.MyDateUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
@EnableScheduling
public class ScheduledTask {
    private static final Logger logger = Logger.getLogger(ScheduledTask.class);
    /**
     * 添加所有公告站点统计数据
     * 只执行一次
     */
//    @Scheduled(cron = "0 0 0 1/5 * ? ")
    public void addSiteAll() {
        logger.info("------------A任务开始执行------------");
        logger.info("-------添加所有公告站点统计数据--------");

        logger.info("------------A任务执行完毕------------");
    }

    /**
     * 添加当天公告站点统计数据
     */
//    @Scheduled(cron = "0 */30 * * * ?")
    public void addTodaySite(){
        logger.info("------------B任务开始执行------------");
        logger.info("-------添加当天公告站点统计数据-------");

        logger.info("------------B任务执行完毕------------");
    }



}
