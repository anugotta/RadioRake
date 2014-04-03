package com.asp.radiorake.filehandling;

import android.util.Log;

import com.asp.radiorake.RadioDetails;
import com.asp.radiorake.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PlsHandler extends FileHandler {
    private static final String PLSTAG = "com.asp.radiorake.filehandling.PlsHandler";

    public static RadioDetails parse(RadioDetails radioDetails, String basePath) {

        String plsFile = getFile(radioDetails.getPlaylistUrl(), basePath);

        try {

            FileReader fileReader = new FileReader(plsFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.toLowerCase().contains("file1")) {
                    radioDetails.setStreamUrl(line.substring(line.indexOf("=") + 1));
                }
                if (line.toLowerCase().contains("title1") && StringUtils.IsNullOrEmpty(radioDetails.getStationName())) {
                    radioDetails.setStationName(line.substring(line.indexOf("=") + 1));
                }
            }
            bufferedReader.close();
            fileReader.close();

        } catch (FileNotFoundException e) {
            Log.e(PLSTAG, plsFile + " cannot be found", e);
        } catch (IOException e) {
            Log.e(PLSTAG, plsFile + " cannot be read", e);
        } finally {
            new File(plsFile).delete();
        }

        return radioDetails;
    }
}
