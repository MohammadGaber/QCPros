package Controllers;

import Models.petsStoreAPIsHandler;
import io.restassured.response.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

//implements Initializable ==> to start the pieChart after running the app without any actions, but I linked it by a button action
public class PetStoreController {
    Response res;
    int statusCode = 0;
    // get all
    @FXML
    private TextArea petsTextArea;


    // add new pet
    @FXML
    private TextField petNameInput;
    @FXML
    private TextField petStatus;
    @FXML
    private TextField newAddedPetID;


    // update pet's details
    @FXML
    private TextField requiredPetIDInputToBeUpdated;
    @FXML
    private TextField petNewNameInput;
    @FXML
    private TextField petNewStatusInput;

    // delete
    @FXML
    private TextField IdToBeDeletedInput;

    //get all pets and list them in the pet's store
    @FXML
    void listAllPetsOnPetStore() {
        String allPets = petsStoreAPIsHandler.getAllPets("available", "name") + " " + petsStoreAPIsHandler.getAllPets("pending", "name") + " " + petsStoreAPIsHandler.getAllPets("sold", "name");
        listResultInTextArea(allPets, petsTextArea);
    }

    // add new pet
    @FXML
    void addNewPet() {

        //calling the api and get the status code to be validated
        res = petsStoreAPIsHandler.addNewPet(addNew_getPetIDInput(), addNew_getEnteredPetName(), addNew_getPetStatus());
        statusCode = res.getStatusCode();
        if (statusCode == 200) {
            eventHandler("Pet with ID: " + addNew_getPetIDInput() + " and name : " + addNew_getEnteredPetName() + " is added successfully", true).handle(actionEvent);
        } else {
            eventHandler("Error while calling the API. Response Code: " + statusCode, false).handle(actionEvent);
        }
    }

    String addNew_getEnteredPetName() {
        return petNameInput.getText();
    }

    String addNew_getPetStatus() {
        return petStatus.getText();
    }

    String addNew_getPetIDInput() {
        return newAddedPetID.getText();
    }

    // update pet details
    @FXML
    void updatePetDetails() {

        res = petsStoreAPIsHandler.findPetById(getRequiredIDInputToBeUpdated());

        statusCode = res.getStatusCode();

        if (statusCode == 200) {
            petsStoreAPIsHandler.updatePetDetails(getRequiredIDInputToBeUpdated(), getPetNewNameInput(), getPetNewStatusInput());
            eventHandler("Pet With ID:  " + getIdToBeDeletedInput() + " " + "is Updated successfully", true).handle(actionEvent);
        } else {
            eventHandler(statusCode + " " + "not found" + "\n" + "Pet ID " + getIdToBeDeletedInput() + " " + "is not exist", false).handle(actionEvent);
        }


    }

    String getRequiredIDInputToBeUpdated() {
        return requiredPetIDInputToBeUpdated.getText();
    }

    String getPetNewNameInput() {
        return petNewNameInput.getText();
    }

    String getPetNewStatusInput() {
        return petNewStatusInput.getText();
    }


    // delete pet
    ActionEvent actionEvent = new ActionEvent();

    @FXML
    void deletePetFromStore() {
        res = petsStoreAPIsHandler.findPetById(getIdToBeDeletedInput());
        int findPetByID = res.getStatusCode();

        if (findPetByID == 200) {
            petsStoreAPIsHandler.deleteAPetDetails(getIdToBeDeletedInput());
            eventHandler("Pet With ID:  " + getIdToBeDeletedInput() + " " + "is deleted successfully", true).handle(actionEvent);
        } else {
            eventHandler(findPetByID + " " + "not found" + "\n" + "Pet ID " + getIdToBeDeletedInput() + " " + "is not exist", false).handle(actionEvent);
        }

    }

    String getIdToBeDeletedInput() {
        return IdToBeDeletedInput.getText();
    }

    // find a pet by status or id
    @FXML
    private TextField statusOrIDInput;
    @FXML
    private TextArea findByIDOrStatusArea;

