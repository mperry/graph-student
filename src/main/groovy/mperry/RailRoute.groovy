
package mperry;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Nishi Sherine
 * Date : 15-09-2013
 * 
 * RailRoute implements all the required operations to perform route calculations
 * @throws RouteException if the distance between 2 nodes is negative
 */
public class RailRoute implements Route {

	private int[][] routeMatrix;
	private ArrayList<String> allRoutesStartEnd = new  ArrayList<String>();

	/*
	 * Constructor creates the adjacency matrix
	 */
	public RailRoute()  {
		routeMatrix= new int[TOWNS][TOWNS];
		// initialise to infinity
	    for (int i = 0; i < TOWNS; i++) {
	        for (int j = 0; j < TOWNS; j++) {
	            routeMatrix[i][j] = INFINITY;
	        }
	    }
	}

	/*
	 * Add the existing paths to the matrix
	 */
	@Override
	public void addPath(char source, char destination, int distance) throws RouteException {
		int src =  ConvertToIndex (Character.toUpperCase(source));
		int dst = ConvertToIndex (Character.toUpperCase(destination));
		routeMatrix[src][dst] = distance;
		if(distance < 0) {
			throw new RouteException("Distance is Negetive");
		 }
	}
	
	/*
	 * Converts the town name to corresponding integer to refer the matrix
	 */
	private int ConvertToIndex ( char town)	{	
		return ((int)(Character.toUpperCase(town)) - 65);
	}
	
	/*
	 * Calculate the distance of a given route ( Eg: A-B-C)
	 * 
	 */
	@Override
	public int calculateRouteDistance(String route)  {
		int totalDistance = 0;
		String[] town = route.split("-");
		for( int i=0;i<= town.length-2;i++)
		{
			int start= ConvertToIndex(town[i].charAt(0));
			int end = ConvertToIndex(town[i+1].charAt(0));
			totalDistance += routeMatrix[start][end];
		}
		return totalDistance;
	}
	
	/*
	 * Display the distance according to the value
	 */
	public void DisplayDistance (String id ,int totalDistance) {
		
		if (totalDistance < INFINITY)
		{
			System.out.println(id + totalDistance);
		}
		else
		{
			System.out.println( id + "NO SUCH ROUTE");
		}
	}
	
	/*
	 * Get all the possible routes between 2 different towns
	 */
	public ArrayList<String> getRoutes (char start , char end) {
		ArrayList<Integer> adjecentTowns = new ArrayList<Integer>();
		int origin = ConvertToIndex (start);
		adjecentTowns.add(origin);
		ArrayList<String> allRoutes= new ArrayList<String>();
		allRoutes=possibleRoutes(end,adjecentTowns);
		return allRoutes;
	}
	
	/*
	 * Calculate all the possible routes
	 */
	private ArrayList<String> possibleRoutes (char end ,ArrayList<Integer>  adjecentTowns) {
		
		ArrayList<String> allPaths = new ArrayList<String>();
		int origin = adjecentTowns.get(adjecentTowns.size()-1);
		int dst = ConvertToIndex (end);
		boolean cheked = false;
		for (int i=0; i<TOWNS; i++) {
			if (routeMatrix[origin][i] < INFINITY) {
				cheked = true;
				 for (int j = 0; j < adjecentTowns.size(); j++) {  
		                if (i == adjecentTowns.get(j))
		                	cheked = false;
		            }
				 if (!cheked)  
		                continue;
				 
				 if (i == dst) { 
					 adjecentTowns.add(dst);
					 String s="";
					 for (int item:adjecentTowns) {
						
						 char city =  (char) (item+65);
		                   s+=city+"-";
		                }
					 
					 ArrayList<String> bidirectionList = GetBidirectionalEdges();
					 for (String item: bidirectionList){
						 if (item.charAt(2) == end) {
							 String addString = s + item;
							 allPaths.add(addString);
							 
						 }
							 
					    }
					 allPaths.add(s);
					 adjecentTowns.remove(adjecentTowns.size()-1);
					 break;
				 }
			}
			
		}
		
		for (int i=0; i<TOWNS; i++) {
			if (routeMatrix[origin][i] < INFINITY) {
				
				cheked = true;
				for (int j = 0; j < adjecentTowns.size(); j++) {  
	                if (i == adjecentTowns.get(j))
	                	cheked = false;
	            }
				 if (!cheked || i == dst) 
		                continue;
				
				 adjecentTowns.add(i);
	            
	            //recursive call
				 possibleRoutes (end ,adjecentTowns);
				 adjecentTowns.remove(adjecentTowns.size()-1);
	        }
	    }
		for (String route: allPaths){	
			allRoutesStartEnd.add(route);		
	    }
		return allRoutesStartEnd;
	}
	
	/*
	 * Get all the bidirectional edges in the graph
	 */
	private ArrayList<String> GetBidirectionalEdges() {
		
		ArrayList<String> bidirectionList = new ArrayList<String>();
	    for (int i = 0; i < TOWNS; i++) {
	        for (int j = 0; j < TOWNS; j++) {
	            if ( routeMatrix[i][j] == routeMatrix[j][i] && routeMatrix[i][j] != INFINITY) {
	            	String str = (char)(i+65) + "-"+(char)(j+65)+"-";
	            	bidirectionList.add(str);
	            }
	        }
	    }

		return bidirectionList;
	}
	
