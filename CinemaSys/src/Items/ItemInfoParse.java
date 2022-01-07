package Items;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ItemInfoParse {
    public static HashMap<String, String> parse(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        char[] chars = text.toCharArray();
        int count = 0;
        HashMap<String, String> map = new HashMap<>();
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
                    if (sbs.length > 1) map.put(sbs[0], sbs[1]);
                    else map.put(sbs[0], null);
                }
            }
            if (j >= chars.length) break;
        }
        return map;
    }

    public static void main(String[] args) {
        String str = "Items{ID=1331661, state=0, itemName=误杀2, engName=Fireflies in the Sun, pics=https://p0.meituan.net/movie/e93b8b54ffd529b26605c8c078b9cbca2295531.jpg, keywords=误杀2,肖央,任达华,文咏珊,戴墨,选座购票,剧情介绍, genreID=剧情／犯罪／家庭, country=0, runtime=, releaseDate=2021-12-17 18:00, rate=-1.0, boxOffice=0.0, introduction=林日朗（肖央 饰）与妻子阿玲、儿子小虫一直过着清贫但幸福的生活，直到儿子小虫突发意外急需救治，几经周折，走投无路的林日朗为了救儿子决定放手一搏。他制定了一个惊天计划....., cast=null, soundMix=, tech=, whereToWatch=null, award=}";
        HashMap<String, String> strs = parse(str);
        for (Map.Entry<String, String> entry: strs.entrySet()){
            System.out.println(entry.getKey() + "----" + entry.getValue());
        }
    }
}