    @FXML
    public void findPet() {

        if (getStatusOrIDInput().equalsIgnoreCase("sold")) {
            res = petsStoreAPIsHandler.GET_FindPetByStatus("sold");
            statusCode = res.getStatusCode();
            if (statusCode == 200) {
                listResultInTextArea(petsStoreAPIsHandler.GET_ResponseAsString(res, "name"), findByIDOrStatusArea);

            } else {
                eventHandler("Something went wrong..", false).handle(actionEvent);
            }

        } else if (getStatusOrIDInput().equalsIgnoreCase("pending")) {
            res = petsStoreAPIsHandler.GET_FindPetByStatus("pending");
            statusCode = res.getStatusCode();
            if (statusCode == 200) {
                listResultInTextArea(petsStoreAPIsHandler.GET_ResponseAsString(res, "name"), findByIDOrStatusArea);
            } else {
                eventHandler("Something went wrong..", false).handle(actionEvent);
            }

        } else if (getStatusOrIDInput().equalsIgnoreCase("available")) {

            res = petsStoreAPIsHandler.GET_FindPetByStatus("available");
            statusCode = res.getStatusCode();
            if (statusCode == 200) {
                listResultInTextArea(petsStoreAPIsHandler.GET_ResponseAsString(res, "name"), findByIDOrStatusArea);
            } else {
                eventHandler("Something went wrong..", false).handle(actionEvent);
            }
        } else {
            res = petsStoreAPIsHandler.findPetById(getStatusOrIDInput());
            statusCode = res.getStatusCode();
            if (statusCode == 200) {
                listResultInTextArea(petsStoreAPIsHandler.GET_ResponseAsString(res, "name"), findByIDOrStatusArea);
            } else {
                eventHandler("Pet ID or status may be not available, please enter valid data", false).handle(actionEvent);
            }
        }


    }


    String getStatusOrIDInput() {
        return statusOrIDInput.getText();
    }

    EventHandler<ActionEvent> eventHandler(String Message, boolean success) {
        Alert a = new Alert(Alert.AlertType.NONE);
        //  if (success) {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // set alert type
                if (success) {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                } else {
                    a.setAlertType(Alert.AlertType.ERROR);
                }

                a.setContentText(Message);
                // show the dialog
                a.show();
            }
        };


        //}
    }

    public void listResultInTextArea(@NotNull String str, TextArea textArea) {
        String[] strArray = str.split(" ");
        if (strArray.length <= 1) {
            textArea.setText(strArray[0] + "\n");
        } else {
            for (String s : strArray) {
                textArea.appendText(s + "\n");
            }
        }
    }

    @FXML
    PieChart pieChart;

    @FXML
    Label precentlable;

    @FXML
    Button showSummary;

    @FXML
    public void setShowSummary() {
        ObservableList<PieChart.Data> pieCharData = FXCollections.observableArrayList(

                new PieChart.Data("Sold" + "(" + Double.valueOf(petsStoreAPIsHandler.findSummary().get("Sold")) + ")", Double.parseDouble(petsStoreAPIsHandler.findSummary().get("Sold"))),
                new PieChart.Data("Pending" + "(" + Double.valueOf(petsStoreAPIsHandler.findSummary().get("Pending")) + ")", Double.parseDouble(petsStoreAPIsHandler.findSummary().get("Pending"))),
                new PieChart.Data("Available" + "(" + Double.valueOf(petsStoreAPIsHandler.findSummary().get("Available")) + ")", Double.parseDouble(petsStoreAPIsHandler.findSummary().get("Available")))
        );

        pieChart.setData(pieCharData);
        pieChart.setTitle("Show all Pets in the store as per status... ");
        pieChart.setCenterShape(true);
        pieChart.setClockwise(true);


        precentlable.setText("Pets PieChart");
        precentlable.setTextFill(Color.BLACK);
        precentlable.setStyle("-fx-font: 14 arial;");

    }

}