	/*
	 * Calculate the shortest path distance of all the available paths between two towns or cyclic path ( B-B-)
	 */
	@Override
	public int calculateMinimumDistance  (char start, char end) {
		int minDistance = INFINITY;
		ArrayList<String> routesStartEnd;
		if (start == end) {
			routesStartEnd = getCycleRoutes(start);
		}
		else {
			routesStartEnd = getRoutes(start,end);
		}
		for (String route: routesStartEnd) {
			if (calculateRouteDistance(route) > 0 && calculateRouteDistance(route) < minDistance){
				minDistance = calculateRouteDistance(route);
			}
		}
		
		//System.out.println("    "+start+"-"+end+" calculated shortest distance : " + minDistance);
		allRoutesStartEnd.clear();
		return minDistance;
	}
	
	/*
	 * Calculate trips with maximum 3 stops between 2 different towns or cyclic path ( C-C-)
	 */
	@Override
	public int calculateMaxThreeStopRoutes(char start, char end) {
		ArrayList<String> routesStartEnd;
		if (start == end) {
			routesStartEnd = getCycleRoutes(start);
		}
		else {
			routesStartEnd = getRoutes(start,end);
		}
		int count = 0;	
		for (String route: routesStartEnd){
			
			// A-B-C-D- 4 nodes and 4 dashes
			if (route.length() <= 8)
			{
				count++;
			}			
		}
		//System.out.println(" 6. "+start+"-"+end+" with Maximum 3 stops : " + count);	
		allRoutesStartEnd.clear();
		return count;
	}
	
	/*
	 * Calculate trips with exactly four stops between 2 different towns or a cyclic path
	 */
	@Override
	public int calculateFourStopRoutes (char start, char end) {
		
		ArrayList<String> routesStartEnd ;//= getRoutes(start,end);
		if (start == end) {
			routesStartEnd = getCycleRoutes(start);
		}
		else {
			routesStartEnd = getRoutes(start,end);
		}
		int count = 0;	
		for (String route: routesStartEnd){
			
			// A-B-C-D-E- :5 nodes and 5 dashes
			if (route.length() == 10)
			{
				count++;
			}			
		}
		//System.out.println(" 7. "+start+"-"+end+" with Exact 4 stops : " + count);	
		allRoutesStartEnd.clear();
		return count;
	}
	
	/*
	 * Get all the diffrernt paths in a cycle
	 */
	public ArrayList<String> getCycleRoutes (char start) {
		ArrayList<Integer> adjecentTowns = new ArrayList<Integer>();
		ArrayList<Integer> prevTowns = new ArrayList<Integer>();
		ArrayList<String> allRoutes= new ArrayList<String>();
		ArrayList<String> allCycleRoutes= new ArrayList<String>();
		int origin = ConvertToIndex (start);
		for (int i=0; i<TOWNS; i++) {
			if (routeMatrix[i][origin] < INFINITY) {
				prevTowns.add(i);
			}
		}
		adjecentTowns.add(origin);
		for (int townID : prevTowns) {	
			allRoutes=possibleRoutes((char)(townID+65),adjecentTowns);
		}
		for (String town : allRoutes) {
			allCycleRoutes.add(town + start +"-");
		}
		return allCycleRoutes;
	}
	
	/*
	 * count the number of routes between 2 different towns or cyclic path ( C-C-) with distance less tha a 
	 * given value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int countMinDistanceRoutes (char start , char end ,int distance) {
		int count=0;
		ArrayList<String> routesStartEnd;
		ArrayList<String> routesCycles = new ArrayList<String>();
		// cyclic path 
		if (start == end) {
			routesStartEnd = getCycleRoutes(start);
			// Generate all the 3 diffrernt combination of cyclic route by combinig strings
			for (String route1 : routesStartEnd) {
				routesCycles.add(route1);
				for (String route2 : routesStartEnd) {
					String str = route2.substring(2);
					String join = route1 + str;
					routesCycles.add(join);
					str = route2.substring(0,route2.length()-2);
					join = str+route1;
					routesCycles.add(join);
					str = route2.substring(2, route2.length()-2);
					join = route1+str+route1;
					routesCycles.add(join);
				}
			}
			
			// Eliminate duplicates in array list
			@SuppressWarnings("rawtypes")
			HashSet hs = new HashSet();
			hs.addAll(routesCycles);
			routesCycles.clear();
			routesCycles.addAll(hs);
			
			
			for (String route : routesCycles) {
				if (calculateRouteDistance(route) < distance) {
					//System.out.println(route);
					count++;
					// first 3 combinations already generated
					for (int i = 4 ; i < distance ; i++) {
						if (calculateRouteDistance(route)* i < distance){
							count++;
						}
						else {
							break;
						}
					}
				}		
			}
			
		}
		//acyclic path
		else {
			routesStartEnd = getRoutes(start,end);
			for (String route : routesStartEnd) {
				if (calculateRouteDistance(route) < distance) {
					count++;
				}		
			}
		}
		//System.out.println("10. "+start+"-"+end+" distance less than " + distance +" : "+count);
		allRoutesStartEnd.clear();
		return count;
	}
	
	/*
	 * Read input from the given txt file
	 */
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
			}

}

