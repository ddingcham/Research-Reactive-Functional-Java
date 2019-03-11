package com.ddingcham.example.core.service;

import com.ddingcham.example.core.data.DummyData;
import com.ddingcham.example.core.data.mapper.VideoMapper;
import com.ddingcham.example.core.domain.model.VideoViewModel;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

import static com.ddingcham.example.core.util.Logger.showThread;

public class VideoRepository {

    public static final String SUBSCRIBED_TO_RETRIEVE_VIDEOS = "Subscribed to retrieve Videos";

    public Observable<VideoViewModel> getVideos() {
        return Observable.fromIterable(DummyData.videos)
                .doOnSubscribe(disposable -> showThread(SUBSCRIBED_TO_RETRIEVE_VIDEOS))
                .delay(2000, TimeUnit.MILLISECONDS)
                .map(VideoMapper::toViewModel);
    }

    public Observable<VideoViewModel> getVideosWithErrors() {
        return Observable.fromIterable(DummyData.videosWithErrors)
                .doOnSubscribe(disposable -> showThread(SUBSCRIBED_TO_RETRIEVE_VIDEOS))
                .delay(1500, TimeUnit.MILLISECONDS)
                .map(VideoMapper::toViewModel);
    }
}
