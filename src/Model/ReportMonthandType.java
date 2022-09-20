package Model;

/**
 *  Report by month and type class object
 */
public class ReportMonthandType {
    private int count;
    private String month;
    private String type;

    /**
     * Constructor for reportMonthAndType
     * @param count     reportMonthAndType count
     * @param month     reportMonthAndType month
     * @param type      reportMonthAndType type
     */
    public ReportMonthandType(int count, String month, String type) {
        this.count = count;
        this.month = month;
        this.type = type;
    }

    /**
     * Gets the reportMonthAndType Type
     *
     * @return reportMonthAndType Type is returned
     */
    public String getType() {
        return type;
    }

    /**
     * Sets reportMonthAndType Type
     *
     * @param type is set
     */
    public void setType(String type) {
        this.type = type;
    }
}
