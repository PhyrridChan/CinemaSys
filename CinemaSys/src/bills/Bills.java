package bills;

import Items.Arrange;
import Items.Items;

public class Bills {
    Items movie;
    String bid;
    String[] tickets;
    Arrange arrange;
    double total;
    int counter;
    int state;

    public Bills(String bid, String[] tickets, Arrange arrange, Items movie, double total, int counter, int state) {
        this.bid = bid;
        this.tickets = tickets;
        this.arrange = arrange;
        this.movie = movie;
        this.total = total;
        this.counter = counter;
        this.state = state;
    }

    public Bills(String bid, Arrange arrange) {
        this.bid = bid;
        this.arrange = arrange;
    }

    public Bills(Arrange arrange) {
        this.arrange = arrange;
    }

    public Bills(Arrange arrange, int state) {
        this.arrange = arrange;
        this.state = state;
    }

    public String getBid() {
        return bid;
    }

    public String[] getTickets() {
        return tickets;
    }

    public String getTicketStr() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (tickets != null)
            while (i < tickets.length) sb.append(tickets[i++]).append(",");
        if (i > 0) sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public String getTicketStr(String bill) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (tickets != null)
            while (i < tickets.length) sb.append(tickets[i++]).append("座,");
        if (i > 0) sb.delete(sb.length() - 1, sb.length());
        sb = replaceAll(sb, "_", "排");
        return sb.toString();
    }

    public void setTickets(String[] tickets) {
        this.tickets = tickets;
        counter = tickets.length;
    }

    public void setTickets(String tickets) {
        setTickets(tickets.split(","));
    }

    public void setTickets(String[] tickets, double total) {
        setTickets(tickets);
        this.total = total;
    }

    public Arrange getArrange() {
        return arrange;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void setArrange(Arrange arrange) {
        this.arrange = arrange;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Items getMovie() {
        return movie;
    }

    public void setMovie(Items movie) {
        this.movie = movie;
    }

    /**
     * 实现StringBuilder的replaceAll
     *
     * @param stb
     * @param oldStr 被替换的字符串
     * @param newStr 替换oldStr
     * @return
     */
    public static StringBuilder replaceAll(StringBuilder stb, String oldStr, String newStr) {
        if (stb == null || oldStr == null || newStr == null || stb.length() == 0 || oldStr.length() == 0)
            return stb;
        int index = stb.indexOf(oldStr);
        if (index > -1 && !oldStr.equals(newStr)) {
            int lastIndex = 0;
            while (index > -1) {
                stb.replace(index, index + oldStr.length(), newStr);
                lastIndex = index + newStr.length();
                index = stb.indexOf(oldStr, lastIndex);
            }
        }
        return stb;
    }
}
