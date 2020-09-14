package test.service.serviceCCapp;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.util.AlarmOnNailLine;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * feed流请求主页
 * name：王琪
 * 2020年7月4日06:08:37
 */
public class FeedStream {
    private static final Logger logger = LoggerFactory.getLogger(FeedStream.class);

    /**
     * feed流请求主页
     *
     * @param normal
     * @return
     * @throws IOException
     */
    public static HashMap feedStream(String normal) throws Exception {
        logger.info("----------执行feed流请求主页case");

        //创建返回参数
        HashMap<String, Object> map = new HashMap<String, Object>();
        //设置返回结果
        Boolean result = false;

        try {
            //获取当前时间转换为字符串
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
            String strDate = sdf.format(new Date());
            //使用的OKhttp连接后台接口
            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(40000, TimeUnit.MILLISECONDS)
                    .readTimeout(40000, TimeUnit.MILLISECONDS)
                    .build();

            //线上地址
            Request request = new Request.Builder()
                    .url("https://api.cc.clipclaps.tv/feeds/feed/video?uid=1679977&devType=iOS&version=1.8.1")
                    .method("GET", null)
                    .addHeader("req_id", "111")
                    .build();

            logger.info("request数据", request);
            Response response = client.newCall(request).execute();

            //获取响应时间
            long responseTime = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
            //请求超时发送消息
            if (responseTime > 4000) {
                HashMap<String, String> dingdingResponseTime = new HashMap<String, String>();
                dingdingResponseTime.put("modular", "feed流响应异常--负责人:@黄大军");
                dingdingResponseTime.put("content", "feed流响应异常，响应时间为：" + responseTime + "毫秒");
                AlarmOnNailLine.alarmOnNailLine(dingdingResponseTime);
            }
            logger.info("响应时间：" + responseTime);
            //请求状态
            String code = response.toString();
            logger.info("请求状态：" + code);
            // 获取头部
            Headers rsultHeaders = response.headers();
            logger.info("获取头部：" + rsultHeaders);
            // 获取身体信息
            String rsultBody = response.body().string();
            logger.info("获取身体信息：" + rsultBody);

            //字符串包含子字符串则测试通过
            if (rsultBody.contains("\"msg\":\"Success\"")) {
                if (normal.equals("true")) {
                    HashMap<String, String> dingding = new HashMap<String, String>();
                    dingding.put("modular", "feed流正常--负责人:黄大军");
                    dingding.put("content", "用户刷feed流正常，feed流内容：" + rsultBody.substring(4000, rsultBody.length() - 1) + ",");
                    AlarmOnNailLine.alarmOnNailLine(dingding);
                }
                result = true;
            } else {
                HashMap<String, String> dingding = new HashMap<String, String>();
                dingding.put("modular", "feed流异常--@黄大军(kebo)");
                dingding.put("content", "用户刷feed流异常，异常内容：" + rsultBody + ",");
                AlarmOnNailLine.alarmOnNailLine(dingding);
            }
            //存入Map
            map.put("feedStream", rsultBody);

        } catch (Exception e) {
            logger.error("异常捕获：" + e);
            HashMap<String, String> dingding = new HashMap<String, String>();
            dingding.put("modular", "feed流异常--@黄大军(kebo)");
            dingding.put("content", "用户刷feed流异常，异常内容：无法获取到相应数据");
            AlarmOnNailLine.alarmOnNailLine(dingding);
        }

        //存入Map
        map.put("result", result);
        return map;

    }
}
