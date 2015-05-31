package at.woodstick.test.integration.utils.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import at.woodstick.test.helper.SortableTestClass;
import at.woodstick.utils.PropertyProvider;
import at.woodstick.utils.sorting.MapSorter;
import at.woodstick.utils.sorting.MapSorterImpl;

/**
 * Integration tests for {@link MapSorterImpl}
 */
public class MapSorterImplIT {
	
	/**
	 * Instance of the logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(MapSorterImplIT.class);
	
	private class SortableTestClassContainer<K> {
		private K key;
		private SortableTestClass testClass;
		
		public SortableTestClassContainer(K key, SortableTestClass testClass) {
			this.key = key;
			this.testClass = testClass;
		}
		
		public K getKey() {
			return key;
		}
		
		public SortableTestClass getTestClass() {
			return testClass;
		}
	}
	
	@Test
	public void shouldSortMapByIntegerValuesInNaturalOrder() {
		
		// Setup
		
		long startTime = System.currentTimeMillis(); 
		
		Random randomGenerator = new Random(System.currentTimeMillis());
		
		Map<String, SortableTestClass> sourceMap = new HashMap<String, SortableTestClass>();
		List<SortableTestClassContainer<String>> testClassesList  = new ArrayList<SortableTestClassContainer<String>>();
		
		for(int i = 0; i < 20000; i++) {
			SortableTestClass testClass = new SortableTestClass(randomGenerator.nextInt());
			sourceMap.put(Integer.toString(i), testClass);
			
			testClassesList.add(
				new SortableTestClassContainer<String>(Integer.toString(i), testClass)
			);
		}
		
		Collections.sort(testClassesList, new Comparator<SortableTestClassContainer<String>>() {
			@Override
			public int compare(SortableTestClassContainer<String> o1, SortableTestClassContainer<String> o2) {
				
				SortableTestClass tc1 = o1.getTestClass();
				SortableTestClass tc2 = o2.getTestClass();
				
				if(tc1.getTestIntegerProperty() < tc2.getTestIntegerProperty()) {
					return -1;
				}
				
				if(tc1.getTestIntegerProperty() > tc2.getTestIntegerProperty()) {
					return 1;
				}
				
				return 0;
			}
		});
		
		List<String> expectedKeysList = new ArrayList<String>(testClassesList.size());
		List<SortableTestClass> expectedTestClassesList = new ArrayList<SortableTestClass>(testClassesList.size());
		
		for(SortableTestClassContainer<String> testClassContainer : testClassesList) {
			expectedKeysList.add(testClassContainer.getKey());
			expectedTestClassesList.add(testClassContainer.getTestClass());
		}
		
		PropertyProvider<SortableTestClass, Integer> sortBy = new PropertyProvider<SortableTestClass, Integer>() {
			public Integer getProperty(SortableTestClass item) {
				return item.getTestIntegerProperty();
			}
		};
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		LOGGER.info("Elapsed time of setup: " + (elapsedTime/1000) + "s | " + (elapsedTime) + "ms");
		
		// Execute
		
		startTime = System.currentTimeMillis(); 
		
		MapSorter<String, SortableTestClass> collectionSorter = new MapSorterImpl<String, SortableTestClass>(sourceMap);
		Map<String, SortableTestClass> resultMap = collectionSorter.sort(sortBy);

		elapsedTime = System.currentTimeMillis() - startTime;
		
		LOGGER.info("Elapsed time of execution: " + (elapsedTime/1000) + "s | " + (elapsedTime) + "ms, sorted " + resultMap.size() + " test objects by property");
		
		// Verify
		
		startTime = System.currentTimeMillis(); 
		
		Assertions.assertThat(resultMap.size()).isEqualTo(expectedTestClassesList.size());
		Assertions.assertThat(new ArrayList<String>(resultMap.keySet())).containsExactlyElementsOf(expectedKeysList);
		Assertions.assertThat(resultMap.values()).containsExactlyElementsOf(expectedTestClassesList);
		
		elapsedTime = System.currentTimeMillis() - startTime;
		
		LOGGER.info("Elapsed time of assertion: " + (elapsedTime/1000) + "s | " + (elapsedTime) + "ms");
	}
}
