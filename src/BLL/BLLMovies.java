package BLL;

import DAL.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jagesh on 05/30/2016.
 */
public class BLLMovies
{
    public ResultSet getAllMoviesFromMovies(int movieid) throws SQLException, ClassNotFoundException
    {
        Connection connection = DAO.getConnection();
        String sql = "SELECT * FROM movie WHERE MovieID=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,movieid);

        ResultSet resultSet = statement.executeQuery();

        return resultSet;
    }
}
