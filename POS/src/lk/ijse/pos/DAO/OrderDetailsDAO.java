package lk.ijse.pos.DAO;

import lk.ijse.pos.model.OrderDetails;

public interface OrderDetailsDAO {
    public boolean addOrderDetails(OrderDetails orderDetails) throws Exception;
}
