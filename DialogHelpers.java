/*
 * File: proj4CorrelEnglishBogatyrev.DialogHelpers.java
 * Names: Cassidy Correl, Nick English, Philipp Bogatyrev
 * Class: CS361
 * Project 4
 * Date: 2/28/2022
 */

package proj4CorrelEnglishBogatyrev;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.HashMap;
import java.util.Optional;

/**
 * Contains helper methods for creating dialogs.
 */
public class DialogHelpers {
    private final CodeAreaHelpers tabHelper;
    private final FileHelpers fileHelper;
    private final TabPane tabPane;
    private HashMap<Tab, File> filenameFileMap;
    private HashMap<Tab, Boolean> textHasChangedMap;
    private final FileChooser chooser = new FileChooser();

    public DialogHelpers(TabPane tabPane, CodeAreaHelpers tabHelper,
                         FileHelpers fileHelper, HashMap<Tab, File> filenameFileMap,
                         HashMap<Tab, Boolean> textHasChangedMap){
        this.tabHelper = tabHelper;
        this.fileHelper = fileHelper;
        this.tabPane = tabPane;
        this.filenameFileMap = filenameFileMap;
        this.textHasChangedMap = textHasChangedMap;
    }
    /**
     * Constructs the dialog for the Hello button and handles the output.
     *
     * @param hello the Hello button to change the text of.
     */
    public void helloDialog(Button hello) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Give me a number");
        dialog.setHeaderText("Give me an integer from 0 to 255:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(number -> {
            hello.setText(number);
        });
    }

    /**
     * Constructs and manages the About Dialog.
     */
    public void aboutDialog(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("About...");
        dialog.setContentText("This is project 4 by Cassidy, Nick, and Philipp");

        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(ok);

        dialog.showAndWait();
    }

    /**
     * Builds and manages the Save As Dialog box.  Saves a Tab's contents
     * to a file path.
     *
     */
    public void saveAsDialog()
    {
        String currentTabTitle = tabHelper.getCurrentTabTitle();
        chooser.setTitle("Save as...");
        chooser.setInitialFileName(currentTabTitle);
        File currentFile = chooser.showSaveDialog(null);
        if (currentFile != null) {
            this.filenameFileMap.put(tabHelper.getCurrentTab(), currentFile);
            fileHelper.saveCurrentFile(currentFile, null);
            this.textHasChangedMap.put(tabHelper.getCurrentTab(), false);
        }
    }

    /**
     * Builds and manages the Open Dialog.  Opens a file into a Tab.
     *
     */
    public void openDialog()
    {
        chooser.setTitle("Open file...");
        File openedFile = chooser.showOpenDialog(null);
        if (openedFile != null){
            for(Tab tab:tabPane.getTabs()){
                if(openedFile.equals(filenameFileMap.getOrDefault(tab, null))){
                    tabPane.getSelectionModel().select(tab);
                    return;
                }
            }
            Tab newTab = new Tab(openedFile.getName());
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
            tabHelper.createCodeAreaForTab(newTab);
            fileHelper.writeFileToCodeArea(openedFile);
            // remember path when saving
            filenameFileMap.put(tabHelper.getCurrentTab(), openedFile);
            textHasChangedMap.put(tabHelper.getCurrentTab(), false);
        }
    }

    /**
     * Constructs the Close Dialog and returns user selection.
     *
     * @return which type of Button was clicked within the dialog.
     */
    public Optional<ButtonType> closeDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(String.format("Do you want to save " + "your progress on %s " +
                "before closing?", tabHelper.getCurrentTab().getText()));
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        ButtonType cancel = new ButtonType("Cancel",
                ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no, cancel);
        return alert.showAndWait();
    }
}
