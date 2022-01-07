package pageConstructor;

import SqlUtil.MySQLUtil;
import Users.UserInfoParse;

import java.util.HashMap;

import static pageConstructor.ContentMaker.cnmTempContentMaker;

//import static pageConstructor.ContentMaker.*;

public class test {
    public static void main(String[] args) {
        MySQLUtil mySQLUtil = new MySQLUtil();
        String id = "1289358";
        String userInfo = "Users{UID=111, status=1, userName=test1, avator=/login_page/CustomerLoginPage/img/qq.png, sex=m, age=12, phone=1111, regionCountry=1, regionProvince=1, regionCity=1, joinTime=2021-12-20 19:47:10}Customers{property=CustomersProperties{preferCopyright='1', preferCategory='1', activity=1, statement='sss', label=1, level=1, grow=10, regionID=0}, credit=10, vip=Vips{type=3, expire='2021-11-16 19:30:59', vipLevel=4}}";
        HashMap<String, String> infoMap = UserInfoParse.parse(userInfo);

        infoMap.get("33");
    }
}
