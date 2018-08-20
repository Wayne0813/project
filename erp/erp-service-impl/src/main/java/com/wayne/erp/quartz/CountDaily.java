package com.wayne.erp.quartz;

import com.wayne.erp.service.CountService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * @author LV
 * @date 2018/8/17
 */
public class CountDaily implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        ApplicationContext context = null;
        try {
            context = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");

            CountService countService = context.getBean(CountService.class);

            countService.countDailyOrder();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }




    }
}
