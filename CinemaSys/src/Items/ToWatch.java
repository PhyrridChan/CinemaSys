package Items;

public class ToWatch {
    private String tencent;
    private String aqiyi;
    private String youku;
    private String bilibili;
    private String theater;

    /**
     * Constructor
     */
    public ToWatch(String tencent, String aqiyi, String youku, String bilibili, String theater) {
        this.tencent = tencent;
        this.aqiyi = aqiyi;
        this.youku = youku;
        this.bilibili = bilibili;
        this.theater = theater;
    }

    /**
     * Getters and Setters
     */
    public String getTencent() {
        return tencent;
    }

    public void setTencent(String tencent) {
        this.tencent = tencent;
    }

    public String getAqiyi() {
        return aqiyi;
    }

    public void setAqiyi(String aqiyi) {
        this.aqiyi = aqiyi;
    }

    public String getYouku() {
        return youku;
    }

    public void setYouku(String youku) {
        this.youku = youku;
    }

    public String getBilibili() {
        return bilibili;
    }

    public void setBilibili(String bilibili) {
        this.bilibili = bilibili;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    @Override
    public String toString() {
        return "ToWatch{" +
                "tencent='" + tencent + '\'' +
                ", aqiyi='" + aqiyi + '\'' +
                ", youku='" + youku + '\'' +
                ", bilibili='" + bilibili + '\'' +
                ", theater='" + theater + '\'' +
                '}';
    }

    public void printInfo(){
        System.out.println(this.toString());
    }
}
