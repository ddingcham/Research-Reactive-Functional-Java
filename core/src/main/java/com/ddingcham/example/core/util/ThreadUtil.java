package com.ddingcham.example.core.util;

import io.reactivex.functions.BiFunction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

import static com.ddingcham.example.core.util.Logger.log;
import static java.lang.Thread.currentThread;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadUtil {

    public static final BiFunction<Integer, Integer, Integer> DEFAULT_MILLIS_CONTROL =
            ((integer1, integer2) -> new Random().nextInt((integer2 - integer1) + 1) + integer1);

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log(e.getMessage());
        }
    }

    public static void sleep(int min, int max) {
        sleep(min, max, DEFAULT_MILLIS_CONTROL);
    }

    public static void sleep(int min, int max, BiFunction<Integer, Integer, Integer> millisControl) {
        try {
            sleep(millisControl.apply(min, max));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Argument : millisControl");
        }
    }

    public static String getCurrentThreadName() {
        return "Thread: " + currentThread().getName();
    }
}
