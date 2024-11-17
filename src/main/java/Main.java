import java.util.*;

public class Main {
    public static void main(String[] args) {
        Filter.filterRGB("src/main/java/googlelogo_input.png", "src/main/java/googlelogo_output.png", 230, 240, 0, 100, 0, 100);
        Filter.markMean("src/main/java/googlelogo_input.png", "src/main/java/googlelogo_output.png", 250, 255, 170, 190, 0, 10);
        // Step 3
        Filter.markMean("src/main/java/pink_input.png", "src/main/java/pink_output.png", 240, 255, 70, 80, 200, 210);
        // Step 4
        float[] out = Filter.angleFromCamera(61, 34, "src/main/java/pink_input.png", "src/main/java/pink_output.png", 240, 255, 70, 80, 200, 210);
        System.out.println(Arrays.toString(out));
    }
}