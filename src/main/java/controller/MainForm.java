package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Huy G To
 * the MainForm class is the main menu in which the program launches on, has tableviews of both parts and products,
 * allows either tableview to be searched by a partial name or its corresponding unique id.
 * It also allows the user to add new parts/products, remove selected parts/products, and modify parts/products. and when exited closes the program */
public class MainForm implements Initializable {

    /**
     * sets the stage for the main menu
     */
    Stage stage;
    /**
     * sets scene for main menu, is parent
     */
    Parent scene;

    /**
     * inventory column
     */
    @FXML
    private TableColumn partsInventoryLevelColumn;

    /**
     * partid column
     */
    @FXML
    private TableColumn partIdColumn;

    /**
     * name column
     */
    @FXML
    private TableColumn partNameColumn;

    /**
     * tableview of parts
     */
    @FXML
    private TableView<Part>  partsMainFormTable;


    /**
     * price column
     */
    @FXML
    private TableColumn partsPriceColumn;

    /**
     * product id column
     */
    @FXML
    private TableColumn productIdColumn;

    /**
     * product inventory level column
     */
    @FXML
    private TableColumn productInventoryLevelColumn;

    /**
     * product name column
     */
    @FXML
    private TableColumn productNameColumn;

    /**
     * product price column
     */
    @FXML
    private TableColumn productPriceColumn;

    /**
     * tableview of products
     */
    @FXML
    private TableView productsMainFormTable;

    /**
     * textfield containing the search for parts
     */
    @FXML
    private TextField queryPart;

    /**
     * textefield containg the search for products
     */
    @FXML
    private TextField queryProduct;

    /**
     * object of part called newpart
     */
    private Part newPart;

    /**
     * object of part which is the selected part in a form
     */
    private Part selectedPart;

    /**
     * object of product which is selected product in a form
     */
    private Product selectedProduct;

    /**
     * setter for selected part
     */
    public  void setPart(Part selectedPart){

       this.selectedPart = selectedPart;
    }

    /**
     * setter for selected product
     */
    public  void setProduct(Product selectedProduct){

        this.selectedProduct = selectedProduct;
    }
    /**
     * setter for a new part
     */
    public  void setNewPart(Part newPart) {

       this.newPart = newPart;
    }

    /**
     * is the default confirmation message in which any action is double checked
     */
    static boolean confirmationMessage(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("PLEASE MAKE SURE");
        alert.setContentText(message);
        Optional<ButtonType> deleter = alert.showAndWait();
        if (deleter.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * deletes part from menu
     */
    @FXML
    public void onActionDeletePartsEntry(ActionEvent event) {

        if (confirmationMessage("are you sure you want to delete this part?")) {
            int selectedPart = partsMainFormTable.getSelectionModel().getSelectedIndex();
            partsMainFormTable.getItems().remove(selectedPart);
        }
    }

    /**
     * deletes product from menu ONLY IF it has no associated parts
     *
     * <p><b>
     *     *****RUNTIME ERROR*****
     *  I had the biggest logical error with the delete product method and how I could not get it for the life of me
     *  to detect whether 1. an associated part belonged with the product, 2. if it does have an associated part it should
     *  not be deleted. It kept deleting no whether it did have the part and after trial and error. I was finally able to
     *  find the right methods for and logic to 1. delete only if it did NOT have a part. and 2. incorporate it along with my confirmation message
     *  </b></p>
     */
    @FXML
    public void onActionDeleteProductEntry(ActionEvent event) {

        Product selProduct = (Product) productsMainFormTable.getSelectionModel().getSelectedItem();

        if (confirmationMessage("are you sure want to delete this product?")) {
            if(selProduct.getAllAssociatedParts().size() > 0){
                confirmationMessage("You cannot delete this product since it has an associated part");
                return;
            }
            int selectedProduct = productsMainFormTable.getSelectionModel().getSelectedIndex();
            productsMainFormTable.getItems().remove(selectedProduct);
        }

    }

    /**
     * onaction event which searches the parts with what is in query
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



        partsMainFormTable.setItems(searchedPart);
        queryPart.setText("");

    }

    /**
     * onaction event which searches the products with what is in query
     */
    @FXML
    void InputSearchFieldProduct(ActionEvent event) {

        String name = queryProduct.getText();

        ObservableList<Product> searchedProduct = searchByProductName(name);

        if(searchedProduct.size() == 0){
            try {
                int id = Integer.parseInt(name);
                Product iSFProduct = searchByProductId(id);
                if (iSFProduct != null)
                    searchedProduct.add(iSFProduct);
            }
            catch (NumberFormatException e){
                //ignore
            }
        }



        productsMainFormTable.setItems(searchedProduct);
        queryProduct.setText("");

    }

    /**
     * displays the add parts menu
     */
    @FXML
    public void onActionDisplayAddPartsForm(ActionEvent event) throws IOException {


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/project/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * displays the addproducts menu
     */
    @FXML
    public void onActionDisplayAddProductsForm(ActionEvent event) throws IOException{

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/project/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * displays the modified parts menu
     */
    @FXML
    public void onActionDisplayModifyPartsForm(ActionEvent event) throws IOException {

        Part selectedPart = partsMainFormTable.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/main/project/ModifyPartForm.fxml"));
        scene = newLoader.load();
        ModifyPartForm controller = newLoader.getController();
        if(selectedPart instanceof InHouse){
            controller.setSelectedPartInHouse(selectedPart);
        }
            else {
            controller.setSelectedPartOutSourced(selectedPart);

        }
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * displays the modified product menu
     */
    @FXML
    public void onActionDisplayModifyProductsForm(ActionEvent event) throws IOException {

        Product selectedProduct = (Product) productsMainFormTable.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/main/project/ModifyProductForm.fxml"));
        scene = newLoader.load();
        ModifyProductForm controller = newLoader.getController();
        controller.setSelectedProduct(selectedProduct);
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * what happens when menu is exited
     */
    @FXML
    public void onActionExitForm(ActionEvent event) throws IOException {

        Platform.exit();

    }
    /**
     * searches the searched part with a partial name
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
     * searches the searched product with a partial name
     */
    private ObservableList<Product> searchByProductName(String partialName){

        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts =  Inventory.getAllProducts();

        for(Product ip : allProducts){
            if(ip.getName().contains(partialName)){
                namedProducts.add(ip);
            }
        }
        return namedProducts;
    }

    /**
     * indexing of allparts to find unique id of what was inputted
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
     * indexing of allproducts to find unique id of what was inputted
     */
    private Product searchByProductId(int id){

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i = 0; i < allProducts.size(); i++){
            Product ip = allProducts.get(i);

            if(ip.getId() == id){
                return ip;
            }
        }

        return null;
    }


    /**
     * initiliazble for class which sets the items of products and parts into the tableview and loads the columns with the values of each respective object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partsMainFormTable.setItems(Inventory.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsMainFormTable.setItems(Inventory.getAllProducts());

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}