package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSoured;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Huy G To
 * The ModifyPartForm class allows a selected part from the main menu to be modified,
 * this means changing its parameters, and changing whether its outsourced/inhouse*/
public class ModifyPartForm implements Initializable {

        /**
         * stage in which class is presented on
         */
        Stage stage;

        /**
         * object scene in which class is presented on
         */
        Object scene;

        /**
         * part ojbect which is the selected part to modify
         */
        private static Part selectedPart;

        /**
         * label changes between machine id and company name
         */
        @FXML
        private Label changeLbl;

        /**
         * inhouse radio button
         */
        @FXML
        private  RadioButton inHouseRdBtn;

        /**
         * outsourced radio button
         */
        @FXML
        private RadioButton outSourcedRdBtn;

        /**
         * save form button
         */
        @FXML
        private Button saveModifyPartRdBtn;

        /**
         *  part machineid text field
         */
        @FXML
        private TextField modifyFormMachineId;

        /**
         *  part id text field
         */
        @FXML
        private TextField modifyFormPartId;


        /**
         * part max text field
         */
        @FXML
        private TextField modifyFormPartMax;

        /**
         * part min text field
         */
        @FXML
        private TextField modifyFormPartMin;

        /**
         * part name text field
         */
        @FXML
        private TextField modifyFormPartName;

        /**
         * part price text field
         */
        @FXML
        private TextField modifyFormPartPrice;

        /**
         * part stock text field
         */
        @FXML
        private TextField modifyFormPartStock;

        /**
         * indexid in which every element of the array has one
         */
        private static int indexId;


        /**
         * sets the selected part to an inhouse object
         */
        public void setSelectedPartInHouse(Part selectedPart) {

                ModifyPartForm.selectedPart = selectedPart;

                ModifyPartForm.indexId = Inventory.getAllParts().indexOf(selectedPart);

                int id = selectedPart.getId();
                String name = selectedPart.getName();
                double price = selectedPart.getPrice();
                int stock = selectedPart.getStock();
                int min = selectedPart.getMin();
                int max = selectedPart.getMax();
                if (selectedPart instanceof InHouse) {

                        int machineId = ((InHouse) selectedPart).getMachineId();

                        String idS = String.valueOf(id);
                        String priceS = String.valueOf(price);
                        String stockS = String.valueOf(stock);
                        String minS = String.valueOf(min);
                        String maxS = String.valueOf(max);
                        String machineS = String.valueOf(machineId);

                        modifyFormPartId.setText(idS);
                        modifyFormPartName.setText(name);
                        modifyFormPartStock.setText(stockS);
                        modifyFormPartPrice.setText(priceS);
                        modifyFormPartMax.setText(maxS);
                        modifyFormPartMin.setText(minS);
                        modifyFormMachineId.setText(machineS);


                }
                else{
                        outSourcedRdBtn.setSelected(true);

                        String companyName = ((OutSoured) selectedPart).getCompanyName();

                        String idS = String.valueOf(id);
                        String priceS = String.valueOf(price);
                        String stockS = String.valueOf(stock);
                        String minS = String.valueOf(min);
                        String maxS = String.valueOf(max);

                        modifyFormPartId.setText(idS);
                        modifyFormPartName.setText(name);
                        modifyFormPartStock.setText(stockS);
                        modifyFormPartPrice.setText(priceS);
                        modifyFormPartMax.setText(maxS);
                        modifyFormPartMin.setText(minS);
                        modifyFormMachineId.setText(companyName);

                }
        }

        /**
         * sets the selected part to an outsourced object
         */
        public void setSelectedPartOutSourced(Part selectedPart) {

                ModifyPartForm.selectedPart = selectedPart;
                outSourcedRdBtn.setSelected(true);

                int id = selectedPart.getId();
                String name = selectedPart.getName();
                double price = selectedPart.getPrice();
                int stock = selectedPart.getStock();
                int min = selectedPart.getMin();
                int max = selectedPart.getMax();

                String companyName = ((OutSoured) selectedPart).getCompanyName();

                String idS = String.valueOf(id);
                String priceS = String.valueOf(price);
                String stockS = String.valueOf(stock);
                String minS = String.valueOf(min);
                String maxS = String.valueOf(max);

                modifyFormPartId.setText(idS);
                modifyFormPartName.setText(name);
                modifyFormPartStock.setText(stockS);
                modifyFormPartPrice.setText(priceS);
                modifyFormPartMax.setText(maxS);
                modifyFormPartMin.setText(minS);
                modifyFormMachineId.setText(companyName);


        }

