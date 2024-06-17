package domain;

import java.io.Serializable;

public class Invoice implements Serializable {
    private String id;
    private String patientId;
    private String date;
    private double amount;


    public Invoice(String id, String patientId, double amount, String date) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Invoice : " + "id : " + id + " , " + "patientId : "
                + patientId + " , " + "dateTime : " + date + " , " + "Amount : " + amount;
    }
}
