package com.ddingcham.example.core.service;

import org.junit.Test;

public class VideoRepositoryTest {

    VideoRepository repository = new VideoRepository();

    @Test
    public void getVideos_simple_launch() {
        repository.getVideos().subscribe();
    }

    @Test
    public void getVideosWithErrors_simple_launch() {
        repository.getVideosWithErrors().subscribe();
    }
}
