package lk.ijse.pos.DAO;

import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.model.Orders;
import lk.ijse.pos.view.tblmodel.OrderDetailTM;

import java.sql.*;
import java.util.ArrayList;

//public class orderDAOimpl {
//    private Connection connection;
//
//    public ResultSet getId(Orders order) throws SQLException {
//
//
//        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
//                            pstm.setObject(1, order.getCustomerId());
//                            ResultSet rst = pstm.executeQuery();
//        return rst;
//    }
//    public ResultSet getCode(Orders order) throws SQLException {
//        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code = ?");
//        pstm.setObject(1, order.getCustomerId());
//
//        ResultSet rst = pstm.executeQuery();
//        return rst;
//    }
//    public ArrayList<Customer> loadAllCustomer() throws SQLException {
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
//        ArrayList<Customer> allCustomer=new ArrayList<>();
//        while (rst.next()) {
//            allCustomer.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3)));
//        }
//        return allCustomer;
//    }
//    public ArrayList<Item> loadAllItem() throws SQLException {
//        Statement stm = connection.createStatement();
//        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
//        ArrayList<Item>allItem=new ArrayList<>();
//            while (rst.next()) {
//            allItem.add(new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4)));
//
//        }
//            return allItem;
//    }
//    public boolean placeOrder(Orders orders) throws SQLException {
//        connection.setAutoCommit(false);
//        String sql = "INSERT INTO Orders VALUES (?,?,?)";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setObject(1, orders.getCustomerId());
//        pstm.setObject(2, orders.getDate());
//        pstm.setObject(3, orders.getCustomerId());
//        int affectedRows = pstm.executeUpdate();
//
//        if (affectedRows == 0) {
//            connection.rollback();
//            return pstm.executeUpdate()>0;
//        }
//
//        pstm = connection.prepareStatement("INSERT INTO OrderDetails VALUES (?,?,?,?)");
//
//
//         {  OrderDetails orderDetails=new OrderDetails();
//            pstm.setObject(1, orderDetails.getOrderId());
//            pstm.setObject(2, orderDetails.getItemCode());
//            pstm.setObject(3, orderDetails.getQty());
//            pstm.setObject(4, orderDetails.getUnitPrice());
//            affectedRows = pstm.executeUpdate();
//
//            if (affectedRows == 0) {
//                connection.rollback();
//                return pstm.executeUpdate()>0;
//            }
//            int qtyOnHand = 0;
//
//            Statement stm = connection.createStatement();
//            ResultSet rst = stm.executeQuery("SELECT * FROM Item WHERE code='" + orderDetails.getItemCode() + "'");
//            if (rst.next()) {
//                qtyOnHand = rst.getInt(4);
//            }
//            PreparedStatement pstm2 = connection.prepareStatement("UPDATE Item SET qtyOnHand=? WHERE code=?");
//            pstm2.setObject(1, qtyOnHand - orderDetails.getQty());
//            pstm2.setObject(2, orderDetails.getItemCode());
//
//            affectedRows = pstm2.executeUpdate();
//
//            if (affectedRows == 0) {
//                connection.rollback();
//                return pstm.executeUpdate()>0;
//            }
//
//        }
//
//        connection.commit();
//        return false;
//    }
//}
