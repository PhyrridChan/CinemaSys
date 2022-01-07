package pageConstructor;

import SqlUtil.MySQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTable {
    private static final String[] key = {
            "电影ID", "状态", "电影名（英文名）", "关键词", "图片", "类别", "国家", "放映时长", "上映时间", "评分", "票房", "介绍", "声音技术", "画面技术", "观看渠道", "获奖"
            , "影城ID", "状态", "影城名", "联系方式", "介绍", "地址", "放映室"
            , "排名", "电影ID", "电影名（英文名）"
            , "用户ID", "状态", "用户名", "头像", "性别", "年龄", "手机", "地区", "注册时间", "property", "credit", "value", "vip"
            , "员工ID", "状态", "姓名", "头像", "性别", "年龄", "手机", "地区", "入职时间", "职位", "工作组", "工作地", "对接客户", "管理员权限", "methods"
            , "movieTable", "cinemaTable", "NewsTable", "rankTable", "cusTable", "staffTable"
    };
    private static final String[] value = {
            "ID", "state", "itemName&engName", "keywords", "pics", "genre", "country", "runtime", "releaseDate", "rate", "boxOffice", "introduction", "soundMix", "tech", "!++whereToWatch++!", "awards"
            , "ID", "state", "theaterName", "phone", "introduction", "site", "showrooms"
            , "ranking", "ID", "itemName&engName"
            , "UID", "state", "userName", "avator", "sex", "age", "phone", "regionCountry&regionProvince&regionCity", "joinTime", "property", "credit", "value", "vip"
            , "UID", "state", "userName", "avator", "sex", "age", "phone", "regionCountry&regionProvince&regionCity", "joinTime", "`rank`", "workGroup", "workPlace", "workOn", "root", "methods"
            , "Items", "Theaters", "!++news++!", "ranking", "tb_Users", "tb_Staffs"
    };

    public static void a() {
        for (int i = 0; i < key.length; i++) {
            System.out.println(key[i] + ":" + value[i]);
        }
    }

    public static HashMap<String, String> getDic() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < key.length; i++) {
            map.put(key[i], value[i]);
        }
        return map;
    }

    public static void updateTable(HashMap<String, String> map) {
        //tableName id
        String tbName = getDic().get(map.get("tableName"));
        String idName = "ID";
        if (tbName.contains("tb_")) idName = "UID";
        String sql = "";
        StringBuilder colBlocks = new StringBuilder();
        if (map.get("id").contains("new")) {
            if (tbName.equals("ranking")) {
                sql = "INSERT INTO " + tbName + "(movieID) VALUES (" + map.get("id") + ");";
            } else {
                StringBuilder valBlocks = new StringBuilder();
                colBlocks.append(idName).append(",");
                valBlocks.append("'").append(defaultId(tbName, idName, new Date(System.currentTimeMillis()))).append("', '");
                for (String key : map.keySet()) {
                    if (key.contains("!++")) continue;
                    if (!key.equals("id") && !key.equals("tableName")) {
                        if (key.contains("&")) {
                            String[] splitKey = key.split("&");
                            String[] splitValue = map.get(key).split(",");
                            int len = splitKey.length == splitValue.length ? splitKey.length : 0;
                            for (int i = 0; i < len; i++) {
                                colBlocks.append(splitKey[i]).append(",");
                                valBlocks.append(splitValue[i]).append("', '");
                            }
                        } else {
                            if (key.equals("releaseDate") || key.equals("joinTime")) {
                                if (!isRqSjFormat(map.get(key))) continue;
                            }
                            colBlocks.append(key).append(",");
                            valBlocks.append(map.get(key)).append("', '");
                        }
                    }
                }
                colBlocks.deleteCharAt(colBlocks.length() - 1);
                valBlocks.delete(valBlocks.length() - 3, valBlocks.length());
                sql = "INSERT INTO " + tbName + "(" + colBlocks.toString() + ") VALUES (" + valBlocks.toString() + ")";
            }
        } else if (map.get("type") == null || !map.get("type").matches(".*Ope")) {
            if (tbName.equals("ranking")) {
                sql = "UPDATE " + tbName + " SET movieID = " + map.get("ID") + " WHERE movieID = " + map.get("id");
            } else {
                for (String key : map.keySet()) {
                    if (!key.equals("id") && !key.equals("tableName")) {
                        if (key.contains("&")) {
                            String[] splitKey = key.split("&");
                            String[] splitValue = map.get(key).split(",");
                            int len = splitKey.length == splitValue.length ? splitKey.length : 0;
                            for (int i = 0; i < len; i++)
                                colBlocks.append(splitKey[i]).append(" = '").append(splitValue[i]).append("',");
                        } else
                            colBlocks.append(key).append(" = '").append(map.get(key)).append("',");
                    }
                }
                if (colBlocks.length() > 0) colBlocks.deleteCharAt(colBlocks.length() - 1);
                sql = "UPDATE " + tbName + " SET " + colBlocks.toString() + " WHERE " + idName + "=" + map.get("id");
            }
        } else {
            String sql1 = "", sql2 = "", sql3 = "";
            switch (map.get("type")) {
                case "delOpe":
                    sql = "DELETE FROM " + tbName + " WHERE " + idName + " = " + map.get("id");
                    break;
                case "higherOpe":
                    sql1 = "UPDATE ranking SET `rank` = -1 WHERE `rank` in (" +
                            " SELECT `rank` - 1 FROM (" +
                            " SELECT `rank` FROM ranking" +
                            " where movieID = " + map.get("id") + ") t1 );";
                    sql2 = "UPDATE ranking SET `rank` = `rank` - 1" +
                            " WHERE `rank` in (" +
                            " SELECT `rank` FROM (" +
                            " SELECT `rank` FROM ranking" +
                            " where movieID = " + map.get("id") + ") t1 );";
                    sql3 = "UPDATE ranking AS rankingA, ranking AS rankingB" +
                            " SET rankingA.`rank` = rankingB.`rank` + 1" +
                            " WHERE rankingA.`rank` = -1" +
                            " AND rankingB.`rank` in (" +
                            " SELECT `rank` FROM " +
                            "( SELECT `rank` FROM ranking where movieID = " + map.get("id") + ") t1)";
                    break;
                case "lowerOpe":
                    sql1 = "UPDATE ranking SET `rank` = -1 WHERE `rank` in (" +
                            " SELECT `rank` + 1 FROM (" +
                            " SELECT `rank` FROM ranking" +
                            " where movieID = " + map.get("id") + ") t1 );";
                    sql2 = "UPDATE ranking SET `rank` = `rank` + 1" +
                            " WHERE `rank` in (" +
                            " SELECT `rank` FROM (" +
                            " SELECT `rank` FROM ranking" +
                            " where movieID = " + map.get("id") + ") t1 );";
                    sql3 = "UPDATE ranking AS rankingA, ranking AS rankingB" +
                            " SET rankingA.`rank` = rankingB.`rank` - 1" +
                            " WHERE rankingA.`rank` = -1" +
                            " AND rankingB.`rank` in (" +
                            " SELECT `rank` FROM " +
                            "( SELECT `rank` FROM ranking where movieID = " + map.get("id") + ") t1)";
                    break;
            }
            if (map.get("type").matches(".*erOpe")) {
                PreparedStatement ps = MySQLUtil.setPreStmt(sql1);
                MySQLUtil.update(ps);
                ps = MySQLUtil.setPreStmt(sql2);
                MySQLUtil.update(ps);
                ps = MySQLUtil.setPreStmt(sql3);
                MySQLUtil.update(ps);
                return;
            }
        }
        PreparedStatement ps = MySQLUtil.setPreStmt(sql);
        MySQLUtil.update(ps);
    }

    public static String defaultId(String tbName, String idName, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String head = formatter.format(date); //12
        String sql = "SELECT " + idName + " FROM " + tbName + " WHERE " + idName + " LIKE '" + head + "%' ORDER BY " + idName + " DESC LIMIT 1";
        ResultSet rs = MySQLUtil.query(sql);
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
        return UID;
    }

    /***
     * 判断字符串是否是yyyyMMddHHmmss格式
     * @param mes 字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isRqSjFormat(String mes) {
        String format = "([0-9]{4})(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])"
                + "([01][0-9]|2[0-3])[0-5][0-9]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(mes);
        if (matcher.matches()) {
            pattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{2}).*");
            matcher = pattern.matcher(mes);
            if (matcher.matches()) {
                int y = Integer.valueOf(matcher.group(1));
                int m = Integer.valueOf(matcher.group(2));
                int d = Integer.valueOf(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    c.set(y, m - 1, 1);//每个月的最大天数
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
            }
            return true;
        }
        return false;
    }
}
