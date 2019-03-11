package com.ddingcham.example.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ddingcham.example.core.util.ThreadUtil.getCurrentThreadName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Logger {

    private static boolean SHOW_THREAD_NAME = false;
    public static boolean DEBUG = false;

    public static void log(Object... x) {
        System.out.println((SHOW_THREAD_NAME ? getCurrentThreadName() + " - " : "")
                + ((x != null && x.length > 0) ? x[0] : ""));
    }

    public static void debug(Object... x) {
        if(!DEBUG) return;
        System.out.println("-- Debug:: " + (SHOW_THREAD_NAME ? getCurrentThreadName() + " - " : "")
                + ((x != null && x.length > 0) ? x[0] : ""));
    }

    public static void showThread(Object... x) {
        System.out.println(ThreadUtil.getCurrentThreadName() + ((x != null && x.length > 0) ? " - " + x[0] : ""));
    }

    public static void newLine() {
        System.out.println();
    }
}
