package at.woodstick.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * <p>
 * Implementation of {@link MapSorter} interface to sort a map
 * by its mapped values. A new instance of the map will be created
 * that contains the key-value pairs in sorted order. 
 * </p>
 * <p>
 * The given map to sort is not stored as an immutable property. Changes to the source
 * map will affect the MapSorter.
 * </p>
 * <p>
 * Mapped values <strong>must</strong> implement {@link Comparable} interface in order 
 * to be able to sort the map with {@link MapSorter#sort()}.
 * </p>
 * <p>
 * The property of the mapped values by which the map is sorted <strong>must</strong> 
 * implement {@link Comparable} interface in order to be able to sort the 
 * map with {@link MapSorter#sort(PropertyProvider)}.
 * </p>
 * <p>
 * If {@link Comparable} is not implemented a {@link ClassCastException} will be thrown.
 * </p>
 * @param <K> the type of keys maintained by the map to sort
 * @param <V> the type of mapped values
 * 
 * @see MapSorter
 * @see PropertyProvider
 * @see Comparable
 */
public class MapSorterImpl<K, V> implements MapSorter<K, V> {

	/**
	 * Reference to the map to sort
	 */
	private Map<K, V> mapToSort;
	
	/**
	 * Indicates that the map should be sorted in descending order
	 */
	private boolean sortDescing = false;

	/**
	 * Container class that holds the key and value of a map entry
	 */
	private class SortItemContainer {
		private K key;
		private V value;
		
		public SortItemContainer(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
	}
	
	/**
	 * Constructs the MapSorter for the specified Map
	 * 
	 * @param sourceMap Map to sort
	 */
	public MapSorterImpl(Map<K, V> sourceMap) {
		this.mapToSort = sourceMap;
	}

	/**
	 * Puts the value in the map for the given key. If the key already
	 * exists the value is added to the list, otherwise a new list will
	 * be created with the value and added to the map with the given key  
	 * 
	 * @param sorterMap Map that stores list of values to a key
	 * @param key Key of the mapped value
	 * @param value Value to map to the key
	 * 
	 * @param <S> the type of keys maintained by the map to sort
	 * @param <R> the type of mapped values
	 */
	protected <S, R> void putInSorterMap(Map<S, List<R>> sorterMap, S key, R value) {
		if(sorterMap.containsKey(key)) {
			List<R> values = sorterMap.get(key);
			values.add(value);
		}
		else {
			List<R> values = new ArrayList<R>();
			values.add(value);
			sorterMap.put(key, values);
		}
	}
	
	/**
	 * Creates map that performs the sorting. If {@link MapSorterImpl#sortDescing} is
	 * true the TreeMap will be create with the {@link Collections#reverseOrder()} 
	 * comparator.
	 * 
	 * @return New TreeMap instance 
	 * 
	 * @param <S> the type of keys maintained by the map to sort
	 * @param <R> the type of mapped values
	 * 
	 * @see TreeMap
	 * @see Collections#reverseOrder()
	 */
	protected <S, R> Map<S, List<R>> createSorterMap() {
		
		if(this.sortDescing) {
			this.sortDescing = false;
			return new TreeMap<S, List<R>>(Collections.reverseOrder());
		}
		
		return new TreeMap<S, List<R>>();
	}
	
	/**
	 * Sorts the map in natural order of the mapped values. If {@link MapSorterImpl#desc()} 
	 * was called prior to {@link MapSorterImpl#sort()} the map will be sorted in reverse
	 * natural order.
	 * 
	 * @return Map containing the key-value pairs sorted according to the mapped values 
	 * 
	 * @throws ClassCastException If mapped values do not implement {@link Comparable}
	 */
	@Override
	public Map<K, V> sort() {
		Map<V, List<K>> sorterMap = createSorterMap();
		
		for(Entry<K, V> entry : mapToSort.entrySet()) {
			putInSorterMap(sorterMap, entry.getValue(), entry.getKey());
		}
		
		Map<K, V> resultMap = new LinkedHashMap<K, V>();
		
		for(Entry<V, List<K>> entry : sorterMap.entrySet()) {
			for(K key : entry.getValue()) {
				resultMap.put(key, entry.getKey());
			}
		}
		
		return resultMap;
	}
	
	
	/**
	 * <p>
	 * Sorts map according to a certain property of the mapped values.
	 * </p>
	 * <p>
	 * Sorts the map in natural order according to the mapped values property retrieved from
	 * the property provider. If {@link MapSorterImpl#desc()} was called prior to {@link MapSorterImpl#sort()} 
	 * the map will be sorted in reverse natural order.
	 * </p>
	 * 
	 * @param propertyProvider Provider that returns the value of the property by which
	 * 						   the map will be sorted
	 * 
	 * @return Map containing the key-value pairs sorted according to the mapped values 
	 * 
	 * @param <P> type of the property
	 * 
	 * @throws ClassCastException If mapped values property does not implement {@link Comparable}
	 * 
	 * @see PropertyProvider
	 */
	@Override
	public <P> Map<K, V> sort(PropertyProvider<V, P> propertyProvider) {
		
		Map<P, List<SortItemContainer>> sorterMap = createSorterMap();
		
		for(Entry<K, V> entry : mapToSort.entrySet()) {
			putInSorterMap(
				sorterMap, 
				propertyProvider.getProperty(entry.getValue()),
				new SortItemContainer(entry.getKey(), entry.getValue())
			);
		}
		
		Map<K, V> resultMap = new LinkedHashMap<K, V>();
		
		for(Entry<P, List<SortItemContainer>> entry : sorterMap.entrySet()) {
			for(SortItemContainer item : entry.getValue()) {
				resultMap.put(
					item.getKey(),
					item.getValue()
				);
			}
		}
		
		return resultMap;
	}

	/**
	 * Next sort operation will be in reverse natural order
	 *  
	 * @return this
	 */
	@Override
	public MapSorter<K, V> desc() {
		
		this.sortDescing = true;
		
		return this;
	}
}
