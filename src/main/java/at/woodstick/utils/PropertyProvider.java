package at.woodstick.utils;

/**
 * An object that provides the value of a certain property for every
 * holder of that property. Returns the value of the property.  
 * 
 * @param <T> type of the property holder
 * @param <V> type of the property value
 */
public interface PropertyProvider<T, V> {
	
	/**
	 * Method that provides the value of a certain property of the
	 * given item
	 * 
	 * @param item Holder of the property
	 * @return Value of the property the provider shall return
	 */
	public V getProperty(T item);
}
