/**
 *
 */
package demand.inn.com.inndemand.utils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author m.farhan
 */
public class StringUtils {


    private static String WHITE_SPACE_REG = "\\s+";

    /**
     * @param string
     * @return true if String is null or empty or contains space only
     */
    public static boolean isNullOrEmpty(String string) {
        if (string == null || string.equalsIgnoreCase("") || string.trim().equalsIgnoreCase("null"))
            return true;
        /*if(TextUtils.isEmpty(string) || string.trim().equalsIgnoreCase("null"))
        return  true;*/
        return false;
    }


    /**
     * @param string
     * @param regex
     * @return true if string is matched according to given regex
     */
    public static boolean isValidString(String string, String regex) {

        Pattern validRegexPattern = Pattern.compile(regex);
        return validRegexPattern.matcher(string).matches();
    }


    /**
     * @param string
     * @return string : first letter of return string is upper & rest of letters are would be in lower case.
     */
    public static String firstLetterToUpperCase(String string) {

        string = string == null ? "" : string;
        String output = "";
        for (int i = 0; i < string.length(); i++) {
            if (i == 0) {
                output += Character.toUpperCase(string.charAt(i));
            } else {
                output += Character.toLowerCase(string.charAt(i));
            }
        }
        return output;
    }

    /**
     * @param string
     * @param splitString
     * @return Array of string
     */
    public static String[] splitString(String string, String splitString) {

        String[] splitArray = {string};
        try {
            splitArray = string.split(splitString);
        } catch (PatternSyntaxException ex) {
            //
        }
        return splitArray;
    }

    /**
     * @param sentence : pass whole sentence as a parameter
     * @return string : convert first letter of all word of sentence is in upper case.
     */
    public static String allWordFirstLetterToUpperCase(String sentence) {

        String output = "";
        String word[] = splitString(sentence, WHITE_SPACE_REG);
        for (String str : word) {
            output = output + firstLetterToUpperCase(str) + " ";
        }

        return output.trim();
    }


    /**
     * @param composeUrl : compose sentense
     * @return string : convert first letter of all word of compose url is in upper case.
     */
    public static String getRefineHeader(String composeUrl) {

        String output = "";
        String word[] = splitString(composeUrl, "/");
        for (String str : word) {
            output = output + firstLetterToUpperCase(str) + " ";
        }

        return output.trim();
    }




    public static int[] getCardNumbers(String cardNumber) {
        int[] number = new int[cardNumber.length()];
        for (int i = 0; i < cardNumber.length(); i++) {
            number[i] = Integer.parseInt(cardNumber.charAt(i) + "");
        }
        return number;
    }

    public static String replaceSpaceWithHyphen(String searchKey) {
        if (searchKey.contains(" "))
            return searchKey.replaceAll(" ", "-");
        else
            return searchKey;
    }

}
