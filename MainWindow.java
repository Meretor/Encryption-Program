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
import java.util.logging.Handler;
//popup stuff
import javafx.stage.Popup;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainWindow extends Application
{
    private Stage secondary;
    private MenuBar mbar;
    private BorderPane bp;
    private File currentFile;
    private boolean isSaved;
    public Popup warning;
    public Handler bleh;


    public enum ciphers{
        VIGENERE,
        FOURSQUARE
    }
    public ciphers currentcipher = ciphers.VIGENERE;

    private Button encrypt = new Button("Encrypt");
    private Button decrypt = new Button("Decrypt");
    private Button myButt = new Button("Not Safe");


    TextArea input = new TextArea();
    TextArea keyWord = new TextArea();
    TextArea output = new TextArea();
    MenuItem saveItem = new MenuItem();
    Menu ioMenu = new Menu();
    MenuItem iKey = new MenuItem();
    MenuItem oKey = new MenuItem();

    private static boolean de;
    private Class<? extends Application> anotherAppClass;


    @Override
    public void init()
    {
        bp = new BorderPane();
        mbar = new MenuBar();
        FourSquare.init();
    }

    @Override
    public void start(Stage primary)
    {

        //making menubar
        bp.setTop(mbar);
        createFileMenu();

        //Makes windows -- potentially put in init and declare stuff outside of block?
        primary.setTitle("Encrypter-Decrypter");
        output.setEditable(false);


///////////// ISSUE WARNING
/*
        Popup fuck = new Popup();
        fuck.setX(300);
        fuck.setY(200);
        fuck.getContent().addAll(new Text(300, 100, "HIT ESC TO CLOSE POPUPS"));

        //fuck.show(primary);
        fuck.isAutoHide();

///////////// POPUP STUFF
        Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);
        popup.getContent().addAll(new Circle(359, 315, 8, Color.RED));
        popup.getContent().addAll(new Text(375, 320, "Encryption NOT Safe"));
        //popup.show(primary,300,200);
        //popup.isShowing();
        popup.isAutoHide();
        //popup.autoHideProperty();


        //TODO: AutoClose the Popup after either certain time or every time a key is pressed.

        // input.setOnKeyPressed(e -> {if(input.getLength() < 1 ) {(fuck.show(primary);)}});
        input.setOnKeyPressed(e -> {if(input.getLength() < 1) {fuck.show(primary);}});
        keyWord.setOnKeyPressed(e -> {if(keyWord.getLength() < 5) {popup.show(primary);}});
        //keyWord.setOnKeyPressed(e -> {if(keyWord.getLength() > 5) {popup.hide();}});


//////////// delete this when you add in the "if key is x long"
        Button show = new Button("Show");
        show.setOnAction(event -> {
            popup.show(primary);
        });
//////////// BUTTONS IN MIDDLE HBOX AROUND POPUP

*/

        encrypt.setOnAction(e -> {
            if (de){
                switchMode();
            }
        });


        decrypt.setOnAction(d -> {
            if (!de){
                switchMode();
            }
        });

//////////// LIVE EDITING

        input.setOnKeyReleased(e -> output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), !de)));

        keyWord.setOnKeyReleased(e -> {
            output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), !de));
            {
                if (keyWord.getLength() < 5) {
                    myButt.setText("Not Safe");
                }
                if (keyWord.getLength() > 5) {
                    myButt.setText("  Safe  ");
                }
            }
        });

        //make it pretty
        VBox mainColumn = new VBox();
        HBox buttonColumn = new HBox();

        bp.setCenter(mainColumn);
        Scene a = new Scene(bp, 500, 595);
        buttonColumn.setSpacing(148);
        buttonColumn.getChildren().addAll(encrypt, myButt, decrypt);
        mainColumn.getChildren().addAll(input, keyWord, buttonColumn, output);
        bp.setCenter(mainColumn);

        primary.setScene(a);
        primary.show();
        //shortKeyWarning();
        //Set button colors and title by calling switchmode(). A bunch of stuff has to run before switchmode works.
        switchCipher(ciphers.VIGENERE);
        de = true;
        switchMode();
    }
    private void switchCipher(ciphers newcipher){
        currentcipher = newcipher;
        switch (currentcipher){
            case VIGENERE:
                input.setOnKeyReleased(e -> output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), !de)));
                keyWord.setOnKeyReleased(e -> {
                    output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), !de));
                    {
                        if (keyWord.getLength() < 5) {
                            myButt.setText("Not Safe");
                        }
                        if (keyWord.getLength() > 5) {
                            myButt.setText("  Safe  ");
                        }
                    }
                });
                keyWord.setVisible(true);
                ioMenu.setVisible(false);
                break;
            case FOURSQUARE:
                input.setOnKeyReleased(e -> output.setText(FourSquare.controller(input.getText(), !de)));
                keyWord.setVisible(false);
                ioMenu.setVisible(true);
                oKey.setText("Export Key");
                iKey.setText("Import Key");
                oKey.setOnAction(event -> exportFSKey());
                iKey.setOnAction(event -> importFSKey());

                break;
        }
        refreshTitle();
    }


    private void switchMode(){
        de = !de;
        String tempInput;
        String tempOutput;
        tempInput = input.getText();
        tempOutput = output.getText();
        output.setText(tempInput);
        input.setText(tempOutput);

        if (de){
            decrypt.setStyle("-fx-base: #00FF00;");
            encrypt.setStyle("-fx-base: #FF0000;");
            saveItem.setText("Save Decrypted text as...");
            input.setPromptText("Phrase to be decrypted");
            keyWord.setPromptText("Phrase to decrypt with");
            output.setPromptText("Decrypted phrase will appear here.");

        }else{
            encrypt.setStyle("-fx-base: #00FF00;");
            decrypt.setStyle("-fx-base: #FF0000;");
            saveItem.setText("Save Encrypted text as...");
            input.setPromptText("Phrase to be encrypted");
            keyWord.setPromptText("Phrase to encrypt with");
            output.setPromptText("Encrypted phrase will appear here.");
        }
        refreshTitle();


    }

    /*
        private void shortKeyWarning()
        {
            Popup popup = new Popup();
            popup.setX(300);
            popup.setY(200);
            popup.getContent().addAll(new Circle(359, 315, 8, Color.RED));
            popup.getContent().addAll(new Text(375, 320, "Encryption NOT Safe"));
            //popup.show(primary,300,200);
            //popup.isShowing();
            //popup.isAutoHide();
        }

    */
    private void createFileMenu()
    {
        Menu fileMenu = new Menu("File");
        Menu cypherMenu = new Menu("Cypher Type");


        mbar.getMenus().add(fileMenu);
        mbar.getMenus().add(cypherMenu);
        //MenuItem newItem = new MenuItem("New window");
        MenuItem openItem = new MenuItem("Open text...");

        // saveItem = new MenuItem("Save Encrypted text as...");


        MenuItem vigenere = new MenuItem("Vigenere");
        vigenere.setOnAction(e -> switchCipher(ciphers.VIGENERE));
        MenuItem fourSquare = new MenuItem("Four Square");
        fourSquare.setOnAction(e -> switchCipher(ciphers.FOURSQUARE));
        //MenuItem solitair = new MenuItem("Solitaire");
        //solitair.SetOnAction(e -> switchCipher(ciphers.SOLITAIRE));
        MenuItem quitItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(openItem,
                saveItem, quitItem);
        cypherMenu.getItems().addAll(vigenere,fourSquare);

        ioMenu.setText("Import/Export Keys");
        ioMenu.getItems().addAll(iKey, oKey);
        mbar.getMenus().add(ioMenu);

        /*newItem.setOnAction( e -> {
            //TODO: Start another instance of the application
        });*/

        openItem.setOnAction( e -> {
            if (selectCurrentFileToOpen()) {
                readCurrentFile();
            }
        });
        saveItem.setOnAction( e -> {
            if(selectCurrentFileToSaveTo()){
                writeCurrentFile();
            }
        });

        quitItem.setOnAction( e -> {
            Platform.exit();
        });
    }
    ///////////// RUN ANOTHER INSTANCE OF APPLICATION - DOESN'T WORK YET
    public void runAnotherApp() throws Exception
    {
        //this.anotherAppClass = anotherAppClass;
        Application app2 = anotherAppClass.newInstance();
        Stage anotherStage = new Stage();
        app2.start(anotherStage);
    }

    private void writeCurrentFile()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    currentFile));
            String blob = output.getText();
            bw.write(blob);
            bw.close();
            isSaved = true;//mark saved
        }
        catch(IOException ex)
        {
            output.setText("IO Exception has occurred.  Crap.");
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
        switch(currentcipher){
            case VIGENERE:
                output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), !de));
                break;
            case FOURSQUARE:
                output.setText(FourSquare.controller(input.getText(), !de));
                break;
        }
    }

    private boolean selectCurrentFileToOpen()
    {
        //return false if user cancels and true if he does not.
        FileChooser fc = new FileChooser();
        File choice = fc.showOpenDialog(secondary);
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
        File choice = fc.showSaveDialog(secondary);
        if(choice == null)
        {
            return false;
        }
        currentFile = choice;
        return true;
    }
    private void refreshTitle(){
        String displayme = "";
        if (de){
            displayme += "Decrypting ";
        }else{
            displayme += "Encrypting ";
        }

        //displayme += currentFile + " ";
        displayme += "Using ";
        switch (currentcipher){
            case VIGENERE:
                displayme += "Vigenere";
                break; // Cases are weird, if break isn't here then it does weird things.
            case FOURSQUARE:
                displayme += "Foursquare";
                break;
            //case
        }
        displayme += " Cipher";
        Stage primStage = (Stage) input.getScene().getWindow();
        primStage.setTitle(displayme);

    }
    private void exportFSKey(){
        FileChooser fc = new FileChooser();
        File choice = fc.showSaveDialog(secondary);
        if(choice == null){
            return;
        }
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    choice));

            String blob = "# Foursquare Cipher Keyfile" + '\n'
                    + Common.alph + '\n'
                    + FourSquare.encOne + '\n'
                    + FourSquare.encTwo + '\n';
            bw.write(blob);
            bw.close();
        }
        catch(IOException ex){
            //TODO: Popup for Export Failed?

        }
    }
    private void importFSKey(){
        FileChooser fc = new FileChooser();
        File choice = fc.showOpenDialog(secondary);
        if(choice == null){
            return;
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(choice));
            StringBuffer sb = new StringBuffer();
            String buf = "";
            int counter = 1;
            while( (buf = br.readLine()) != null)
            {
                if((buf.charAt(0) != '#' ) && (buf.charAt(0) != '\n')){ //If a line is not commented out or blank
                    switch (counter){
                        case 1:
                            Common.alph = buf;
                            break;
                        case 2:
                            FourSquare.encOne = buf;
                            break;
                        case 3:
                            FourSquare.encTwo = buf;
                            break;
                    }
                    counter++;
                }
            }
        }
        catch(FileNotFoundException ex)
        {
            //TODO: popup for Import Failed?
        }
        catch(IOException ex)
        {
            //TODO: popup for Import Failed?
        }
        switch(currentcipher) {
            case FOURSQUARE:
                output.setText(FourSquare.controller(input.getText(), !de));
                break;
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
