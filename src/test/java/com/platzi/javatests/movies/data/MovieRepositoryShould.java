package com.platzi.javatests.movies.data;

import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import com.platzi.javatests.movies.service.MovieService;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MovieRepositoryShould {

    @Test
    public void return_movies_by_genre() {
        MovieRepository repo = Mockito.mock(MovieRepository.class);

        Mockito.when(repo.findAll()).thenReturn(
                Arrays.asList(
                   new Movie(1, "Dark Knight", 152, Genre.ACTION),
                   new Movie(2, "Memento", 113, Genre.THRILLER),
                   new Movie(3, "Something about Mary", 152, Genre.COMEDY),
                   new Movie(4, "Super 8", 152, Genre.THRILLER),
                   new Movie(5, "Scream", 152, Genre.HORROR),
                   new Movie(6, "Home Alone", 152, Genre.COMEDY),
                   new Movie(7, "Matrix", 152, Genre.ACTION)
                )
        );

        MovieService service = new MovieService(repo);
        Collection<Movie> moviesByGenre = service.findMoviesByGenre(Genre.COMEDY);

        System.out.println(moviesByGenre);
        List<Integer> comedyIDs = moviesByGenre
                .stream()
                //.map(Movie::getId)
                .map(m -> m.getId())
                .collect(Collectors.toList());
        //System.out.println(comedyIDs);

        assertThat(comedyIDs, CoreMatchers.is(Arrays.asList(3, 6)));
    }

}