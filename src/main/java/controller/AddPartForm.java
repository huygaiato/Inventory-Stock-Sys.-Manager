package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSoured;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Huy G To
 * The AddProductForm class is where users add a new part to the inventory. it allows a user to select whether the part be inhouse or outsourced,
 * and has all the parameters of each corresponding option layed out on the textfields. saves on pressing the save button. */
public class AddPartForm implements Initializable {

    /**
     * integer used for calculating random unique id, sets lower bound
     */
    private int min = 1;
    /**
     * integer used for calculating random unique id, sets upper bound
     */
    private int max = 50;
    /**
     * randomly generated unique id that every part contains
     */
    private int randomId = (int)Math.floor(Math.random() * (max - min + 1) + min);

    /**
     * stage in which class is presented on
     */
    Stage stage;

    /**
     * parent scene in which class is presented on
     */
    Parent scene;

    /**
     * toggle group of buttons for add menu
     */
    @FXML
    private ToggleGroup addPartToggleGroup;

    /**
     * label changes between machine id and company name
     */
    @FXML
    private Label changeLbl;

    /**
     * inhouse radio button
     */
    @FXML
    private RadioButton inHouseRdBtn;

    /**
     * outsourced radio button
     */
    @FXML
    private RadioButton outSourcedRdBtn;

    /**
     * save form button
     */
    @FXML
    private Button saveAddPartFormRdBtn;

    /**
     *  part id text field
     */
    @FXML
    private TextField addFormPartId;

    /**
     * part min text field
     */
    @FXML
    private TextField addFormPartMin;

    /**
     * part name text field
     */
    @FXML
    private TextField addFormPartName;

    /**
     * part price text field
     */
    @FXML
    private TextField addFormPartPrice;

    /**
     * part stock text field
     */
    @FXML
    private TextField addFormPartStock;

    /**
     * part machine id text field
     */
    @FXML
    private TextField addPartFormMachineId;

    /**
     * part max text field
     */
    @FXML
    private TextField addformPartMax;


    /**
     * exits back to main menu
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/main/project/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * saves the added part and adds it to inventory
     */
    @FXML
    void onActionSaveAddPartForm(ActionEvent event) throws IOException {

        if(inHouseRdBtn.isSelected()) {

            String idS = addFormPartId.getText();
            String nameS = addFormPartName.getText();
            String priceS = addFormPartPrice.getText();
            String invS = addFormPartStock.getText();
            String minS = addFormPartMin.getText();
            String maxS = addformPartMax.getText();
            String machineS = addPartFormMachineId.getText();

            if(nameS.isBlank()){
                MainForm.confirmationMessage("Please put in a name");
                return;
            }

            String error = "";
            int id = 0;
            try {
                 id = Integer.parseInt(idS);
            }
            catch (NumberFormatException e){
                MainForm.confirmationMessage("Id has to be a number");
                return;
            }
            try{
                error = "price";
                double price = Double.parseDouble(priceS);
                error = "inventory";
                int stock = Integer.parseInt(invS);
                error = "min";
                int min = Integer.parseInt(minS);
                error = "max";
                int max = Integer.parseInt(maxS);
                error = "machineId";
                int machineId = Integer.parseInt(machineS);

                if(min > max){
                    MainForm.confirmationMessage("min has to be less than max");
                    return;
                }

                InHouse newPart = new InHouse(id, nameS, price, stock, min, max, machineId);
                Inventory.addPart(newPart);
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
        else if(outSourcedRdBtn.isSelected()) {

            String idS = addFormPartId.getText();
            String nameS = addFormPartName.getText();
            String priceS = addFormPartPrice.getText();
            String invS = addFormPartStock.getText();
            String minS = addFormPartMin.getText();
            String maxS = addformPartMax.getText();
            String companyS = addPartFormMachineId.getText();

            if(nameS.isBlank()){
                MainForm.confirmationMessage("Please put in a name");
                return;
            }
            if(companyS.isBlank()){
                MainForm.confirmationMessage("Please put in a company name");
                return;
            }

            String error = "";
            int id = 0;
            try {
                id = Integer.parseInt(idS);
            }
            catch (NumberFormatException e){
                MainForm.confirmationMessage("Id has to be a number");
                return;
            }

            try{
                error = "price";
                double price = Double.parseDouble(priceS);
                error = "inventory";
                int stock = Integer.parseInt(invS);
                error = "min";
                int min = Integer.parseInt(minS);
                error = "max";
                int max = Integer.parseInt(maxS);

                if(min > max){
                    MainForm.confirmationMessage("min has to be less than max");
                    return;
                }

                OutSoured newPart = new OutSoured(id, nameS, price, stock, min, max, companyS);
                // MainForm.setNewPart(newPart);
                Inventory.addPart(newPart);
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

        }


    /**
     * if inhouse button is selected changes the label to machine Id
     */
    @FXML
    void onInHouseRdBtn(ActionEvent event) {
        changeLbl.setText("Machine ID");
    }

    /**
     * if outsourced rd btn is selected changes the label to company name
     */
    @FXML
    void onOutSourcedRdBtn(ActionEvent event) {
        changeLbl.setText("Company Name");
    }

    /**
     * initializble for addpart menu, sets the unique generated id to a part
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String autoGenID = Integer.toString(randomId);
        addFormPartId.setText(autoGenID);

    }


}
