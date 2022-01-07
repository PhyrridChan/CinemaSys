package Users;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserInfoParse {
    public static HashMap<String, String> parse(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        char[] chars = text.toCharArray();
        int count = 0;
        HashMap<String, String> map = new HashMap<>();
        String[] strs = new String[50];
        int j = 0;
        while (true) {
            StringBuilder sb = new StringBuilder();
            while (true) {
                char c = chars[j++];
                if (c != '{' && c != '}' && c != ',' && c != '\n' && c != ' ') {
                    sb.append(c);
                } else if (c != '\n' && c != ' ') {
                    break;
                }
            }
            if (sb.length() != 0) {
                String sbstr = sb.toString();
                if (sbstr.contains("=")) {
                    String[] sbs = sbstr.split("=");
                    map.put(sbs[0], sbs[1]);
                }
            }
            if (j >= chars.length) break;
        }
        return map;
    }

    public static void main(String[] args) {
        String str = "Users{UID='111', status=1, userName='test1', avator='path', sex=m, age=12, phone='1111', regionCountry=1, regionProvince=1, regionCity=1, joinTime='2021-11-15 20:38:11'}Customers{property=CustomersProperties{preferCopyright='1', preferCategory='1', activity=1, statement='sss', label=1, level=1, grow=10, regionID=0}, credit=10, vip=Vips{type=3, expire='2021-11-16 19:30:59', vipLevel=4}}";
        HashMap<String, String> strs = parse(str);
        for (Map.Entry<String, String> entry: strs.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
}


