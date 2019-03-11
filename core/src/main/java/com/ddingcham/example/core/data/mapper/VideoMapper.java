package com.ddingcham.example.core.data.mapper;

import com.ddingcham.example.core.data.Video;
import com.ddingcham.example.core.domain.model.VideoViewModel;

import static com.ddingcham.example.core.util.Logger.debug;

public class VideoMapper {

    public static VideoViewModel toViewModel(Video video) {
        debug("- Mapping Video to VideoViewModel");
        return VideoViewModel
                .builder()
                .id(video.getId())
                .title(video.getTitle())
                .genre(video.getGenre())
                .watchers(video.getWatchers())
                .build();
    }
}
