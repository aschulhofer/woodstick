package at.woodstick.test.helper;

public class SortableTestClass {
	private int testIntegerProperty;
	
	public SortableTestClass(int testIntegerProperty) {
		this.testIntegerProperty = testIntegerProperty;
	}

	public int getTestIntegerProperty() {
		return testIntegerProperty;
	}
	
	@Override
	public String toString() {
		return "TestClass: [" +
				"testIntegerProperty=" + testIntegerProperty + 
				"]";
	}
}
