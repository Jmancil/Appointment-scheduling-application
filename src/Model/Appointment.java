package Model;
import java.time.LocalDateTime;

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

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

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