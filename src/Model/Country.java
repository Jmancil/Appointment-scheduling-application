package Model;

//Country class constructor object creation
//Used for popualting combo boxes of countrys

public class Country {
    private final int id;
    private final String countryName;
    //Constructor
    public Country(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }
    //getter for id
    public int getId() {
        return id;
    }
    //getter for countryName
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
