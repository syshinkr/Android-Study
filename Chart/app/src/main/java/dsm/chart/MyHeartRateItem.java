package dsm.chart;

/**
 * Created by admin on 2017-10-08.
 */

public class MyHeartRateItem {
    private int rate;
    private String date;

    public MyHeartRateItem(int rate, String date) {
        this.rate = rate;
        this.date = date;
    }

    public int getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }
}
