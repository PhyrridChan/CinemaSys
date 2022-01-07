package Items;

import JSON.JSONhandle;


import java.io.IOException;
import java.util.Arrays;

import static JSON.JSONhandle.searchJsonArray;
import static JSON.SpecificUtil.searchJsonArraySite;

public class Theaters {
    private String ID;
    private int state;
    private String theaterName;

    //ref
    private int regionCity;
    private int regionDistrict;

    private String site;

    private Arrange[] onShowMovie = new Arrange[30];

    private String[] showrooms = new String[20];

    private String pic;

    private String phone;
    private String introduction;

    /**
     * Constructor
     */
    public Theaters(String ID, int state, String theaterName, String site, Arrange[] onShowMovie, String[] showrooms, String pic, String phone, String introduction) {
        this.ID = ID;
        this.state = state;
        this.theaterName = theaterName;
        this.site = site;
        this.onShowMovie = onShowMovie;
        this.showrooms = showrooms;
        this.pic = pic;
        this.phone = phone;
        this.introduction = introduction;
    }

    public static String parseSite(String prov, String city, String site) throws IOException {
        JSONhandle jsoNhandle = new JSONhandle();
        String province = "", regionCity = "", regionSite = "";
        if (prov != null && city != null && site != null) {
            province = searchJsonArray("provList", prov);
            regionCity = searchJsonArray("cityList", prov, city);
            regionSite = searchJsonArraySite("siteList", prov, city, site);
        }
        return province + regionCity + regionSite;
    }

    /**
     * Getters and Setters
     */
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public int getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(int regionCity) {
        this.regionCity = regionCity;
    }

    public int getRegionDistrict() {
        return regionDistrict;
    }

    public void setRegionDistrict(int regionDistrict) {
        this.regionDistrict = regionDistrict;
    }

    public Arrange[] getOnShowMovie() {
        return onShowMovie;
    }

    public void setOnShowMovie(Arrange[] onShowMovie) {
        this.onShowMovie = onShowMovie;
    }

    public String[] getShowrooms() {
        return showrooms;
    }

    public void setShowrooms(String[] showrooms) {
        this.showrooms = showrooms;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Theaters{" +
                "ID=" + ID +
                ", state=" + state +
                ", itemName=" + theaterName +
                ", site=" + site +
                ", onShowMovie=" + Arrays.toString(onShowMovie) +
                ", showrooms=" + Arrays.toString(showrooms) +
                ", pics=" + pic +
                ", phone=" + phone +
                ", introduction=" + introduction +
                '}';
    }
}
