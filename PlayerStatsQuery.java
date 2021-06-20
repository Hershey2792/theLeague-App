package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class PlayerStatsQuery {

    Connection connection;

    //constructor
    public PlayerStatsQuery() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");//load driver and connect to database
        connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/All_Players", "scott", "tiger");

    }


/*
    points methods to get the current points numbers, total points numbers
    of every player better than him and his all time points rank
 */

    public ArrayList<String> getPoints(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(pts,2), round((pts/GP),2)" +
                " as PPG from All_time_stats WHERE player = '" +
                playerNames + "'";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " scored " + resultSet.getString(2)
                    + " total points and averaged " + resultSet.getString(3) + " points per game for his career\n\n\n");
        }

        return list;
    }

    public ArrayList<String> getTotal(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(pts,2), round((pts/GP),2)" +
                " as PPG from All_time_stats WHERE pts >= (SELECT pts FROM All_time_stats WHERE player = '" +
                playerNames + "')";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " scored " + resultSet.getString(2)
                    + " total points and averaged " + resultSet.getString(3) + " points per game for his career\n");
        }

        return list;
    }


    public ArrayList<String> allTimePointsRank(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(player_id,0)" +
                " as PPG from All_time_stats WHERE player = '" +
                playerNames + "'";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(" RANK:  " + resultSet.getString(2)
                    + " all time");
        }

        return list;
    }


    /*
    rebounds methods to get the current rebounds numbers, total rebounds numbers
    of every player better than him and his all time rebound rank
     */
    public ArrayList<String> getRebounds(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(reb,2), round((reb/GP),2) from All_time_stats WHERE player = '" +
                playerNames + "'";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " brought down " + resultSet.getString(2)
                    + " total rebounds and averaged " + resultSet.getString(3) +
                    " rebounds per game for his career\n\n\n");
        }

        return list;
    }


    public ArrayList<String> getTotalRebs(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(reb,2), round((reb/GP),2)" +
                " from All_time_stats WHERE reb >= (SELECT reb FROM All_time_stats WHERE player = '" +
                playerNames + "') order by reb DESC";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " brought down " + resultSet.getString(2)
                    + " total rebounds and averaged " + resultSet.getString(3) +
                    " rebounds per game for his career\n");
        }

        return list;
    }


    public ArrayList<String> allTimeReboundsRank(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select count(*)" +
                " from All_time_stats where reb >= (SELECT reb FROM All_time_stats WHERE player = '" +
                playerNames + "')";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(" RANK:  " + resultSet.getString(1)
                    + " all time");
        }

        return list;
    }


    /*
    Assists methods to get the current assits numbers, total assist numbers
    of every player better than him and his all time assits rank
     */
    public ArrayList<String> getAssists(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(ast,2), round((ast/GP),2)" +
                " as assists from All_time_stats WHERE player = '" +
                playerNames + "'";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " assisted on " + resultSet.getString(2)
                    + " points and averaged " + resultSet.getString(3) + " assists per game for his career\n\n\n");
        }

        return list;
    }

    public ArrayList<String> getTotalAssists(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(ast,2), round((ast/GP),2)" +
                " from All_time_stats WHERE ast >= (SELECT ast FROM All_time_stats WHERE player = '" +
                playerNames + "') order by ast DESC";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " assisted on " + resultSet.getString(2)
                    + " points and averaged " + resultSet.getString(3) + " assists per game for his career\n");
        }

        return list;
    }

    public ArrayList<String> allTimeAssistsRank(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select count(*)" +
                " from All_time_stats where ast >= (SELECT ast FROM All_time_stats WHERE player = '" +
                playerNames + "')";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(" RANK:  " + resultSet.getString(1)
                    + " all time");
        }

        return list;
    }


    /*
    Assists methods to get the current fg% numbers, total fg%
    of every player better than him and his all time field goal% rank
    */
    public ArrayList<String> getFieldGoal(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(fg,2)" +
                " as fieldgoals from All_time_stats WHERE player = '" +
                playerNames + "'";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " averaged a " + resultSet.getString(2)
                    + " field goal percentage for his career\n\n\n");
        }

        return list;
    }

    public ArrayList<String> getTotalFieldGoal(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select player, round(fg,2)" +
                " from All_time_stats WHERE fg >= (SELECT fg FROM All_time_stats WHERE player = '" +
                playerNames + "') order by ast DESC";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(resultSet.getString(1) + " averaged a " + resultSet.getString(2)
                    + " field goal percentage for his career\n");
        }

        return list;
    }


    public ArrayList<String> allTimeFGRank(String playerNames) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        String pointsQuery = "select count(*)" +
                " from All_time_stats where fg >= (SELECT fg FROM All_time_stats WHERE player = '" +
                playerNames + "')";
        PreparedStatement ps = connection.prepareStatement(pointsQuery);
        ResultSet resultSet = ps.executeQuery(pointsQuery);
        while (resultSet.next()) {
            list.add(" RANK:  " + resultSet.getString(1)
                    + " all time");
        }

        return list;
    }


//close the connection once you are done with all methods

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

