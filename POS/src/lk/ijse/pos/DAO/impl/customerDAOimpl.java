package lk.ijse.pos.DAO.impl;

import lk.ijse.pos.DAO.CrudUtil;
import lk.ijse.pos.DAO.CustomerDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.view.tblmodel.CustomerTM;

import java.sql.*;
import java.util.ArrayList;

public class customerDAOimpl implements CustomerDAO {
    public boolean addCustomer(Customer customer) throws Exception {

        String sql="insert into Customer values (?,?,?,?,?) ";
        return CrudUtil.executeUpdate(sql,customer.getcID(),customer.getName(),customer.getAddress());
    }
    public boolean updateCustomer(Customer customer) throws Exception {

        String sql="UPDATE Customer SET name=?, address=? WHERE id=?" ;
        return CrudUtil.executeUpdate(sql,customer.getName(),customer.getAddress(),customer.getcID());

    }
    public boolean deleteCustomer(String id) throws Exception {

        String sql="DELETE FROM Customer WHERE id=?";
        return CrudUtil.executeUpdate(sql,id);


    }
    public Customer searchCustomer(String id) throws Exception {


        ResultSet rst = CrudUtil.executeQuery("select * from Customer where custName=?", id);
        System.out.println(rst);
        while(rst.next())
            return new Customer(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3));



        return null;

    }
    public ArrayList<Customer> getAllCustomer() throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3)));
        }
        return allCustomers;

    }

}
