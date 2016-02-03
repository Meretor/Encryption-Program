import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import javafx.scene.control.TextArea;
public class MainWindow extends Application
{
    @Override
    public void start(Stage primary)
    {
        //Makes windows -- potentially put in init and declare stuff outside of block?
        primary.setTitle("Vigenere Encrypter-Decrypter");
        TextArea inputEn = new TextArea();
        inputEn.setPromptText("Phrase to be encrypted");
        TextArea keyWordEn = new TextArea();
        keyWordEn.setPromptText("Phrase to encrypt with");
        TextArea outputEn = new TextArea();
        outputEn.setPromptText("Encrypted phrase will appear here.");
        TextArea inputDe = new TextArea();
        inputDe.setPromptText("Phrase to be decrypted");
        TextArea keyWordDe = new TextArea();
        keyWordDe.setPromptText("Prase to decrypt with");
        TextArea outputDe = new TextArea();
        outputDe.setPromptText("Decrypted phrase will appear here.");
        outputDe.setEditable(false);
        outputEn.setEditable(false);
        Button encrypt = new Button("Encrypt!");
        Button decrypt = new Button("Decrypt!");
        encrypt.setOnAction(e -> {
            //System.out.println(inputEn.getText());
            //System.out.println(keyWordEn.getText());
            outputEn.setText(Crypto.controller(inputEn.getText(), Crypto.sanitize(keyWordEn.getText()), true)); // Currently tells 
        });
        decrypt.setOnAction(d -> {
            //System.out.println(inputDe.getText());
            //System.out.println(keyWordDe.getText());
            outputDe.setText(Crypto.controller(inputDe.getText(), Crypto.sanitize(keyWordDe.getText()), false));
        });
        HBox row = new HBox();
        VBox enRow = new VBox();
        VBox deRow = new VBox();
        row.getChildren().addAll(enRow, deRow);
        enRow.getChildren().addAll(inputEn, keyWordEn, encrypt, outputEn);
        deRow.getChildren().addAll(inputDe, keyWordDe, decrypt, outputDe);
        Scene a = new Scene(row);
        primary.setScene(a);
        primary.show();
    }


    
    public static void main(String[] args)
    {
        launch(args);
    }
}