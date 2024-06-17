package usecases;

import domain.Appointment;
import exception.InfoRequireException;
import exception.UnFindException;
import repositories.AppointmentRepository;

import java.io.IOException;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void scheduleAppointment(String id, String patientId, String dateTime, String reason) throws IOException, ClassNotFoundException {
        if ( patientId != null && id != null && dateTime != null && reason != null) {
            Appointment appointment = new Appointment(id, patientId, dateTime, reason);
            appointmentRepository.addAppointment(appointment);
        }else {
            throw new InfoRequireException("Need more Info");
        }
    }

    public void rescheduleAppointment(String id, String newDateTime) throws IOException, ClassNotFoundException {
        Appointment appointment = appointmentRepository.getAppointmentById(id);
        if (appointment != null) {
            appointment.reschedule(newDateTime);
            appointmentRepository.updateAppointment(appointment);
        }else{
            throw new UnFindException();
        }
    }

    public Appointment getAppointmentById(String id) throws IOException, ClassNotFoundException {
        return appointmentRepository.getAppointmentById(id);
    }

    public void deleteAppointmentById(String id) throws IOException, ClassNotFoundException {
        appointmentRepository.deleteAppointmentById(id);
    }

}
