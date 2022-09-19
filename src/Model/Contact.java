package Model;

public class Contact {
    private final String contactEm;
    private final String contactNa;
    private final int contactId;

    public Contact(String contactEm, String contactNa, int contactId) {
        this.contactEm = contactEm;
        this.contactNa = contactNa;
        this.contactId = contactId;
    }

    public String getContactEm() {
        return contactEm;
    }

    public String getContactNa() {
        return contactNa;
    }

    public int getContactId() {
        return contactId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactEm='" + contactEm + '\'' +
                ", contactNa='" + contactNa + '\'' +
                ", contactId=" + contactId +
                '}';
    }
}
