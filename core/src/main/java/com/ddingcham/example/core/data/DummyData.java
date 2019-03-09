package com.ddingcham.example.core.data;

import com.ddingcham.example.core.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Simple Video Dummy Data
 */
public class DummyData {

    public static List<Video> videos;

    static {
        videos = Stream.of(
                new Video(1, "Video 1", Genre.ACTION, 0),
                new Video(2, "Video 2", Genre.ADVENTURE, 5),
                new Video(3, "Video 3", Genre.ADVENTURE, 0),
                new Video(4, "Video 4", Genre.COMEDY, 10),
                new Video(5, "Video 5", Genre.SCI_FI, 15),
                new Video(6, "Video 6", Genre.DRAMA, 0),
                new Video(7, "Video 7", Genre.FANTASY, 111),
                new Video(8, "Video 8", Genre.ACTION, 0),
                new Video(9, "Video 9", Genre.ACTION, 34),
                new Video(10, "Video 10", Genre.ACTION, 78)
        ).collect(Collectors.toList());
    }

    public static List<Video> videosWithErrors;

    static {
        videosWithErrors = Stream.of(
                new Video(1, "Video 1", Genre.ACTION, 0),
                new Video(2, "Video 2", Genre.ADVENTURE, null),
                new Video(3, "Video 3", Genre.ADVENTURE, null),
                new Video(4, "Video 4", Genre.COMEDY, 10),
                new Video(5, "Video 5", null, 15),
                new Video(6, "Video 6", Genre.DRAMA, 0),
                new Video(7, "Video 7", Genre.FANTASY, 111),
                new Video(8, "Video 8", Genre.ACTION, null),
                new Video(9, "Video 9", Genre.ACTION, 34),
                new Video(10, "Video 10", Genre.ACTION, 78)
        ).collect(Collectors.toList());
    }
}
