package Users;

public class CreditSys {
    private int creditLevel;

    public int getCreditLevel(int credit) {
        do {
            if (credit < 0) return -1;
            if (credit < 10) return 1;
            if (credit < 50) return 2;
            if (credit < 120) return 3;
            if (credit < 250) return 4;
            if (credit < 500) return 5;
            if (credit < 1000) return 6;
            if (credit < 2000) return 7;
            return 8;
        } while (false);
    }
}
