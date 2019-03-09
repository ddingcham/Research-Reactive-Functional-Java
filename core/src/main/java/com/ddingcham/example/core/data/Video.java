package com.ddingcham.example.core.data;

import com.ddingcham.example.core.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Video {

    private int id;
    private String title;
    private Genre genre;
    private Integer watchers;
}
