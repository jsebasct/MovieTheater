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
                        new Movie(1, "Dark Knight", 152, Genre.ACTION, "Christopher Nolan"),
                        new Movie(2, "Memento", 113, Genre.THRILLER, "Christopher Nolan"),
                        new Movie(3, "There's Something About Mary", 119, Genre.COMEDY, "Peter Farrelly, Bobby Farrelly"),
                        new Movie(4, "Super 8", 112, Genre.THRILLER, "J.J. Abrams"),
                        new Movie(5, "Scream", 111, Genre.HORROR, "Wes Craven"),
                        new Movie(6, "Home Alone", 103, Genre.COMEDY, "Chris Columbus"),
                        new Movie(7, "Matrix", 136, Genre.ACTION, "Lana Wachowski, Lilly Wachowski")
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