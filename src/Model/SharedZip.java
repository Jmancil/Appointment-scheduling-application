package Model;
/**
 * SharedZip Class object
 */
public class SharedZip {
        private int count;
        private String zip;

    /**
     * Constructor for SharedZip
      * @param count    SharedZip Count - integer for number of customers with the same zip
     * @param zip       SharedZip Zip - string for shared zip codes
     */
    public SharedZip(int count, String zip) {
        this.count = count;
        this.zip = zip;
    }

    /**
     * Gets the SharedZip count
     *
     * @return SharedZip count is returned
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets SharedZip count
     *
     * @param count is set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the SharedZip zip
     *
     * @return SharedZip zip is returned
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets SharedZip zip
     *
     * @param zip is set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
