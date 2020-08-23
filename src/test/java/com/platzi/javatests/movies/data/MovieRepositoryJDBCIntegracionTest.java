package com.platzi.javatests.movies.data;

import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MovieRepositoryJDBCIntegracionTest {

    private MovieRepository repository;
    private DriverManagerDataSource driver;

    @Before
    public void setUp() throws Exception {
        driver = new DriverManagerDataSource("jdbc:h2:mem:test;MODE=MYSQL", "sa", "sa");

        //sql-scripts/
        ScriptUtils.executeSqlScript(driver.getConnection(), new ClassPathResource("test-data.sql"));

        JdbcTemplate template = new JdbcTemplate(driver);
        repository = new MovieRepositoryJDBC(template);
    }

    @Test
    public void insertMovie() {
        Movie movie = new Movie("Super 8", 112, Genre.THRILLER, "J.J. Abrams");
        repository.saveOrUpdate(movie);
        Movie movideFromDB = repository.findById(4);

        Movie expectedMovie = new Movie(4,"Super 8", 112, Genre.THRILLER, "J.J. Abrams");
        assertThat(movideFromDB, is(expectedMovie));
    }

    @Test
    public void loadAllMovies() throws SQLException {

        Collection<Movie> todas = repository.findAll();
        assertThat(todas, is(Arrays.asList(
                new Movie(1, "Dark Knight", 152, Genre.ACTION, "Christopher Nolan"),
                new Movie(2, "Memento", 113, Genre.THRILLER, "Christopher Nolan"),
                new Movie(3, "Matrix", 136, Genre.ACTION, "Lana Wachowski, Lilly Wachowski")
        )));
    }

    @Test
    public void loadMovieById() {
        Movie mo = repository.findById(2);
        assertThat(mo, is(new Movie(2, "Memento", 113, Genre.THRILLER, "Christopher Nolan")));
    }

    @Test
    public void findMoviesByName() {
        Collection<Movie> todas = repository.findByName("matrix");
        assertThat(todas, is(Arrays.asList(
                new Movie(3, "Matrix", 136, Genre.ACTION, "Lana Wachowski, Lilly Wachowski")
        )));

        Collection<Movie> mat = repository.findByName("mat");
        assertThat(mat, is(Arrays.asList(
                new Movie(3, "Matrix", 136, Genre.ACTION, "Lana Wachowski, Lilly Wachowski")
        )));
    }

    @Test
    public void findByDirector() {
        Collection<Movie> moviesByDirector = repository.findByDirector("wachow");
        assertThat(moviesByDirector, is(Arrays.asList(
                new Movie(3, "Matrix", 136, Genre.ACTION, "Lana Wachowski, Lilly Wachowski")
        )));
    }

    @After
    public void tearDown() throws Exception {
        final Statement s = driver.getConnection().createStatement();
        s.execute("drop all objects delete files");
    }
}