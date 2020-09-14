package test.service.serviceCCapp;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.util.AlarmOnNailLine;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 上传视频
 * name：王琪
 * 2020年7月1日06:08:37
 */
public class VideoUpload {
    private static final Logger logger = LoggerFactory.getLogger(VideoUpload.class);

    /**
     * 上传视频
     *
     * @return
     * @throws IOException
     */
    public static HashMap videoUpload(String token, String userId, String normal) throws Exception {
        logger.info("------------执行上传视频case");
        logger.info("打印入参：token：" + token + "userId:" + userId);

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

            //本地环境
//            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("videoTitle", "上传测试请未必拒绝" + strDate)
//                    .addFormDataPart("videoDescribe", "ldaskdlaskjldkjksald")
//                    .addFormDataPart("file", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ceshishiping.mp4",
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ceshishiping.mp4")))
//                    .build();
            //线上环境
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("videoTitle", "视频上传测试" + strDate)
                    .addFormDataPart("videoDescribe", "ldaskdlaskjldkjksald")
                    .addFormDataPart("file", System.getProperty("user.dir") + File.separator + "ceshishiping.mp4",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(System.getProperty("user.dir") + File.separator + "ceshishiping.mp4")))
                    .build();

            logger.info("请求body内容" + body);

            //等待2秒
            Thread.sleep(1000);

            //线上环境
            Request request = new Request.Builder()
                    .url("https://ugc.cc.lerjin.com/video/upload")
                    .method("POST", body)
                    .addHeader("token", token)
                    .addHeader("lang", "CN")
                    .addHeader("userId", userId)
                    .addHeader("module", "ugc")
                    .build();
            Response response = client.newCall(request).execute();
            logger.info("响应内容" + response);

            //获取响应时间
            long responseTime = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
            logger.info("响应时间：" + responseTime);

            //请求超时发送消息
            if (responseTime > 10000) {
                HashMap<String, String> dingdingResponseTime = new HashMap<String, String>();
                dingdingResponseTime.put("modular", "视频上传响应异常--@黄兴");
                dingdingResponseTime.put("content", "视频上传响应异常，响应时间为：" + responseTime + "毫秒");
                AlarmOnNailLine.alarmOnNailLine(dingdingResponseTime);
            }

            // 获取头部
            Headers rsultHeaders = response.headers();
            logger.info("获取响应头部：" + rsultHeaders);

            // 获取身体信息
            String rsultBody = response.body().string();
            logger.info("获取响应身体信息：" + rsultBody);
            String VideoId = rsultBody.substring(7, 39);
            logger.info("获取上传视频ID：" + VideoId);

            //字符串包含子字符串则测试通过
            if (rsultBody.contains("\"videoDescribe\":\"ldaskdlaskjldkjksald\"")) {
                if (normal.equals("true")) {
                    //向钉钉发送消息
                    HashMap<String, String> dingding = new HashMap<String, String>();
                    dingding.put("modular", "视频上传正常--负责人(黄兴)");
                    dingding.put("content", "视频上传正常，上传视频ID,VideoId：" + VideoId + ",");
                    AlarmOnNailLine.alarmOnNailLine(dingding);
                }
                result = true;
            } else {
                HashMap<String, String> dingding = new HashMap<String, String>();
                dingding.put("modular", "视频上传异常--@黄兴");
                dingding.put("content", "视频上传异常，异常内容：" + rsultBody + ",");
                AlarmOnNailLine.alarmOnNailLine(dingding);
            }

            //存入Map
            map.put("VideoId", VideoId);

        } catch (Exception e) {
            logger.error("异常捕获:" + e);
            //向钉钉发送消息
            HashMap<String, String> dingding = new HashMap<String, String>();
            dingding.put("modular", "视频上传异常--@黄兴");
            dingding.put("content", "视频上传异常，异常内容：上传失败");
            AlarmOnNailLine.alarmOnNailLine(dingding);
        }

        map.put("result", result);
        logger.info("打印结果：" + map);
        return map;
    }
}
