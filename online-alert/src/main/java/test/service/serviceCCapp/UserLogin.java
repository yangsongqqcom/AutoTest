package test.service.serviceCCapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.util.AlarmOnNailLine;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * 用户登录(手机)
 * name：王琪
 * 2020年7月4日06:08:37
 */
public class UserLogin {
    private static final Logger logger = LoggerFactory.getLogger(UserLogin.class);

    /**
     * 用户登录(手机)
     *
     * @param normal
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static HashMap userLogin(String normal) throws Exception {
        logger.info("----------执行用户登录case");

        //创建返回参数
        HashMap<String, Object> map = new HashMap<String, Object>();
        //设置返回结果
        Boolean result = false;

        try {
            //获取验证码
            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(40000, TimeUnit.MILLISECONDS)
                    .readTimeout(40000, TimeUnit.MILLISECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("application/json,text/plain");
            //测试地址
//            RequestBody body = RequestBody.create(mediaType,
//                    "{\r\n    \"areaCode\": 86,\r\n    \"phone\" : 17323226097,\r\n    \"verifyType\" : 4\r\n}");
//            Request request = new Request.Builder()
//                    .url("https://api.testing.clipclaps.tv/sms/verify")
//                    .method("POST", body)
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Content-Type", "text/plain")
//                    .build();
            //线上地址
            RequestBody body = RequestBody.create(mediaType,
                    "{\r\n    \"areaCode\": 86,\r\n    \"phone\" : 17323226097,\r\n    \"verifyType\" : 4\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.cc.clipclaps.tv/sms/verify")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Content-Type", "text/plain")
                    .build();

            //发送请求
            Response response = client.newCall(request).execute();
            String code = response.toString();
            // 获取头部
            Headers rsultHeaders = response.headers();

            // 获取身体信息
            String rsultBody = response.body().string();
            JSONObject joRsultBody = JSONObject.parseObject(rsultBody);
            logger.info("验证码数据：" + joRsultBody);
            //josn取值
            String verifyCode = JSON.toJSONString(joRsultBody.getJSONObject("data").get("verifyCode"));
            logger.info("获取验证码：" + verifyCode);

            //等待2秒
            Thread.sleep(2000);
            //获取ccToken
            OkHttpClient loginClient = new OkHttpClient().newBuilder().connectTimeout(40000, TimeUnit.MILLISECONDS)
                    .readTimeout(40000, TimeUnit.MILLISECONDS)
                    .build();
            MediaType loginMediaType = MediaType.parse("application/json,text/plain");

            //线上环境
            RequestBody loginBody = RequestBody.create(loginMediaType,
                    "{\r\n\t\"areaCode\":86,\r\n\t\"phone\":17323226097,\r\n\t\"verifyCode\":" + verifyCode + "\r\n}");
            Request loginRequest = new Request.Builder()
                    .url("https://api.cc.clipclaps.tv/account/login")
                    .method("POST", loginBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Content-Type", "text/plain")
                    .addHeader("version", "40")
                    .build();
            logger.info("请求数据：" + loginRequest);

            //发送请求
            Response loginResponse = loginClient.newCall(loginRequest).execute();

            //获取响应时间
            long responseTime = loginResponse.receivedResponseAtMillis() - loginResponse.sentRequestAtMillis();
            logger.info("响应时间：" + responseTime);

            //请求超时发送消息
            if (responseTime > 4000) {
                HashMap<String, String> dingdingResponseTime = new HashMap<String, String>();
                dingdingResponseTime.put("modular", "登录响应异常--@郑磊");
                dingdingResponseTime.put("content", "登录响应异常，响应时间为：" + responseTime + "毫秒");
                AlarmOnNailLine.alarmOnNailLine(dingdingResponseTime);
            }

            String loginResponsecode = loginResponse.toString();
            logger.info("响应时间状态：" + loginResponsecode);
            // 获取头部
            Headers loginRsultHeaders = loginResponse.headers();
            logger.info("获取头部返回数据：" + loginRsultHeaders);
            // 获取身体信息
            String loginRsultBody = loginResponse.body().string();
            logger.info("获取身体信息返回数据：" + loginRsultBody);

            //获取ccToken
            JSONObject loginJoRsultBody = JSONObject.parseObject(loginRsultBody);
            String ccToken = JSON.toJSONString(loginJoRsultBody.getJSONObject("data").get("token"));
            logger.info("获取ccToken：" + ccToken);
            String userid = JSON.toJSONString(loginJoRsultBody.getJSONObject("data").getJSONObject("principal").get("userid"));
            logger.info("获取userid：" + userid);

            //等待2秒
            Thread.sleep(2000);
            String strToken = ccToken.replace("\"", "");
            String strUserid = userid.replace("\"", "");
            result = true;

            //字符串包含子字符串则测试通过
            if (loginRsultBody.contains("\"msg\":\"Success\"")) {
                if (normal.equals("true")) {
                    //通过钉钉发送状态
                    HashMap<String, String> dingding = new HashMap<String, String>();
                    dingding.put("modular", "登录正常--负责人(郑磊)");
                    dingding.put("content", "用户登录正常，userId：" + strUserid + ",Token：" + strToken + "");
                    AlarmOnNailLine.alarmOnNailLine(dingding);
                }
                result = true;
            } else {
                HashMap<String, String> dingding = new HashMap<String, String>();
                dingding.put("modular", "登录正常异常--@郑磊");
                dingding.put("content", "用户登录异常，异常内容：" + loginRsultBody + ",");
                AlarmOnNailLine.alarmOnNailLine(dingding);
            }

            //存入Map
            map.put("strToken", strToken);
            map.put("strUserid", strUserid);

        } catch (Exception e) {
            logger.error("异常捕获：" + e);
            //通过钉钉发送状态
            HashMap<String, String> dingding = new HashMap<String, String>();
            dingding.put("modular", "登录正常异常--@郑磊");
            dingding.put("content", "用户登录异常，异常内容：登录失败");
            AlarmOnNailLine.alarmOnNailLine(dingding);
        }

        map.put("result", result);
        return map;
    }
}
