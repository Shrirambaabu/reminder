package in.myreminder.srb.model;

public class MyAlert {

    private int alertId;
    private String alertTime;
    private String alertRead;

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getAlertRead() {
        return alertRead;
    }

    public void setAlertRead(String alertRead) {
        this.alertRead = alertRead;
    }

    public MyAlert() {
    }
}
