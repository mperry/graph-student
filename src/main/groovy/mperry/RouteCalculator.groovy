package mperry

import groovy.transform.TypeChecked;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/*
 * @author Nishi Sherine
 * Date : 15-09-2013
 * Perform all the route calculations
 */
public class RouteCalculator {


	List<NodeString> toPath(String s) {
		s.inject([]){ acc, it ->
			acc + new NodeString(it)
		}

	}



	@TypeChecked
	public static void main(String[] args) throws IOException, RouteException {
		new RouteCalculator().calculate()
	}

	void calculate() {
        def sampleRoute = new File("src/route.txt").text
		def routeList = Arrays.asList(sampleRoute.split("\\s*,\\s*"));
		def paths = routeList.collect { String it ->
			new PathString(it).toPath()
		}
		def rm = RouteMatrix.fromList(paths)

		def r1 = ["A", "B", "C"]
		def r2 = ["ABC", "AD", "ADC", "AEBCD", "AC"]
//		def d1 = rm.calculateRouteDistance(r1.collect { String it -> new NodeString(it)});
//		def d1 = rm.calculateRouteDistance(toPath("ABC"))
		r2.each { String s ->
			def d = rm.calculateRouteDistance(toPath(s))
			println("route: $s, distance: $d")
		}


//		currentRoutes.displayDistance (" 1. A-B-C Distance: ",routeDistance);
//		routeDistance = currentRoutes.calculateRouteDistance("A-D");
//		currentRoutes.displayDistance (" 2. A-D Distance: ",routeDistance);
//		routeDistance = currentRoutes.calculateRouteDistance("A-D-C");
//		currentRoutes.displayDistance (" 3. A-D-C Distance: ",routeDistance);
//		routeDistance = currentRoutes.calculateRouteDistance("A-E-B-C-D");
//		currentRoutes.displayDistance (" 4. A-E-B-C-D Distance: ",routeDistance);
//		routeDistance = currentRoutes.calculateRouteDistance("A-E-D");
//		currentRoutes.displayDistance (" 5. A-E-D Distance: ",routeDistance);
//		int count=currentRoutes.calculateMaxThreeStopRoutes('C','C');
//		System.out.println(" 6. C-C with Maximum 3 stops : " + count);
//		count = currentRoutes.calculateFourStopRoutes('A','C');
//		System.out.println(" 7. A-C with Exact 4 stops : " + count);
//		int minDistance=currentRoutes.calculateMinimumDistance('A','C');
//		System.out.println(" 8. A-C calculated shortest distance : " + minDistance);
//		minDistance = currentRoutes.calculateMinimumDistance('B','B');
//		System.out.println(" 9. B-B calculated shortest distance : " + minDistance);
//		count = currentRoutes.countMinDistanceRoutes('C','C', 30);
//		System.out.println("10. C-C routes with distance less than 30 : "+count);

	}

}

