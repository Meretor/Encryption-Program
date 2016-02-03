import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import javafx.scene.control.TextArea;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class MainWindow extends Application
{
    private Stage primary;
    private MenuBar mbar;
    private BorderPane bp;
    private File currentFile;
    private boolean isSaved;

    TextArea input = new TextArea();


    TextArea keyWord = new TextArea();


    TextArea output = new TextArea();

    private boolean de;


    @Override
    public void init()
    {
        bp = new BorderPane();
        mbar = new MenuBar();
    }

    @Override
    public void start(Stage primary)
    {

        //making menubar
        bp.setTop(mbar);
        createFileMenu();
        //createOptionsMenu();

        //Makes windows -- potentially put in init and declare stuff outside of block?
        primary.setTitle("Vigenere Encrypter-Decrypter");

        input.setPromptText("Phrase to be encrypted");
        keyWord.setPromptText("Phrase to encrypt with");
        output.setPromptText("Encrypted phrase will appear here.");

        output.setEditable(false);

        Button encrypt = new Button("Encrypt!");
        //The text on this button will need to change at some point
        encrypt.setOnAction(e -> {
            System.out.println(input.getText());
            System.out.println(keyWord.getText());
            output.setText(Crypto.controller(input.getText(), Crypto.sanitize(keyWord.getText()), true)); // Currently tells
        });
        //decrypt.setOnAction(d -> {
            //System.out.println(inputDe.getText());
            //System.out.println(keyWordDe.getText());
            //outputDe.setText(Crypto.controller(inputDe.getText(), Crypto.sanitize(keyWordDe.getText()), false));
        //});

        //make it pretty
        VBox mainColumn = new VBox();

        mainColumn.getChildren().addAll(input, keyWord, encrypt, output);
        bp.setCenter(mainColumn);
        Scene a = new Scene(bp);

        primary.setScene(a);
        primary.show();
    }

    private void createFileMenu()
    {
        Menu fileMenu = new Menu("File");


        mbar.getMenus().add(fileMenu);
        MenuItem newItemE = new MenuItem("New Encryption");
        MenuItem newItemD = new MenuItem("New Decryption");
        MenuItem openItemE = new MenuItem("Open in Encryption...");
        MenuItem openItemD = new MenuItem("Open in Decryption...");
        MenuItem saveItemD = new MenuItem("Save Encryption");
        MenuItem saveItemE = new MenuItem("Save Decription");
        MenuItem saveAsItemD = new MenuItem("Save Decryption As...");
        MenuItem saveAsItemE = new MenuItem("Save Encryption As...");
        MenuItem quitItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(newItemE, newItemD, openItemE, openItemD,
                saveItemE, saveItemD, saveAsItemE, saveAsItemD, quitItem);

        Menu switchMode = new Menu("Switch Encrypt/Decrypt");
        mbar.getMenus().add(switchMode);

        switchMode.setOnAction( e -> {
            de = !de;
            System.out.println(de);
            System.out.println("Please");
        });

        newItemE.setOnAction( e -> {
            input.setText("");
        });
        openItemE.setOnAction( e -> {
            selectCurrentFileToOpen();
            readCurrentFile();
            //refreshTitleBar();
            // TODO: Refresh title bar as program name: file name
        });
        openItemD.setOnAction( e -> {
            selectCurrentFileToOpen();
            readCurrentFile();
        });
        saveItemE.setOnAction( e -> {
            if(currentFile != null)
            {
                writeCurrentFile();
            }
            //handle the current file being null
            boolean choice = false;
            if(currentFile == null)
            {
                choice = selectCurrentFileToSaveTo();
            }
            if(choice)
            {
                writeCurrentFile();
            }

        });
        saveItemD.setOnAction( e -> {
            if(currentFile != null)
            {
                writeCurrentFile();
            }
            //handle the current file being null
            boolean choice = false;
            if(currentFile == null)
            {
                choice = selectCurrentFileToSaveTo();
            }
            if(choice)
            {
                writeCurrentFile();
            }

        });

        //TODO: This falls on the same problem within writeCurrentFile(). Refer to TODO below.
        saveAsItemE.setOnAction( e -> {
            writeCurrentFile();
        });

        quitItem.setOnAction( e -> {
            Platform.exit();
        });
    }

    private void writeCurrentFile()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    currentFile));
            //TODO: make it so that if it's writing to an En file vs a De file it's doing it to the correct box.
            //ta (as written below) isn't real btw, it's what you need to switch to inputEn or inputDe.
            String blob = input.getText();
            bw.write(blob);
            bw.close();
            isSaved = true;//mark saved
        }
        catch(IOException ex)
        {
            //output.setText("IO Exception has occurred.  Crap.");
        }
    }

    private void readCurrentFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(
                    currentFile));
            StringBuffer sb = new StringBuffer();
            String buf = "";
            while( (buf = br.readLine()) != null)
            {
                sb.append(buf + "\n");
            }
            input.setText(sb.toString());
        }
        catch(FileNotFoundException ex)
        {
            input.setText(String.format("File %s not found.", currentFile.getAbsolutePath()));
        }
        catch(IOException ex)
        {
            input.setText("IO Exception has occurred.  Crap.");
        }
    }

    private boolean selectCurrentFileToOpen()
    {
        //return false if user cancels and true if he does not.
        FileChooser fc = new FileChooser();
        File choice = fc.showOpenDialog(primary);
        if(choice == null)
        {
            return false;
        }
        currentFile = choice;
        return true;

    }
    private boolean selectCurrentFileToSaveTo()
    {
        //return false if user cancels and true if he does not.
        FileChooser fc = new FileChooser();
        File choice = fc.showSaveDialog(primary);
        if(choice == null)
        {
            return false;
        }
        currentFile = choice;
        return true;

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}