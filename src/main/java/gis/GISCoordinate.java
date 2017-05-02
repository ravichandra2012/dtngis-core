package gis;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;


/**
 * Class to represent Coordinates
 * Created by arka on 17/4/17.
 */
public class GISCoordinate {
    String coordinate;

    double x;
    double y;

    public GISCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setCoordinateEPSG3857(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordinateEPSG4326(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void convertEPSG3857toEPSG4326() throws FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:3857");
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, false);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(x, y));
        Point targetPoint = (Point) JTS.transform(point, transform);
    }
}
