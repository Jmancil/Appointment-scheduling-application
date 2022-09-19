package Model;

public class Customer {
    private int division_id;
    private String phone;
    private String postal_code;
    private String address;
    private String name;
    private int id;
    private String country_name;
    private String loggedUser;

    public Customer(int id, String name, String address, String postal_code, String phone, int division_id, String loggedUser){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division_id = division_id;
        this.loggedUser = loggedUser;

    }

    public Customer(int id, String name, String address, String postal_code, String phone, int division_id){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division_id = division_id;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public int getDivision_id() {
        return division_id;
    }

    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

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
