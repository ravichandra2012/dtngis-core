import constants.Paths;
import gis.FeatureCollector;
import gis.GisFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import util.GisFilesFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Main class to merge Gis conflicting/redundant data
 * Created by arka on 17/4/17.
 */
public class MergeGis {

    public static void main(String args[]) throws IOException, FactoryException, TransformException {
        List<File> gisFiles = new GisFilesFinder(Paths.WORKING_DIR_TEMP).getGisFiles();

        FeatureCollector featureCollector = new FeatureCollector(gisFiles);
        List<GisFeature> gisFeatures = featureCollector.getAllGisFeature();
        featureCollector.displayAllFeatures(gisFeatures);

        mergeGisFeatures(gisFeatures);
    }

    private static void mergeGisFeatures(List<GisFeature> gisFeatures) {
        List<List<GisFeature>> mergeList = new ArrayList<List<GisFeature>>();

        matchFeatures(mergeList, gisFeatures);
        
    }

    /**
     * Find out which features represent the same data in out gisFeatures list
     * @param mergeList
     * @param gisFeatures
     */
    private static void matchFeatures(List<List<GisFeature>> mergeList, List<GisFeature> gisFeatures) {
        matchMetaData(mergeList, gisFeatures);
        matchGeometries(mergeList, gisFeatures);
    }

    private static void matchGeometries(List<List<GisFeature>> mergeList, List<GisFeature> gisFeatures) {
    }

    /**
     * Group features which represent the same data on basis of their meta data 
     * @param mergeList
     * @param gisFeatures
     */
    private static void matchMetaData(List<List<GisFeature>> mergeList, List<GisFeature> gisFeatures) {
        boolean[] isGrouped = new boolean[gisFeatures.size()];
        for(int i = 0;i < gisFeatures.size(); i++) {
            if(isGrouped[i]) {
                continue;
            }

            List<GisFeature> group = null;
            for(int j =  i + 1; j < gisFeatures.size(); j++) {
                if(metaDataCheck(gisFeatures.get(i), gisFeatures.get(j))) {
                    // add to list
                    if(group == null) {
                        group = new ArrayList<GisFeature>();
                    }

                    group.add(gisFeatures.get(j));
                    isGrouped[j] = true;
                }
            }
            if(group != null) {
                group.add(gisFeatures.get(i));
                isGrouped[i] = true;
            }
        }
    }

    /**
     * Check if meta data of two feature suggest that they represent the same data
     * @param feature1
     * @param feature2
     * @return true if both features represent same data, false otherwise
     */
    private static boolean metaDataCheck(GisFeature feature1, GisFeature feature2) {
        // check for properties of features
        if(!feature1.getProperties().getTEXT().equals(feature2.getProperties().getTEXT())) {
            return false;
        }
        if(feature1.getProperties().getFID() != feature2.getProperties().getFID()) {
            return false;
        }

        // check for geometry types of features
        if(!feature1.getGeometryType().equals(feature2.getGeometryType())) {
            return false;
        }
        return true;
    }
}
