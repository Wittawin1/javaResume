package repositories;

import domain.Patient;

import java.io.IOException;
import java.util.Map;

public interface PatientRepository {
    void addPatient(Patient patient) throws IOException, ClassNotFoundException;
    Patient getPatientById(String id) throws IOException, ClassNotFoundException;
    void updatePatient(Patient patient) throws IOException, ClassNotFoundException;
     Map<String, Patient> getAllPatients() throws IOException, ClassNotFoundException;
    public void deletePatientById(String id) throws ClassNotFoundException, IOException;
}
