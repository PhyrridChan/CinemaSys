package Items;

import SqlUtil.MySQLUtil;
import Users.UserInfoParse;
import bills.Bills;

import java.sql.SQLException;
import java.util.HashMap;

import static pageConstructor.ContentMaker.*;

public class test {
    public static void main(String[] args) throws SQLException {
        MySQLUtil mySQLUtil = new MySQLUtil();
        HashMap<String,String> hashMap = GetArrange.getSupInfo("232323");
        System.out.println();
    }
}