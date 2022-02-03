package com.duannd.tech.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

@Slf4j
public class QuickStartMain {

    public static void main(String[] args) {
        System.setProperty("org.quartz.properties", "quartz-quickstart.properties");
        // Grab the Scheduler instance from the Factory
        try {
            log.info("------- Initializing ----------------------");
            // First we must get a reference to a scheduler
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            log.info("------- Initialization Complete -----------");

            log.info("------- Scheduling Job  -------------------");
            Date runTime = DateBuilder.evenMinuteDate(new Date());
            // define the job and tie it to our HelloJob class
            JobDetail jobDetail = JobBuilder
                    .newJob(HelloJob.class)
                    .withIdentity("helloJob", "helloGroup")
                    .build();

            // Trigger the job to run on the next round minute
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("helloTrigger", "helloGroup")
                    .startAt(runTime)
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(jobDetail, trigger);
            log.info(jobDetail.getKey() + " will run at: " + runTime);


            // Start up the scheduler (nothing can actually run until the scheduler has been started)
            scheduler.start();

            log.info("------- Started Scheduler -----------------");

            // wait long enough so that the scheduler as an opportunity to run the job!
            log.info("------- Waiting 130 seconds... -------------");
            try {
                // wait 65 seconds to show job
                Thread.sleep(130 * 1000L);
                // executing...
            } catch (Exception e) {
                //
            }

            // shut down the scheduler
            log.info("------- Shutting Down ---------------------");
            scheduler.shutdown(true);
            log.info("------- Shutdown Complete -----------------");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Slf4j
    public static class HelloJob implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            // Say Hello to the World and display the date/time
            log.info("Hello World! - " + new Date());
        }

    }
}
