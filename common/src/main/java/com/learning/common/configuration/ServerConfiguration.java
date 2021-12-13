package com.learning.common.configuration;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName ServerConfiguration
 * @Description 获取后台服务IP及运行端口号
 * @Author hufei
 * @Date 2020/8/3 15:26
 * @Version 1.0
 */
@Component
public class ServerConfiguration implements ApplicationListener<WebServerInitializedEvent> {

    private String address = "";
    private int port;

    public String getAddress() {
        if (address.isEmpty()) {
            InetAddress addr = null;
            try {
                addr = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (null != addr)
                address = addr.getHostAddress();
        }
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }
}