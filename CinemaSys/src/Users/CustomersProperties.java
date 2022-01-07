package Users;


import SqlUtil.MySQLUtil;

import java.util.Arrays;

public class CustomersProperties {
    private String preferCopyright;
    private String preferCategory;
    private int activity;
    private String statement;
    private int label;
    private int level;
    private int grow;
    private int regionID;

    final int[] eventCre = {
            2, 3, 4, 5, 6, -3
    };

    final int[] increLevel = {
            0, 10, 50, 120, 250, 500, 1000, 2000
    };

    public CustomersProperties(String preferCopyright, String preferCategory, int activity, String statement, int label, int level, int grow) {
        this.preferCopyright = preferCopyright;
        this.preferCategory = preferCategory;
        this.activity = activity;
        this.statement = statement;
        this.label = label;
        this.level = level;
        this.grow = grow;
    }

    public void updateGrow(Customers customers, int eventTag, String event) {
        try {
            String UID = customers.getUID();
            Activities.activities(UID, "", eventTag, event);
            this.grow += eventCre[eventTag - 10];
            MySQLUtil.update("tb_CustomersProperties", "grow", grow + "", "UID", UID);
            updateLevel(UID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLevel(String UID) {
        try {
            while (level < 8 && grow > increLevel[level]) level++;
            MySQLUtil.update("tb_CustomersProperties", "level", level + "", "UID", UID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CustomersProperties{" +
                "preferCopyright='" + preferCopyright + '\'' +
                ", preferCategory='" + preferCategory + '\'' +
                ", activity=" + activity +
                ", statement='" + statement + '\'' +
                ", label=" + label +
                ", level=" + level +
                ", grow=" + grow +
                ", regionID=" + regionID +
//                ", eventCre=" + Arrays.toString(eventCre) +
//                ", increLevel=" + Arrays.toString(increLevel) +
                '}';
    }
}
