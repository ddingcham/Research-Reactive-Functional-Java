package com.ddingcham.example.core.util;

import org.junit.Test;

import static com.ddingcham.example.core.util.ThreadUtil.sleep;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class ThreadUtilTest {

    @Test(timeout = 100)
    public void sleepWithNoSleepMillisControl() {
        sleep(5000, 10000, (min, max) -> 0);
    }

    @Test(timeout = 460)
    public void sleepWithRandomMillsSleep() {
        long start = currentTimeMillis();
        sleep(300, 400);
        long end = currentTimeMillis();
        assertThat(end - start, greaterThan(300L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sleepWithThrowingMillisControl() {
        sleep(0, 0, (min, max) -> {throw new Exception();});
    }

}
