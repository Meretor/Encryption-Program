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
            outputEn.setText(controller(inputEn.getText(), sanitize(keyWordEn.getText()), true));
        });
        decrypt.setOnAction(d -> {
            //System.out.println(inputDe.getText());
            //System.out.println(keyWordDe.getText());
            outputDe.setText(controller(inputDe.getText(), sanitize(keyWordDe.getText()), false));
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
    public void spew(String s)
    {
        for ( int i = 0; i < s.length(); i++)
        {
            System.out.println(s.charAt(i));
            System.out.println(s.charAt(i) == '\n' || s.charAt(i) == ' ');
        }
    }
    public String alph = new String("abcdefghijklmnopqrstuvwxyz��� ");
    public char encrypt(char work, boolean workUp, char key, boolean keyUp)
    {
        //System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        int getChar = getCharVal(work, workUp) + getCharVal(key, keyUp);
        //System.out.println(getChar);
        if (getChar >= alph.length())
        {
            getChar -= alph.length();
        }
        /*System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        System.out.println(getChar);
        System.out.println(alph.charAt(getChar));*/   
        if (! workUp)
        {
            return alph.charAt(getChar);
        }
        return Character.toUpperCase(alph.charAt(getChar));
    }
    public char decrypt(char work, boolean workUp, char key, boolean keyUp)
    {
        //System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        int getChar = getCharVal(work, workUp) - getCharVal(key, keyUp);
        //System.out.println(getChar);
        if (getChar < 0)
        {
            getChar += alph.length();
        }
        /*System.out.println(""+work+"\n"+workUp+"\n"+key+"\n"+keyUp);
        System.out.println(getChar);
        System.out.println(alph.charAt(getChar));*/        
        if (! workUp)
        {
            return alph.charAt(getChar);
        }
        return Character.toUpperCase(alph.charAt(getChar));  
    }
    public String sanitize(String keyIn)
    {
        StringBuffer out = new StringBuffer();
        StringBuffer checKey = new StringBuffer();
        for ( int i = 0; i < keyIn.length(); i++)
        {
            checKey.append(Character.toLowerCase(keyIn.charAt(i)));
            if (alph.contains(checKey))
            {
                out.append(checKey);
            }
            checKey.deleteCharAt(0);
        }
        return new String(out);
    }
            
    public int getCharVal(char check, boolean isUp)
    {
        //System.out.println(check);
        //System.out.println(checkInt);
        if (isUp)
        {
            check = Character.toLowerCase(check);
        }
        
        //System.out.println(check);
        //System.out.println(checkInt);
        return alph.indexOf(check);
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
                if (! alph.contains(checking))
                {
                    out.append(work.charAt(i));
                }
                else
                {
                    if (choice)
                    {
                        out.append(encrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup), Character.isUpperCase(key.charAt(keyPup))));
                    }
                    else
                    {
                        out.append(decrypt(work.charAt(i), Character.isUpperCase(work.charAt(i)), key.charAt(keyPup), Character.isUpperCase(key.charAt(keyPup))));
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