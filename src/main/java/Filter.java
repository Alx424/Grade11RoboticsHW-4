
import java.util.*;
import java.awt.image.BufferedImage; // subclass of Image describing images
import java.awt.Color;
import javax.imageio.ImageIO; // provides simple encoding and decoding for images
import java.io.File;
import java.io.IOException;

public class Filter {
    public static void filterRGB(String inputImage, String outputImage, int Rmin, int Rmax, int Gmin, int Gmax, int Bmin, int Bmax) {
        int w, h;
        Color minColor = new Color(Rmin, Gmin, Bmin), maxColor = new Color(Rmax, Gmax, Bmax);
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
    public static void meanFilteredPixels(String inputImage, String outputImage, int Rmin, int Rmax, int Gmin, int Gmax, int Bmin, int Bmax) {
        int w, h;
        Color minColor = new Color(Rmin, Gmin, Bmin), maxColor = new Color(Rmax, Gmax, Bmax);
        BufferedImage image, newImage;
        try {
            image = ImageIO.read(new File(inputImage));
            newImage = ImageIO.read(new File(outputImage));
            w = image.getWidth(); h = image.getHeight();
            long numOfPixels = w * h;
            int xCoordSum = 0;
            int yCoordSum = 0;
            int numOfPassedPixels = 0;
            if(numOfPixels != 0) {
                for(int i = 0; i < numOfPixels; i++) {
                    // for pixel x=i%w, y=Math.floor(i/w)
                    if(image.getRGB(i%w, (int)Math.floor(i/w)) > minColor.getRGB() && image.getRGB(i%w, (int)Math.floor(i/w)) < maxColor.getRGB()) {
                        newImage.setRGB(i%w, (int)Math.floor(i/w), image.getRGB(i%w, (int)Math.floor(i/w)));
                        xCoordSum += i%w;
                        yCoordSum += (int)Math.floor(i/w);
                        numOfPassedPixels++;
                    } else {
                        Color black = new Color(0, 0, 0);
                        newImage.setRGB(i%w, (int)Math.floor(i/w), black.getRGB());
                    }
                }
                int meanX = Math.round(xCoordSum/numOfPassedPixels);
                int meanY = Math.round(yCoordSum/numOfPassedPixels);

                // creating cross marking mean pixel
                newImage.setRGB(meanX, meanY, 0);
                newImage.setRGB(meanX, meanY>0?meanY-1:meanY, 0);
                newImage.setRGB(meanX, meanY<numOfPixels-w?meanY+1:meanY, 0);
                newImage.setRGB(meanX>0?meanX-1:meanX, meanY, 0);
                newImage.setRGB(meanX<numOfPixels?meanX+1:meanX, meanY, 0);

                ImageIO.write(newImage, "PNG", new File(outputImage));
            }
        } catch (IOException e) {
            System.out.println("Error retrieving file");
            return;
        }
        return;
    }
}
