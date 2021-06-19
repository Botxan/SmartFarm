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
	private Sensor sensor;
	
	@BeforeEach
	void setUp() throws Exception {
		sensorList = farm.getSensorList(); // 0 Sensors
		sensorListLength = farm.howManySensor(); // 0
		
		farmAnimalSet = farm.getFarmAnimalSet(); // 0 animals
		farmAnimalSetLength = farm.howManyAnimals(); // 0
	}
	
	@AfterEach
	void tearDown() throws Exception {
		farm.reset();
	}

	/**
	 * Test FarmAnimal when sensorList is not empty
	 */
	@Test
	@DisplayName("addFarmAnimalWithSensors")
	void testAddFarmAnimalWithSensorsAvailable() {
		sensor = new Sensor("ID732");
		farm.addSensor(sensor);
		// Check sensor list has increased
		assertEquals(farm.howManySensor(), sensorListLength + 1); 
		// Check that the last sensor in the array is the new one
		assertEquals(sensorList.get(farm.howManySensor()-1), sensor); 
		
		try {
			String animalId = "BOV820";
			farm.addFarmAnimal(animalId, 4, 450.68);
			// Check that the sensor list has decreased
			assertEquals(farm.howManySensor(), sensorListLength);
			// Check that the farm animal set has increased
			assertEquals(farm.howManyAnimals(), farmAnimalSetLength + 1);
			// Check that the last animal is the new one
			assertEquals(farmAnimalSet.get(farm.howManyAnimals()-1), new FarmAnimal(animalId));
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
		sensor = new Sensor("ID732");
		farm.addSensor(sensor);
		// Check that the sensor list has increased
		assertEquals(farm.howManySensor(), sensorListLength + 1);
		// Check that the last sensor in the array is the new one
		assertEquals(sensorList.get(farm.howManySensor()-1), sensor);
		
		try {
			String animalId = "BOV820";
			farm.addFarmAnimal("BOV820", 4, 450.68);
			// Check that the sensor list has decreased
			assertEquals(farm.howManySensor(), sensorListLength);
			// Check that the farm animal set has increased
			assertEquals(farm.howManyAnimals(), farmAnimalSetLength + 1);
			// Check that the last animal is the new one
			assertEquals(farmAnimalSet.get(farm.howManyAnimals()-1), new FarmAnimal(animalId));
		} catch(IndexOutOfBoundsException e) {
			fail("(Unexpected) No sensors available");
		}
		
		try {
			farm.removeFarmAnimal("BOV820");
			// Check that sensor list has increased
			assertEquals(farm.howManySensor(), sensorListLength + 1);
			// Check that the animal farm set has decreased
			assertEquals(farm.howManyAnimals(), farmAnimalSetLength);
			// Check that the last sensor in the sensor that has been taken from the removed animal
			assertEquals(sensorList.get(farm.howManySensor()-1), sensor);
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
		// Not compulsory, the exception will be raised anyway
		farm.addSensor(new Sensor("ID732"));
		assertEquals(farm.howManySensor(), sensorListLength + 1); 
		
		assertThrows(IndexOutOfBoundsException.class, () -> farm.removeFarmAnimal("BOV820"));
	}
}
