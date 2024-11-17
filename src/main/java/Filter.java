
import java.util.*;
import java.awt.image.BufferedImage; // subclass of Image describing images
import java.awt.Color;
import javax.imageio.ImageIO; // provides simple encoding and decoding for images
import java.io.File;
import java.io.IOException;

public class Filter {
    public static void filterRGB(String inputImage, String outputImage, int rMin, int rMax, int gMin, int gMax, int bMin, int bMax) {
        int w, h;
        Color minColor = new Color(rMin, gMin, bMin), maxColor = new Color(rMax, gMax, bMax);
        BufferedImage image, newImage;
        try {
            image = ImageIO.read(new File(inputImage));
            newImage = ImageIO.read(new File(outputImage));
            w = image.getWidth(); h = image.getHeight();
            long numOfPixels = w * h;
            if(numOfPixels != 0) {
                for(int i = 0; i < numOfPixels; i++) {
                    // for pixel x=i%w, y=Math.floor(i/w)
                    if(image.getRGB(i%w, (int)Math.floor(i/w)) > minColor.getRGB() && image.getRGB(i%w, (int)Math.floor(i/w)) < maxColor.getRGB()) {
                        newImage.setRGB(i%w, (int)Math.floor(i/w), image.getRGB(i%w, (int)Math.floor(i/w)));
                    } else {
                        Color black = new Color(0, 0, 0);
                        newImage.setRGB(i%w, (int)Math.floor(i/w), black.getRGB());
                    }
                    //System.out.println(image.getRGB(i%w, (int)Math.floor(i/w)));
                }
                ImageIO.write(newImage, "PNG", new File(outputImage));
            }
        } catch (IOException e) {
            System.out.println("Error retrieving file");
            return;
        }
        return;
        
    }
    public static int[] findMeanOfPixels(String inputImage, int rMin, int rMax, int gMin, int gMax, int bMin, int bMax) {
        int w, h;
        Color minColor = new Color(rMin, gMin, bMin), maxColor = new Color(rMax, gMax, bMax);
        BufferedImage image;
        int meanX = 0, meanY = 0;
        try {
            image = ImageIO.read(new File(inputImage));
            w = image.getWidth(); h = image.getHeight();
            long numOfPixels = w * h;
            int xCoordSum = 0;
            int yCoordSum = 0;
            int numOfPassedPixels = 0;
            if(numOfPixels != 0) {
                for(int i = 0; i < numOfPixels; i++) {
                    // for pixel x=i%w, y=Math.floor(i/w)
                    if(image.getRGB(i%w, (int)Math.floor(i/w)) > minColor.getRGB() && image.getRGB(i%w, (int)Math.floor(i/w)) < maxColor.getRGB()) {
                        //newImage.setRGB(i%w, (int)Math.floor(i/w), image.getRGB(i%w, (int)Math.floor(i/w)));
                        xCoordSum += i%w;
                        yCoordSum += (int)Math.floor(i/w);
                        numOfPassedPixels++;
                    } else {
                        //Color black = new Color(0, 0, 0);
                        //newImage.setRGB(i%w, (int)Math.floor(i/w), black.getRGB());
                    }
                }
                if(numOfPassedPixels > 0) {
                    meanX = (int)Math.round(xCoordSum/numOfPassedPixels);
                    meanY = (int)Math.round(yCoordSum/numOfPassedPixels);
                }
            }
        } catch (IOException e) {
            System.out.println("Error retrieving file");
        }
        int[] ans = {meanX, meanY};
        return ans;
    }
    public static void drawCross(String image, int pixelX, int pixelY) {
        int h, w, numOfPixels;
        Color cross = new Color(138, 224, 235);
        try {
            BufferedImage newImage = ImageIO.read(new File(image));
            h = newImage.getHeight(); w = newImage.getWidth(); numOfPixels = w*h;

            newImage.setRGB(pixelX, pixelY, cross.getRGB());
            newImage.setRGB(pixelX, pixelY>0?pixelY-1:pixelY, cross.getRGB());
            newImage.setRGB(pixelX, pixelY<numOfPixels-w?pixelY+1:pixelY, cross.getRGB());
            newImage.setRGB(pixelX>0?pixelX-1:pixelX, pixelY, cross.getRGB());
            newImage.setRGB(pixelX<numOfPixels?pixelX+1:pixelX, pixelY, cross.getRGB());
            ImageIO.write(newImage, "PNG", new File(image));
        } catch (IOException e) {
            System.out.println("Error Retrieving File");
            return;
        }
        return;
    }
    public static void markMean(String inputImage, String outputImage, int rMin, int rMax, int gMin, int gMax, int bMin, int bMax) {
        int[] means = findMeanOfPixels(inputImage, rMin, rMax, gMin, gMax, bMin, bMax);
        drawCross(outputImage, means[0], means[1]);
    }
    public static float[] simpleFOVAngleCalculator(int xFOV, int yFOV, int meanX, int meanY, int w, int h) {
        float xAngle, yAngle;
        xAngle = meanX*((float)xFOV/w) - xFOV/2;
        yAngle = meanY*((float)yFOV/h) - yFOV/2;
        float[] ans = {xAngle, yAngle};
        return ans;
    }
    public static float[] angleFromCamera(int xFOV, int yFOV, String inputImage, String outputImage, int rMin, int rMax, int gMin, int gMax, int bMin, int bMax) {
        int w = 0, h = 0;
        try{
            w = ImageIO.read(new File(inputImage)).getWidth(); h = ImageIO.read(new File(inputImage)).getHeight();
        } catch (IOException e) {}
        int[] means = findMeanOfPixels(inputImage, rMin, rMax, gMin, gMax, bMin, bMax);
        return simpleFOVAngleCalculator(xFOV, yFOV, means[0], means[1], w, h);
    }
}
