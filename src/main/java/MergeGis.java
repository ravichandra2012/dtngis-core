import com.google.gson.JsonObject;
import constants.Paths;
import util.GisFilesFinder;
import util.GisZipReader;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Main class to merge Gis conflicting/redundant data
 * Created by arka on 17/4/17.
 */
public class MergeGis {

    public static void main(String args[])throws IOException {
        List<File> gisFiles = new GisFilesFinder(Paths.WORKING_DIR_TEMP).getGisFiles();
        for(File file:gisFiles) {
            System.out.println(file.getName());

            JsonObject geoJson = GisZipReader.getGeoJsonFromZip(file);
            System.out.println(geoJson.get("features"));
        }
    }
}
