package com.learning.examples.examples;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @ClassName SimulationTest
 * @Description TODO
 * @Author hufei
 * @Date 2020/10/13 14:17
 * @Version 1.0
 */
public class SimulationTest extends Thread {

    private StringBuilder builder = new StringBuilder();
    private Thread threadInput = null;
    private Thread threadOutput = null;

    private void stopNow() {
        if (null != threadInput)
            this.threadInput.interrupt();
        if (null != threadOutput)
            this.threadOutput.interrupt();
        this.interrupt();
    }

    public void startNow() {
        this.start();
    }

    /**
     * @Description 启动线程
     * @Param []
     * @Return
     * @Author jiashudong
     * @Date 2020/8/10 14:00
     */
    @Override
    public void run() {
        String temp = "/var/ronds/test/123/";
        String graph = "{\"nodes\":[{\"operatorId\":\"894d11cf-c7ff-42a0-af47-08bbef031b13\",\"id\":\"c87c4f95-5ff9-4167-abc8-2462ef3bfbcf\",\"label\":\"报警算子\",\"metadata\":{\"value\":\"Alarm_Operater\",\"inputs\":[{\"id\":\"A1\",\"type\":null}],\"outputs\":[],\"config\":{}}},{\"operatorId\":\"11355c5e-953f-4bbf-beab-e0c8700cd71f\",\"id\":\"3a1303a5-0113-455d-9c3b-3553b730cc44\",\"label\":\"data\",\"metadata\":{\"value\":\"Operator_GetInput\",\"inputs\":[],\"outputs\":[{\"id\":\"Out1\",\"type\":null}],\"config\":{}}}],\"edges\":[{\"source\":\"3a1303a5-0113-455d-9c3b-3553b730cc44\",\"target\":\"c87c4f95-5ff9-4167-abc8-2462ef3bfbcf\",\"metadata\":{\"output\":\"Out1\",\"input\":\"A1\"}}]}";
        try {
            int i = 1;
            appendLog("开始执行测试.");

            //获取数据
            appendLog("开始读取数据.");
            List<String> datas = Files.readAllLines(Paths.get("/var/ronds/file/data.csv"));
            appendLog("成功读取" + datas.size() + "行数据.");

            for (String data : datas) {
                appendLog("读取第" + i + "行数据.");
                appendLog("数据内容:" + data);
                appendLog("开始运行算法.");
                //数据写入文件
                String path = temp + i;
                updateFile(path, "{\"graph\":" + graph + ",\"datasource\":{\"data\":" + data + "}}");
                //python参数
                String[] arguments = new String[]{"python3", temp + "pythons/__simulate__.py", path};
                //开启进程来执行脚本文件
                Process process = Runtime.getRuntime().exec(arguments);

                StringBuilder var1_log = new StringBuilder();
                StringBuilder var2_log = new StringBuilder();
                StringBuilder var_data = new StringBuilder();

                startStream(var1_log, var_data, process.getInputStream(), false);
                startStream(var2_log, var_data, process.getErrorStream(), true);

                //进程成功执行完毕
                if (process.waitFor() == 0) {
                    builder.append(var1_log.toString());
                    System.out.println(builder.toString());
                    System.out.println(var_data.toString());
                } else {
                    builder.append(var2_log.toString());
                    System.out.println(builder.toString());
                }
                process.destroy();
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        stopNow();
    }

    private void appendLog(StringBuilder builder, String str) {
        builder.append(str).append("\n");
    }

    private void appendLog(String str) {
        builder.append(str).append("\n");
    }

    private void startStream(StringBuilder builderLog, StringBuilder builderData, InputStream stream, boolean isError) {
        if (isError) {
            threadOutput = new Thread(() -> {
                try (
                        InputStreamReader reader = new InputStreamReader(stream, "GBK");
                        BufferedReader buffer = new BufferedReader(reader)
                ) {
                    appendLog(builderLog, "算法运行失败.");
                    appendLog(builderLog, "错误信息:");
                    String line;
                    while ((line = buffer.readLine()) != null) {
                        appendLog(builderLog, line);
                    }
                } catch (IOException ignored) {
                }
            });
            threadOutput.start();
        } else {
            threadInput = new Thread(() -> {
                try (
                        InputStreamReader reader = new InputStreamReader(stream, "GBK");
                        BufferedReader buffer = new BufferedReader(reader)
                ) {
                    appendLog(builderLog, "算法运行完成.");
                    String line;
                    while ((line = buffer.readLine()) != null) {
                        builderData.append(line);
                    }
                } catch (IOException ignored) {
                }
            });
            threadInput.start();
        }
    }

    private void updateFile(String path, String content) {
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.print(content);
            printStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException fe) {
            System.out.println("the file not existed");
        } catch (IOException ie) {
            System.out.println("the file write failed.");
        }
    }
}
