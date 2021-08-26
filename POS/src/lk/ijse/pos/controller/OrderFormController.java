package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.pos.DAO.orderDAOimpl;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.model.Orders;
import lk.ijse.pos.view.tblmodel.OrderDetailTM;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/

public class OrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerID;
    @FXML
    private JFXComboBox<String> cmbItemCode;


    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtQtyOnHand;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private TableView<OrderDetailTM> tblOrderDetails;

    private ObservableList<OrderDetailTM> olOrderDetails;

    private boolean update = false;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private Label lblTotal;
    @FXML
    private JFXTextField txtOrderID;
    @FXML
    private JFXDatePicker txtOrderDate;

    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
             connection = DBConnection.getInstance().getConnection();

            // Create a day cell factory
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            // Must call super
                            super.updateItem(item, empty);
                            LocalDate today = LocalDate.now();
                            setDisable(empty || item.compareTo(today) < 0);
                        }
                    };
                }
            };

            txtOrderDate.setDayCellFactory(dayCellFactory);
            loadAllData();
        } catch (SQLException ex) {
            Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }


        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String customerID = observable.getValue();
                if (customerID == null) {
                    txtCustomerName.setText("");
                    return;
                }

                try {
                   orderDAOimpl dao=new orderDAOimpl();
                    ResultSet rst = dao.getId(new Orders());

                    if (rst.next()) {
                        String customerName = rst.getString(2);
                        txtCustomerName.setText(customerName);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String itemCode = observable.getValue();

                if (itemCode == null) {
                    txtDescription.setText("");
                    txtQtyOnHand.setText("");
                    txtUnitPrice.setText("");
                    txtQty.setText("");
                    return;
                }

                try {
                    orderDAOimpl dao=new orderDAOimpl();
                    ResultSet rst = dao.getId(new Orders());

                    if (rst.next()) {
                        String description = rst.getString(2);
                        double unitPrice = rst.getDouble(3);
                        int qtyOnHand = rst.getInt(4);

                        txtDescription.setText(description);
                        txtUnitPrice.setText(unitPrice + "");
                        txtQtyOnHand.setText(qtyOnHand + "");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        olOrderDetails = FXCollections.observableArrayList();
        tblOrderDetails.setItems(olOrderDetails);

        tblOrderDetails.getItems().addListener(new ListChangeListener<OrderDetailTM>() {
            @Override
            public void onChanged(Change<? extends OrderDetailTM> c) {

                double total = 0.0;

                for (OrderDetailTM orderDetail : olOrderDetails) {
                    total += orderDetail.getTotal();
                }

                lblTotal.setText("Total : " + total);

            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM newValue) {

                OrderDetailTM currentRow = observable.getValue();

                if (currentRow == null) {
                    cmbItemCode.getSelectionModel().clearSelection();
                    update = false;
                    btnRemove.setDisable(true);
                    return;
                }

                update = true;
                String itemCode = currentRow.getItemCode();
                btnRemove.setDisable(false);

                cmbItemCode.getSelectionModel().select(itemCode);
                txtQty.setText(currentRow.getQty() + "");

            }
        });

        btnRemove.setDisable(true);

    }

    private void loadAllData() throws SQLException {

//        orderDAOimpl daOimpl=new orderDAOimpl();
//        ArrayList<Customer>allcust=daOimpl.loadAllCustomer();

        cmbCustomerID.getItems().removeAll(cmbCustomerID.getItems());


        cmbItemCode.getItems().removeAll(cmbItemCode.getItems());


    }

    @FXML
    private void navigateToMain(MouseEvent event) throws IOException {
        Label lblMainNav = (Label) event.getSource();
        Stage primaryStage = (Stage) lblMainNav.getScene().getWindow();

        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/pos/view/MainForm.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        if (!update) {
            for (OrderDetailTM orderDetail : olOrderDetails) {
                if (orderDetail.getItemCode().equals(itemCode)) {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Please update the item instead of adding", ButtonType.OK);
                    error.setHeaderText("Duplicate Entry Found");
                    error.setTitle("Duplicate Error");
                    error.show();
                    return;
                }
            }
        }

        OrderDetailTM orderDetail = new OrderDetailTM(
                itemCode,
                txtDescription.getText(),
                qty,
                unitPrice,
                qty * unitPrice);


        if (!update) {
            olOrderDetails.add(orderDetail);
            tblOrderDetails.setItems(olOrderDetails);
        } else {
            OrderDetailTM selectedRow = tblOrderDetails.getSelectionModel().getSelectedItem();
            int index = olOrderDetails.indexOf(selectedRow);
            olOrderDetails.set(index, orderDetail);
        }

        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();

    }

    @FXML
    private void btnRemoveOnAction(ActionEvent event) {
        OrderDetailTM selectedRow = tblOrderDetails.getSelectionModel().getSelectedItem();
        olOrderDetails.remove(selectedRow);

    }

    @FXML
    private void btnPlaceOrderOnAction(ActionEvent event) {
        try {

            orderDAOimpl daOimpl = new orderDAOimpl();
//            boolean b=daOimpl.placeOrder(new OrderDetails(txtOrderID.getText(),t))

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Placed", ButtonType.OK);
            alert.show();

        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {

            Logger.getLogger(OrderFormController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
