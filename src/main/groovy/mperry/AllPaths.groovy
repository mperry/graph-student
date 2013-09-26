package mperry

import fj.data.Option

/**
 * Created with IntelliJ IDEA.
 * User: MarkPerry
 * Date: 26/09/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
class AllPaths {


	static void main(String[] args) {
		new AllPaths().calc()
	}

	void calc() {
		def m = RouteMatrix.fromDisk()
		def start = new NodeString("B")
		def end = new NodeString("B")
		def maxSteps = 5
		def list = paths(m, [], start, end, 0, 0, maxSteps)
		println("list: $list")

	}

	List<List<NodeString>> paths(RouteMatrix m, List<NodeString> pathSoFar, NodeString from, NodeString to, int dist, int steps, int maxSteps) {
		if (from == to && steps > 0) {
			pathSoFar + [from]
		} else if (steps >= maxSteps) {
			[]
		} else {
			m.getRow(from).collect { Option<Path> op ->
				op.bind { Path p ->
					def newPath = paths(m, pathSoFar + [from], p.to, to, dist + p.distance, steps + 1, maxSteps)
					newPath.size() == 0 ? Option.none() : Option.some(newPath)
				}
			}.filter {it.isSome()}

		}
	}

}
