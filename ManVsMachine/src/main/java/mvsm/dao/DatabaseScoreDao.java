package mvsm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that implements the ScoreDao and handles the interaction with the
 * database that involves high-scores and HighScoreusers.
 */
public class DatabaseScoreDao implements ScoreDao {

    private final Connector connector;

    public DatabaseScoreDao(Connector conn) {
        this.connector = conn;
    }

    @Override
    public int createDefault(String username, String algorithm) throws SQLException {
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + algorithm + "(username) VALUES(?)")) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
        return 1;
    }

    @Override
    public int read(String algorithm, String username, String map) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT " + map + " FROM " + algorithm + " WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            return rs.getInt(map);
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public boolean updateScore(String algorithm, String username, String map, int score) throws SQLException {
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("UPDATE " + algorithm + " SET " + map + " = ?" + " WHERE username = ?")) {
            stmt.setInt(1, score);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<HighScoreUser> listAllSorted(String algorithm, String mapToSortBy) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + algorithm + " ORDER BY " + mapToSortBy + " DESC;")) {
            rs = stmt.executeQuery();
            ArrayList<HighScoreUser> ret = new ArrayList<>();
            while (rs.next()) {
                HighScoreUser user = new HighScoreUser(rs.getString("username"));
                for (int i = 1; i < 11; i++) {
                    user.addScore("map" + i, rs.getInt("map" + i));
                }
//                user.addScore("map1", rs.getInt("map1"));
//                user.addScore("map2", rs.getInt("map2"));
//                user.addScore("map3", rs.getInt("map3"));
//                user.addScore("map4", rs.getInt("map4"));
//                user.addScore("map5", rs.getInt("map5"));
//                user.addScore("map6", rs.getInt("map6"));
//                user.addScore("map7", rs.getInt("map7"));
//                user.addScore("map8", rs.getInt("map8"));
//                user.addScore("map9", rs.getInt("map9"));
//                user.addScore("map10", rs.getInt("map10"));
                ret.add(user);
            }
            return ret;
        }
    }

    @Override
    public HighScoreUser listUser(String algorithm, String username) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + algorithm + " WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            HighScoreUser ret = new HighScoreUser(username);
            for (int i = 1; i < 11; i++) {
                ret.addScore("map" + i, rs.getInt("map" + i));
            }
//            ret.addScore("map1", rs.getInt("map1"));
//            ret.addScore("map2", rs.getInt("map2"));
//            ret.addScore("map3", rs.getInt("map3"));
//            ret.addScore("map4", rs.getInt("map4"));
//            ret.addScore("map5", rs.getInt("map5"));
//            ret.addScore("map6", rs.getInt("map6"));
//            ret.addScore("map7", rs.getInt("map7"));
//            ret.addScore("map8", rs.getInt("map8"));
//            ret.addScore("map9", rs.getInt("map9"));
//            ret.addScore("map10", rs.getInt("map10"));
            return ret;
        }
    }

}
