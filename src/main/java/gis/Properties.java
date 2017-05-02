package gis;

/**
 * Class to represent properties of GIS feature
 * Created by arka on 17/4/17.
 */
public class Properties {
    private final int FID;
    private final String TEXT;

    public Properties(int FID, String TEXT) {
        this.FID = FID;
        this.TEXT = TEXT;
    }

    public int getFID() {
        return FID;
    }

    public String getTEXT() {
        return TEXT;
    }
}
