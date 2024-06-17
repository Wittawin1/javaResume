package repositories;

import domain.Invoice;

import java.io.IOException;
import java.util.Map;

public interface InvoiceRepository {
    void addInvoice(Invoice invoice) throws IOException, ClassNotFoundException;
    Invoice getInvoiceById(String id) throws IOException, ClassNotFoundException;
    Map<String, Invoice> getAllInvoices() throws IOException, ClassNotFoundException;
    public void deleteInvoiceById(String id) throws ClassNotFoundException, IOException;
}
