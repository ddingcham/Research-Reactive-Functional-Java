package com.ddingcham.example.core.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

public class MainExecutor implements Executor {

    private BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();

    public void runLoop() throws InterruptedException {
        while(!Thread.interrupted()) {
            tasks.take().run();
        }
    }

    @Override
    public void execute(Runnable command) {
        // tasks : add command
        System.out.println("add task :  " + command);
        tasks.add(command);
    }

    public void shutdown() {
        Thread currentThread = Thread.currentThread();
        if(currentThread.isAlive() && !currentThread.isInterrupted()) {
            currentThread.interrupt();
        }
    }
}
