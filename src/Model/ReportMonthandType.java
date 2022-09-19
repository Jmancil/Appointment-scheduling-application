package Model;

public class ReportMonthandType {
    private int count;
    private String month;
    private String type;

    public ReportMonthandType(int count, String month, String type) {
        this.count = count;
        this.month = month;
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
