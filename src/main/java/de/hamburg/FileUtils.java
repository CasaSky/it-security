package de.hamburg;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class FileUtils {

    public static void savePasswordToFile(File file, String username, String password, Boolean isOTP) {

        String data = generateJson(username, password, isOTP);

        FileWriter fooWriter = null; // true to append
        try {
            fooWriter = new FileWriter(file, true);
            // false to overwrite.
            fooWriter.append(data);
            fooWriter.append(System.getProperty("line.separator"));
            fooWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readPasswordFromFile(File file, String username, String password) {

        FileReader fooReader = null; // true to append
        try {
            fooReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fooReader);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                try {
                    JSONObject json = new JSONObject(sCurrentLine);
                    json.get("username");

                    //TODO
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String generateJson(String username, String password, Boolean isOTP) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("isOtp", isOTP);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        return json.toString();
    }
}
