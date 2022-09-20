package Model;

/**
 Contact object class
 */
public class Contact {
    private final String contactEm;
    private final String contactNa;
    private final int contactId;

    /**
     *
     * @param contactEm Contacts email address
     * @param contactNa Contacts Name
     * @param contactId Contacts contactId
     */
    public Contact(String contactEm, String contactNa, int contactId) {
        this.contactEm = contactEm;
        this.contactNa = contactNa;
        this.contactId = contactId;
    }

    /**
     * Gets the contactEmail
     *
     * @return contactEmail is returned
     */
    public String getContactEm() {
        return contactEm;
    }

    /**
     * Gets the contactName
     *
     * @return contactName is returned
     */
    public String getContactNa() {
        return contactNa;
    }

    /**
     * Gets the contactId
     *
     * @return contactId is returned
     */
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
