package mperry

/**
 * Created with IntelliJ IDEA.
 * User: MarkPerry
 * Date: 26/09/13
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
class MyTest {

	static void main(String [] args) {
		new MyTest().test()
	}

	void test() {
		def a = [1, 2, 3].collect { Integer it -> it * 2}
		def b = a.filter { it > 2 }
		def c = a.inject(1){acc, it ->
        	acc * it
		}
		println("$a $b $c")
	}

}
