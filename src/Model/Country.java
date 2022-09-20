package Model;

/**
 * Country class object
 */

public class Country {
    private final int id;
    private final String countryName;

    /**
     *
     * @param id                Country ID
     * @param countryName       Country Name
     */
    public Country(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }
    /**
     * Gets the Country Id
     *
     * @return Country Id is returned
     */
    public int getId() {
        return id;
    }
    /**
     * Gets the Country Name
     *
     * @return Country Name is returned
     */
    public String getCountryName() {
        return countryName;
    }
    //Returning toString - might need to change later
    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
