/**
 * 
 */
package trainTracks;

/**
 * @author Nishi Sherine
 * Date : 15-09-2013
 *
 * Route interface having operations and constants to be used for the implementation of RailRoutes
 */
public interface Route {
	public static final int INFINITY = 9999999; 
	public static final int TOWNS = 5; 
	
	public void addPath(char source, char destination , int distance) throws RouteException;
	public int calculateRouteDistance(String route);
	public int calculateMaxThreeStopRoutes(char start, char end);
	public int calculateFourStopRoutes (char start, char end);
	public int calculateMinimumDistance  (char start, char end);
	public int countMinDistanceRoutes (char start , char end ,int distance);

}
