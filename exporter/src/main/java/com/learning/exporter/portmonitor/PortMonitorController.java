package com.learning.exporter.portmonitor;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import io.prometheus.client.Gauge;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RestController
public class PortMonitorController {

    private Connection conn = null;
    private Session session = null;
    private final static String cmd = "netstat -n | awk '/^tcp/ {n=split($(NF-1),array,\":0\");if(n<=2)++S[array[(1)]];else++S[array[(4)]];++s[$NF];++N} END {for(a in S){printf(\"%-20s %s\\n\", a, S[a]);++I}printf(\"%-20s %s\\n\",\"TOTAL_IP\",I);for(a in s) printf(\"%-20s %s\\n\",a, s[a]);printf(\"%-20s %s\\n\",\"TOTAL_LINK\",N);}'";

    private static final Gauge portRequests = Gauge.build()
            .name("port_status").labelNames("portstatus").help("A real time monitor about connection numbers on tcp ports").register();

    @Scheduled(cron = "30 * * * * ?")
    void processRequest() {

        if (isConnect()) {
            String str = getResponse();
            if (!Objects.isNull(str)) {
                String[] ports = convertString(str).split(",");
                for (int i = 0; i < ports.length; i++) {
                    switch (ports[i]) {
                        case "TOTAL_IP":
                            portRequests.labels("TOTAL_IP").set(Integer.parseInt(ports[i + 1]));
                            break;
                        case "CLOSE_WAIT":
                            portRequests.labels("CLOSE_WAIT").set(Integer.parseInt(ports[i + 1]));
                            break;
                        case "ESTABLISHED":
                            portRequests.labels("ESTABLISHED").set(Integer.parseInt(ports[i + 1]));
                            break;
                        case "TIME_WAIT":
                            portRequests.labels("TIME_WAIT").set(Integer.parseInt(ports[i + 1]));
                            break;
                        case "TOTAL_LINK":
                            portRequests.labels("TOTAL_LINK").set(Integer.parseInt(ports[i + 1]));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    //连接服务器
    private boolean isConnect() {

        if (Objects.isNull(conn)) {
            conn = new Connection("localhost");
            try {
                conn.connect();//连接
                conn.authenticateWithPassword("root", "Rozh123srv");//认证
            } catch (IOException e) {
                e.printStackTrace();
                conn.close();
                conn = null;
                return false;
            }
        }
        if (Objects.isNull(session)) {
            try {
                session = conn.openSession();//打开一个会话
            } catch (IOException e) {
                e.printStackTrace();
                session.close();
                session = null;
                conn.close();
                conn = null;
                return false;
            }
        }
        return true;
    }

    //获取服务器端口信息
    private String getResponse() {
        try {
            session.execCommand(cmd);//执行命令
            String str = processStdout(session.getStdout());
            session.close();
            session = null;
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            session.close();
            session = null;
            return null;
        }
    }


    private static String processStdout(InputStream in) {
        InputStream stdout = new StreamGobbler(in);
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 除去字符串空格和换行符并用逗号替代
     */
    private String convertString(String oldString) {
        String regEx = "[' ' | \n]+"; // 一个或多个空格和换行符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(oldString);
        return m.replaceAll(",").trim();
    }

}
