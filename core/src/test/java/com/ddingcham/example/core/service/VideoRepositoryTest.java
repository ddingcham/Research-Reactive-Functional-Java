package com.ddingcham.example.core.service;

import org.junit.Test;

import static com.ddingcham.example.core.util.Logger.log;
import static com.ddingcham.example.core.util.ThreadUtil.sleep;

public class VideoRepositoryTest {

    VideoRepository repository = new VideoRepository();

    @Test
    public void getVideos_simple_launch() {
        repository
                .getVideos()
                .forEach(video -> log(video));
        sleep(2500);
    }

    @Test
    public void getVideosWithErrors_simple_launch() {
        repository
                .getVideosWithErrors()
                .doOnError(error -> log(error.getMessage()));
         sleep(2500);
    }
}
