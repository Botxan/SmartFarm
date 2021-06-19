package packsimulator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import packfarm.*;


/**
 * The FarmSimulator class takes the management of the smart farm by simulating in its main method
 * the behaviour of the farm creation, of all its sensors, of all the animals and the control of
 * each one of them.
 * @author Oihan
 * @version 1
 */
public class FarmSimulator {
	private static Farm farm;
	
	/**
	 * Main method
	 * @param args No parameters required
	 */
	public static void main(String args[]) {
		// Farm singleton instance
		farm = Farm.getInstance();
		
		// Load sensors and print amount of available sensors
		loadSensors();
		System.out.println("Available sensors: " + farm.howManySensor() + ".");
		
		// Load animals data and print total animals
		loadFarmAnimals();
		System.out.println("Total animals: " + farm.howManyAnimals() + ".");
		
		// Get and print animals older than 17
		ArrayList<String> olderIds = farm.obtainFarmAnimalOlder(17);
		System.out.println("\nAmount of animals older than 17: " + olderIds.size() + ".");
		
		// Register physiological values for animals older than 17
		registerOlder(olderIds);
		
		// Register physiological values of each animal 7 times
		for (int i = 0; i < 7; i++) {
			farm.register();
		}
		
		// Print all information stored in historicalValues.txt
		// We can check that physiological values of every animal older than 17 have been
		// stored in historicalValues.txt file. The block is commented because it will saturate
		// the console with too much data.
//		try {
//			Scanner reader = new Scanner(new FileReader("./data/historicalValues.txt"));
//			System.out.println("\nShowing all data of historicalValues:");
//			while (reader.hasNext()) {
//				System.out.println(reader.nextLine());
//			}
//			reader.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
		// Print amount of available sensors
		System.out.println("\nAvailable sensors after adding all animals: " + farm.howManySensor() + ".");
		
		// Add new animal to the farm with "XXXX" code
		try {
			System.out.println("\nTrying to add a new animal...");
			farm.addFarmAnimal("XXXX", 15, 529.43);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error while adding a new animal: There are no sensors available.");
		}
		
		// The previous block should raise an exception, so try again to
		// add the new animal
		try {
			farm.addSensor(new Sensor("ID360"));
			System.out.println("Added new sensor.");
			farm.addFarmAnimal("XXXX", 15, 529.43);
			System.out.println("Animal added succesfully.");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: There are no sensors available.");
		}
		
		// Get possible sick animals and print their data
		ArrayList<FarmAnimal> possiblySickAnimals = farm.obtainPossiblySick(102.0);
		System.out.println("\nAmount of posibly sick animals (+102ºF): " + possiblySickAnimals.size());
		
		// Remove animals that weigh more than 500kg and are not sick
		ArrayList<String> departureIdList = farm.farmAnimalDeparture(500, 102.0);
		System.out.println("\nTotal of animals that have left the farm: " + departureIdList.size());

		
		// Print amount of sensors after departure
		System.out.println("\nAmount of available sensors after departure: " + farm.howManySensor());
	}
	
	/**
	 * Reads the sensor file and adds all the sensors to the farm sensor list.
	 */
	private static void loadSensors() {
		Scanner sc;
		try {
			sc = new Scanner(new FileReader("./data/availableSensors.txt"));
			while (sc.hasNext()) {
				farm.addSensor(new Sensor(sc.nextLine()));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: available sensors file not found");
		}
	}
	
	/**
	 * Reads the animal data file and adds all data to the farm animal set.
	 */
	// we are supossing that animal list file is always correct
	private static void loadFarmAnimals() {
		Scanner sc;
		try {
			sc = new Scanner(new FileReader("./data/smartFarm.txt"));
			while (sc.hasNext()) {
				String[] rawAnimal = sc.nextLine().split(" ");
				farm.addFarmAnimal(rawAnimal[0], Integer.parseInt(rawAnimal[1]), Double.parseDouble(rawAnimal[2]));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: animal list file not found");
		}
	}
	
	/**
	 * Registers physiological values for each animal whose id is in the array
	 * @param animals an array with farm animal ids
	 */
	private static void registerOlder(ArrayList<String> animalIds) {	
		for (String animalId: animalIds) {
			farm.obtainFarmAnimal(animalId).register();
		}
	}
}