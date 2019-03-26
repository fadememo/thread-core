package club.majoy.springbootmajoyqueuedPub.controller;

import club.majoy.springbootmajoyqueuedPub.entity.Job;
import club.majoy.springbootmajoyqueuedPub.service.QueuedPubService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.PriorityBlockingQueue;

@RestController
@RequestMapping
public class QueuedPubController{
    @Resource
    private QueuedPubService service;

    private PriorityBlockingQueue<Job> jobQueue = new PriorityBlockingQueue<>();


    @GetMapping("/publish/{msg}")
    public String pub(@PathVariable String msg){
        service.publish(msg);
        return "ok";
    }
    @PostMapping("/pubJob")
    public String pubJob(@RequestBody Job job){
        //Assert.isTrue((0==job.getBaseStamp())||(0==job.getDelay()), "该配送任务的时间信息为空");
        //Assert.isTrue((job.getBaseStamp()+job.getDelay()< System.currentTimeMillis()), "该配送任务已经超期");
        jobQueue.add(job);
        while(jobQueue.size()>0){
            long currentTime = System.currentTimeMillis();
            Job lattestJob = jobQueue.peek();
            //如果任务已经超过延时时间则发布
            long timeDelayStamp = lattestJob.getBaseStamp()+lattestJob.getDelay();
            if(currentTime>= timeDelayStamp){
                service.publish(lattestJob);
                jobQueue.poll();
            }
        }
        return "job published ok";
    }


}
