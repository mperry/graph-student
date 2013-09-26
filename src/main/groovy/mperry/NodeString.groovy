package mperry

import groovy.transform.Canonical

/**
 * Created with IntelliJ IDEA.
 * User: MarkPerry
 * Date: 26/09/13
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Canonical
class NodeString {
	String name

	NodeInt toNodeInt() {
		new NodeInt(toInt(name))
	}

	int toInt(String s) {
		((int)(Character.toUpperCase(name.charAt(0))) - 65)
	}


}
