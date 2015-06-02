package at.woodstick.test.utils.sorting;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.woodstick.utils.sorting.MapSorter;
import at.woodstick.utils.sorting.MapSorterImpl;

/**
 * Unit tests for {@link MapSorterImpl}
 */
public class MapSorterImplTest extends MapSorterBaseTests {
	
	/**
	 * Instance of the logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(MapSorterImplTest.class);
	
	@Override
	protected <K, V> MapSorter<K, V> createSUT(Map<K, V> sourceMap) {
		return new MapSorterImpl<K, V>(sourceMap);
	}
	
	@Override 
	protected Logger getLogger() {
		return LOGGER;
	}
}
