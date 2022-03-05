/*
 * File: proj4CorrelEnglishBogatyrev.FileHelpers.java
 * Names: Cassidy Correl, Nick English, Philipp Bogatyrev
 * Class: CS361
 * Project 4
 * Date: 2/28/2022
 */

package proj4CorrelEnglishBogatyrev;

import org.fxmisc.richtext.CodeArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Contains helper methods for saving and opening files.
 */
public class FileHelpers {
    private final CodeAreaHelpers tabHelper;

    public FileHelpers(CodeAreaHelpers tabHelper){
        this.tabHelper = tabHelper;
    }

    /**
     * Writes the content of a file to the new CodeArea.
     *
     * @param openedFile the file to write into the CodeArea.
     */
    public void writeFileToCodeArea(File openedFile){
        CodeArea currentArea = this.tabHelper.getCurrentCodeArea();
        currentArea.replaceText(""); // clear default "Sample text" message
        try {
            Scanner scan = new Scanner(new File(String
                    .valueOf(openedFile))).useDelimiter("\\s+");
            while (scan.hasNextLine()) {
                currentArea.appendText(scan.nextLine() + "\n");
            }
        }catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Writes the content of a Tab to a given file path.
     *
     * @param currentFile the File currently referenced by the Tab.
     * @param outFile the file to write the CodeArea contents to.
     */
    public void saveCurrentFile(File currentFile, PrintWriter outFile){
        try {
            outFile = new PrintWriter(String.valueOf(currentFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        outFile.println(this.tabHelper.getCurrentCodeArea().getText());
        outFile.close();
        this.tabHelper.getCurrentTab().setText(currentFile.getName());
    }
}
