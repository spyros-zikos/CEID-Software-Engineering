package my.casheri;

public class Filters {

    public Filters(String startDateTime, String endDateTime, String minDuration, String maxDuration, int minPassengers, int maxPassengers, float minProfit, float maxProfit) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.minPassengers = minPassengers;
        this.maxPassengers = maxPassengers;
        this.minProfit = minProfit;
        this.maxProfit = maxProfit;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }

    public String getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(String maxDuration) {
        this.maxDuration = maxDuration;
    }

    public int getMinPassengers() {
        return minPassengers;
    }

    public void setMinPassengers(int minPassengers) {
        this.minPassengers = minPassengers;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public float getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(float minProfit) {
        this.minProfit = minProfit;
    }

    public float getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(float maxProfit) {
        this.maxProfit = maxProfit;
    }
    
    private String startDateTime;
    private String endDateTime;
    private String minDuration;
    private String maxDuration;
    private int minPassengers;
    private int maxPassengers;
    private float minProfit;
    private float maxProfit;
}
