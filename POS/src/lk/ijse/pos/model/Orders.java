package lk.ijse.pos.model;

import java.util.Date;


/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/
public class Orders {
    private String id;
    private Date date;
    private String customerId;

    public Orders() {
    }

    public Orders(String id, Date date, String customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Orders{" + "id=" + id + ", date=" + date + ", customerId=" + customerId + '}';
    }

}
