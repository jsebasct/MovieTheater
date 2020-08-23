package com.platzi.javatests.movies.data;

import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieRepositoryJDBC implements MovieRepository {

    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Movie> movieMapper = new RowMapper<Movie>() {
        @Override
        public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Movie(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("minutes"),
                    Genre.valueOf(resultSet.getString("genre")),
                    resultSet.getString("director")
            );
        }
    };

    public MovieRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie findById(long id) {
        Object[] args = {id};
        return jdbcTemplate.queryForObject("select * from movies where id = ?", args, movieMapper);
    }

    @Override
    public Collection<Movie> findAll() {

        List<Movie> queryResult = jdbcTemplate.query("select * from movies", movieMapper);
        return queryResult;
    }

    @Override
    public void saveOrUpdate(Movie movie) {
        int updated = jdbcTemplate.update("insert into movies (name, minutes, genre) values (? , ? , ?)", movie.getName(),
                movie.getMinutes(),
                movie.getGenre().toString());
        System.out.println("Actualizados " + updated);
    }

    @Override
    public Collection<Movie> findByName(String movieTitle) {
        Object[] args = {"%" + movieTitle.toLowerCase() + "%"};
        String queryString = "select * from movies where LOWER(name) like ?";
        return jdbcTemplate.query(queryString, args, movieMapper);
    }

    @Override
    public Collection<Movie> findByDirector(String directorName) {
        String queryString = "select * from movies where LOWER(director) like ?";
        Object[] args = {"%" + directorName.toLowerCase() + "%"};
        return jdbcTemplate.query(queryString, args, movieMapper);
    }

    @Override
    public Collection<Movie> findByTemplate(Movie movieTemplate) {
        StringBuilder queryString = new StringBuilder("select * from movies ");
        queryString.append(" where 1=1 ");

        List<Object> surrogate = new ArrayList<>();

        if (movieTemplate.getName() != null) {
            queryString.append(" and LOWER(name) like ? ");
            surrogate.add("%" + movieTemplate.getName().toLowerCase() + "%");
        }

        if (movieTemplate.getMinutes() != null) {
            queryString.append(" and minutes <= ? ");
            surrogate.add(movieTemplate.getMinutes());
        }

        if (movieTemplate.getGenre() != null) {
            queryString.append(" and LOWER(genre) like ? ");
            surrogate.add("%" + movieTemplate.getGenre().toString().toLowerCase() + "%");
        }

        if (movieTemplate.getDirector() != null) {
            queryString.append(" and LOWER(director) like ? ");
            surrogate.add("%" + movieTemplate.getDirector().toLowerCase() + "%");
        }

//        Object[] args = {
//                "%" + movieTemplate.getName().toLowerCase() + "%",
//                movieTemplate.getMinutes(),
//                "%" + movieTemplate.getGenre().toString().toLowerCase() + "%",
//                "%" + movieTemplate.getDirector().toLowerCase() + "%"
//        };
        Object[] args = surrogate.toArray();

        return jdbcTemplate.query(queryString.toString(), args, movieMapper);
    }
}
