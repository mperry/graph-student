package mperry

import groovy.transform.Canonical

/**
 * Created with IntelliJ IDEA.
 * User: MarkPerry
 * Date: 26/09/13
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Canonical
class Path {
	NodeString from
	NodeString to
	int distance
}
