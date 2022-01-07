package SqlUtil;

import java.util.ResourceBundle;
/**
 * Using for get the config info from the properties file
 * @author zhangfan
 */
public class Config {
    private final static ResourceBundle bundle = ResourceBundle.getBundle("SqlUtil.jdbc_mysql");
    private static String USER = bundle.getString("USER");
    private static String PWD = bundle.getString("PWD");
    private static String jdbc_driver = bundle.getString("jdbc-driver");
    private static String jbdc_url = bundle.getString("jdbc-url");
    private static String DB_name = bundle.getString("name");

    public static String getUSER() {
        return USER;
    }

    public static void setUSER(String USER) {
        Config.USER = USER;
    }

    public static String getPWD() {
        return PWD;
    }

    public static void setPWD(String PWD) {
        Config.PWD = PWD;
    }

    public static String getJdbc_driver() {
        return jdbc_driver;
    }

    public static void setJdbc_driver(String jdbc_driver) {
        Config.jdbc_driver = jdbc_driver;
    }

    public static String getJbdc_url() {
        return jbdc_url;
    }

    public static void setJbdc_url(String jbdc_url) {
        Config.jbdc_url = jbdc_url;
    }

    public static String getDB_name() {
        return DB_name;
    }

    public static void setDB_name(String DB_name) {
        Config.DB_name = DB_name;
    }
}
