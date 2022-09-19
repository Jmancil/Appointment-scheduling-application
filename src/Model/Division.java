package Model;

public class Division {
    private final int divisionId;
    private final String divisionName;
    private final int countryId;

    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

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
