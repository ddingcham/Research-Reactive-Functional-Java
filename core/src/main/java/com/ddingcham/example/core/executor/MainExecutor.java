package com.ddingcham.example.core.executor;

import java.util.concurrent.Executor;

public class MainExecutor implements Executor {

    public void runLoop() throws InterruptedException {
        while(!Thread.interrupted()) {
            // task run
        }
    }

    @Override
    public void execute(Runnable command) {
        // tasks : add command
        System.out.println(command);
    }
}
