package gis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.deploy.util.SyncAccess;
import constants.Constants;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import util.GisZipReader;
import util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects all features from a list of GIS files
 * Created by arka on 2/5/17.
 */
public class FeatureCollector {
    private final String TAG = "FeatureCollector ";
    private List<File> gisFiles;

    public FeatureCollector(List<File> gisFiles) {
        this.gisFiles = gisFiles;
    }

    public List<GisFeature> getAllGisFeature() throws IOException, FactoryException, TransformException {
        List<GisFeature> gisFeatures = new ArrayList<GisFeature>();

        for(File file:gisFiles) {
            String fileName = file.getName().substring(0, file.getName().indexOf('.'));

            String source = getSourceFromFileName(fileName);
            String timestamp = getTimestampFromFileName(fileName);
            String destination = getDestinationFromFileName(fileName);
            String createLat = getLatFromFileName(fileName);
            String createLon = getLonFromFileName(fileName);

            JsonObject geoJson = GisZipReader.getGeoJsonFromZip(file);
            JsonArray features = geoJson.getAsJsonArray("features");

            for(JsonElement feature:features) {

                if(!feature.getAsJsonObject().get("type").getAsString().equals("Feature")) {
                    continue;
                }

                // get the geometry
                JsonObject geometry = feature.getAsJsonObject().get("geometry").getAsJsonObject();
                String geometryType = geometry.get("type").getAsString();

                // get the coordinates
                String coordinates = geometry.get("coordinates").toString();
                String[] c = coordinates.split(",");
                for(int i = 0; i < c.length; ++i) {
                    c[i] = Util.trimString(c[i], '[');
                    c[i] = Util.trimString(c[i], ']');
                }

                List<GISCoordinate> list = new ArrayList<GISCoordinate>();

                for(int i=0; i < c.length; i += 2) {
                    System.out.print(Constants.MSG_DEBUG + TAG + c[i] + "," + c[i+1] + " ===> ");
                    GISCoordinate coordinate = new GISCoordinate(Double.parseDouble(c[i]), Double.parseDouble(c[i + 1]),
                            "EPSG:3857");
                    coordinate.convertEPSG3857toEPSG4326();
                    System.out.println(coordinate.getX() + "," + coordinate.getY());
                    list.add(coordinate);
                }

                // get the properties
                JsonObject property = feature.getAsJsonObject().get("properties").getAsJsonObject();
                int fid = property.get("FID").getAsInt();
                String text = property.get("TEXT").getAsString();
                Properties properties = new Properties(fid, text);

                GisFeature gisFeature = new GisFeature(source, timestamp, destination, createLat, createLon,
                        geometryType, list, properties);
                gisFeatures.add(gisFeature);
            }
        }

        return gisFeatures;
    }

    private String getLonFromFileName(String fileName) {
        try {
            return fileName.split("_")[6];
        } catch (Exception e) {
            System.out.println(Constants.MSG_DEBUG + TAG + "Longitude not in Filename " + fileName);
            return null;
        }
    }

    private String getLatFromFileName(String fileName) {
        try {
            return fileName.split("_")[5];
        } catch (Exception e) {
            System.out.println(Constants.MSG_DEBUG + TAG + "Latitude not in Filename " + fileName);
            return null;
        }
    }

    private String getDestinationFromFileName(String fileName) {
        try {
            return fileName.split("_")[4];
        } catch (Exception e) {
            System.out.println(Constants.MSG_DEBUG + TAG + "Destination not in Filename " + fileName);
            return null;
        }
    }

    private String getTimestampFromFileName(String fileName) {
        try {
            return fileName.split("_")[7];
        } catch (Exception e) {
            System.out.println(Constants.MSG_DEBUG + TAG + "Timestamp not in Filename " + fileName);
            return null;
        }
    }

    /**
     * Collect source of GIS file from file name
     * @param fileName
     * @return the source phone number
     */
    private String getSourceFromFileName(String fileName) {
        try {
            return fileName.split("_")[3];
        } catch (Exception e) {
            System.out.println(Constants.MSG_DEBUG + TAG + "Source not in Filename " + fileName);
            return null;
        }
    }

    public void displayAllFeatures(List<GisFeature> gisFeatures) {
        for(GisFeature feature:gisFeatures) {
            System.out.println(Constants.MSG_DEBUG + TAG + feature.getSource() + "|" + feature.getTimestamp() + "|" +
                    feature.getDestination() + "|" + feature.getCreateLat() + "|" + feature.getCreateLon() + "|" +
                    feature.getGeometryType() + "|" + feature.getProperties().getFID() + "|" + feature.getProperties().getTEXT());
            System.out.print(Constants.MSG_DEBUG + TAG + "Coordinates : ");
            for (GISCoordinate coordinate:feature.getCoordinates()) {
                System.out.print(coordinate.getX() + "," + coordinate.getY() + " ; ");
            }
            System.out.println();
        }
    }
}
