package SqlUtil;

import java.sql.*;
import SqlUtil.Config;
import java.lang.Class.*;

public class MySQLUtil {
    static Connection conn = null;
    static Statement stmt = null;

    /*may be used in the future
    PreparedStatement ps = null;
    ResultSet rs = null;
     */

    public MySQLUtil() {
        registerDri();
        getConn();
    }

    /**
     * Using for get connection with DB.
     */
    public static void registerDri() {
        try {
            Class.forName(Config.getJdbc_driver());
            System.out.println("\033[92;1msuccessed to register driver!\033[0m");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("failed to register driver!");
        }
    }

    public static void getConn() {
        try {
            conn = DriverManager.getConnection(Config.getJbdc_url(), Config.getUSER(), Config.getPWD());
            System.out.println("\033[92;1msuccessed to get connection!\033[0m");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("failed to get connection!");
        }
    }



    /**
     * Multiply methods of one same name,
     * using for close ResultSet, Connection or Statement.
     */
    public static void close() {
        close(stmt);
        close(conn);
        System.out.println("\033[92;1msuccessed to close them!\033[0m");
    }

    public static void close(ResultSet rs, Connection conn, Statement stmt) {
        close(rs);
        close(stmt);
        close(conn);
        System.out.println("\033[92;1msuccessed to close them!\033[0m");
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                System.out.println("\033[92;1msuccessed to close ResultSet!\033[0m");
            } else System.err.println("empty pointer!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("\033[92;1msuccessed to close Connection!\033[0m");
            } else System.err.println("empty pointer!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("\033[92;1msuccessed to close Statement!\033[0m");
            } else System.err.println("empty pointer!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
                System.out.println("\033[92;1msuccessed to close PreparedStatement!\033[0m");
            } else System.err.println("empty pointer!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * To run SQL
     */
    public static ResultSet query(String sql) {
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement setPreStmt(String sql) {
        try {
            return conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet query(PreparedStatement ps, String... strings) {
        try {
            for (int i = 0; i < strings.length; i++) {
                ps.setString(i + 1, strings[i]);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(PreparedStatement ps , String... strings){
        try {
            for (int i = 0; i < strings.length; i++) {
                ps.setString(i + 1, strings[i]);
            }
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(String tbName, String column, String newData, String conColumn, String conNewData) {
        //run SQL
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE " + tbName + " SET " + column + "=? where " + conColumn + "=?");
            ps.setString(1, newData);
            ps.setString(2, conNewData);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean add(String tbName, String... newData) {
        //run SQL
        try {
            int n = newData.length;
            ;
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO " + tbName + " VALUES(?");
            for (int i = 1; i < n; i++) {
                sql.append(",?");
            }
            sql.append(")");
            System.out.println(sql.toString());
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            StringBuilder data = new StringBuilder();
            for (int i = 0; i < n; i++) {
                ps.setString(i+1,newData[i]);
            }
            System.out.println(ps.toString());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(String tbName, String con) {
        //run SQL
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tbName + " where ?");
            ps.setString(1, con);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet select(String tbName, String con) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tbName + " where " + con);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * output the ResultSet
     */
    public static String getString(ResultSet rs, String keyword) {
        try {
            return rs.getString(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getString(ResultSet rs, int column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getInt(ResultSet rs, String keyword) {
        try {
            return rs.getInt(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Wrong! DEFAULT SET IT -1");
        return -1;
    }

    public static int getInt(ResultSet rs, int column) {
        try {
            return rs.getInt(column);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Wrong! DEFAULT SET IT -1");
        return -1;
    }

    public static double getDouble(ResultSet rs, String keyword) {
        try {
            return rs.getDouble(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Wrong! DEFAULT SET IT -1");
        return -1;
    }

    public static double getDouble(ResultSet rs, int column) {
        try {
            return rs.getDouble(column);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Wrong! DEFAULT SET IT -1");
        return -1;
    }

    public static String getDate(ResultSet rs, String keyword) {
        try {
            Date result = rs.getDate(keyword);
            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDate(ResultSet rs, int column) {
        try {
            Date result = rs.getDate(column);
            return result.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
