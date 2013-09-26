package mperry

import groovy.transform.Canonical

/**
 * Created with IntelliJ IDEA.
 * User: MarkPerry
 * Date: 26/09/13
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Canonical
class PathString {
	static final FROM_INDEX = 0
	static final TO_INDEX = 1
	static final DISTANCE_INDEX = 2

	String value

	Path toPath() {
		new Path(from: new NodeString(value.charAt(FROM_INDEX).toString()),
				to: new NodeString(value.charAt(TO_INDEX).toString()),
				distance: value.charAt(DISTANCE_INDEX).toString().toInteger())
	}
}
