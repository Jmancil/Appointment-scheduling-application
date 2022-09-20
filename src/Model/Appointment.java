package Model;
import java.time.LocalDateTime;

/**
appointment object class
 */
public class Appointment {
    public String loggedInUser;
    public String type;
    public String location;
    public String description;
    public String title;
    public int contactId;
    public int customerId;
    public int userId;
    public int appointmentId;
    LocalDateTime endDateTime;
    LocalDateTime startDateTime;

    /**
     Constructor for an appointment when a user is not logged in
     @param type           Appointment Type
     @param location       Appointment Location
     @param description    Appointment Description
     @param title          Appointment Title
     @param contactId      Appointment ContactId
     @param customerId     Appointment CustomerId
     @param userId         Appointment Userid
     @param appointmentId  Appointment AppointmentId
     @param endDateTime    Appointments end date and time
     @param startDateTime  Appointments start date and time
     */

    public Appointment(String type, String location, String description,
                       String title, int contactId, int customerId, int userId, int appointmentId, LocalDateTime endDateTime, LocalDateTime startDateTime) {
        this.type = type;
        this.location = location;
        this.description = description;
        this.title = title;
        this.contactId = contactId;
        this.customerId = customerId;
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.endDateTime = endDateTime;
        this.startDateTime = startDateTime;
    }

    /**
     Constructor for an appointment when a user is logged in
     @param type           Appointment Type
     @param location       Appointment Location
     @param description    Appointment Description
     @param title          Appointment Title
     @param contactId      Appointment ContactId
     @param customerId     Appointment CustomerId
     @param userId         Appointment Userid
     @param appointmentId  Appointment AppointmentId
     @param endDateTime    Appointments end date and time
     @param startDateTime  Appointments start date and time
     @param loggedInUser   The user that is logged in when appointment is created
                           the logged in user is used for updating the database when creating or modifying items
     */
    public Appointment(String type, String location, String description,
                       String title, int contactId, int customerId, int userId, int appointmentId, LocalDateTime endDateTime, LocalDateTime startDateTime, String loggedInUser) {
        this.type = type;
        this.location = location;
        this.description = description;
        this.title = title;
        this.contactId = contactId;
        this.customerId = customerId;
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.endDateTime = endDateTime;
        this.startDateTime = startDateTime;
        this.loggedInUser = loggedInUser;
    }

    public Appointment() {
    }

    /**
     * Gets the loggedInUser
     *
     * @return LoggedInUser is returned
     */
    public String getLoggedInUser() {
        return loggedInUser;
    }

//    public void setLoggedInUser(String loggedInUser) {
//        this.loggedInUser = loggedInUser;
//    }

    /**
     * Gets the appointment id
     *
     * @return Appointment id returned
     */
    public String getType() {
        return type;
    }

    /**
     * Sets appointment type
     *
     * @param type is set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment id
     *
     * @return Appointment id returned
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets appointment location
     *
     * @param location is set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the appointment id
     *
     * @return Appointment id returned
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets appointment description
     *
     * @param description is set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment id
     *
     * @return Appointment id returned
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets appointment title
     *
     * @param title is set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the contact id
     *
     * @return Contact Id is returned
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets appointment contactId
     *
     * @param contactId is set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the customer Id
     *
     * @return Customer Id is returned
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets appointment customerId
     *
     * @param customerId is set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the user Id
     *
     * @return User Id is returned
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets appointment userId
     *
     * @param userId is set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the appointment id
     *
     * @return Appointment id is returned
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointment appointmentId
     *
     * @param appointmentId is set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the EndDateTime
     *
     * @return EndDateTime is returned
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets appointment endDateTime
     *
     * @param endDateTime is set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets the StartDateTime
     *
     * @return StartDateTime is returned
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets appointment startDateTime
     *
     * @param startDateTime is set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "loggedInUser='" + loggedInUser + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", contactId=" + contactId +
                ", customerId=" + customerId +
                ", userId=" + userId +
                ", appointmentId=" + appointmentId +
                ", endDateTime=" + endDateTime +
                ", startDateTime=" + startDateTime +
                '}';
    }
}