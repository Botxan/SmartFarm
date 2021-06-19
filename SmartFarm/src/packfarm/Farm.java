package packfarm;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Farm class includes all necessary information in order to monitorize farm animals
 * The class uses Singleton pattern, taking into account that the information
 * relative to the monitorization must be centralized and unique.
 * @author Oihan
 * @version 1
 */
public class Farm {
	private static Farm instance;
	private ArrayList<FarmAnimal> farmAnimalSet;
	private ArrayList<Sensor> sensorList;	
	
	/**
	 * Farm Class constructor. Initializes animal and sensor lists.
	 */
	private Farm() {
		farmAnimalSet = new ArrayList<FarmAnimal>();
		sensorList = new ArrayList<Sensor>();
	}
	
	/**
	 * Returns the Farm class instance (following Singleton pattern).
	 * @return the Farm class instance
	 */
	public static Farm getInstance() {
		if (instance == null) instance = new Farm();
		return instance;
	}

	/**
	 * Adds an animal to the farm animal set.
	 * @param id animal id
	 * @param age animal age
	 * @param weight animal weight
	 * @throws IndexOutOfBoundsException if no sensors are available (empty list)
	 * It is not mandatory to declare IndexOutOfBoundsException in the header because it
	 * is a runtime exception, so the compiler is aware that it can be raised.
	 */
	public void addFarmAnimal(String id, int age, double weight) throws IndexOutOfBoundsException {
		if (sensorList.size() == 0) {
			throw new IndexOutOfBoundsException("No sensors available");
		}
		farmAnimalSet.add(new FarmAnimal(id, age, weight, sensorList.remove(0)));
	}
	
	/**
	 * Returns the amount of animals in the farm animal set.
	 * @return the amount of animals in the farm animal set
	 */
	public int howManyAnimals() {
		return farmAnimalSet.size();
	}
	
	/**
	 * Adds a sensor to the sensor list.
	 * @param sensor the sensor to add
	 */
	public void addSensor(Sensor sensor) {
		sensorList.add(sensor);
	}
	
	/**
	 * Returns the amount of sensors in the sensor list.
	 * @return the amount of sensors in the sensor list
	 */
	public int howManySensor() {
		return sensorList.size();
	}
	
	
	/**
	 * Gets a physiological register from every farm animal.
	 */
	public void register() {
		for (FarmAnimal animal: farmAnimalSet) {
			animal.register();
		}
	}
	
	/**
	 * Given a maximum temperature, returns an array with all animals that exceed
	 * that temperature.
	 * @param max_temperature the maximum temperature before considering any sickness
	 * @return an array with all animals that exceed the given temperature
	 */
	public ArrayList<FarmAnimal> obtainPossiblySick(double max_temperature) {
		ArrayList<FarmAnimal> sickAnimals = new ArrayList<FarmAnimal>();
		
		for (FarmAnimal animal: farmAnimalSet) {
			if (animal.avgTemperature() > max_temperature) sickAnimals.add(animal);
		}
		
		return sickAnimals;
	}
	
	/**
	 * Given an id, returns the animal with the id that matchesthe one
	 * passed by parameter*.
	 * *We are suposing that only registered animal ID's will be passed by parameter
	 * @param id animal id
	 * @return the animal with the id that matches the id passed by parameter
	 */
	public FarmAnimal obtainFarmAnimal(String id) {
		return farmAnimalSet.get(farmAnimalSet.indexOf(new FarmAnimal(id)));
	}
	
	/**
	 * Given an id, removes and returns the animal with the id that matches the
	 * one passed by parameter*. Also, the sensor of the deleted animal is added to
	 * available sensors list.
	 * *We are suposing that only registered animal ID's will be passed by parameter
	 * @param id animal id
	 * @return the animal that has been deleted
	 */
	public FarmAnimal removeFarmAnimal(String id) {
		FarmAnimal removedAnimal = farmAnimalSet.remove(farmAnimalSet.indexOf(new FarmAnimal(id)));
		sensorList.add(removedAnimal.getMySensor());
		return removedAnimal;
	}
	
	/**
	 * Given an age, returns an array with every animal that is older than the age passed
	 * by parameter.
	 * @param age animal age
	 * @return an array with every animal that is older than the age passed by parameter
	 */
	public ArrayList<String> obtainFarmAnimalOlder(int age) {
		ArrayList<String> olderAnimalIds = new ArrayList<String>();		
		for (FarmAnimal animal: farmAnimalSet) {
			if (animal.getAge() > age) olderAnimalIds.add(animal.getId());
		}
		return olderAnimalIds;
	}
	
	/**
	 * Given an maximum weight and maximum temperature, removes every animal on the animal
	 * farm set that its weight is greater than the one passed by parameter but its 
	 * temperature is lesser that the one passed by parameter. Also, it returns an array
	 * with all the animals removed, and adds their sensor to the available sensor list.
	 * @param max_weight the maximum weight before considering the animal to be removed
	 * @param max_temperature the maximum temperature before considering any sickness
	 * @return an array with all the animals removed
	 */
	public ArrayList<String> farmAnimalDeparture(double max_weight, double max_temperature) {
		ArrayList<String> departureAnimalIds = new ArrayList<String>(); 
		Iterator<FarmAnimal> it = farmAnimalSet.iterator();
		
		while (it.hasNext()) {
			FarmAnimal animal = it.next();
			if (animal.getWeight() > max_weight && animal.avgTemperature() <= max_temperature) {
				departureAnimalIds.add(animal.getId());
				sensorList.add(animal.getMySensor());
				it.remove();
			}
		}
		
		return departureAnimalIds;
	}
	
	/**
	 * Returns a list with all sensors available.
	 * Method used for JUnit testing purpouses.
	 * @return a list with all sensors available
	 */
	public ArrayList<Sensor> getSensorList() {
		return sensorList;
	}
	
	/**
	 * Returns a list of all the animals on the farm.
	 * Method used for JUnit testing purpouses.
	 * @return a list of all the animals
	 */
	public ArrayList<FarmAnimal> getFarmAnimalSet() {
		return farmAnimalSet;
	}
	
	/**
	 * Resets the Farm class instance.
	 */
	public void reset() {
		instance = null;
	}
}