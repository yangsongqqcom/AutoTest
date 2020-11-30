package test.util;

import com.ceshi.util.SendHttps;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AlarmOnNailLine {

    public static String alarmOnNailLine(HashMap<String, String> map) throws Exception {
        // 钉钉的webhook---线上
        String dingDingToken = "https://oapi.dingtalk.com/robot/send?access_token=c15f576220a323c6d277a029001b8d0c34285f8dbdcb0ab8974e6b18c7f78009";
        //测试
        //String dingDingToken = "https://oapi.dingtalk.com/robot/send?access_token=b398045b402d44fd72cbffde505ccaab96080cb3e82e9dfd24bdb4ae587f1e25";

        //判断大小
        if (map.get("content").toString().length() > 5000) {
            map.put("content", "日志内容过多，导致宝宝无法加载，请查看具体日志详情");
        }

        //获取当前时间转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        // 请求的JSON数据，这里我用map在工具类里转成json格式
        Map<String, Object> json = new HashMap();
        Map<String, Object> text = new HashMap();
        json.put("msgtype", "markdown");
        text.put("title", "线上报警");
        text.put("text", "#### CCapp报警推送(测试) \n 模块状态：(" + map.get("modular").toString() + ") \n  ###### 内容：[" + map.get("content").toString() + " \n ###### 日期:" + strDate + " \n");
        json.put("markdown", text);
        // 发送post请求
        String response = SendHttps.sendPostByMap(dingDingToken, json);
        System.out.println(response);

        return null;
    }
}

