package com.platzi.javatests.movies.data;

import com.platzi.javatests.movies.model.Movie;

import java.util.Collection;

public interface MovieRepository {

    Movie findById(long id);
    Collection<Movie> findAll();
    void saveOrUpdate(Movie movie);

    Collection<Movie> findByName(String movieTitle);

    Collection<Movie> findByDirector(String directorName);

    Collection<Movie> findByTemplate(Movie template);
}
