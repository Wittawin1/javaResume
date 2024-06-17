package usecases;

import domain.Invoice;
import exception.InfoRequireException;
import exception.UnCheckAmountException;
import repositories.InvoiceRepository;

import java.io.IOException;

public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {

        this.invoiceRepository = invoiceRepository;
    }

    public void generateInvoice(String id, String patientId, double amount, String date) throws IOException, ClassNotFoundException {
        if (id == null || patientId == null || date == null) throw new InfoRequireException();
        if (amount < 0) throw new UnCheckAmountException();
        Invoice invoice = new Invoice(id, patientId, amount, date);
        invoiceRepository.addInvoice(invoice);
    }

    public Invoice getInvoiceById(String id) throws IOException, ClassNotFoundException {
        return invoiceRepository.getInvoiceById(id);
    }

    public void deleteInvoiceById(String id) throws IOException, ClassNotFoundException {
        invoiceRepository.deleteInvoiceById(id);
    }
}
