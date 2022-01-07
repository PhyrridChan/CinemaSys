package Users;

import SqlUtil.MySQLUtil;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activities {
    private String eventID;
    private String UID_cstm;
    private String UID_staff;
    private int eventTag;
    private String event;

    public static void activities(String UID_cstm, String UID_staff, int eventTag, String event) throws Exception {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String head = formatter.format(date); //12
        ResultSet rs = MySQLUtil.query("SELECT eventID FROM tb_ActivityRate WHERE eventID LIKE '"+ head +"%' ORDER BY eventID DESC LIMIT 1;");
        String rear = "";
        if (rs.next()) {
            rear = MySQLUtil.getString(rs, "eventID").substring(11, 17);
            rear = head + String.format("%04d", Integer.parseInt(rear) + 1);
        } else {
            rear = "000001";
        }
        String eventID =  head + rear;
        if (MySQLUtil.add("tb_ActivityRate",eventID, UID_cstm, UID_staff, eventTag+"", event))
            System.out.println("\033[92;1msuccessed to insert this event!\033[0m");
    }
}
