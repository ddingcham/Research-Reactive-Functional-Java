package com.ddingcham.example.core.domain.model;

import com.ddingcham.example.core.domain.Genre;
import com.ddingcham.example.core.domain.contract.IFavourite;
import com.ddingcham.example.core.domain.contract.IGenre;
import com.ddingcham.example.core.domain.contract.IViewModel;
import com.ddingcham.example.core.domain.contract.IWatcher;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class VideoViewModel implements IViewModel, IGenre, IWatcher, IFavourite {

    private int id;
    private String title;
    private Genre genre;
    private Integer watchers;
    private boolean favourite;

    @Builder
    private static VideoViewModel builderConfig(int id, String title, Genre genre, Integer watchers) {
        try {
            return new VideoViewModel(id, title, genre, watchers);
        } catch(NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    VideoViewModel(@NonNull int id, @NonNull String title, @NonNull Genre genre, @NonNull Integer watchers) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.watchers = watchers;
    }

    VideoViewModel(IViewModel viewModel, boolean favourite) {
        VideoViewModel videoViewModel = (VideoViewModel) viewModel;
        this.id = videoViewModel.id;
        this.title = videoViewModel.title;
        this.genre = videoViewModel.genre;
        this.watchers = videoViewModel.watchers;
        this.favourite = favourite;
    }


}
