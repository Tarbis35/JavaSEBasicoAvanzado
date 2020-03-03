package com.anncode.amazonviewer.dao;

import com.anncode.amazonviewer.db.IDBConnection;
import com.anncode.amazonviewer.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import static com.anncode.amazonviewer.db.DataBase.*;

public interface MovieDAO extends IDBConnection {
    default Movie setMovieViewed(Movie movie){

        return movie;
    }
    default ArrayList<Movie> read(){
        ArrayList<Movie> movies = new ArrayList<>();
        try (Connection connection = connectionToDB()){
            String query = "SELECT * FROM "+TMOVIE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Movie  movie = new Movie(rs.getString(TMOVIE_TITLE),
                        rs.getString(TMOVIE_GENRE),
                        rs.getString(TMOVIE_CREATOR),
                        Integer.parseInt(rs.getString(TMOVIE_DURATION)) ,
                        Short.parseShort(rs.getString(TMOVIE_YEAR)));
                movie.setId(Integer.valueOf(rs.getString(TMOVIE_ID)));
                movies.add(movie);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
