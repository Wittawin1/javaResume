package repositories;

import database.DbConnection;
import domain.Appointment;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FileAppointmentRepository implements AppointmentRepository {
    private final String PATH = "AppointmentFile.txt";
    private static int nextId = 0;
    private Map<String, Appointment> appointments;

    @Override
    public void addAppointment(Appointment appointment) throws IOException, ClassNotFoundException {
            readAppointmentsFromFile();
            appointments.put(appointment.getId(),appointment);
            writeAppointmentsToFile();
    }

    @Override
    public Appointment getAppointmentById(String id) throws IOException, ClassNotFoundException {
        readAppointmentsFromFile();
        if (appointments.containsKey(id)){
            return appointments.get(id);
        }
        return null;
    }

    @Override
    public void updateAppointment(Appointment appointment) throws IOException, ClassNotFoundException {
        readAppointmentsFromFile();
        appointments.replace(appointment.getId(),appointment);
        writeAppointmentsToFile();
    }
    @Override
    public Map<String, Appointment> getAllAppointments() throws ClassNotFoundException {
         readAppointmentsFromFile();
         return appointments;
    }
    @Override
    public void deleteAppointmentById(String id) throws ClassNotFoundException, IOException {
        readAppointmentsFromFile();
        appointments.remove(id);
        writeAppointmentsToFile();
    }

    private void readAppointmentsFromFile() throws ClassNotFoundException {
        appointments = new HashMap<>();
        File f = new File(PATH);
        if (f.exists()) {
            try (FileInputStream fi = new FileInputStream(PATH);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {
                nextId = oi.available();
                appointments = (Map<String, Appointment>) oi.readObject();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void writeAppointmentsToFile() throws IOException {
        try (FileOutputStream fo = new FileOutputStream(PATH);
             BufferedOutputStream bo = new BufferedOutputStream(fo);
             ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(appointments);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}


