package lk.ijse.pos.DAO;

import lk.ijse.pos.model.Customer;

import java.util.ArrayList;

public interface CustomerDAO {

    public boolean addCustomer(Customer customer) throws Exception;
    public boolean updateCustomer(Customer customer) throws Exception;
    public boolean deleteCustomer(String id) throws Exception;
    public Customer searchCustomer(String id) throws Exception;
    public ArrayList<Customer> getAllCustomer() throws Exception;
}
