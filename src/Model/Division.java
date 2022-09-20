package Model;

/**
 * Division class object
 */
public class Division {
    private final int divisionId;
    private final String divisionName;
    private final int countryId;

    /**
     * Constructor for division
     * @param divisionId        Division ID
     * @param divisionName      Division Name
     * @param countryId         Division Country ID
     */
    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * Gets the division Id
     *
     * @return division Id is returned
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets the Division Name
     *
     * @return Division Name is returned
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Gets the Country Id
     *
     * @return Country Id is returned
     */
    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return "Division{" +
                "divisionId=" + divisionId +
                ", divisionName='" + divisionName + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}
