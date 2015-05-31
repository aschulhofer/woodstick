package at.woodstick.test.utils.sorting;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import at.woodstick.test.helper.SortableTestClass;
import at.woodstick.utils.PropertyProvider;
import at.woodstick.utils.sorting.MapSorter;
import at.woodstick.utils.sorting.MapSorterImpl;

/**
 * Unit tests for {@link MapSorterImpl}
 */
public class MapSorterImplTest {
	
	/**
	 * Instance of the logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(MapSorterImplTest.class);
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	// ################################## Natural Order ##################################
	
	@Test
	public void shouldSortMapByIntegerValuesInNaturalOrder() {
		
		// Setup
		
		Map<String, Integer> sourceMap = new HashMap<String, Integer>();
		sourceMap.put("A", 3);
		sourceMap.put("E", 5);
		sourceMap.put("D", 2);
		sourceMap.put("F", 2);
		
		Map<String, Integer> expectedMap = new LinkedHashMap<String, Integer>();
		expectedMap.put("D", 2);
		expectedMap.put("F", 2);
		expectedMap.put("A", 3);
		expectedMap.put("E", 5);
		
		// Execute
		
		MapSorter<String, Integer> collectionSorter = new MapSorterImpl<String, Integer>(sourceMap);
		Map<String, Integer> resultMap = collectionSorter.sort();

		// Verify
		
		LOGGER.info(sourceMap.values());
		LOGGER.info(resultMap.values());
		LOGGER.info(expectedMap.values());
		
		Assertions.assertThat(resultMap.size()).isEqualTo(expectedMap.size());
		Assertions.assertThat(resultMap.values()).containsExactlyElementsOf(expectedMap.values());
	}
	
	@Test
	public void shouldSortMapByStringValuesInNaturalOrder() {
		
		// Setup
		
		Map<String, String> sourceMap = new HashMap<String, String>();
		sourceMap.put("A", "And");
		sourceMap.put("E", "End");
		sourceMap.put("B", "Bnd");
		sourceMap.put("G", "Cnd");
		sourceMap.put("D", "Dnd");
		sourceMap.put("C", "Cnd");
		sourceMap.put("F", "Cndi");
		
		Map<String, String> expectedMap = new LinkedHashMap<String, String>();
		expectedMap.put("A", "And");
		expectedMap.put("B", "Bnd");
		expectedMap.put("C", "Cnd");
		expectedMap.put("G", "Cnd");
		expectedMap.put("F", "Cndi");
		expectedMap.put("D", "Dnd");
		expectedMap.put("E", "End");
		
		// Execute
		
		MapSorter<String, String> collectionSorter = new MapSorterImpl<String, String>(sourceMap);
		Map<String, String> resultMap = collectionSorter.sort();

		// Verify
		
		LOGGER.info(sourceMap.values());
		LOGGER.info(resultMap.values());
		LOGGER.info(expectedMap.values());
		
		Assertions.assertThat(resultMap.size()).isEqualTo(expectedMap.size());
		Assertions.assertThat(resultMap.values()).containsExactlyElementsOf(expectedMap.values());
	}
	
	@Test
	public void shouldSortMapByClassValuesPropertyInNaturalOrder() {
		
		// Setup
		
		SortableTestClass testClassA = new SortableTestClass(3);
		SortableTestClass testClassE = new SortableTestClass(5);
		SortableTestClass testClassD = new SortableTestClass(2);
		SortableTestClass testClassC = new SortableTestClass(2);
		
		Map<String, SortableTestClass> sourceMap   = new HashMap<String, SortableTestClass>();
		sourceMap.put("A", testClassA);
		sourceMap.put("E", testClassE);
		sourceMap.put("D", testClassD);
		sourceMap.put("C", testClassC);
		
		Map<String, SortableTestClass> expectedMap = new LinkedHashMap<String, SortableTestClass>();
		expectedMap.put("D", testClassD);
		expectedMap.put("C", testClassC);
		expectedMap.put("A", testClassA);
		expectedMap.put("E", testClassE);
		
		PropertyProvider<SortableTestClass, Integer> sortBy = new PropertyProvider<SortableTestClass, Integer>() {
			public Integer getProperty(SortableTestClass item) {
				return item.getTestIntegerProperty();
			}
		};
		
		// Execute
		
		MapSorter<String, SortableTestClass> collectionSorter = new MapSorterImpl<String, SortableTestClass>(sourceMap);
		Map<String, SortableTestClass> resultMap = collectionSorter.sort(sortBy);

		// Verify
		
		LOGGER.info(sourceMap.values());
		LOGGER.info(resultMap.values());
		LOGGER.info(expectedMap.values());
		
		Assertions.assertThat(resultMap.size()).isEqualTo(expectedMap.size());
		Assertions.assertThat(resultMap.values()).containsExactlyElementsOf(expectedMap.values());
	}
	
	@Test
	public void shouldThrowClassCastExceptionForValuesThatDoNotImplementComparable() {
		
		// Setup
		
		SortableTestClass testClassA = new SortableTestClass(3);
		SortableTestClass testClassE = new SortableTestClass(5);
		SortableTestClass testClassD = new SortableTestClass(2);
		SortableTestClass testClassC = new SortableTestClass(2);
		
		Map<String, SortableTestClass> sourceMap   = new HashMap<String, SortableTestClass>();
		sourceMap.put("A", testClassA);
		sourceMap.put("E", testClassE);
		sourceMap.put("D", testClassD);
		sourceMap.put("C", testClassC);
		
		// Execute & Verify
		
		MapSorter<String, SortableTestClass> collectionSorter = new MapSorterImpl<String, SortableTestClass>(sourceMap);
		
		exception.expect(ClassCastException.class);
		collectionSorter.sort();
	}
	
	// ################################## Reverse Natural Order ##################################
	
	@Test
	public void shouldSortMapByIntegerValuesInReverseNaturalOrder() {
		
		// Setup
		
		Map<String, Integer> sourceMap = new HashMap<String, Integer>();
		sourceMap.put("A", 3);
		sourceMap.put("E", 5);
		sourceMap.put("D", 2);
		sourceMap.put("F", 2);
		
		Map<String, Integer> expectedMap = new LinkedHashMap<String, Integer>();
		expectedMap.put("E", 5);
		expectedMap.put("A", 3);
		expectedMap.put("F", 2);
		expectedMap.put("D", 2);
		
		// Execute
		
		MapSorter<String, Integer> collectionSorter = new MapSorterImpl<String, Integer>(sourceMap);
		Map<String, Integer> resultMap = collectionSorter.desc().sort();

		// Verify
		
		LOGGER.info(sourceMap.values());
		LOGGER.info(resultMap.values());
		LOGGER.info(expectedMap.values());
		
		Assertions.assertThat(resultMap.size()).isEqualTo(expectedMap.size());
		Assertions.assertThat(resultMap.values()).containsExactlyElementsOf(expectedMap.values());
	}
}
