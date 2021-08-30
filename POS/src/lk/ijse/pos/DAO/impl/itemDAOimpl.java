package lk.ijse.pos.DAO.impl;

import lk.ijse.pos.DAO.CrudUtil;
import lk.ijse.pos.DAO.ItemDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.view.tblmodel.ItemTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class itemDAOimpl implements ItemDAO {
    public boolean addItem(Item item) throws Exception {

        String sql="INSERT INTO Item VALUES (?,?,?,?)";

        return CrudUtil.executeUpdate(sql,item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());

    }
    public boolean updateItem(Item item) throws Exception {

        String sql="UPDATE Item SET description=?,unitPrice =?,qtyOnHand=?, WHERE id=?" ;
        return CrudUtil.executeUpdate(sql,item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode());

    }

    public boolean deleteItem(String id) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");

        pstm.setObject(1, id);

        return pstm.executeUpdate()>0;
    }
    public Item searchItem(String id) throws Exception {

        ResultSet rst = CrudUtil.executeQuery("select * from Item where code=?", id);

        while(rst.next())
            return new Item(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4));



        return null;
        
    }
    public ArrayList<Item> getAllItem() throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        Statement stm = connection.createStatement();

        ResultSet rst = stm.executeQuery("SELECT * FROM Item");

        ArrayList<Item> alItems = new ArrayList<>();

        while (rst.next()) {

            Item item = new Item(rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4));

            alItems.add(item);
        }
        return alItems;

    }

    public boolean updateItemQtyOnHand(String code,int qtyOnHand) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET qtyOnHand=? WHERE code=?");
        pstm.setObject(1, qtyOnHand);
        pstm.setObject(2, code);
        return (pstm.executeUpdate() > 0);
    }
}
