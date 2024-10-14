import java.util.*;

public class Main {
    public static void main(String[] args) {
        Filter.filterRGB("src/main/java/googlelogo_input.png", "src/main/java/googlelogo_output.png", 230, 240, 0, 100, 0, 100);
        Filter.meanFilteredPixels("src/main/java/googlelogo_input.png", "src/main/java/googlelogo_output.png", 230, 240, 0, 100, 0, 100);
        
    }
}