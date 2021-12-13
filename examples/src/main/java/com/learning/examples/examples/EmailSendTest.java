package com.learning.examples.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author hufei
 * @Date 2021/12/1 19:25
 * @Version 1.0
 */
public class EmailSendTest {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        //消息接收者
        Map<String, Object> recerver_ = new HashMap<>();
        recerver_.put("mail", "xuxiaozhong@999.com.cn");
        recerver_.put("sms", "");
        recerver_.put("weixin", "");
        recerver_.put("todo", "");

        //微信及待办参数
        //weixin:text、news，todo:1待办、2待阅、3已办、4删除
        Map<String, Object> msgJson_ = new HashMap<>();
        msgJson_.put("weixin", "");    //news图文，text文本
        msgJson_.put("todo", "");

        //消息体部分
        Map<String, Object> msgData_ = new HashMap<>();
        msgData_.put("subject", "测试消息接口1");
        msgData_.put("description", "测试消息接口");
        msgData_.put("receiver", recerver_);
        msgData_.put("creator", "徐小钟");
        msgData_.put("type", msgJson_);
        msgData_.put("url", "http://fly.999.com.cn");
        msgData_.put("picname", "fly");
        msgData_.put("number", "_CCSQ2018042600004");
        msgData_.put("origin", "XX共享");
        msgData_.put("originSysFlag", "com.cr999.bup");
        msgData_.put("originDocKey", "ccsq2018081400003");

        System.out.println("==========================");
        System.out.println(msgData_.toString());
        System.out.println("==========================");

        Map<String, Object> createMsg_ = new HashMap<>();
        createMsg_.put("appid", "100000017");
        createMsg_.put("appname", "epm");
        createMsg_.put("modeid", "500000001");
        createMsg_.put("modename", "***");
        createMsg_.put("urgent", "1");
        createMsg_.put("msgtype", "mail");
        createMsg_.put("data", msgData_);
        String APP_KEY = "epm";
        String SECRET_KEY = "5614##ASvf9";
        String auth = APP_KEY + ":" + SECRET_KEY;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        String tmpstr = Request.Post("http://esb.999.com.cn/msgcenter/v1/message/deal")
                .setHeader("APP_ID", "100000013")
                .setHeader("MOD_ID", "500000003")
                .setHeader("APP_NAME", "test系统")
                .setHeader("MOD_NAME", "test模块")
                .setHeader("Authorization", authHeader)
                .bodyForm(Form.form()
                                .add("content", mapper.writeValueAsString(createMsg_))
                                .build()
                        , Charset.defaultCharset())
                .execute().returnContent().toString();
        System.out.println(tmpstr);
        System.out.println("=============================================");
        System.out.println(createMsg_.toString());
        System.out.println("=============================================");
        System.out.println(authHeader);
    }
}
