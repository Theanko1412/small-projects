import java.util.*;

public class Main {

    public static void main(String[] args) {

        char[] letters = args[0].toLowerCase().toCharArray();
        //using TreeMap so out values are sorted by key
        TreeMap<Character, Integer> letterCount = new TreeMap<>();

        for (char character : letters) {
            if(!letterCount.containsKey(character)) {
                letterCount.put(character, 1);
            } else {
                letterCount.put(character, letterCount.get(character)+1);
            }
        }

        for (Map.Entry<Character, Integer> entry : letterCount.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
