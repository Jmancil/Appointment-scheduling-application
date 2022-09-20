package Model;

/**
 * Customer class object
 */
public class Customer {
    private int division_id;
    private String phone;
    private String postal_code;
    private String address;
    private String name;
    private int id;
    private String loggedUser;

    /**
     * Customer constructor without loggedInUser
     * @param id                Customer Id
     * @param name              Customer Name
     * @param address           Customer Address
     * @param postal_code       Customer Zip
     * @param phone             Customer Phone
     * @param division_id       Customer division ID
     */
    public Customer(int id, String name, String address, String postal_code, String phone, int division_id){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division_id = division_id;
    }

    /**
     * Constructor for customer with loggedInUser
     * @param id            Customer ID
     * @param name          Customer Name
     * @param address       Customer Address
     * @param postal_code   Customer Zip Code
     * @param phone         Customer Phone
     * @param division_id   Customer Division ID
     * @param loggedUser    Current logged in user
     */
    public Customer(int id, String name, String address, String postal_code, String phone, int division_id, String loggedUser){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division_id = division_id;
        this.loggedUser = loggedUser;

    }

    /**
     * Gets the loggedInUser
     *
     * @return LoggedInUser is returned
     */
    public String getLoggedUser() {
        return loggedUser;
    }

    /**
     * Sets loggedUser
     *
     * @param loggedUser is set
     */
    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Gets the Division_Id
     *
     * @return Division_Id is returned
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * Sets division_id
     *
     * @param division_id is set
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    /**
     * Gets the customer Phone
     *
     * @return Phone is returned
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone
     *
     * @param phone is set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the Postal Code
     *
     * @return Postal Code is returned
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Sets postal_code
     *
     * @param postal_code is set
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Gets the Address
     *
     * @return Address is returned
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address
     *
     * @param address is set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the Name
     *
     * @return Name is returned
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name is set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Id
     *
     * @return Id is returned
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id
     *
     * @param id is set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "division_id=" + division_id +
                ", phone='" + phone + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
