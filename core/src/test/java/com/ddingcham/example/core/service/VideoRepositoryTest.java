package com.ddingcham.example.core.service;

import com.ddingcham.example.core.util.Logger;
import org.junit.Test;

import static com.ddingcham.example.core.util.ThreadUtil.sleep;

public class VideoRepositoryTest {

    private VideoRepository repository = new VideoRepository();

    @Test
    public void getVideos_simple_launch() {
        repository
                .getVideos()
                .subscribe(Logger::log);
        sleep(2500);
    }

    @Test
    public void getVideos_blocking_launch() {
        repository
                .getVideos()
                .blockingForEach(Logger::log);
    }

    @Test
    public void getVideosWithErrors_simple_launch() {
        repository
                .getVideosWithErrors()
                .subscribe(Logger::log, Logger::showThread);
         sleep(2500);
    }

    @Test
    public void getVideosWithErrors_blocking_launch() {
        repository
                .getVideosWithErrors()
                .blockingSubscribe(Logger::log, Logger::showThread);
    }
}
