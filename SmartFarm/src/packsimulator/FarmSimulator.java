package packsimulator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import packfarm.*;


/**
 * The FarmSimultor class takes the management of the smart farm by simulating in its main method
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
		System.out.println("Amount of animals older than 17: " + olderIds.size() + ".");
		
		// Register physiological values for animals older than 17
		ArrayList<FarmAnimal> olders = new ArrayList<FarmAnimal>(); 
		for (String id: olderIds) olders.add(farm.obtainFarmAnimal(id));
		registerOlder(olders);
		
		// Register physiological values of each animal 7 times
		for (int i = 0; i < 7; i++) {
			farm.register();
		}
		
		// Print amount of available sensors
		System.out.println("Available sensors after adding all animals: " + farm.howManySensor() + ".");
		
		// Add new animal to the farm with "XXXX" code
		try {
			farm.addFarmAnimal("XXXX", 15, 529.43);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("\nError while adding new animal: There are no sensors available.");
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
		System.out.println("\nAmount of posibly sick animals (+102ºF):" + possiblySickAnimals.size());
		
		// Remove animals that weigh more than 500kg and are not sick
		ArrayList<String> departureIdList = farm.farmAnimalDeparture(500, 102.0);
		System.out.println("\nTotal of animals that have left the farm: " + departureIdList.size());

		
		// Print amount of sensors after departure
		System.out.println("\nAmount of available sensors after departure:" + farm.howManySensor());
	}
	
	/**
	 * Reads the sensor file and adds all the sensors to the farm sensor list.
	 * @param farm the farm instance
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
	 * @param farm the farm instance
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
	 * Registers physiological values for every animal in the array passed by parameter.
	 * @param animals an array of farm animals
	 */
	private static void registerOlder(ArrayList<FarmAnimal> animals) {
		for (FarmAnimal animal: animals) {
			animal.register();
		}
	}
}

