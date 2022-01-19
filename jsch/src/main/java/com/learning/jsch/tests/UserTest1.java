package com.learning.jsch.tests;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.UUID;

/**
 * @ClassName UserTest1
 * @Description TODO
 * @Author hufei
 * @Date 2022/1/19 18:51
 * @Version 1.0
 */
public class UserTest1 {
    public static void main(String[] args) {
        try {
            String targetDir = "/tmp/" + UUID.randomUUID() + "/";

            //启动会话
            JSch jsch = new JSch();
            Session session = jsch.getSession("root", "192.168.1.120", 22);
            session.setPassword("123456");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(30000);
            session.connect();

            //上传文件
            ChannelSftp ftpChannel = (ChannelSftp) session.openChannel("sftp");
            ftpChannel.connect();
            ftpChannel.mkdir(targetDir);
            ftpChannel.put("C:\\Files\\hadoop\\bin\\namenode.xml", targetDir + "namenode.xml", ChannelSftp.OVERWRITE);
            ftpChannel.quit();
            ftpChannel.disconnect();

            //执行命令
            ChannelExec execChannel = (ChannelExec) session.openChannel("exec");
            execChannel.setCommand("echo \"ok2\" > /tmp/log.log");
            execChannel.setInputStream(null);
            execChannel.setErrStream(null);
            execChannel.connect();
            execChannel.disconnect();

            //关闭会话
            session.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
