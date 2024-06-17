package repositories;

import database.DbConnection;
import domain.Patient;
import start.Start;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbPatientRepository implements PatientRepository {
    private Map<String, Patient> patients;

    @Override
    public void addPatient(Patient patient) throws IOException, ClassNotFoundException {
        patients = new HashMap<>();
        patients.put(patient.getId(),patient);
        writePatientsToDb();
    }

    @Override
    public Patient getPatientById(String id) throws IOException, ClassNotFoundException {
        readPatientsFromDb();
        if (patients.get(id) != null) return patients.get(id);
        return null;
    }

    @Override
    public void updatePatient(Patient patient) throws IOException, ClassNotFoundException {
        patients = new HashMap<>();
        deletePatientById(patient.getId());
        patients.put(patient.getId(),patient);
        writePatientsToDb();
    }

    @Override
    public Map<String, Patient> getAllPatients() throws ClassNotFoundException {
        readPatientsFromDb();
        return patients;
    }

    @Override
    public void deletePatientById(String sid){
        patients = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DbConnection.getConnection();
            String sql2 = "delete from `patient` where `id` = '"+sid+"';";
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

    private void readPatientsFromDb() throws ClassNotFoundException {
        patients = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection connect = DbConnection.getConnection();
            Statement statement = connect.createStatement();
            String sql = "select * FROM patient";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Patient pa = new Patient(resultSet.getString(1), resultSet.getString(2)
                        , resultSet.getInt(3), resultSet.getString(4));
                patients.put(pa.getId(), pa);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writePatientsToDb() throws IOException {
        try {
            Connection connect = DbConnection.getConnection();
            String sqlWrite = "insert into patient values(?,?,?,?)";

            patients.values().forEach(p -> {
                try {
                    PreparedStatement pre = connect.prepareStatement(sqlWrite);
                    pre.setString(1, p.getId());
                    pre.setString(2, p.getName());
                    pre.setInt(3, p.getAge());
                    pre.setString(4, p.getAddress());
                    pre.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkDistinctId(Patient p) {
        if (patients.containsKey(p.getId())) {
            return false;
        }return true;
    }
}



