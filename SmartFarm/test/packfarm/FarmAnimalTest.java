package packfarm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test class for FarmAnimal class")
class FarmAnimalTest {
	private FarmAnimal animal;
	int i;
	double sum;	

	@BeforeEach
	void setUp() throws Exception {
		animal = new FarmAnimal("BOV138", 5, 618.54, new Sensor("ID452"));
		i = 0;
		sum = 0;
	}

	@Test
	@DisplayName("register without records")
	void testRegisterNoRecords() {
		animal.register();
		assertEquals(1, animal.getMyValuesLength());
	}
	
	@Test
	@DisplayName("register with 1 record")
	void testRegister1Record() {
		for (int i = 0; i < 2; i++) animal.register();
		assertEquals(2, animal.getMyValuesLength());
	}
	
	@Test
	@DisplayName("register with 4 records")
	void testRegister4Records() {
		for (int i = 0; i < 5; i++) animal.register();
		assertEquals(5, animal.getMyValuesLength());
	}
	
	@Test
	@DisplayName("register with 6 records")
	void testRegister6Records() {
		for (int i = 0; i < 7; i++) animal.register();
		assertEquals(7, animal.getMyValuesLength());
	}
	
	@Test
	@DisplayName("register full of records")
	void testRegisterFull() {
		for (int i = 0; i < 8; i++) animal.register();
		assertEquals(1, animal.getMyValuesLength());
	}
	
	@Test
	@DisplayName("avgTemperature without records")
	void testAvgTemperatureNoRecords() {
		assertEquals(animal.avgTemperature(), 0);
	}
	
	@Test
	@DisplayName("avgTemperature with 1 record")
	void testAvgTemperature1Record() {		
		for (i = 0; i < 1; i++) {
			animal.register();
			sum += animal.getPhyTemperature(i);
		}
		
		assertEquals(sum/i, animal.avgTemperature());
	}
	
	@Test
	@DisplayName("avgTemperature with 4 records")
	void testAvgTemperature4Records() {
		for (i = 0; i < 4; i++) {
			animal.register();
			sum += animal.getPhyTemperature(i);
		}
		
		assertEquals(sum/i, animal.avgTemperature());
	}
	
	@Test
	@DisplayName("avgTemperature with 7 records")
	void testAvgTemperature7Records() {
		for (i = 0; i < 7; i++) {
			animal.register();
			sum += animal.getPhyTemperature(i);
		}
		
		assertEquals(sum/i, animal.avgTemperature());
	}
	
	@Test
	@DisplayName("avgTemperature full of records")
	void testAvgTemperatureFull() {
		for (i = 0; i < 8; i++) { // reset sum every 7 registers
			if (i % 7 == 0) {
				sum = 0;
			}
			animal.register();
			sum += animal.getPhyTemperature(i % 7);
		}
		assertEquals(sum/(i % 7), animal.avgTemperature());
	}

}
