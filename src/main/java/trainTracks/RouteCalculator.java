package trainTracks;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import trainTracks.RouteException;

/*
 * @author Nishi Sherine
 * Date : 15-09-2013
 * Perform all the route calculations
 */
public class RouteCalculator {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws RouteException 
	 *  
	 * 
	 */
	public static void main(String[] args) throws IOException, RouteException {
		
		//String sampleRoute = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		String sampleRoute = RailRoute.readFile("src/route.txt", Charset.defaultCharset());
		List<String> routeList = Arrays.asList(sampleRoute.split("\\s*,\\s*"));
		int routeDistance;
		RailRoute currentRoutes = new RailRoute();
		
		// update the adjacency matrix with the distance between each pair of towns
		for (String item : routeList)
		{
	    	currentRoutes.addPath(item.charAt(0), item.charAt(1), Integer.parseInt(item.substring(2)));			
		}
		routeDistance = currentRoutes.calculateRouteDistance("A-B-C");
		currentRoutes.DisplayDistance (" 1. A-B-C Distance: ",routeDistance);
		routeDistance = currentRoutes.calculateRouteDistance("A-D");
		currentRoutes.DisplayDistance (" 2. A-D Distance: ",routeDistance);
		routeDistance = currentRoutes.calculateRouteDistance("A-D-C");
		currentRoutes.DisplayDistance (" 3. A-D-C Distance: ",routeDistance);
		routeDistance = currentRoutes.calculateRouteDistance("A-E-B-C-D");
		currentRoutes.DisplayDistance (" 4. A-E-B-C-D Distance: ",routeDistance);
		routeDistance = currentRoutes.calculateRouteDistance("A-E-D");
		currentRoutes.DisplayDistance (" 5. A-E-D Distance: ",routeDistance);
		int count=currentRoutes.calculateMaxThreeStopRoutes('C','C');
		System.out.println(" 6. C-C with Maximum 3 stops : " + count);	
		count = currentRoutes.calculateFourStopRoutes('A','C');
		System.out.println(" 7. A-C with Exact 4 stops : " + count);
		int minDistance=currentRoutes.calculateMinimumDistance('A','C');
		System.out.println(" 8. A-C calculated shortest distance : " + minDistance);
		minDistance = currentRoutes.calculateMinimumDistance('B','B');
		System.out.println(" 9. B-B calculated shortest distance : " + minDistance);
		count = currentRoutes.countMinDistanceRoutes('C','C', 30);
		System.out.println("10. C-C routes with distance less than 30 : "+count);

	}

}

