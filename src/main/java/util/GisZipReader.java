package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class to extract information out of gis zip files
 * Created by arka on 17/4/17.
 */
public class GisZipReader {

    public static JsonObject getGeoJsonFromZip(File file) throws IOException {
        ZipFile gisZipFile = new ZipFile(file);
        String geoJsonName = null;
        boolean geoJsonFound = false;
        Enumeration zipEntries = gisZipFile.entries();
        while (zipEntries.hasMoreElements()) {
            if( (geoJsonName = ((ZipEntry)zipEntries.nextElement()).getName()).endsWith(".geojson") ) {
                geoJsonFound = true;
                break;
            }
        }

        if(!geoJsonFound) {
            return null;
        }

        ZipEntry gisGeoJson = gisZipFile.getEntry(geoJsonName);
        InputStream inputStream = gisZipFile.getInputStream(gisGeoJson);
        String inputStreamString = new Scanner(inputStream, "UTF-8").useDelimiter("\n").next();
        JsonParser jsonParser = new JsonParser();

        return jsonParser.parse(inputStreamString).getAsJsonObject();
    }
}
