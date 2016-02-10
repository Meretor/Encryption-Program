public class Common
{
    
    public static String alph = new String("abcdefghijklmnopqrstuvwxyzåäö "); 
    /*
     * Function used to remove any characters not within the alph string - without this, the program would have characters it doesn't
     * know what to do with. This is mainly used with keys in Vigenere, as anything that doesn't have meaning to the program will only get
     * in the way in that case. In the case of the plaintext, however, removing characters may remove character -- thus, don't use this
     *on plaintext, only keys!
     */
    /*
     * Used in FourSquare to check the length for appending X to end of string
     */
    public static String sanitize(String keyIn)
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
}