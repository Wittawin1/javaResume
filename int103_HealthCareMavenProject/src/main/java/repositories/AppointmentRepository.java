package repositories;

import domain.Appointment;

import java.io.IOException;
import java.util.Map;

public interface AppointmentRepository {
    void addAppointment(Appointment appointment) throws IOException, ClassNotFoundException;
    Appointment getAppointmentById(String id) throws IOException, ClassNotFoundException;
    void updateAppointment(Appointment appointment) throws IOException, ClassNotFoundException;
    Map<String, Appointment> getAllAppointments() throws IOException, ClassNotFoundException;
    public void deleteAppointmentById(String id) throws ClassNotFoundException, IOException;
}
