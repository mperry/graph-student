package mperry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/*
 * @author Nishi Sherine
 * Date : 15-09-2013
 * 
 * This program tests RailRoute Class
 */

public class RailRouteTest {
	private RailRoute route;
	private RailRoute cyclicRoute;
	
	@Before
	public void setUp() throws RouteException {
		route = new RailRoute();
		route.addPath('A', 'B', 3);
		route.addPath('B', 'D', 4);
		route.addPath('D', 'C', 8);
		route.addPath('A', 'D', 5);
		route.addPath('C', 'D', 8);
		cyclicRoute = new RailRoute();
		cyclicRoute.addPath('A', 'B', 2);
		cyclicRoute.addPath('B', 'C', 3);
		cyclicRoute.addPath('C', 'D', 4);
		cyclicRoute.addPath('D', 'C', 4);
		cyclicRoute.addPath('D', 'A', 3);
		cyclicRoute.addPath('A', 'E', 2);
		cyclicRoute.addPath('E', 'D', 1);
		
	}
	
	@Test
	public void testAddEdge() throws RouteException {
		route.addPath('A','B', 5 );	
		
	}
	@Test(expected= RouteException.class)
	public void testNegativeAddEdge() throws RouteException {
		route.addPath('A','B', -5 );	// throws exception for negetive distance
	}
	

	@Test
	public void testCalculateRouteDistance() {
		assertEquals((int)route.calculateRouteDistance("A-B-D"),7);
		assertEquals((int)route.calculateRouteDistance("A-B-D-c"),15); // lower case changed to upper
		
	}
	@Test
	public void testCalculateRouteDistanceNoRoute() {
		assertEquals((int)route.calculateRouteDistance("A-C"),9999999); // No route exists: Infinit value
	}

	@Test
	public void testGetRoutes() {
		ArrayList<String> listRoutesGenerated=route.getRoutes('A','D');
		ArrayList<String> listRoutesCalculated= new ArrayList<String>();
		
		listRoutesCalculated.add("A-B-D-C-D-");
		listRoutesCalculated.add("A-B-D-");
		listRoutesCalculated.add("A-D-C-D-");
		listRoutesCalculated.add("A-D-");
		boolean sameList = listRoutesGenerated.equals(listRoutesCalculated);
		assertTrue(sameList);

	}

	@Test
	public void testCalculateFourStopRoutes() {
		int count = route.calculateFourStopRoutes('A', 'D');
		assertEquals(count,1);
	}

	@Test
	public void testCalculateMinimumDistance() {
		int dist = route.calculateMinimumDistance('A', 'D');
		assertEquals(dist,5);
	}

	@Test
	public void testCalculateMaxThreeStopRoutes() {
		int count = route.calculateMaxThreeStopRoutes('A', 'D');
		assertEquals(count,3);
	}

	@Test
	public void testCountMinDistanceRoutes() {
		int count = route.countMinDistanceRoutes('A', 'D', 10);
		assertEquals(count,2);
	}

	@Test
	public void testGetCycleRoutes() {
		ArrayList<String> listRoutesGenerated=cyclicRoute.getCycleRoutes('B');
		ArrayList<String> listRoutesCalculated= new ArrayList<String>();
		listRoutesCalculated.add("B-C-D-A-B-");
		boolean sameList = listRoutesGenerated.equals(listRoutesCalculated);
		assertTrue(sameList);
	}
	@Test
	public void testCountMinDistanceRoutesCycle() {
		int count = cyclicRoute.countMinDistanceRoutes('B', 'B', 15);
		assertEquals(count,1);
	}
	@Test
	public void testCalculateMaxThreeStopRoutesCycle() {
		int count = cyclicRoute.calculateMaxThreeStopRoutes('B', 'B');
		assertEquals(count,0);
	}
	@Test
	public void testCalculateFourStopRoutesCycle() {
		int count = cyclicRoute.calculateFourStopRoutes('B', 'B');
		assertEquals(count,1);
	}

	@Test
	public void testCalculateMinimumDistanceCycle() {
		int dist = cyclicRoute.calculateMinimumDistance('B', 'B');
		assertEquals(dist,12);
	}
	
}
