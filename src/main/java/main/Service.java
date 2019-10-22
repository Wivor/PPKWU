package main;

import java.util.LinkedHashMap;
import java.util.Map;

class Service {
    String reverse(String string){
        return new StringBuilder(string).reverse().toString();
    }

    Map stringInfo(String string){
        LinkedHashMap<String, Integer> infoMap = new LinkedHashMap<>();
        int upperCase = 0;
        int lowerCase= 0;
        int numbers = 0;
        int specials = 0;


        for (char ch: string.toCharArray()) {
            if (Character.isUpperCase(ch))
                upperCase += 1;
            else if (Character.isLowerCase(ch))
                lowerCase += 1;
            else if (Character.isDigit(ch))
                numbers += 1;
            else
                specials += 1;
        }

        infoMap.put("Upper Case Letters", upperCase);
        infoMap.put("Lower Case Letters", lowerCase);
        infoMap.put("Numbers", numbers);
        infoMap.put("Special Signs", specials);

        return infoMap;
    }
}
