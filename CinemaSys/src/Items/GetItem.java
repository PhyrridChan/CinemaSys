package Items;

import SqlUtil.MySQLUtil;
import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetItem {
    private static ResultSet rs;

    public static Items get(String ID) {
        Items items;
        try (ResultSet rs = getQuery(ID)) {
            if (rs.next()) {
                Casts casts = castsQuery(ID);
                ToWatch toWatch = toWatchQuery(ID);
                return new Items(ID, rs.getInt("state"),
                        rs.getString("itemName"),
                        rs.getString("engName"),
                        rs.getString("pics"),
                        rs.getString("keywords"),
                        rs.getString("genre"),
                        rs.getInt("country"),
                        rs.getString("runtime"),
                        rs.getString("releaseDate"),
                        rs.getDouble("rate"),
                        rs.getDouble("boxOffice"),
                        rs.getString("introduction"), casts,
                        rs.getString("soundMix"),
                        rs.getString("tech"), toWatch,
                        rs.getString("awards"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getQuery(String ID) throws SQLException {
        String tbName = "Items";
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT * FROM " + tbName + " WHERE ID=?");
        return MySQLUtil.query(ps, ID);
    }

    public static Casts castsQuery(String ID) throws SQLException {
        String tbName = "Casts";
        ResultSet rs = tableQueryCstm(tbName, ID);
        if (rs.next())
            return new Casts(rs.getString("director"),
                    rs.getString("cast"),
                    rs.getString("producer"),
                    rs.getString("writer"),
                    rs.getString("cameraman"),
                    rs.getString("artDirector"),
                    rs.getString("editor"),
                    rs.getString("sound"));
        else return null;
    }

    public static ToWatch toWatchQuery(String ID) throws SQLException {
        String tbName = "ToWatch";
        ResultSet rs = tableQueryCstm(tbName, ID);
        if (rs.next())
            return new ToWatch(rs.getString("tencent"),
                    rs.getString("aqiyi"),
                    rs.getString("youku"),
                    rs.getString("bilibili"),
                    rs.getString("theater"));
        else return null;
    }

    public static ResultSet tableQueryCstm(String tbName, String ID) {
        String sql = "SELECT * FROM " + tbName + " WHERE ID=?";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        return MySQLUtil.query(ps, ID);
    }

    public static ResultSet getRec() {
        String tbName = "movieRec";
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM " + tbName + " LIMIT 3");
        return MySQLUtil.query(ps);
    }

    public static String[] RecInfo() {
        String[] str = new String[10];
        try (ResultSet rs = getRec();) {
            int i = 0;
            if (rs.next()) {
                str[i++] = rs.getString("ID"); //0
                str[i++] = rs.getString("itemName");
                str[i++] = rs.getString("introduction");
                str[i++] = rs.getString("recImg"); //3
                if (rs.next()) {
                    str[i++] = rs.getString("ID"); //4
                    str[i++] = rs.getString("itemName");
                    str[i++] = rs.getString("pics"); //6
                }
                if (rs.next()) {
                    str[i++] = rs.getString("ID"); //7
                    str[i++] = rs.getString("itemName");
                    str[i++] = rs.getString("pics"); //9
                }
            }
            return str;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getMoviePoster(int type, String... para) throws IOException {
        String tbName = "";
        String limit = "";
        switch (type) {
            case 1:
                tbName = "moviehit";
                limit = " LIMIT 20";
                break;
            case 2:
                tbName = "moviecomming";
                limit = " LIMIT 20";
                break;
            case 3:
                tbName = "items";
                if (para.length == 2) {
                    String movieType = parseType(para[0]);
                    limit = " WHERE type genre '%" + movieType + "%' AND ID != '-1' LIMIT 50";
                } else limit = " WHERE ID != '-1' LIMIT 50";
                break;
            case 4:
                tbName = "Theaters";
                if (para.length == 3) {
                    String site = Theaters.parseSite(para[0], para[1], para[2]);
                    limit = " WHERE site like '%" + site + "%' LIMIT 20";
                } else limit = " LIMIT 20";
                break;
        }
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM " + tbName + limit);

        return MySQLUtil.query(ps);
    }

    private static final String[] genere = {
            "动作片", "喜剧片", "纪录片", "戏剧", "适合家庭的", "奇幻片", "外语片", "恐怖片", "音乐片", "爱情片", "科幻片", "惊悚电影"
    };

    private static String parseType(String s) {
        return genere[Integer.parseInt(s)];
    }

    public static ResultSet getTheaterInfo(String id) {
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM Theaters WHERE ID = ?");
        return MySQLUtil.query(ps, id);
    }

    public static ResultSet getMovieInfo(String id) {
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM items WHERE ID = ?");
        return MySQLUtil.query(ps, id);
    }

    public static ResultSet getMovieInfo(int type, int page, String query) {
        if (type == 7) return getRank(page, query);
        int capacity = 0;
        String tail = "";
        switch (type) {
            case 6:
                capacity = 10;
                break;
        }
        if (query != null && !query.equals("")) {
            tail = getWhereLimit("items", query);
        }
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM items" + tail + " LIMIT " + capacity + " OFFSET " + ((page - 1) * capacity));
        return MySQLUtil.query(ps);
    }

    public static String getWhereLimit(String tbName, String query) {
        String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_NAME like '" + tbName + "'";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        ResultSet rs = MySQLUtil.query(ps);
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE ");
        try {
            while (rs.next()) {
                String temp = rs.getString(1) + " LIKE '%" + query + "%'";
                sb.append(temp).append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static Items moviePoster(String id) {
        try (ResultSet rs = getMovieInfo(id)) {
            if (rs != null) return handleRsOfMovie(rs)[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Items[] moviePoster(int type, int page, String query) {
        try (ResultSet rs = getMovieInfo(type, page, query)) {
            if (rs != null) return handleRsOfMovie(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Items[] handleRsOfMovie(ResultSet rs) throws SQLException {
        Items[] items;
        System.out.println(rs.getType());
        rs.last();
        items = new Items[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Items item = new Items(rs.getString("ID"), rs.getInt("state"),
                    rs.getString("itemName"),
                    rs.getString("engName"),
                    rs.getString("pics"),
                    rs.getString("keywords"),
                    rs.getString("genre"), rs.getInt("country"), rs.getString("runtime"),
                    rs.getString("releaseDate"),
                    rs.getDouble("rate"),
                    rs.getDouble("boxOffice"),
                    rs.getString("introduction"),
                    null,
                    "", "", null,
                    rs.getString("awards"));
            items[++i] = item;
        }
        return items;
    }

    public static Items[] moviePoster(int type, String... para) {
        try (ResultSet rs = getMoviePoster(type, para)) {
            if (rs != null) return handleRsOfMovie(rs);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Theaters theatersPoster(String id) {
        try (ResultSet rs = getTheaterInfo(id)) {
            if (rs != null) return handleRsOfTheaters(rs)[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Theaters[] theatersPoster(int type, String... para) {
        try (ResultSet rs = getMoviePoster(type, para)) {
            if (rs != null) return handleRsOfTheaters(rs);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Theaters[] theatersPoster(int type, String movie) {
        try (ResultSet rs = getMoviePoster(type, movie)) {
            if (rs != null) return handleRsOfTheaters(type, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Theaters[] theatersPoster(int type, int page, String query) {
        try (ResultSet rs = getMoviePoster(type, page, query)) {
            if (rs != null) return handleRsOfTheaters(type, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Theaters[] handleRsOfTheaters(int type, ResultSet rs) throws SQLException {
        Theaters[] items;
        System.out.println(rs.getType());
        rs.last();
        items = new Theaters[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Theaters item = GetItem.theatersPoster(rs.getString("ID"));
            items[++i] = item;
        }
        return items;
    }

    private static ResultSet getMoviePoster(int type, String movie) {
        String tbName = "";
        String limit = "";
        switch (type) {
            case 5:
                tbName = "Arrange";
                limit = " WHERE movieID = '" + movie + "' LIMIT 50";
                break;
        }
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT DISTINCT theaterID AS ID FROM " + tbName + limit);
        return MySQLUtil.query(ps);
    }

    private static ResultSet getMoviePoster(int type, int page, String query) {
        int capacity = 0;
        String tail = "";
        switch (type) {
            case 6:
                capacity = 10;
                break;
        }
        if (query != null && !query.equals("")) {
            tail = getWhereLimit("Theaters", query);
        }
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM Theaters" + tail + " LIMIT " + capacity + " OFFSET " + ((page - 1) * capacity));
        return MySQLUtil.query(ps);
    }

    private static Theaters[] handleRsOfTheaters(ResultSet rs) throws SQLException {
        Theaters[] items;
        System.out.println(rs.getType());
        rs.last();
        items = new Theaters[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Arrange[] arranges = GetArrange.getArranges(rs.getString("ID"));
            Theaters item = new Theaters(rs.getString("ID"), rs.getInt("state"), rs.getString("theaterName"), rs.getString("site"), arranges, (rs.getString("showrooms")!=null ? rs.getString("showrooms").split(",") : null), "/imgs/cinemaLogo/" + rs.getString("pic") + ".png", rs.getString("phone"), rs.getString("introduction"));
            items[++i] = item;
        }
        return items;
    }

    public static ResultSet getRank() {
        return getRank(1, "");
    }

    public static ResultSet getRank(int page, String query) {
        String tbName = "ranktable";
        String whereLimit = "";
        if (query != null && !query.equals("")) whereLimit = getWhereLimit(tbName, query);
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM " + tbName + whereLimit + " ORDER BY `rank` LIMIT 10 OFFSET " + ((page - 1) * 10));
        return MySQLUtil.query(ps);
    }

    public static Items[] rankInfo() {
        Items[] items;
        try (ResultSet rs = getRank()) {
//            System.out.println(rs.getType());
            rs.last();
            items = new Items[rs.getRow()];
            rs.beforeFirst();
            int i = -1;
            while (rs.next()) {
                Items item = new Items(rs.getString("ID"), rs.getInt("state"),
                        rs.getString("itemName"),
                        rs.getString("engName"),
                        rs.getString("pics"),
                        rs.getString("keywords"),
                        rs.getString("genre"), rs.getInt("country"), rs.getString("runtime"),
                        rs.getString("releaseDate"),
                        rs.getDouble("rate"),
                        rs.getDouble("boxOffice"),
                        rs.getString("introduction"),
                        null,
                        "", "", null,
                        rs.getString("awards"));
                items[++i] = item;
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
