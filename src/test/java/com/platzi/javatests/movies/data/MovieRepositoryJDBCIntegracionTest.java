package com.platzi.javatests.movies.data;

import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class MovieRepositoryJDBCIntegracionTest {

    @Test
    public void loadAllMovies() throws SQLException {

        DriverManagerDataSource driver = new DriverManagerDataSource("jdbc:h2:mem:test;MODE=MYSQL", "sa", "sa");

        //sql-scripts/
        ScriptUtils.executeSqlScript(driver.getConnection(), new ClassPathResource("test-data.sql"));

        JdbcTemplate template = new JdbcTemplate(driver);
        MovieRepository repository = new MovieRepositoryJDBC(template);

        Collection<Movie> todas = repository.findAll();
        assertThat(todas, is(Arrays.asList(
                new Movie(1, "Dark Knight", 152, Genre.ACTION),
                new Movie(2, "Memento", 113, Genre.THRILLER),
                new Movie(3, "Matrix", 136, Genre.ACTION)
        )));
    }
}