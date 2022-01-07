package bills;

import Items.GetItem;
import Items.Arrange;
import SqlUtil.MySQLUtil;
import Users.UserInfoParse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static Items.GetArrange.getArrange;

public class GetBill {
    public static Bills getbill(String bid) {
        Bills bills;
        try (ResultSet rs = getbillInfo(bid);) {
            return handleRsOfBill(rs)[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bills[] getbills(String uid) {
        try (ResultSet rs = getbillInfoOfUser(uid)) {
            return handleRsOfBillAndUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bills[] handleRsOfBillAndUser(ResultSet rs) throws SQLException {
        Bills[] bills;
        System.out.println(rs.getType());
        rs.last();
        bills = new Bills[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Bills bill = getbill(rs.getString("billID"));
            bills[++i] = bill;
        }
        return bills;
    }

    private static ResultSet getbillInfoOfUser(String uid) {
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT * FROM billAndUsers WHERE UID = ? ORDER BY billID DESC");
        return MySQLUtil.query(ps, uid);
    }

    private static Bills[] handleRsOfBill(ResultSet rs) throws SQLException {
        Bills[] bills;
        System.out.println(rs.getType());
        rs.last();
        bills = new Bills[rs.getRow()];
        rs.beforeFirst();
        int i = -1;
        while (rs.next()) {
            Arrange arrange = getArrange(rs.getString("arrangeID"));
            String ticketStr = rs.getString("tickets");
            String[] tickets = ticketStr == "" || ticketStr == null ? null : ticketStr.split(",");
            Bills bill = new Bills(rs.getString("billID"), tickets,
                    arrange, GetItem.get(arrange.getMovieID()), rs.getDouble("total"), rs.getInt("counter"), rs.getInt("state"));
            bills[++i] = bill;
        }
        return bills;
    }

    public static ResultSet getbillInfo(String bid) {
        PreparedStatement ps = MySQLUtil.setPreStmt("SELECT * FROM Bill WHERE billID = ?");
        return MySQLUtil.query(ps, bid);
    }

    public static Bills uploadBill(Bills bills) {
        String billID = bills.getBid(), arrangeID = bills.getArrange().getID();
        String tickets = bills.getTicketStr();
        int counter = bills.getCounter(), state = bills.getState();
        double total = bills.getTotal();
        PreparedStatement ps;
        int flag = -1;
        if (billID == null) {
            flag = 1;
            billID = createBillID();
            ps = MySQLUtil.setPreStmt("INSERT INTO Bill value (?,?,?,?,?,?);");
        } else
            ps = MySQLUtil.setPreStmt("UPDATE Bill SET tickets = ?, arrangeID = ?, total = ?, counter = ?, state = ? WHERE billID = ?;");

        if (flag == 1) MySQLUtil.update(ps, billID, tickets, arrangeID, "" + total, "" + counter, "" + state);
        else MySQLUtil.update(ps, tickets, arrangeID, "" + total, "" + counter, "" + state, billID);
        return getbill(billID);
    }

    private static String createBillID() {
        boolean suc = false;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String head = formatter.format(date); //12
        ResultSet rs = MySQLUtil.query("SELECT billID FROM Bill WHERE billID LIKE '" + head + "%' ORDER BY billID DESC LIMIT 1;");
        String rear = "";
        try {
            if (rs.next()) {
                rear = MySQLUtil.getString(rs, "billID").substring(11, 17);
                rear = head + String.format("%04d", Integer.parseInt(rear) + 1);
            } else {
                rear = "000001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return head + rear;
    }

    public static void main(String[] args) {
        MySQLUtil mySQLUtil = new MySQLUtil();
        System.out.println(createBillID());
    }

    public static boolean updateUser(String bid, String userInfo) {
        HashMap<String, String> info = UserInfoParse.parse(userInfo);
        String uid = info.get("UID");
        String sql = "INSERT INTO billAndUsers VALUES (?, ?)";
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        return MySQLUtil.update(ps, bid, uid);
    }
}


