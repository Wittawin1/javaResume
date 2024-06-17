package repositories;

import domain.Patient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryPatientRepository implements PatientRepository {
    private Map<String, Patient> patients = new HashMap<>();

    @Override
    public void addPatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }

    @Override
    public Patient getPatientById(String id) {
        return patients.get(id);
    }

    @Override
    public void updatePatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }


    public Map<String, Patient> getAllPatients() {
        return patients;
    }
    @Override
    public void deletePatientById(String id)  {
        patients.remove(id);
    }
}
