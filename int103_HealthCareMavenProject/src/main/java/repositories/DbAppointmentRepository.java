package repositories;

import database.DbConnection;
import domain.Appointment;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbAppointmentRepository implements AppointmentRepository{
    private Map<String, Appointment> appointments;

    @Override
    public void addAppointment(Appointment appointment) throws IOException, ClassNotFoundException {
        appointments = new HashMap<>();
        appointments.put(appointment.getId(),appointment);
        writeAppointmentsToDb();
    }

    @Override
    public Appointment getAppointmentById(String id) throws IOException, ClassNotFoundException {
        readAppointmentsFromDb();
        if (appointments.get(id) != null) return appointments.get(id);
        return null;
    }

    @Override
    public void updateAppointment(Appointment appointment) throws IOException, ClassNotFoundException {
        appointments = new HashMap<>();
        deleteAppointmentById(appointment.getId());
        appointments.put(appointment.getId(),appointment);
        writeAppointmentsToDb();
    }

    @Override
    public Map<String, Appointment> getAllAppointments() throws IOException, ClassNotFoundException {
        readAppointmentsFromDb();
        return appointments;
    }

    @Override
    public void deleteAppointmentById(String sid){
        appointments = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DbConnection.getConnection();
            String sql2 = "delete from `appointment` where `id` = '"+sid+"';";
            Statement statement0 = connect.createStatement();
            Statement statement1 = connect.createStatement();
            PreparedStatement pstatement = connect.prepareStatement(sql2);
            statement0.execute("SET sql_safe_updates = 0;");
            pstatement.executeUpdate(sql2);
            statement1.execute(" SET sql_safe_updates = 1;");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void readAppointmentsFromDb()  {
        appointments = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection connect = DbConnection.getConnection();
            Statement statement = connect.createStatement();
            String sql = "select * FROM appointment";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Appointment ap = new Appointment(resultSet.getString(1), resultSet.getString(2)
                        , resultSet.getString(3), resultSet.getString(4));
                appointments.put(ap.getId(), ap);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeAppointmentsToDb() throws IOException {
        try {
            Connection connect = DbConnection.getConnection();
            String sqlWrite = "insert into appointment values(?,?,?,?)";

            appointments.values().forEach(p -> {
                try {
                    PreparedStatement pre = connect.prepareStatement(sqlWrite);
                    pre.setString(1, p.getId());
                    pre.setString(2, p.getPatientId());
                    pre.setString(3, p.getDateTime());
                    pre.setString(4, p.getReason());
                    pre.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkDistinctId(Appointment p) {
        if (appointments.containsKey(p.getId())) {
            return false;
        }return true;
    }

}


