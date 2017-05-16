package gis;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import constants.Constants;
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
    String coordinateSystem;

    double x;
    double y;

    public GISCoordinate(double x, double y, String coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
        if (this.coordinateSystem.equals(Constants.COORDINATE_SYSTEM_EPSG3857)) {
            setCoordinateEPSG3857(x, y);
        } else if (this.coordinateSystem.equals(Constants.COORDINATE_SYSTEM_EPSG4326)) {
            setCoordinateEPSG4326(x, y);
        }
    }

    private void setCoordinateEPSG3857(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private void setCoordinateEPSG4326(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void convertEPSG3857toEPSG4326() throws FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.decode(Constants.COORDINATE_SYSTEM_EPSG3857);
        CoordinateReferenceSystem targetCRS = CRS.decode(Constants.COORDINATE_SYSTEM_EPSG4326);
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, false);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(x, y));
        Point targetPoint = (Point) JTS.transform(point, transform);
        setCoordinateEPSG4326(targetPoint.getX(), targetPoint.getY());
    }

    public void convertEPSG4326toEPSG3857() throws FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.decode(Constants.COORDINATE_SYSTEM_EPSG4326);
        CoordinateReferenceSystem targetCRS = CRS.decode(Constants.COORDINATE_SYSTEM_EPSG3857);
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, false);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(x, y));
        Point targetPoint = (Point) JTS.transform(point, transform);
        setCoordinateEPSG3857(targetPoint.getX(), targetPoint.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getCoordinateSystem() {
        return coordinateSystem;
    }
}
