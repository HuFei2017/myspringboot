package com.learning.jsch.tests;

import com.jcraft.jsch.JSchException;
import com.learning.jsch.interfaces.Test;
import com.learning.jsch.utils.JschClient;

import java.io.IOException;

public class SimpleShellTest implements Test {

    private String cmd;

    public SimpleShellTest(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public void run() {
        JschClient client = JschClient.getInstance("192.168.1.16");
        try {
            String msg = client.execute(cmd);
            System.out.println(msg);
        } catch (IOException | JSchException ioe) {
            ioe.printStackTrace();
        }
    }
}
