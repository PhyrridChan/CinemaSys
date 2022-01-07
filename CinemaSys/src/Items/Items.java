package Items;

public class Items {
    private String ID;
    //ref
    private int state;
    private String itemName;
    private String engName;
    private String pics;
    private String keywords;
    //ref
    private String genreID;
    //ref
    private int country;
    private String runtime;
    private String releaseDate;
    private double rate;
    private double boxOffice;
    private String introduction;
    private Casts cast;
    //ref
    private String soundMix;
    //ref
    private String tech;
    private ToWatch whereToWatch;
    //ref :using class
    //award:"{awards},{awarded},{nominated}"
    private String award;
    private Awards awards_;

    public Items(int state, String itemName, String engName, String pics, String keywords, String genreID, int country, String runtime, String releaseDate, double rate, double boxOffice, String introduction, Casts cast, String soundMix, String tech, ToWatch whereToWatch, String award) {
        String ID = "";
        this.ID = ID;
        this.state = state;
        this.itemName = itemName;
        this.engName = engName;
        this.pics = pics;
        this.keywords = keywords;
        this.genreID = genreID;
        this.country = country;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.boxOffice = boxOffice;
        this.introduction = introduction;
        this.cast = cast;
        this.soundMix = soundMix;
        this.tech = tech;
        this.whereToWatch = whereToWatch;
        this.award = award;
    }

    public Items(String ID, int state, String itemName, String engName, String pics, String keywords, String genreID, int country, String runtime, String releaseDate, double rate, double boxOffice, String introduction, Casts cast, String soundMix, String tech, ToWatch whereToWatch, String award) {
        this.ID = ID;
        this.state = state;
        this.itemName = itemName;
        this.engName = engName;
        this.pics = pics;
        this.keywords = keywords;
        this.genreID = genreID;
        this.country = country;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.boxOffice = boxOffice;
        this.introduction = introduction;
        this.cast = cast;
        this.soundMix = soundMix;
        this.tech = tech;
        this.whereToWatch = whereToWatch;
        this.award = award;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGenreID() {
        return genreID;
    }

    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Casts getCast() {
        return cast;
    }

    public void setCast(Casts cast) {
        this.cast = cast;
    }

    public String getSoundMix() {
        return soundMix;
    }

    public void setSoundMix(String soundMix) {
        this.soundMix = soundMix;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public ToWatch getWhereToWatch() {
        return whereToWatch;
    }

    public void setWhereToWatch(ToWatch whereToWatch) {
        this.whereToWatch = whereToWatch;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }
    /**
     * More methods
     *
     */
    @Override
    public String toString() {
        return "Items{" +
                "ID=" + ID +
                ", state=" + state +
                ", itemName=" + itemName +
                ", engName=" + engName +
                ", pics=" + pics +
                ", keywords=" + keywords +
                ", genreID=" + genreID +
                ", country=" + country +
                ", runtime=" + runtime +
                ", releaseDate=" + releaseDate +
                ", rate=" + rate +
                ", boxOffice=" + boxOffice +
                ", introduction=" + introduction +
                ", cast=" + cast +
                ", soundMix=" + soundMix +
                ", tech=" + tech +
                ", whereToWatch=" + whereToWatch +
                ", award=" + award +
                '}';
    }

    public void printInfo(){
        System.out.print("{ ");
        System.out.println(this.toString() + "'" + this.cast.toString() + "'" + "'" + this.whereToWatch.toString() + "' }");
    }
}
