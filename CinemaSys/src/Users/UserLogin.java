package Users;

import SqlUtil.MySQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    public static Users login(int loginType, String userName, String userPWD) {
        Users users;
        ResultSet rs = null;
        try {
            rs = loginQuery(loginType, userName, userPWD);
            if (loginType == 1) {
                if (rs.next()) {
                    String UID = rs.getString("UID");
                    CustomersProperties properties = propertiesQuery(UID);
                    int credit = CreditSysQuery(UID);
                    Vips vip = vipsQuery(UID);
                    return new Customers(UID, rs.getInt("state"), rs.getString("userName"),
                            "/cinema/upload" + rs.getString("avator"), rs.getString("sex").charAt(0), rs.getInt("age"), rs.getString("phone"), rs.getInt("regionCountry"), rs.getInt("regionProvince"), rs.getInt("regionCity"), rs.getString("joinTime"), properties, credit, vip);
                }

            } else if (loginType == 2) {
                if (rs.next()) {
                    return new Staffs(rs.getString("UID"), rs.getInt("state"), rs.getString("userName"),
                            "/cinema/upload" + rs.getString("avator"), rs.getString("sex").charAt(0), rs.getInt("age"), rs.getString("phone"), rs.getInt("regionCountry"), rs.getInt("regionProvince"), rs.getInt("regionCity"), rs.getString("joinTime"), rs.getInt("rank"), rs.getInt("root"), rs.getInt("workGroup"), rs.getInt("workPlace"), rs.getString("workOn"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet loginQuery(int loginType, String userName, String userPwd) throws SQLException {
        String tbName = "";
        tbName = loginType == 1 ? "tb_Users" : "tb_Staffs";
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT * FROM " + tbName + " WHERE userName=? and userPwd=?");
        return MySQLUtil.query(ps, userName, userPwd);
    }

    public static CustomersProperties propertiesQuery(String UID) throws SQLException {
        String tbName = "tb_CustomersProperties";
        ResultSet rs = tableQueryCstm(tbName, UID);
        if (rs.next())
            return new CustomersProperties(rs.getString("preferCopyright"), rs.getString("preferCategory"), rs.getInt("activity"), rs.getString("statement"), rs.getInt("label"), rs.getInt("level"), rs.getInt("grow"));
        else return null;
    }

    public static int CreditSysQuery(String UID) throws SQLException {
        String tbName = "credit";
        ResultSet rs = tableQueryCstm(tbName, UID);
        if (rs.next()) return rs.getInt("credit");
        System.err.println("Wrong! DEFAULT SET IT -1");
        return -1;
    }

    public static Vips vipsQuery(String UID) throws SQLException {
        String tbName = "vipview";
        ResultSet rs = tableQueryCstm(tbName, UID);
        if (rs.next()) return new Vips(rs.getInt("type"), rs.getString("expire"), rs.getInt("VipLevel"));
        else return null;
    }

    public static ResultSet tableQueryCstm(String tbName, String UID) {
        String sql = "SELECT * FROM " + tbName + " WHERE UID=?";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        return MySQLUtil.query(ps, UID);
    }
}
