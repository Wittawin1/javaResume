package repositories;

import domain.Patient;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FilePatientRepository implements PatientRepository {
    private final String PATH = "PatientFile.txt";
    private Map<String, Patient> patients;

    @Override
    public void addPatient(Patient patient) throws IOException, ClassNotFoundException {
            readPatientsFromFile();
            patients.put(patient.getId(),patient);
            writePatientsToFile();
    }

    @Override
    public Patient getPatientById(String id) throws IOException, ClassNotFoundException {
        readPatientsFromFile();
        if (patients.containsKey(id)){
            return patients.get(id);
        }return null;
    }

    @Override
    public void updatePatient(Patient patient) throws IOException, ClassNotFoundException {
        readPatientsFromFile();
        patients.replace(patient.getId(),patient);
        writePatientsToFile();
    }

    @Override
    public  Map<String,Patient> getAllPatients() throws ClassNotFoundException {
        readPatientsFromFile();
        return patients;
    }
    @Override
    public void deletePatientById(String id) throws ClassNotFoundException, IOException {
        readPatientsFromFile();
        patients.remove(id);
        writePatientsToFile();
    }

    private void readPatientsFromFile() throws ClassNotFoundException {
        patients = new HashMap<>();
        File f = new File(PATH);
        if (f.exists()) {
            try (FileInputStream fi = new FileInputStream(PATH);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {
                patients = (Map<String, Patient>) oi.readObject();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void writePatientsToFile() throws IOException {
        try (FileOutputStream fo = new FileOutputStream(PATH);
             BufferedOutputStream bo = new BufferedOutputStream(fo);
             ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(patients);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}

