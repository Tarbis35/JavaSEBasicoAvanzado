package com.anncode.amazonviewer.dao;

import com.anncode.amazonviewer.db.IDBConnection;
import com.anncode.amazonviewer.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                movie.setViewed(getMovieViewed(preparedStatement, connection, movie.getId()));
                movies.add(movie);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }



    private boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int id_movie){
        boolean viewed = false;
        String query = "SELECT * FROM "+TVIEWED+
                " WHERE "+TVIEWED_ID_MATERIAL + " = ?"+
                " AND "+TVIEWED_ID_ELEMENT +" = ?"+
                " AND "+TVIEWED_ID_USER+" = ?";
        ResultSet rs;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID_TMATERIALS[0]);
            preparedStatement.setInt(2, id_movie);
            preparedStatement.setInt(3, TUSER_IDUSUARIO);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                System.out.println("pelicula no vista");
                viewed =true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viewed;
    }

}
