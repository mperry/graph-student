package mperry

import fj.F
import fj.P
import fj.P2
import fj.data.Option
import groovy.transform.Canonical
import groovy.transform.TypeChecked

/**
 * Created with IntelliJ IDEA.
 * User: MarkPerry
 * Date: 26/09/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
@Canonical
class RouteMatrix {

	static RouteMatrix fromDisk() {
		def sampleRoute = new File("CodeAssignment/src/route.txt").text
		def routeList = Arrays.asList(sampleRoute.split("\\s*,\\s*"));
		def paths = routeList.collect { String it ->
			new PathString(it).toPath()
		}
		RouteMatrix.fromList(paths)
	}


	List<List<Option<Path>>> matrix


	List<Option<Path>> getRow(NodeString from) {
		matrix[from.toNodeInt().value]
	}

	Option<Path> get(NodeString from, NodeString to) {
		matrix[from.toNodeInt().value][to.toNodeInt().value]
	}

	void put(NodeString from, NodeString to, Path p) {
		matrix[from.toNodeInt().value][to.toNodeInt().value] = Option.some(p)
	}

	static RouteMatrix fromList(List<Path> paths) {
		def list = (1..Route.TOWNS).collect { it ->
			[Option.none(), Option.none(), Option.none(), Option.none(), Option.none()]
//			(1..Route.TOWNS).inject([]) { acc, val ->
//				acc + Option.none()
//			}
		}
		paths.each { Path it ->
			list[it.from.toNodeInt().value][it.to.toNodeInt().value] = Option.some(it)
		}
		new RouteMatrix(list)
	}

	@TypeChecked
	Option<Integer> calculateRouteDistance(List<NodeString> route) {
		def p = (P2<Option<Integer>, NodeString>) route.tail().inject(P.p(Option.some(0), route.head())) { P2<Option<Integer>, NodeString> acc, NodeString to ->
			def dist = acc._1()
			def from = acc._2()
			def newDist = dist.bind({ Integer it ->
				get(from, to).bind({ Path p ->
					Option.some(p.distance + it)
				} as F)
			} as F)
			P.p(newDist, to)
		}
		p._1()
	}

}
