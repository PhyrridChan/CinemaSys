package Items;

import SqlUtil.MySQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GetArrange {
    String AID;

    public static ResultSet arrangeQuery(String AID) {
        String sql = "SELECT * FROM Arrange WHERE ID = ?";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        return MySQLUtil.query(ps, AID);
    }

    public static ResultSet arrangesQuery(String theaterID) {
        String sql = "SELECT * FROM Arrange WHERE theaterID = ?";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        return MySQLUtil.query(ps, theaterID);
    }

    public static Arrange getArrange(String AID) {
        try (ResultSet rs = arrangeQuery(AID);) {
            return handleRsOfArrange(rs)[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Arrange[] getArranges(String theaterID) {
        try (ResultSet rs = arrangesQuery(theaterID);) {
            return handleRsOfArrange(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Arrange[] handleRsOfArrange(ResultSet rs) throws SQLException {
        Arrange[] arranges;
        System.out.println(rs.getType());
        rs.last();
        arranges = new Arrange[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Arrange arrange = new Arrange(
                    rs.getString("ID"),
                    rs.getString("movieID"),
                    rs.getString("theaterID"),
                    rs.getString("startTime"),
                    rs.getInt("room"),
                    rs.getDouble("price"),
                    rs.getInt("state"));
            arranges[++i] = arrange;
        }
        return arranges;
    }

    public static HashMap<String, String> getSupInfo(String AID) {
        HashMap<String, String> map = new HashMap<>();
        String sql = "SELECT * FROM supInfo WHERE ID = ?";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        try (ResultSet rs = MySQLUtil.query(ps, AID)) {
            if (rs.next()) {
                map.put("time", rs.getString("time"));
                map.put("theaterName", rs.getString("theaterName"));
                map.put("name", rs.getString("movieName"));
                map.put("showroom", rs.getString("showrooms"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Arrange[] reduceArrange(Arrange[] movieArranges , String movieID) {
        int count = 0;
        for (Arrange arrange : movieArranges) {
            if (arrange.getMovieID().equals(movieID)) count++;
        }
        Arrange[] arranges = new Arrange[count];
        int j = -1;
        for (Arrange arrange : movieArranges) {
            if (arrange.getMovieID().equals(movieID)) arranges[++j] = arrange;
        }
        return arranges;
    }

    public static Arrange[] reduceMovie(Arrange[] movieArranges) {
        Set<String> set = new HashSet<>();
        for (Arrange arrange : movieArranges) {
            set.add(arrange.getMovieID());
        }
        Arrange[] arranges = new Arrange[set.size()];
        int j = -1;
        set.clear();
        for (Arrange arrange : movieArranges) {
            if (!set.contains(arrange.getMovieID())) {
                set.add(arrange.getMovieID());
                arranges[++j] = arrange;
            }
        }
        return arranges;
    }
}
