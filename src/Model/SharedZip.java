package Model;

public class SharedZip {
        private int count;
        private String zip;

    public SharedZip(int count, String zip) {
        this.count = count;
        this.zip = zip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
