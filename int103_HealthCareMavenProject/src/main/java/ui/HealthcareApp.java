package ui;

import database.DbConnection;
import domain.Appointment;
import domain.Invoice;
import domain.Patient;
import repositories.*;
import usecases.AppointmentService;
import usecases.InvoiceService;
import usecases.PatientService;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class HealthcareApp {

    private static final char[] PASSWORD = {'i', 'n', 't', '1', '0', '3'};

    static InMemoryPatientRepository inMemPatientRepository = new InMemoryPatientRepository();
    static InMemoryAppointmentRepository inMemAppointmentRepository = new InMemoryAppointmentRepository();
    static InMemoryInvoiceRepository inMemInvoiceRepository = new InMemoryInvoiceRepository();
    static FilePatientRepository filePatientRepository = new FilePatientRepository();
    static FileAppointmentRepository fileAppointmentRepository = new FileAppointmentRepository();
    static FileInvoiceRepository fileInvoiceRepository = new FileInvoiceRepository();
    static DbPatientRepository dbPatientRepository= new DbPatientRepository();
    static DbAppointmentRepository dbAppointmentRepository = new DbAppointmentRepository();
    static DbInvoiceRepository dbInvoiceRepository = new DbInvoiceRepository();

    static PatientService patientService = new PatientService(inMemPatientRepository);
    static AppointmentService appointmentService = new AppointmentService(inMemAppointmentRepository);
    static InvoiceService invoiceService = new InvoiceService(inMemInvoiceRepository);
    static int repoChoose = 1;


    public static void start() {
        Console console = System.console();
        String password = "int103";

        if (console == null) {
            System.out.println("No console available. \nPlease enter the password : ");
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine();
        } else {
            char[] enteredPassword = console.readPassword("Enter password: ");
            password = new String(enteredPassword);
        }
        if (!isCorrectPassword(password.toCharArray())) {
            System.out.println("Incorrect password. Exiting...");
            System.exit(1);
        }
        System.out.println("Password is correct. Continuing...");

        try {
            healthProgram();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    static void healthProgram() throws IOException, ClassNotFoundException {
        System.out.println("---- Healthcare Management System ----");
        System.out.println("1. Choose Repository");
        System.out.println("2. Menu");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose an option: ");
        switch (scanner.nextInt()) {
            case 1:
                chooseRepository();
            case 2:
                menu();
            case 3:
                exit();
            default:
                System.out.println("Invalid choice. Please try again.");
                healthProgram();
                System.out.println();
        }
    }

    static void chooseRepository() throws IOException, ClassNotFoundException {
        System.out.println("Choose your repository you need");
        System.out.println("1. InMemory Repository");
        System.out.println("2. File Repository");
        System.out.println("3. Database Repository");
        System.out.println("4. Back");
        System.out.print("Choose your choice : ");
        Scanner sn = new Scanner(System.in);
        switch (sn.nextInt()) {
            case 1:
                repoChoose = 1;
                patientService = new PatientService(inMemPatientRepository);
                appointmentService = new AppointmentService(inMemAppointmentRepository);
                invoiceService = new InvoiceService(inMemInvoiceRepository);
                healthProgram();
            case 2:
                repoChoose = 2;
                patientService = new PatientService(filePatientRepository);
                appointmentService = new AppointmentService(fileAppointmentRepository);
                invoiceService = new InvoiceService(fileInvoiceRepository);
                healthProgram();
            case 3:
                repoChoose = 3;
                patientService = new PatientService(dbPatientRepository);
                appointmentService = new AppointmentService(dbAppointmentRepository);
                invoiceService = new InvoiceService(dbInvoiceRepository);
                dbInsertConnection();
                healthProgram();

            case 4: healthProgram();

            default: System.out.println("Wrong Choice");
                chooseRepository();
        }healthProgram();
    }

    static void dbInsertConnection() {
        System.out.println("---- Login to your Database ----");
        try (Scanner sn = new Scanner(System.in)) {
            System.out.print("Your URL Connection : ");
            String url = sn.next();
            DbConnection.changeConnection(url);

            System.out.print("Your username : ");
            String user = sn.next();
            DbConnection.changeUser(user);

            System.out.print("Your password : ");
            String password = sn.next();
            DbConnection.changePassword(password);

            try (Connection conn = DbConnection.getConnection()) {
                if (conn != null) {
                    System.out.println("---- Login Success ----");
                    healthProgram();
                } else {
                    System.out.println("---- Login Failed ----");
                    healthProgram();
                }
            } catch (SQLException | IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());

            }
        }

    }

    static void menu() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
            System.out.println("---- Menu ----");
            System.out.println("1. Patient");
            System.out.println("2. Appointment");
            System.out.println("3. Invoice");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    patientMenu();
                case 2:
                    appointmentMenu();
                case 3:
                    invoiceMenu();
                case 4:
                    healthProgram();
                default:
                    System.out.println("Invalid choice. Please try again.");
                    menu();


            }
        scanner.close();
    }

    static void exit(){
        System.out.println("Exiting the application...");
        System.exit(0);
    }

    static void patientMenu() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
            System.out.println("---- Patient ---");
            System.out.println("1. List all Patients");
            System.out.println("2. Add patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listAllPatient();
                case 2:
                    addPatient();
                case 3:
                    updatePatientDetail();
                case 4:
                    deletePatientById();
                case 5:
                    menu();
                default:
                    System.out.println("Invalid choice. Please try again.");
                    patientMenu();
        }
    }
    static void listAllPatient() throws IOException, ClassNotFoundException {
        System.out.println("---- List All Patient ----");
        if (repoChoose == 1) {
            System.out.println("Patient from in Memory");
            Collection<Patient> patients = inMemPatientRepository.getAllPatients().values();
            List<Patient> patientList = patients.stream().toList();
            patientList.forEach(System.out::println);
            patientMenu();
        }

        if (repoChoose == 2) {
            System.out.println("Patient from in File");
            Collection<Patient> patients = filePatientRepository.getAllPatients().values();
            List<Patient> patientList = patients.stream().toList();
            patientList.forEach(System.out::println);
            patientMenu();
        }
        if (repoChoose == 3) {
            System.out.println("Patient from in Database");
            Collection<Patient> patients = dbPatientRepository.getAllPatients().values();
            List<Patient> patientList = patients.stream().toList();
            patientList.forEach(System.out::println);
            patientMenu();
        } patientMenu();
    }

    static void addPatient() throws IOException, ClassNotFoundException {
        System.out.println("---- Add Patient ----");
        String id,name,address;
        int age;
        Scanner c1 = new Scanner(System.in);
        System.out.print("user ID : ");
        id = c1.next();
        System.out.print("user Name : ");
        name = c1.next();
        System.out.print("user Age : ");
        age = c1.nextInt();
        System.out.print("user Address : ");
        address = c1.next();
        patientService.addPatient(id, name, age, address);
        System.out.println("--- Add Successfully ---");
        patientMenu();
    }


    static void updatePatientDetail() throws IOException, ClassNotFoundException {
        System.out.println("---- updatePatientDetail ----");
        Scanner sc = new Scanner(System.in);
        System.out.print("What Patient ID you need to update : ");
        String upPatient = sc.next();
        Patient patient = patientService.getPatientById(upPatient);
        if (patient == null){
            System.out.println("Not have this patient ID");
            patientMenu();
        }
        else {
            System.out.println("Change patient detail");
            System.out.print("Name : ");
            String name = sc.next();
            System.out.print("Age : ");
            int age = sc.nextInt();
            System.out.print("Address : ");
            String address = sc.next();
            patientService.updatePatientDetails(upPatient, name, age, address);
            System.out.println("--- Change Successfully ---");
            patientMenu();
        }
    }

    static void deletePatientById() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Delete patient by Id ---");
        System.out.print("what Id you need to remove : ");
        String re = scanner.next();
        if (patientService.getPatientById(re) == null){
            System.out.println("Not have this patient ID");
            patientMenu();
        }else {
            patientService.deletePatientById(re);
            System.out.println("Delete Success");
            patientMenu();
        }
    }

    static void appointmentMenu() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
            System.out.println("---- Appointment ---");
            System.out.println("1. List all Appointment");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Reschedule Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listAllAppointment();
                case 2:
                    scheduleAppointment();
                case 3:
                    rescheduleAppointment();
                case 4:
                    deleteAppointmentById();
                case 5:
                    menu();
                default:
                    System.out.println("Invalid choice. Please try again.");
                    appointmentMenu();
            }
    }


    static void listAllAppointment() throws IOException, ClassNotFoundException {
        System.out.println("---- List All Appointment ----");
        if (repoChoose == 1) {
            Collection<Appointment> appointments = inMemAppointmentRepository.getAllAppointments().values();
            List<Appointment> appointmentList = appointments.stream().toList();
            appointmentList.forEach(System.out::println);
            appointmentMenu();
        }

        if (repoChoose == 2) {
                Collection<Appointment> appointments = fileAppointmentRepository.getAllAppointments().values();
                List<Appointment> appointmentList = appointments.stream().toList();
                appointmentList.forEach(System.out::println);
            appointmentMenu();
            }
        if (repoChoose == 3) {
            Collection<Appointment> appointments = dbAppointmentRepository.getAllAppointments().values();
            List<Appointment> appointmentList = appointments.stream().toList();
            appointmentList.forEach(System.out::println);
            appointmentMenu();
            }
        appointmentMenu();
        }


    static void scheduleAppointment() throws IOException, ClassNotFoundException {
        System.out.println("---- Schedule Appointment ----");
        String apId,patId,date,reason;
        Scanner c2 = new Scanner(System.in);
        System.out.print("Appointment ID : ");
        apId = c2.next();
        System.out.print("Patient Id : ");
        patId = c2.next();
        System.out.println("Date (12-10-2024): ");
        date = c2.next();
        System.out.print("Reason : ");
        reason = c2.next();

        appointmentService.scheduleAppointment(apId,patId,date,reason);
        System.out.println("--- Add Successfully ---");
        appointmentMenu();
    }

    static void rescheduleAppointment() throws IOException, ClassNotFoundException {
        System.out.println("---- rescheduleAppointment ----");
        Scanner sc = new Scanner(System.in);
        System.out.print("What Appointment ID you need to reschedule : ");
        String reschedule = sc.next();
        if (appointmentService.getAppointmentById(reschedule) == null){
            System.out.println("Not have this appointment ID");
            appointmentMenu();
        }
        else {
            System.out.print("Choose new Date");
            String date = sc.next();
            appointmentService.rescheduleAppointment(reschedule, date);
            System.out.println("--- Change Successfully ---");
            appointmentMenu();
        }
    }

    static void deleteAppointmentById() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Delete appointment by Id ----");
        System.out.print("what Id you need to remove : ");
        String re = scanner.next();
        if (appointmentService.getAppointmentById(re) == null){
            System.out.println("Not have this appointment ID");
            appointmentMenu();
        }else {
            appointmentService.deleteAppointmentById(re);
            System.out.println("Delete Success");
            appointmentMenu();
        }
    }

    static void invoiceMenu() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
            System.out.println("---- Invoice ----");
            System.out.println("1. List all Invoice");
            System.out.println("2. Generate Invoice");
            System.out.println("3. Delete Invoice");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listAllInvoice();
                case 2:
                    generateInvoice();
                case 3:
                    deleteInvoiceById();
                case 4:
                    menu();
                default:
                    System.out.println("Invalid choice. Please try again.");
                    invoiceMenu();
            }
    }

    static void listAllInvoice() throws IOException, ClassNotFoundException {
        System.out.println("---- List All Invoice ----");
        if (repoChoose == 1) {
            Collection<Invoice> invoices = inMemInvoiceRepository.getAllInvoices().values();
            List<Invoice> invoiceList = invoices.stream().toList();
            invoiceList.forEach(System.out::println);
            invoiceMenu();
        }

        if (repoChoose == 2) {
            Collection<Invoice> invoices = fileInvoiceRepository.getAllInvoices().values();
            List<Invoice> invoiceList = invoices.stream().toList();
            invoiceList.forEach(System.out::println);
            invoiceMenu();
        }
        if (repoChoose == 3) {
            Collection<Invoice> invoices = dbInvoiceRepository.getAllInvoices().values();
            List<Invoice> invoiceList = invoices.stream().toList();
            invoiceList.forEach(System.out::println);
            invoiceMenu();
        }
    }


    static void generateInvoice() throws IOException, ClassNotFoundException {
        System.out.println("---- Generate Invoice ----");
        String invoiceId,patientId,invoiceDate;
        double invoiceAmount;
        Scanner invoiceSc = new Scanner(System.in);
        System.out.print("Invoice ID : ");
        invoiceId = invoiceSc.next();
        System.out.print("Patient ID : ");
        patientId = invoiceSc.next();
        System.out.print("Date (12-10-2024): ");
        invoiceDate = invoiceSc.next();
        System.out.print("Amount : ");
        invoiceAmount = invoiceSc.nextDouble();
        invoiceService.generateInvoice(invoiceId,patientId,invoiceAmount,invoiceDate);
        System.out.println("--- Add Successfully ---");
        invoiceMenu();
    }

    static void deleteInvoiceById() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Delete Invoice by Id ----");
        System.out.print("what Id you need to remove : ");
        String re = scanner.next();
        if (invoiceService.getInvoiceById(re) == null){
            System.out.println("Not have this invoice ID");
            invoiceMenu();
        }else {
            invoiceService.deleteInvoiceById(re);
            System.out.println("Delete Success");
            invoiceMenu();
        }
    }

    private static boolean isCorrectPassword(char[] enteredPassword) {
        if (enteredPassword.length != PASSWORD.length) {
            return false;
        }
        for (int i = 0; i < PASSWORD.length; i++) {
            if (enteredPassword[i] != PASSWORD[i]) {
                return false;
            }
        }
        return true;
    }
}
