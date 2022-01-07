package Users;

public class Users {
    private String UID;
    private int state;
    private String userName;
    private String avator;
    private char sex;
    private int age;
    private String phone;
    private int regionCountry;
    private int regionProvince;
    private int regionCity;
    private String joinTime;

    public Users(String UID, int state, String userName, String avator, char sex, int age, String phone, int regionCountry, int regionProvince, int regionCity, String joinTime) {
        this.UID = UID;
        this.state = state;
        this.userName = userName;
        this.avator = avator;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.regionCountry = regionCountry;
        this.regionProvince = regionProvince;
        this.regionCity = regionCity;
        this.joinTime = joinTime;
    }

    public Users(String userName, String avator, char sex, int age, String phone, int regionCountry, int regionProvince, int regionCity) {
        this.userName = userName;
        this.avator = avator;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.regionCountry = regionCountry;
        this.regionProvince = regionProvince;
        this.regionCity = regionCity;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRegionCountry() {
        return regionCountry;
    }

    public void setRegionCountry(int regionCountry) {
        this.regionCountry = regionCountry;
    }

    public int getRegionProvince() {
        return regionProvince;
    }

    public void setRegionProvince(int regionProvince) {
        this.regionProvince = regionProvince;
    }

    public int getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(int regionCity) {
        this.regionCity = regionCity;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public void printInfo() {
        System.out.print(
                "UID='" + UID + '\'' +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", avator='" + avator + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", regionCountry=" + regionCountry +
                ", regionProvince=" + regionProvince +
                ", regionCity=" + regionCity +
                ", joinTime='" + joinTime + '\''
                );
    }

    @Override
    public String toString() {
        return "Users{" +
                "UID=" + UID +
                ", state=" + state +
                ", userName=" + userName +
                ", avator=" + avator +
                ", sex=" + sex +
                ", age=" + age +
                ", phone=" + phone +
                ", regionCountry=" + regionCountry +
                ", regionProvince=" + regionProvince +
                ", regionCity=" + regionCity +
                ", joinTime=" + joinTime +
                '}';
    }
}
