package lk.ijse.pos.view.tblmodel;

/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/

public class OrderDetailTM {
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String itemCode, String description, int qty, double unitPrice, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
