package com.ceshi.scheduled;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import test.service.serviceCCapp.FeedStream;
import test.service.serviceCCapp.NewFeedStream;
import test.service.serviceCCapp.UserLogin;
import test.service.serviceCCapp.VideoUpload;
import test.util.AlarmOnNailLine;

import java.io.IOException;
import java.util.HashMap;

/**
 * 定时线上预警
 * name：王琪
 * 2020年7月4日06:08:37
 */
@Component
public class OnlineAlarm {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(OnlineAlarm.class);

    /**
     * 用户Token
     */
    private String strToken = null;

    /**
     * 用户Id
     */
    private String strUserid = null;

    /**
     * 视频id
     */
    private String VideoId = null;

    /**
     * feed流
     */
    private String feedStream = null;


    /**
     * 添加定时任务,报警确认
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    public String alarmConfirmation() throws Exception {
        HashMap<String, String> dingding = new HashMap<String, String>();
        dingding.put("modular", "正常");
        dingding.put("content", "线上环境报警运行正常");
        AlarmOnNailLine.alarmOnNailLine(dingding);
        logger.info("----------报警运行确认");
        return null;
    }


    /**
     * 添加定时任务,报警
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
//    @Scheduled(cron = "0 */9 * * * ?")
    @Scheduled(cron = "*/30 * * * * ?")
    public String dezhouAutomation() throws Exception {
        logger.info("---------定时执行报警");
        //是否打印正确请求
        String normal = "false";
        //feed流
        HashMap feed = FeedStream.feedStream(normal);
        //用户登录
        HashMap user = UserLogin.userLogin(normal);
        if (user.get("strToken") != null) {
            strToken = user.get("strToken").toString();
            strUserid = user.get("strUserid").toString();
        }
        //新feed流
        HashMap newFeed = NewFeedStream.newfeedStream(strToken, strUserid, normal);
        //视频上传
//        if(user.get("strToken") != null){
//            strToken = user.get("strToken").toString();
//            strUserid = user.get("strUserid").toString();
//        }
//        HashMap video =  VideoUpload.videoUpload(strToken,strUserid,normal);
        return null;
    }


}
