package Users;

import SqlUtil.MySQLUtil;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        MySQLUtil mySQLUtil = new MySQLUtil();
        Customers users = new Customers("userName", "avator", '男', 13, "12222222222", 1, 1, 1);
        users.printInfo();

        users.addCus("111111");
    }
}
