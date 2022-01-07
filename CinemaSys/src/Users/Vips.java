package Users;

public class Vips {
    private int type;
    private String expire;
    private int vipLevel;

    public Vips(int type, String expire, int vipLevel) {
        this.type = type;
        this.expire = expire;
        this.vipLevel = vipLevel;
    }

    /**
     * Getters and Setters
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void getVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    /**
     *
     */
    public boolean updateExpire(String billNo) {
        boolean suc = false;
        return suc;
    }

    public boolean updateType(String billNo) {
        boolean suc = false;
        return suc;
    }

    public boolean updateVipLevel(int vipLevel) {
        boolean suc = false;
        return suc;
    }

    @Override
    public String toString() {
        return "Vips{" +
                "type=" + type +
                ", expire='" + expire + '\'' +
                ", vipLevel=" + vipLevel +
                '}';
    }
}
