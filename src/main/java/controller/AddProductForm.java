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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Huy G To
 * The AddProductForm class is where users add a new product to the inventory. it allows for a user to select parts and associate it with a product.
 * It also generates a unique id and contains all the parameters for a product ojbect in its completion. saves on pressing the save button. */
public class AddProductForm implements Initializable {

    /**
     * integer used for calculating random unique id, sets lower bound
     */
    private int min = 1000;
    /**
     * integer used for calculating random unique id, sets upper bound
     */
    private int max = 9999;
    /**
     * randomly generated unique id that every product contains
     */
    private int randomId = (int)Math.floor(Math.random() * (max - min + 1) + min);

    /**
     * observable list of associated prats
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * observable list of original parts
     */
    private ObservableList<Part> oParts = FXCollections.observableArrayList();

    /**
     * stage in which class is presented on
     */
    Stage stage;

    /**
     * parent scene in which class is presented on
     */
    Parent scene;

    /**
     *  product id text field
     */
    @FXML
    private TextField addFormProductId;

    /**
     *  product max text field
     */
    @FXML
    private TextField addFormProductMax;
    /**
     *  product min text field
     */
    @FXML
    private TextField addFormProductMin;
    /**
     *  product name text field
     */
    @FXML
    private TextField addFormProductName;
    /**
     *  product price text field
     */
    @FXML
    private TextField addFormProductPrice;
    /**
     *  product stock text field
     */
    @FXML
    private TextField addFormProductStock;
    /**
     *  associated part id column
     */
    @FXML
    private TableColumn<?, ?> associatedPartsFormId;
    /**
     *  associated part tableview
     */
    @FXML
    private TableView<Part> associatedPartsFormTable;
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
     *   original part stock column
     */
    @FXML
    private TableColumn<?, ?> formInventoryLevel;

    /**
     *   original part id column
     */
    @FXML
    private TableColumn<?, ?> formPartId;
    /**
     *   original part name column
     */
    @FXML
    private TableColumn<?, ?> formPartName;
    /**
     *   original part price column
     */
    @FXML
    private TableColumn<?, ?> formPrice;
    /**
     *   original part tableview
     */
    @FXML
    private TableView<Part> partsFormTable;

    /**
     *   query for search textfield
     */
    @FXML
    private TextField queryPart;

    /**
     *   updates parts in the main tableview
     */
    public void updatePartsFormTable() {

        partsFormTable.setItems(Inventory.getAllParts());
    }

    /**
     *   updates associated parts in the tableview
     */
    private void updateAssociatedPartsFormTable() {

        associatedPartsFormTable.setItems(associatedParts);
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
    void saveProductForm(ActionEvent event) throws IOException{

        String name = addFormProductName.getText();

        if(name.isBlank()){
            MainForm.confirmationMessage("Please put in a name");
            return;
        }


        String error = "";
        int productId = 0;

        try {
            productId = Integer.parseInt(addFormProductId.getText());
        }
        catch (NumberFormatException e){
            MainForm.confirmationMessage("Id has to be a number");
            return;
        }
        try {
            error = "price";
            double price = Double.parseDouble(addFormProductPrice.getText());
            error = "inventory";
            int stock = Integer.parseInt(addFormProductStock.getText());
            error = "min";
            int min = Integer.parseInt(addFormProductMin.getText());
            error = "max";
            int max = Integer.parseInt(addFormProductMax.getText());
            error = "machineId";

            if(min > max){
                MainForm.confirmationMessage("min has to be less than max");
                return;
            }

            Product newProduct = new Product(productId, name, price, stock, min, max);
            newProduct.addAssociatedPart(associatedParts);
            Inventory.addProduct(newProduct);
        }
        catch(NumberFormatException e){
            MainForm.confirmationMessage(error + " has to be a number");
            return;
        }


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/project/MainForm.fxml"));
        stage.setScene(new Scene(scene));
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
     * sets the unique id to a product and initializes the tableviews with associated and original parts.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String autoGenID = Integer.toString(randomId);
        addFormProductId.setText(autoGenID);

        oParts = Inventory.getAllParts();

        partsFormTable.setItems(oParts);

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
