package domain;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String id;
    private String patientId;
    private String dateTime;
    private String reason;

    public Appointment(String id, String patientId, String dateTime, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getReason() {
        return reason;
    }

    public void reschedule(String newDateTime) {
        this.dateTime = newDateTime;
    }

    @Override
    public String toString() {
        return "Appointment : " + "id : " + id + " , " + "patientId : "
                + patientId + " , " + "dateTime : " + dateTime + " , " + "reason : " + reason;
    }
}
