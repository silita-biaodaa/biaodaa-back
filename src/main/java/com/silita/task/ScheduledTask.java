package com.silita.task;

import com.silita.dao.*;
import com.silita.utils.ExecutorProcessPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时任务
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTask {
    @Autowired
    TbNtMianMapper tbNtMianMapper;
    @Autowired
    private TbNtSiteMapper tbNtSiteMapper;
    @Autowired
    TbSiteCountMapper tbSiteCountMapper;

    /**
     * 添加所有公告站点统计数据
     * 只执行一次
     */
  /*  @Scheduled(cron = "0 14 14 29 10 2019 ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void addSiteAll() {
        ExecutorProcessPool pool = ExecutorProcessPool.getInstance();
        pool.execute(new SendSubscriptionTask(tbNtSiteMapper, tbNtMianMapper, tbSiteCountMapper));
    }*/
    /**
     * 添加当天公告站点统计数据
     */
  /*  @Scheduled(cron = "0 0/1 * * * ?")
    public void addTodaySite(){

        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new ScheduledTask());
            thread.setName("线程" + i);
            thread.start();
        }

    }*/

}
