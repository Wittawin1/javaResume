package repositories;

import domain.Invoice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryInvoiceRepository implements InvoiceRepository {
    private Map<String, Invoice> invoices = new HashMap<>();

    @Override
    public void addInvoice(Invoice invoice) {
        invoices.put(invoice.getId(), invoice);
    }

    @Override
    public Invoice getInvoiceById(String id) {
        return invoices.get(id);
    }

    @Override
    public Map<String, Invoice> getAllInvoices() {
        return invoices;
    }
    @Override
    public void deleteInvoiceById(String id) {
        invoices.remove(id);
    }
}
