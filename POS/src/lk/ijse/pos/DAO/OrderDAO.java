package lk.ijse.pos.DAO;

import lk.ijse.pos.model.Orders;

public interface OrderDAO {
    public boolean addOrder(Orders orders) throws Exception;
}
