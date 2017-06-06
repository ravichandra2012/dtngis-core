package constants;

/**
 * Created by arka on 2/5/17.
 */
public class Constants {

    public static final String MSG_DEBUG = "DEBUG : ";

    public static final String GIS_GEOMETRY_TYPE_POLYGON = "Polygon";
    public static final String GIS_GEOMETRY_TYPE_LINESTRING = "Linestring";
    public static final String GIS_GEOMETRY_TYPE_POINT = "Point";

    public static final String COORDINATE_SYSTEM_EPSG3857 = "EPSG:3857";
    public static final String COORDINATE_SYSTEM_EPSG4326 = "EPSG:4326";

    public static final String GEOJSON_EPSG3857 = "{\"crs\":{\"type\":\"name\",\"properties\":{\"name\":\"urn:ogc:def:crs:EPSG::3857\"}},\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"FID\":123,\"TEXT\":\"yolo\",\"attaches\":[]},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[9858378,3509112],[9667768,2335497.5],[10419483,2280492],[9858378,3509112]]]}}]}";
    public static final String GEOJSON_EPSG4326 = "{\"crs\":{\"type\":\"name\",\"properties\":{\"name\":\"urn:ogc:def:crs:EPSG::4326\"}},\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"FID\":123,\"TEXT\":\"yolo\",\"attaches\":[]},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[9858378,3509112],[9667768,2335497.5],[10419483,2280492],[9858378,3509112]]]}}]}";
    public static final String GEOJSON_FEATURE = "{\"type\":\"Feature\",\"properties\":{\"FID\":123,\"TEXT\":\"yolo\",\"attaches\":[]},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[9858378,3509112],[9667768,2335497.5],[10419483,2280492],[9858378,3509112]]]}}";
}
