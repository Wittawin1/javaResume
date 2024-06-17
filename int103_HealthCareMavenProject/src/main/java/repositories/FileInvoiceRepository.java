package repositories;

import domain.Invoice;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileInvoiceRepository implements InvoiceRepository {
    private final String PATH = "InvoiceFile.txt";
    private static int nextId = 0;
    private Map<String, Invoice> invoices;


    @Override
    public void addInvoice(Invoice invoice) throws IOException, ClassNotFoundException {
            readInvoicesFromFile();
            invoices.put(invoice.getId(),invoice);
            writeInvoicesToFile();
    }

    @Override
    public Invoice getInvoiceById(String id) throws IOException, ClassNotFoundException {
            readInvoicesFromFile();
            if (invoices.containsKey(id)){
                return invoices.get(id);
            }
            return null;
    }

    @Override
    public Map<String, Invoice> getAllInvoices() throws ClassNotFoundException {
         readInvoicesFromFile();
         return invoices;
    }
    @Override
    public void deleteInvoiceById(String id) throws ClassNotFoundException, IOException {
        readInvoicesFromFile();
        invoices.remove(id);
        writeInvoicesToFile();
    }

    private void readInvoicesFromFile() throws ClassNotFoundException {
        invoices = new HashMap<>();
        File f = new File(PATH);
        if (f.exists()){
            try (FileInputStream fi = new FileInputStream(PATH);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)) {
                nextId = oi.available();
                invoices = (Map<String, Invoice>) oi.readObject();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void writeInvoicesToFile() throws IOException {
        try (FileOutputStream fo = new FileOutputStream(PATH);
             BufferedOutputStream bo = new BufferedOutputStream(fo);
             ObjectOutputStream oo = new ObjectOutputStream(bo)) {
            oo.writeObject(invoices);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

