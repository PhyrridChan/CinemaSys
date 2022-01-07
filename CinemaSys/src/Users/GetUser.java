package Users;

import Items.Items;
import SqlUtil.MySQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Items.GetItem.getWhereLimit;
import static Users.UserLogin.*;

public class GetUser {
    public static Customers[] cusPoster(int page, String query) {
        try (ResultSet rs = getUserInfo(1, page, query)) {
            if (rs != null) return handleRsOfCus(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Customers[] handleRsOfCus(ResultSet rs) throws SQLException {
        Customers[] items;
        System.out.println(rs.getType());
        rs.last();
        items = new Customers[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            String UID = rs.getString("UID");
            CustomersProperties properties = propertiesQuery(UID);
            int credit = CreditSysQuery(UID);
            Vips vip = vipsQuery(UID);
            Customers item = new Customers(UID, rs.getInt("state"), rs.getString("userName"),
                    "/cinema/upload" + rs.getString("avator"), rs.getString("sex").charAt(0), rs.getInt("age"), rs.getString("phone"), rs.getInt("regionCountry"), rs.getInt("regionProvince"), rs.getInt("regionCity"), rs.getString("joinTime"), properties, credit, vip);
            items[++i] = item;
        }
        return items;
    }

    public static Staffs[] staffPoster(int page, String query) {
        try (ResultSet rs = getUserInfo(2, page, query)) {
            if (rs != null) return handleRsOfStaffs(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Staffs[] handleRsOfStaffs(ResultSet rs) throws SQLException {
        Staffs[] items;
        System.out.println(rs.getType());
        rs.last();
        items = new Staffs[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Staffs item = new Staffs(rs.getString("UID"), rs.getInt("state"), rs.getString("userName"),
                    "/cinema/upload" + rs.getString("avator"), rs.getString("sex").charAt(0), rs.getInt("age"), rs.getString("phone"), rs.getInt("regionCountry"), rs.getInt("regionProvince"), rs.getInt("regionCity"), rs.getString("joinTime"), rs.getInt("rank"), rs.getInt("root"), rs.getInt("workGroup"), rs.getInt("workPlace"), rs.getString("workOn"));
            items[++i] = item;
        }
        return items;
    }

    private static ResultSet getUserInfo(int type, int page, String query) {
        int capacity = 10;
        String tb_name = "";
        String tail = "";
        switch (type) {
            case 1:
                tb_name = "tb_Users";
                break;
            case 2:
                tb_name = "tb_Staffs";
                break;
        }
        if (query != null && !query.equals("")) {
            tail = getWhereLimit(tb_name, query);
        }
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT *  FROM " + tb_name + tail + " LIMIT " + capacity + " OFFSET " + ((page - 1) * capacity));
        return MySQLUtil.query(ps);
    }
}
