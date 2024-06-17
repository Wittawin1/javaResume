package repositories;

import domain.Appointment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAppointmentRepository implements AppointmentRepository {
    private Map<String, Appointment> appointments = new HashMap<>();

    @Override
    public void addAppointment(Appointment appointment) {
        appointments.put(appointment.getId(), appointment);
    }

    @Override
    public Appointment getAppointmentById(String id) {
        return appointments.get(id);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        appointments.put(appointment.getId(), appointment);
    }

    @Override
    public Map<String, Appointment> getAllAppointments() {
        return appointments;
    }

    @Override
    public void deleteAppointmentById(String id) {
        appointments.remove(id);
    }
}
