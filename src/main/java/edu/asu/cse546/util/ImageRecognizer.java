package edu.asu.cse546.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImageRecognizer {

    // Recognize image by its url
    public String getImageRecognitionResult(String url) {
        try {
            // Run the commands
            Runtime rt = Runtime.getRuntime();
            String[] sourceCmd = {
                    "/bin/bash",
                    "-c",
                    "cd /home/ubuntu/tensorflow/bin/ && source activate && python /home/ubuntu/tensorflow/models/tutorials/image/imagenet/classify_image.py --image_file " + url + " --num_top_predictions 1"
            };
            Process proc = rt.exec(sourceCmd);

            // Read the output from the command to get the recognition of the image
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                sb.append(s);
            }

            return sb.toString().split("\\(")[0].trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        ImageRecognizer ip = new ImageRecognizer();
        String result = ip.getImageRecognitionResult("https://www.pets4homes.co.uk/images/articles/771/large/cat-lifespan-the-life-expectancy-of-cats-568e40723c336.jpg");
        System.out.println(result);
    }
}