package Items;

import java.util.Map;

public class Arrange {
    private String ID;
    private String movieID;
    private String theaterID;
    private String startTime;
    private int room;
    private double price;
    private int state;

    public Arrange(String movieID, String theaterID, String startTime, int room, double price, int state) {
        this.movieID = movieID;
        this.theaterID = theaterID;
        this.startTime = startTime;
        this.room = room;
        this.price = price;
        this.state = state;
    }

    public Arrange(String ID, String movieID, String theaterID, String startTime, int room, double price, int state) {
        this.ID = ID;
        this.movieID = movieID;
        this.theaterID = theaterID;
        this.startTime = startTime;
        this.room = room;
        this.price = price;
        this.state = state;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getTheaterID() {
        return theaterID;
    }

    public void setTheaterID(String theaterID) {
        this.theaterID = theaterID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSetMap(int room) {
        // a == available
        // _ == noSet
        return "[" +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aaaaaaaaaa'," +
                "'aa__aa__aa'" + "]";
    }

    public String getSold(int room) {
        return "['2_3', '2_4', '4_5', '7_1', '7_2', '9_4', '9_5', '9_6', '9_7', '10_5', '10_6']";
    }
}
