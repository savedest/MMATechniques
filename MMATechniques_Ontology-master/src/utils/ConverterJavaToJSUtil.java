package utils;

import java.util.ArrayList;

public class ConverterJavaToJSUtil {
    public static String getArrayString(ArrayList items){
        String result = "[";
        for(int i = 0; i < items.size(); i++) {
            result += "\"" + items.get(i) + "\"";
            if(i < items.size() - 1) {
                result += ", ";
            }
        }
        result += "]";

        return result;
    }

    public static String getArrayInt(Integer[] items) {
        String result = "[";
        for(int i = 0; i < items.length; i++) {
            result += items[i];
            if(i < items.length - 1) {
                result += ", ";
            }
        }
        result += "]";

        return result;
    }
    
    public static String getArrayString(String[] items) {
        String result = "[";
        for(int i = 0; i < items.length; i++) {
            result += items[i];
            if(i < items.length - 1) {
                result += ", ";
            }
        }
        result += "]";

        return result;
    }
}
