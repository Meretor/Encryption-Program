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
            outputEn.setText(controller(inputEn.getText(), Crypto.sanitize(keyWordEn.getText()), true));
        });
        decrypt.setOnAction(d -> {
            //System.out.println(inputDe.getText());
            //System.out.println(keyWordDe.getText());
            outputDe.setText(controller(inputDe.getText(), Crypto.sanitize(keyWordDe.getText()), false));
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

    public String controller(String work, String key, boolean choice)
    {
        StringBuffer checking = new StringBuffer();
        StringBuffer out = new StringBuffer();
        int keyPup = 0;
        for ( int i = 0; i < work.length(); i++)
        {
            checking.append(Character.toLowerCase(work.charAt(i)));
            //System.out.println(i);

                if (! Crypto.alph.contains(checking))
                {
                    out.append(work.charAt(i));
                }
                else
                {
                    if (choice)
                    {
                        out.append(Crypto.encrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup), Character.isUpperCase(key.charAt(keyPup))));
                    }
                    else
                    {
                        out.append(Crypto.decrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup), Character.isUpperCase(key.charAt(keyPup))));
                    }
                    keyPup +=1;
                }
            if (keyPup >= key.length())
            {
                keyPup -= key.length();
            }
            checking.deleteCharAt(0);
        }
        return new String(out);
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}