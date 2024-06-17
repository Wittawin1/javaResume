package repositories;

import database.DbConnection;
import domain.Invoice;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbInvoiceRepository implements InvoiceRepository{
    private Map<String, Invoice> invoices ;
    @Override
    public void addInvoice(Invoice invoice) throws IOException, ClassNotFoundException {
        invoices = new HashMap<>();
        invoices.put(invoice.getId(),invoice);
        writeInvoicesToDb();
    }

    @Override
    public Invoice getInvoiceById(String id) throws IOException, ClassNotFoundException {
        readInvoicesFromDb();
        if (invoices.get(id) != null) return invoices.get(id);
        return null;
    }

    @Override
    public Map<String,Invoice> getAllInvoices() throws IOException, ClassNotFoundException {
        readInvoicesFromDb();
        return invoices;
    }

    @Override
    public void deleteInvoiceById(String sid){
        invoices = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DbConnection.getConnection();
            String sql2 = "delete from `invoice` where `id` = '"+sid+"';";
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

    private void readInvoicesFromDb() throws IOException, ClassNotFoundException {
        invoices = new HashMap<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection connect = DbConnection.getConnection();
            Statement statement = connect.createStatement();
            String sql = "select * FROM invoice";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Invoice in = new Invoice(resultSet.getString(1),resultSet.getString(2)
                        ,resultSet.getDouble(3),resultSet.getString(4));
                invoices.put(in.getId(),in);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeInvoicesToDb() throws IOException {
        try {
            Connection connect = DbConnection.getConnection();
            String sqlWrite = "insert into invoice values(?,?,?,?)";

            invoices.values().forEach(p -> {
                try {
                    PreparedStatement pre = connect.prepareStatement(sqlWrite);
                    pre.setString(1, p.getId());
                    pre.setString(2, p.getPatientId());
                    pre.setDouble(3, p.getAmount());
                    pre.setString(4, p.getDate());
                    pre.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkDistinctId(Invoice p) {
        if (invoices.containsKey(p.getId())) {
            return false;
        }return true;
    }
}

