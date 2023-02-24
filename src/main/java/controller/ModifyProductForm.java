package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Huy G To
 * The ModifyProductForm class allows a selected product from the main menu to be modified,
 * this means changing its associated parts, removing any associated parts, and modifying and parameters of the product. */
public class ModifyProductForm implements Initializable{

    /**
     * stage in which class is presented on
     */
    Stage stage;

    /**
     * parent scene in which class is presented on
     */
    Parent scene;
    /**
     * selected product that is clicked
     */
    private Product selectedProduct;
    /**
     * modified product which is the one being changed
     */
    private Product modifiedProduct;

    /**
     * index id in which every item in an array contains
     */
    private static int indexId;

    /**
     * observablelist of associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * tableview of all the parts
     */
    @FXML
    private TableView<Part> partsFormTable;
    /**
     *  associated part  tableview
     */
    @FXML
    private TableView<Part> associatedPartsFormTable;
    /**
     *  associated part id column
     */
    @FXML
    private TableColumn<?, ?> associatedPartsFormId;
    /**
     *  associated part stock column
     */
    @FXML
    private TableColumn<?, ?> associatedPartsInventory;
    /**
     *  associated part name column
     */
    @FXML
    private TableColumn<?, ?> associatedPartsName;
    /**
     *  associated part price column
     */
    @FXML
    private TableColumn<?, ?> associatedPartsPrice;
    /**
     *    part stock column
     */
    @FXML
    private TableColumn<?, ?> formInventoryLevel;
    /**
     *    part id column
     */
    @FXML
    private TableColumn<?, ?> formPartId;
    /**
     *    part name column
     */
    @FXML
    private TableColumn<?, ?> formPartName;
    /**
     *    part price column
     */
    @FXML
    private TableColumn<?, ?> formPrice;
    /**
     *   textfield of product id
     */
    @FXML
    private TextField modFormProductId;
    /**
     *   textfield of product max
     */
    @FXML
    private TextField modFormProductMax;
    /**
     *   textfield of product min
     */
    @FXML
    private TextField modFormProductMin;
    /**
     *   textfield of product name
     */
    @FXML
    private TextField modFormProductName;
    /**
     *   textfield of product price
     */
    @FXML
    private TextField modFormProductPrice;
    /**
     *   textfield of product stock
     */
    @FXML
    private TextField modFormProductStock;
    /**
     *   textfield of query
     */
    @FXML
    private TextField queryPart;
    /**
     *  searches by the unique id
     */
    private Part searchById(int id){

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(int i = 0; i < allParts.size(); i++){
            Part ip = allParts.get(i);

            if(ip.getId() == id){
                return ip;
            }
        }

        return null;
    }
    /**
     * searches all parts to see if the name used in query matches up to a part
     */
    private ObservableList<Part> searchByName(String partialName){

        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts =  Inventory.getAllParts();

        for(Part ip : allParts){
            if(ip.getName().contains(partialName)){
                namedParts.add(ip);
            }
        }
        return namedParts;
    }
    /**
     * onaction event which searches with the query through with the name or id
     */
    @FXML
    void inputSearchField(ActionEvent event) {

        String name = queryPart.getText();

        ObservableList<Part> searchedPart = searchByName(name);

        if(searchedPart.size() == 0){
            try {
                int id = Integer.parseInt(name);
                Part iSFPart = searchById(id);
                if (iSFPart != null)
                    searchedPart.add(iSFPart);
            }
            catch (NumberFormatException e){
                //ignore
            }
        }

        partsFormTable.setItems(searchedPart);
        queryPart.setText("");

    }
    /**
     * sets the selected product from the main menu and passes on the info to the textfields
     */
    public void setSelectedProduct(Product selectedProduct) {

        this.selectedProduct = selectedProduct;

        ModifyProductForm.indexId = Inventory.getAllParts().indexOf(selectedProduct);

        int id = selectedProduct.getId();
        String name = selectedProduct.getName();
        double price = selectedProduct.getPrice();
        int stock = selectedProduct.getStock();
        int min = selectedProduct.getMin();
        int max = selectedProduct.getMax();

        String idS = String.valueOf(id);
        String priceS = String.valueOf(price);
        String stockS = String.valueOf(stock);
        String minS = String.valueOf(min);
        String maxS = String.valueOf(max);

        modFormProductId.setText(idS);
        modFormProductName.setText(name);
        modFormProductStock.setText(stockS);
        modFormProductPrice.setText(priceS);
        modFormProductMax.setText(maxS);
        modFormProductMin.setText(minS);

        associatedParts.addAll(selectedProduct.getAllAssociatedParts());

    }
    /**
     *   adds parts to the associated parts
     */
    @FXML
    void addPart(ActionEvent event) {

        Part selectedPart = partsFormTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
        updatePartsFormTable();
        updateAssociatedPartsFormTable();

    }

    /**
     * removed part from associated parts
     */
    @FXML
    void removePart(ActionEvent event) {

        if(MainForm.confirmationMessage("Are you sure you'd like to remove this part?")){
            Part selectedPart = associatedPartsFormTable.getSelectionModel().getSelectedItem();
            associatedParts.remove(selectedPart);

            updatePartsFormTable();
            updateAssociatedPartsFormTable();
        }

    }
    /**
     * saves the product form and adds it as a new product to inventory
     */
    @FXML
    void saveProductForm(ActionEvent event) throws IOException {

        this.modifiedProduct = selectedProduct;

        String productMin = modFormProductMin.getText();
        String productMax = modFormProductMax.getText();

        String error = "";

        try {
            error = "minimum";
            int minMin = Integer.parseInt(productMin);
            error = "max";
            int maxMax = Integer.parseInt(productMax);

            if(minMin > maxMax){
                MainForm.confirmationMessage("min has to be less than max");
                return;
            }

        }
        catch(NumberFormatException e){
            MainForm.confirmationMessage(error + " has to be a number");
            return;
        }
        String selectedProductName = modFormProductName.getText();
        if(selectedProductName.isBlank()){
            MainForm.confirmationMessage("Please put in a name");
            return;
        }

        selectedProduct.setName(modFormProductName.getText());

        selectedProduct.setId(Integer.parseInt(modFormProductId.getText()));
        try {
            error = "inventory";
            selectedProduct.setStock(Integer.parseInt(modFormProductStock.getText()));
            error = "price";
            selectedProduct.setPrice(Double.parseDouble(modFormProductPrice.getText()));
        }
        catch(NumberFormatException e){
            MainForm.confirmationMessage(error + " has to be a number");
            return;
        }


        selectedProduct.setMin(Integer.parseInt(modFormProductMin.getText()));
        selectedProduct.setMax(Integer.parseInt(modFormProductMax.getText()));
        selectedProduct.getAllAssociatedParts().clear();
        selectedProduct.addAssociatedPart(associatedParts);

        Inventory.updateProduct(indexId, selectedProduct);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/project/MainForm.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();

    }

    /**
     * displays the main menu
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/project/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * updates the parts tableview with the modifications
     */
    public void updatePartsFormTable() {

        partsFormTable.setItems(Inventory.getAllParts());
    }

    /**
     * updates the associated parts tableview with the modifications
     */
    private void updateAssociatedPartsFormTable() {

        associatedPartsFormTable.setItems(associatedParts);
    }

    /**
     * initializble which sets the tableviews with the corresponding parts and associated parts and populizes the columns with values.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsFormTable.setItems(Inventory.getAllParts());

        formPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        formPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        formInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        formPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        updatePartsFormTable();

        associatedPartsFormTable.setItems(associatedParts);

        associatedPartsFormId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        updateAssociatedPartsFormTable();

    }
}