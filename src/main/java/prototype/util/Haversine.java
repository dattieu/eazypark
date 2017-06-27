package prototype.util;

public final class Haversine {
	
    private static final double EARTH_RADIUS = 6372.8;

    public static double getDistance(double startLat, double startLong, double endLat, double endLong) {

        double deltaLatInRad  = Math.toRadians(endLat - startLat);
        double deltaLongInRad = Math.toRadians(endLong - startLong);

        double startLatInRad = Math.toRadians(startLat);
        double endLatRadInRad   = Math.toRadians(endLat);

        double a = Math.pow(Math.sin(deltaLatInRad / 2), 2) + 
        			Math.pow(Math.sin(deltaLongInRad / 2), 2) * Math.cos(startLatInRad) * Math.cos(endLatRadInRad);
        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS * c;
    }

}
