package packfarm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test class for Farm Class")
class FarmTest {
	
	private ArrayList<Sensor> sensorList;
	private int sensorListLength;
	
	private ArrayList<FarmAnimal> farmAnimalSet;
	private int farmAnimalSetLength;
	
	private Farm farm = Farm.getInstance();
	
	@BeforeEach
	void setUp() throws Exception {
		sensorList = farm.getSensorList();
		sensorListLength = sensorList.size();
		
		farmAnimalSet = farm.getFarmAnimalSet();
		farmAnimalSetLength = farmAnimalSet.size();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		sensorList.clear();
		farmAnimalSet.clear();
	}

	/**
	 * Test FarmAnimal when sensorList is not empty
	 */
	@Test
	@DisplayName("addFarmAnimalWithSensors")
	void testAddFarmAnimalWithSensorsAvailable() {
		farm.addSensor(new Sensor("ID732"));
		assertEquals(sensorList.size(), sensorListLength + 1);
		
		try {
			farm.addFarmAnimal("BOV820", 4, 450.68);
			assertEquals(sensorList.size(), sensorListLength);
			assertEquals(farmAnimalSet.size(), farmAnimalSetLength + 1);
		} catch(IndexOutOfBoundsException e) {
			fail("(Unexpected) No sensors available");
		}
	}
	
	/**
	 * Test FarmAnimal when sensorList is empty
	 */
	@Test
	@DisplayName("addFarmAnimalWithNoSensors")
	void testAddFarmAnimalWithNoSensorsAvailable() {
		assertThrows(IndexOutOfBoundsException.class, () -> farm.addFarmAnimal("BOV820", 4, 450.68));
	}

	/**
	 * Test removeFarmAnimal passing an animal id by parameter that exist in the array 
	 */
	@Test
	@DisplayName("removeFarmAnimalCorrectID")
	void testRemoveFarmAnimalCorrectId() {	
		farm.addSensor(new Sensor("ID732"));
		assertEquals(sensorList.size(), sensorListLength + 1);
		
		farm.addFarmAnimal("BOV820", 4, 450.68);
		assertEquals(sensorList.size(), sensorListLength);
		assertEquals(farmAnimalSet.size(), farmAnimalSetLength + 1);
		
		try {
			farm.removeFarmAnimal("BOV820");
			assertEquals(farmAnimalSet.size(), farmAnimalSetLength);
			assertEquals(sensorList.size(), sensorListLength + 1);
		} catch (IndexOutOfBoundsException e) {
			fail("(Unexpected) The ID doesn't match any FarmAnimal ID");
		}
	}
	
	/**
	 * Test removeFarmAnimal passing an animal id by parameter that doest not exist in the array 
	 */
	@Test
	@DisplayName("removeFarmAnimalIncorrectID")
	void testRemoveFarmAnimalIncorrectId() {
		farm.addSensor(new Sensor("ID732"));
		assertEquals(sensorList.size(), sensorListLength + 1);
		
		assertThrows(IndexOutOfBoundsException.class, () -> farm.removeFarmAnimal("BOV820"));
	}
}
