package lk.ijse.pos.DAO;

import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDetailsDAO {


    public boolean addOrderDetails(OrderDetails oDetails) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO OrderDetails VALUES (?,?,?,?)");
        pstm.setObject(1, oDetails.getOrderId());
        pstm.setObject(2, oDetails.getItemCode());
        pstm.setObject(3, oDetails.getQty());
        pstm.setObject(4, oDetails.getUnitPrice());
        return (pstm.executeUpdate() > 0);
    }
}
