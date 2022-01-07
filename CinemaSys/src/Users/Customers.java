package Users;

import SqlUtil.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Customers extends Users {
    private CustomersProperties property;
    private int credit;
    private Vips vip;
    private CreditSys creditSys;
    private Activities activities;
    private CustomersProperties properties;
    //Customer绑定第三方流媒体账号
    //爱奇艺开放平台不开放注册，需要进行商务联系
    //腾讯开放平台在12年后仅提供13年后停止了官方的SDK下载
    //bilibili/tencent 拿mid与现有用户进行绑定，存储用户公开信息
        /*
        引导用户跳转至哔哩哔哩授权页面
        用户决定是否同意授权
        第三方服务器获取授权码(code)
        通过授权码获取access_token
         */

    public Customers(String UID, int status, String userName, String avator, char sex, int age, String phone, int regionCountry, int regionProvince, int regionCity, String joinTime, CustomersProperties property, int credit, Vips vip) {
        super(UID, status, userName, avator, sex, age, phone, regionCountry, regionProvince, regionCity, joinTime);
        this.property = property;
        this.credit = credit;
        this.vip = vip;
    }

    public Customers(String userName, String avator, char sex, int age, String phone, int regionCountry, int regionProvince, int regionCity) {
        super(userName, avator, sex, age, phone, regionCountry, regionProvince, regionCity);
    }

    @Override
    public String toString() {
        String str = null;
        if (!(property == null || vip == null)) str = super.toString() + "Customers{" +
                "property=" + property +
                ", credit=" + credit +
                ", vip=" + vip +
                '}';
        else str = super.toString();
        return str;
    }

    public void printInfo() {
        System.out.print("Customers{");
        super.printInfo();
        if (!(property == null || vip == null))
            System.out.println(", property=" + property.toString()
                    + ", credit=" + credit
                    + ", " + vip.toString() + "}");
    }

    public boolean addCus(String PWD) {
        boolean suc = false;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String head = formatter.format(date); //12
        ResultSet rs = MySQLUtil.query("SELECT UID FROM tb_Users WHERE UID LIKE '" + head + "%' ORDER BY UID DESC LIMIT 1;");
        String rear = "";
        try {
            if (rs.next()) {
                rear = MySQLUtil.getString(rs, "eventID").substring(11, 17);
                rear = head + String.format("%04d", Integer.parseInt(rear) + 1);
            } else {
                rear = "000001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String UID = head + rear;
        if (MySQLUtil.add("tb_Users", UID, "1",
                this.getUserName(),
                this.getAvator(),
                this.getSex() + "",
                this.getAge() + "",
                this.getPhone(),
                this.getRegionCountry() + "",
                this.getRegionProvince() + "",
                this.getRegionCity() + "",
                formatter2.format(date),
                "", "", "", "", PWD
        ))
            System.out.println("\033[92;1msuccessed to insert!\033[0m");
        return suc;
    }
}
