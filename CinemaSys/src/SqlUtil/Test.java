package SqlUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        System.out.println(Config.getDB_name());
        System.out.println(Config.getJbdc_url());
        System.out.println(Config.getPWD());
        System.out.println(Config.getJdbc_driver());
        System.out.println(Config.getUSER());
        MySQLUtil mysql = new MySQLUtil();
//        ResultSet rs2 = MySQLUtil.query("SELECT * FROM area_code_2021 where level = 1");
//        int i = 0;
//        while (rs2.next()) {
//            String pcode = rs2.getString("code");
////            System.out.println(pcode);
//            ResultSet rs = MySQLUtil.query("SELECT * FROM area_code_2021 where level = 2 and pcode="+pcode);
//            System.out.println("cityList[" + (i++) + "] = [");
//            if (rs.next()) {
//                System.out.println("'" + rs.getString("name") + "'");
//                while (rs.next()) {
//                    System.out.print(",");
//                    System.out.println("'" + rs.getString("name") + "'");
//                }
//            }
//            System.out.println("];");
//        }

//        ResultSet rs2 = MySQLUtil.query("SELECT * FROM area_code_2021 where level = 1");
//        int i = 0,j = 0, m = 0;
//        System.out.println("{");
//        while (rs2.next()) {
//            String pcode = rs2.getString("code");
//            ResultSet rs = MySQLUtil.query("SELECT count(*) FROM area_code_2021 where level = 2 and pcode="+pcode);
//            if (rs.next()) j = rs.getInt(1);
//            int k = (i++)*30;
//            for (int p = 0;p < j; p++) System.out.println((k+p) +":"+ m++ +",");
//        }
//        System.out.println("}");

        ResultSet rs2 = MySQLUtil.query("SELECT * FROM area_code_2021 where level = 2");
        int i = 0, m = 0 ;
        while (rs2.next()) {
            String pcode = rs2.getString("code");
//            System.out.println(pcode);
            ResultSet rs = MySQLUtil.query("SELECT * FROM area_code_2021 where level = 3 and pcode="+pcode);
            System.out.println("cityList[" + (m++) + "] = [");
            if (rs.next()) {
                System.out.println("'" + rs.getString("name") + "'");
                while (rs.next()) {
                    System.out.print(",");
                    System.out.println("'" + rs.getString("name") + "'");
                }
            }
            System.out.println("];");
        }
        MySQLUtil.close();
    }
}
