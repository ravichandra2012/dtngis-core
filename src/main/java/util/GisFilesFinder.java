package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Find all Gis files (zip/geojson) format
 * Created by arka on 17/4/17.
 */
public class GisFilesFinder {
    private String path;
    private List<File> files;

    public GisFilesFinder(String path) {
        this.path = path;
        files = new ArrayList<File>();
    }

    /**
     * Return a list of all Gis files in directory
     * @return a list of all Gis files in directory
     */
    public List<File> getGisFiles() {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for(File file:listOfFiles) {
            if(file.isFile() && file.getName().startsWith("GIS_")) {
                files.add(file);
            }
        }
        return files;
    }
}
