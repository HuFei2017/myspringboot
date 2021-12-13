package com.learning.jsch;

import com.learning.jsch.interfaces.Test;
import com.learning.jsch.tests.SimpleShellTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JschApplication {

    public static void main(String[] args) {
        String cmd = "ansible '*' -m ping";
        Test test = new SimpleShellTest(cmd);
        test.run();
    }

}
