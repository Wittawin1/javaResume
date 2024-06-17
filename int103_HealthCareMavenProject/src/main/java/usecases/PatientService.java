package usecases;

import domain.Patient;
import exception.InfoRequireException;
import exception.UnCheckAgeException;
import exception.UnFindPatientException;
import repositories.PatientRepository;

import java.io.IOException;


public class PatientService {
    private final PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addPatient(String id, String name, int age, String address) throws IOException, ClassNotFoundException {
        if (id == null || name == null || address == null) throw new InfoRequireException("Please fill require info");
        if (age < 0) throw new UnCheckAgeException("Please add age more than 0");
        else {
            Patient patient = new Patient(id, name, age, address);
            patientRepository.addPatient(patient);
        }
    }

    public void updatePatientDetails(String id, String name, int age, String address) throws IOException, ClassNotFoundException {
        Patient patient = patientRepository.getPatientById(id);
        if (patient == null) {throw new UnFindPatientException("Cant find this patient Id");}
        else {
            patient.updateDetails(name, age, address);
            patientRepository.updatePatient(patient);
        }

    }

    public Patient getPatientById(String id) throws IOException, ClassNotFoundException {
        return patientRepository.getPatientById(id);
    }

    public void deletePatientById(String id) throws IOException, ClassNotFoundException {
        patientRepository.deletePatientById(id);
    }

}
