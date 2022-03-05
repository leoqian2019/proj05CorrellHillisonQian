/*
 * File: proj4CorrelEnglishBogatyrev.Controller.java
 * Names: Cassidy Correl, Nick English, Philipp Bogatyrev
 * Class: CS361
 * Project 4
 * Date: 2/28/2022
 */

package proj4CorrelEnglishBogatyrev;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;


/**
 * Controller handles ActionEvents for the Application.
 *
 */
public class Controller {

    @FXML
    private TabPane tabPane;
    @FXML
    private Button hello;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem saveAs;
    @FXML
    private Menu edit;

    private final HashMap<Tab, File> filenameFileMap = new HashMap<>();
    private final HashMap<Tab, Boolean> textHasChangedMap = new HashMap<>();
    private CodeAreaHelpers tabHelper;
    private FileHelpers fileHelper;
    private DialogHelpers dialogHelper;
    private static int tabNum = 1;

    /**
     *
     * Loads initial content on launch.
     */
    @FXML
    public void initialize() {
        tabHelper = new CodeAreaHelpers(tabPane, textHasChangedMap);
        fileHelper = new FileHelpers(tabHelper);
        dialogHelper = new DialogHelpers(tabPane, tabHelper, fileHelper, filenameFileMap,
                textHasChangedMap);
        tabHelper.createCodeAreaForTab(tabHelper.getCurrentTab());
    }

    /**
     * Displays about dialog.
     *
     */
    @FXML
    private void handleAbout(){
        dialogHelper.aboutDialog();
    }

    /**
     * Handles saving under a specified filepath.
     *
     *
     */
    @FXML
    private void handleSaveAs(){
        dialogHelper.saveAsDialog();
    }

    /**
     * Saves a file if saved previously, else prompts to save as.
     */
    @FXML
    private void handleSave(){
        Tab currentTab = tabHelper.getCurrentTab();
        if(!filenameFileMap.containsKey(currentTab)){
            handleSaveAs();
        }else{
            fileHelper.saveCurrentFile(filenameFileMap.get(currentTab), null);
            textHasChangedMap.put(currentTab, false);
        }
    }

    /**
     * Opens a new Tab with a CodeArea.
     *
     *
     */
    @FXML
    private void handleNew(){
        Tab newTab = new Tab("Untitled Tab " + tabNum++);
        newTab.setOnCloseRequest(e -> handleClose());


        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);

        tabHelper.createCodeAreaForTab(newTab);

        if(tabPane.getTabs().size() != 0){
            close.setDisable(false);
            save.setDisable(false);
            saveAs.setDisable(false);
            for (MenuItem item : edit.getItems())
            {
                item.setDisable(false);
            }
        }
    }

    /**
     * Closes a tab if all changes have been saved, or prompts the
     * user to save progress before closing.
     * @return whether to continue with closing
     */
    @FXML
    private boolean handleClose(){
        Tab currentTab = tabHelper.getCurrentTab();
        if (currentTab == null){
            return false;
        }
        if(!textHasChangedMap.get(currentTab)){
            tabPane.getTabs().remove(currentTab);
        }else{
            Optional<ButtonType> result = dialogHelper.closeDialog();
            if (result.get().getText().equals("Yes")){
                handleSave();
                return handleClose(); // remove tab if saved, else repeat
            } else if (result.get().getText().equals("No")){
                tabPane.getTabs().remove(currentTab);
            } else {
                return false; // user pressed cancel
            }
        }
        if(tabPane.getTabs().size() == 0){
            close.setDisable(true);
            save.setDisable(true);
            saveAs.setDisable(true);
            for (MenuItem item : edit.getItems())
            {
                item.setDisable(true);
            }
        }
        return true;
    }

    /**
     * Opens a file into a new Tab and CodeArea.
     *
     *
     */
    @FXML
    private void handleOpen(){
        dialogHelper.openDialog();
        if(tabPane.getTabs().size() != 0){
            close.setDisable(false);
            save.setDisable(false);
            saveAs.setDisable(false);
            for (MenuItem item : edit.getItems())
            {
                item.setDisable(false);
            }

        }
    }

    /**
     * Closes each tab and the Application after checking whether unsaved changes exist.
     */
    @FXML
    public void handleExit(){
        boolean closing = true;
        while (closing){
            closing = handleClose();
        }
        if (tabHelper.getCurrentTab() == null) {
            System.exit(0);
        }
    }

    @FXML
    private void handleUndo(){ tabHelper.getCurrentCodeArea().undo(); }

    @FXML
    private void handleRedo(){
        tabHelper.getCurrentCodeArea().redo();
    }

    @FXML
    private void handleCut(){
        tabHelper.getCurrentCodeArea().cut();
    }

    @FXML
    private void handleCopy(){
        tabHelper.getCurrentCodeArea().copy();
    }

    @FXML
    private void handlePaste(){
        tabHelper.getCurrentCodeArea().paste();
    }


    @FXML
    private void handleSelectAll(){
        tabHelper.getCurrentCodeArea().selectAll();
    }

    /**
     * Manages popup when Hello clicked.
     */
    @FXML
    private void handleHelloButton(){
        dialogHelper.helloDialog(hello);
    }

    /**
     * Adds text when Goodbye clicked.
     */
    @FXML
    private void handleGoodbyeButton(){
        if(!tabPane.getTabs().isEmpty()) {
            tabHelper.getCurrentCodeArea().appendText("Goodbye");
        }
    }
}
