package it.unina.dietideals24.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyFileUtils {
    private MyFileUtils() {}

    public static File uriToFile(Uri uri, Context context) {
        InputStream inputStream = null;
        File tempFile = null;

        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            tempFile = File.createTempFile("temp", ".jpg");

            if (inputStream != null) {
                try (FileOutputStream fileOut = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOut.write(buffer, 0, bytesRead);
                    }
                }
            }

            tempFile.deleteOnExit();
            if (inputStream != null) {
                inputStream.close();
            }

            return tempFile;
        } catch (IOException e) {
            if (tempFile != null) {
                tempFile.delete(); // Elimina il file temporaneo in caso di errore
            }
            return null;
        }
    }
}
