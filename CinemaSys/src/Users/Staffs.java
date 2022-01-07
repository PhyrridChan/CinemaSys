package Users;

import SqlUtil.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pageConstructor.UpdateTable.defaultId;

public class Staffs extends Users {
    private int rank;
    private int root;
    private int workGroup;
    private int workPlace;
    private String workOn;

    public Staffs(String UID, int status, String userName, String avator, char sex, int age, String phone, int regionCountry, int regionProvince, int regionCity, String joinTime, int rank, int root, int workGroup, int workPlace, String workOn) {
        super(UID, status, userName, avator, sex, age, phone, regionCountry, regionProvince, regionCity, joinTime);
        this.rank = rank;
        this.root = root;
        this.workGroup = workGroup;
        this.workPlace = workPlace;
        this.workOn = workOn;
    }

    public Staffs(String userName, String avator, char sex, int age, String phone, int regionCountry, int regionProvince, int regionCity) {
        super(userName, avator, sex, age, phone, regionCountry, regionProvince, regionCity);
    }

    /**
     *
     */
    public String getReducedInfo() {
        return "";
    }

    @Override
    public String toString() {
        return "Staffs{" +
                super.toString() +
                ", rank=" + rank +
                ", root=" + root +
                ", workGroup=" + workGroup +
                ", workPlace=" + workPlace +
                ", workOn=" + workOn +
                '}';
    }

    public void printInfo() {
        System.out.print("Staffs{");
        super.printInfo();
        System.out.println(", rank=" + rank +
                ", root=" + root +
                ", workGroup=" + workGroup +
                ", workPlace=" + workPlace +
                ", workOn=" + workOn +
                "}"
        );
    }

    public boolean addStaff(String PWD) {
        boolean suc = false;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String UID = defaultId("tb_Staffs", "UID",date);
        if (MySQLUtil.add("tb_Staffs", UID, "1",
                this.getUserName(),
                this.getAvator(),
                this.getSex() + "",
                this.getAge() + "",
                this.getPhone(),
                this.getRegionCountry() + "",
                this.getRegionProvince() + "",
                this.getRegionCity() + "",
                formatter2.format(date),
                "1", "1", "1", "1", "", PWD
        ))
            System.out.println("\033[92;1msuccessed to insert!\033[0m");
        return suc;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public int getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(int workGroup) {
        this.workGroup = workGroup;
    }

    public int getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(int workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkOn() {
        return workOn;
    }

    public void setWorkOn(String workOn) {
        this.workOn = workOn;
    }
}
