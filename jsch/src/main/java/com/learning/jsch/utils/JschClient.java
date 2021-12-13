package com.learning.jsch.utils;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class JschClient {

    private static JschClient client;

    private String host;
    private String name;
    private int port;

    private JschClient(String host, String name, int port) {
        this.host = host;
        this.name = name;
        this.port = port;
    }

    public static JschClient getInstance(String host) {
        if (Objects.isNull(client))
            client = new JschClient(host, "root", 22);
        return client;
    }

    public String execute(String cmd) throws JSchException, IOException {
        JSch jsch = new JSch();
        String pubKeyPath = "C:\\Project\\IDEA Projects\\myspringboot\\src\\main\\java\\com\\learning\\jsch\\sshkey\\id_rsa";
        jsch.addIdentity(pubKeyPath);
        Session session = jsch.getSession(name, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(30000);
        session.connect();
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(cmd);
        channel.setInputStream(null);
        channel.setPty(true);
        channel.setErrStream(System.err, true);

        BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
        channel.connect();
        StringBuilder builder = new StringBuilder();
        String msg;
        while ((msg = in.readLine()) != null) {
            builder.append(msg).append("\n");
        }
        channel.disconnect();
        session.disconnect();
        return builder.toString();
    }
}
