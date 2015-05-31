package at.woodstick.utils.sorting;

import java.util.Map;

import at.woodstick.utils.PropertyProvider;

/**
 * An object that sorts maps by its values in natural or reverse
 * natural order. 
 * 
 * @param <K> the type of keys maintained by the map to sort
 * @param <V> the type of mapped values
 * 
 * @see PropertyProvider
 */
public interface MapSorter<K, V> {

	/**
	 * Sorts a map in natural order of the mapped values
	 * 
	 * @return New map containing the sorted key-value pairs of the source map
	 */
	public Map<K, V> sort();
	
	/**
	 * Sorts a map in natural order of the mapped values by a certain property of these
	 * values
	 * 
	 * @param propertyProvider Provider that returns the value of the property by which
	 * 						   the map will be sorted
	 * @return New map containing the sorted key-value pairs of the source map
	 */
	public <P> Map<K, V> sort(PropertyProvider<V, P> propertyProvider);
	
	/**
	 * Next sort is applied in descending (reverse natural) order
	 * 
	 * @return Instance of {@link MapSorter} 
	 */
	public MapSorter<K, V> desc();
}
