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
//popup stuff
import javafx.stage.Popup;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class MainWindow extends Application
{
    private Stage primary;
    private MenuBar mbar;
    private BorderPane bp;
    private File currentFile;
    private boolean isSaved;
    public Popup warning;
    private Menu switchMode = new Menu("Switch to Decrypt");

    TextArea input = new TextArea();
    TextArea keyWord = new TextArea();
    TextArea output = new TextArea();

    private boolean de;
    private Class<? extends Application> anotherAppClass;


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
        primary.setTitle("Encrypter-Decrypter");

        input.setPromptText("Phrase to be encrypted");
        keyWord.setPromptText("Phrase to encrypt with");
        output.setPromptText("Encrypted phrase will appear here.");

        output.setEditable(false);



///////////// ISSUE WARNING

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


        // input.setOnKeyPressed(e -> {if(input.getLength() < 1 ) {(fuck.show(primary);)}});
        input.setOnKeyPressed(e -> {if(input.getLength() < 1) {fuck.show(primary);}});
        keyWord.setOnKeyPressed(e -> {if(keyWord.getLength() < 5) {popup.show(primary);}});

//////////// delete this when you add in the "if key is x long"
        Button show = new Button("Show");
        show.setOnAction(event -> {
            popup.show(primary);
        });
////////////





        //TODO: If Encrypt button is clicked AND text in key =< 5 characters in length, then run keyShortWarning.

        //live editing instead of encrypt button
        input.setOnKeyReleased(e -> {
            output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), true));
        });

        keyWord.setOnKeyReleased(e -> {
            output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), true));
        });

        //make it pretty
        VBox mainColumn = new VBox();

        mainColumn.getChildren().addAll(input, keyWord, output, show);
        bp.setCenter(mainColumn);
        Scene a = new Scene(bp, 500, 595);

        primary.setScene(a);
        primary.show();
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
            input.setOnKeyReleased(e -> {
                output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), true));
            });
            keyWord.setOnKeyReleased(e -> {
                output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), true));
            });
            switchMode.setText("Switch to Decrypt");
        }else{
            input.setOnKeyReleased(e -> {
                output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), false));
            });
            keyWord.setOnKeyReleased(e -> {
                output.setText(Vigenere.controller(input.getText(), Common.sanitize(keyWord.getText()), false));
            });
            switchMode.setText("Switch to Encrypt");
        }


    }

    private void createFileMenu()
    {
        Menu fileMenu = new Menu("File");
        Menu cypherMenu = new Menu("Cypher Type");


        mbar.getMenus().add(fileMenu);
        mbar.getMenus().add(cypherMenu);
        MenuItem newItem = new MenuItem("New window");
        MenuItem openItem = new MenuItem("Open text...");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveItemAs = new MenuItem("Save As...");
        //we don't need save as...
        MenuItem vigenere = new MenuItem("Vigenere");
        MenuItem fourSquare = new MenuItem("Four Square");
        MenuItem solitair = new MenuItem("Solitaire");
        MenuItem quitItem = new MenuItem("Quit");
        fileMenu.getItems().addAll(newItem, openItem,
                saveItemAs, saveItem, quitItem);
        cypherMenu.getItems().addAll(vigenere,fourSquare, solitair);

        MenuItem switchBack = new MenuItem("Switch back");
        switchMode.getItems().add(switchBack);
        mbar.getMenus().add(switchMode);

        //https://community.oracle.com/thread/2398419?tstart=0
        switchMode.showingProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue.booleanValue()) {
                switchMode();
            }
        });
        switchBack.setOnAction( e -> {
            switchMode();
        });

        newItem.setOnAction( e -> {
            //TODO: Start another instance of the application
        });
        openItem.setOnAction( e -> {
            selectCurrentFileToOpen();
            readCurrentFile();
            //refreshTitleBar();
            // TODO: Refresh title bar as program name: file name
        });
        openItem.setOnAction( e -> {
            selectCurrentFileToOpen();
            readCurrentFile();
        });
        saveItemAs.setOnAction( e -> {
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
        saveItem.setOnAction( e -> {
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
        //saveAsItemE.setOnAction( e -> {
        //    writeCurrentFile();
        //});

        quitItem.setOnAction( e -> {
            Platform.exit();
        });
    }

    //thought this would work...can't quite figure out how to make it work...
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
            //TODO: make it so that if it's writing to an En file vs a De file it's doing it to the correct box.
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