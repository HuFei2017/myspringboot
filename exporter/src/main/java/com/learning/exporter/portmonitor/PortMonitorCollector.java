package com.learning.exporter.portmonitor;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import io.prometheus.client.Collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortMonitorCollector extends Collector {

    private final static String HOST="localhost";
    private final static String USER="root";
    private final static String PASSWORD="Rozh123srv";
    private Connection conn = null;
    private Session session = null;
    private final static String cmd = "netstat -n | awk '/^tcp/ {n=split($(NF-1),array,\":0\");if(n<=2)++S[array[(1)]];else++S[array[(4)]];++s[$NF];++N} END {for(a in S){printf(\"%-20s %s\\n\", a, S[a]);++I}printf(\"%-20s %s\\n\",\"TOTAL_IP\",I);for(a in s) printf(\"%-20s %s\\n\",a, s[a]);printf(\"%-20s %s\\n\",\"TOTAL_LINK\",N);}'";

    /**
     * 收集服务器状态指标
     */
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> result = new ArrayList<>();

        String metricName = "port_status";
        MetricFamilySamples.Sample sample1 = null;
        MetricFamilySamples.Sample sample2 = null;
        MetricFamilySamples.Sample sample3 = null;
        MetricFamilySamples.Sample sample4 = null;
        MetricFamilySamples.Sample sample5 = null;


        if (!isConnect())
            return result;

        String str = getResponse();

        if (Objects.isNull(str))
            return result;

        String[] ports = convertString(str).split(",");
        for (int i = 0; i < ports.length; i++) {
            switch (ports[i]) {
                case "TOTAL_IP":
                    sample1 = new MetricFamilySamples.Sample(metricName, Arrays.asList("portstatus"), Arrays.asList("TOTAL_IP"), Integer.parseInt(ports[i + 1]));
                    break;
                case "CLOSE_WAIT":
                    sample2 = new MetricFamilySamples.Sample(metricName, Arrays.asList("portstatus"), Arrays.asList("CLOSE_WAIT"), Integer.parseInt(ports[i + 1]));
                    break;
                case "ESTABLISHED":
                    sample3 = new MetricFamilySamples.Sample(metricName, Arrays.asList("portstatus"), Arrays.asList("ESTABLISHED"), Integer.parseInt(ports[i + 1]));
                    break;
                case "TIME_WAIT":
                    sample4 = new MetricFamilySamples.Sample(metricName, Arrays.asList("portstatus"), Arrays.asList("TIME_WAIT"), Integer.parseInt(ports[i + 1]));
                    break;
                case "TOTAL_LINK":
                    sample5 = new MetricFamilySamples.Sample(metricName, Arrays.asList("portstatus"), Arrays.asList("TOTAL_LINK"), Integer.parseInt(ports[i + 1]));
                    break;
                default:
                    break;
            }
        }
        MetricFamilySamples samples = new MetricFamilySamples(metricName, Type.GAUGE, "help", Arrays.asList(sample1, sample2, sample3, sample4, sample5));
        result.add(samples);
        return result;
    }

    //连接服务器
    private boolean isConnect() {

        if (Objects.isNull(conn)) {
            conn = new Connection(HOST);
            try {
                conn.connect();//连接
                conn.authenticateWithPassword(USER, PASSWORD);//认证
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
