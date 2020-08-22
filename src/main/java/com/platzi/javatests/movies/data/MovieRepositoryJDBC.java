package com.platzi.javatests.movies.data;

import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MovieRepositoryJDBC implements MovieRepository {

    private JdbcTemplate template;

    private static RowMapper<Movie> movieMapper = new RowMapper<Movie>() {
        @Override
        public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Movie(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("minutes"),
                    Genre.valueOf(resultSet.getString("genre"))
            );
        }
    };

    public MovieRepositoryJDBC(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Movie findById(long id) {
        Object[] args = {id};
        return template.queryForObject("select * from movies where id = ?", args, movieMapper);
    }

    @Override
    public Collection<Movie> findAll() {

        List<Movie> queryResult = template.query("select * from movies", movieMapper);
        return queryResult;
    }

    @Override
    public void saveOrUpdate(Movie movie) {
        int updated = template.update("insert into movies (name, minutes, genre) values (? , ? , ?)", movie.getName(),
                movie.getMinutes(),
                movie.getGenre().toString());
        System.out.println("Actualizados " + updated);
    }
}