        /**
         * exits back to main menu
         */
        @FXML
        void onActionDisplayMainForm(ActionEvent event) throws IOException {

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/main/project/MainForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

        }

        /**
         * saves the modified part and adds it to inventory
         * <p><b>
         *     *****FUTURE ENHANCEMENT*****
         *  On the saving the modified part to the menu I think a better way of improving on this is being able to modify all the parts at once through the main
         *  menu without having to go to another screen and populating text fields. It would make it alot more intuitive and allow the user to modify multiple parts at once.
         *  I also think out of all the methods that I've made and all the incorporations of things I've learned throughout the course
         *  my implementation of saving the modified part is probably the ugliest work I've made. It works 100% but if you look through it
         *  you can tell its not the most optimized way to do it and If i spent more time on it I could for sure improve on it and make it
         *  be way cleaner and less wordy. It was one of my first challenges throughout the program and as I went through further and further I got better along the way.
         *  </b></p>
         */
        @FXML
        void onActionSaveModifyPartForm(ActionEvent event) throws IOException {

                if(inHouseRdBtn.isSelected()){


                        String name = modifyFormPartName.getText();

                        if(name.isBlank()){
                                MainForm.confirmationMessage("Please put in a name");
                                return;
                        }

                        String error = "";
                        int partId = 0;

                        try {
                                partId = Integer.parseInt(modifyFormPartId.getText());
                        }

                        catch (NumberFormatException e){
                                MainForm.confirmationMessage("Id has to be a number");
                                return;
                        }
                        try{
                                        error = "price";
                                        double price = Double.parseDouble(modifyFormPartPrice.getText());
                                        error = "inventory";
                                        int stock = Integer.parseInt(modifyFormPartStock.getText());
                                        error = "min";
                                        int min = Integer.parseInt(modifyFormPartMin.getText());
                                        error = "max";
                                        int max = Integer.parseInt(modifyFormPartMax.getText());
                                        error = "machineId";
                                        int machineId = Integer.parseInt(modifyFormMachineId.getText());

                                if(min > max){
                                        MainForm.confirmationMessage("min has to be less than max");
                                        return;
                                }

                                InHouse modifiedPart = new InHouse(partId, name, price, stock, min, max, machineId);
                                Inventory.updatePart(indexId, modifiedPart);

                        }
                        catch (NumberFormatException e){
                                MainForm.confirmationMessage(error + " has to be a number");
                                return;
                        }


                }
                else{
                        String name = modifyFormPartName.getText();
                        String companyName = modifyFormMachineId.getText();

                        if(name.isBlank()){
                                MainForm.confirmationMessage("Please put in a name");
                                return;
                        }
                        if(companyName.isBlank()){
                                MainForm.confirmationMessage("Please put in a company name");
                                return;
                        }


                        String error = "";
                        int partId = 0;
                        try {
                                partId = Integer.parseInt(modifyFormPartId.getText());
                        }

                        catch (NumberFormatException e){
                                MainForm.confirmationMessage("Id has to be a number");
                                return;
                        }
                        try{
                                error = "price";
                                double price = Double.parseDouble(modifyFormPartPrice.getText());
                                error = "inventory";
                                int stock = Integer.parseInt(modifyFormPartStock.getText());
                                error = "min";
                                int min = Integer.parseInt(modifyFormPartMin.getText());
                                error = "max";
                                int max = Integer.parseInt(modifyFormPartMax.getText());
                                error = "machineId";
                                int machineId = Integer.parseInt(modifyFormMachineId.getText());

                                if(min > max){
                                        MainForm.confirmationMessage("min has to be less than max");
                                        return;
                                }

                                OutSoured modifiedPart = new OutSoured(partId, name, price, stock, min, max, companyName);
                                Inventory.updatePart(indexId, modifiedPart);

                        }
                        catch(NumberFormatException e){

                        }

                }
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/main/project/MainForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

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
         * initializble for modifypart menu, sets the unique generated id to a part
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}



