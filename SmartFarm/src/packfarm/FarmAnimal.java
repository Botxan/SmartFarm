package packfarm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents any animal on the farm. Every animal is monitorized until it leaves the farm.
 * @author Oihan
 * @version 1
 */
public class FarmAnimal {
	private final String ID;
	private int age;
	private double weight;
	private Sensor mySensor;
	private PhysiologicalValues[] myValues;
	private final int MAX_VALUE = 7;
	private int length;
	
	/**
	 * FarmAnimal class constructor. Initializes ID attribute.
	 * @param id animal id
	 */
	public FarmAnimal(String id) {
		ID = id;
		
		myValues = new PhysiologicalValues[MAX_VALUE];
		length = 0;
	}
	
	/**
	 * FarmAnimal class constructor. Initializes ID, age, weight and mySensor.
	 * @param id animal id
	 * @param age animal age
	 * @param weight animal weight
	 * @param mySensor the sensor assigned to the animal
	 */
	public FarmAnimal(String id, int age, double weight, Sensor mySensor) {
		this(id);
		this.age = age;
		this.weight = weight;
		this.mySensor = mySensor;
	}
	
	/**
	 * Getter for ID.
	 * @return animal identification string
	 */
	public String getId() {
		return ID;
	}
	
	/**
	 * Getter for age.
	 * @return animal age
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Getter for weight.
	 * @return animal weigth
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Getter for mySensor.
	 * @return the sensor associated to the animal
	 */
	public Sensor getMySensor() {
		return mySensor;
	}
	
	/**
	 * Returns MyValues array length.
	 * Method used for JUnit testing purpouses.
	 * @return MyValues array length
	 */
	public int getMyValuesLength() {
		return length;
	}
	
	/**
	 * Returnn MyValues array last value.
	 * Method used for JUnit testing purpouses
	 * @return MyValues array last value
	 */
	public PhysiologicalValues getLastValue() {
		return length == 0 ? null : myValues[length-1];
	}
	
	/**
	 * Returns the temperature of the physiologicalValues object with the index passed by parameter.
	 * Method used for JUnit testing purpouses.
	 * @param index array index
	 * @return the temperature of the physiologicalValues object with the index passed by parameter.
	 */
	public double getPhyTemperature(int index) {
		return myValues[index].getTemperature();
	}
	
	/**
	 * Setter for age.
	 * @param age animal age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * Setter for weight.
	 * @param weight animal weight.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/** Setter for mySensor.
	 * @param mySensor the sensor associated to the animal
	 */
	public void setMySensor(Sensor mySensor) {
		this.mySensor = mySensor;
	}
	
	/**
	 * Overwritten from Object superclass in order to return a propper String with FarmAnimal data.
	 */
	@Override
	public String toString() {
		return ID + " " + age + " " + weight + " " + mySensor;
	}
	
	/**
	 * Gets and returns physiological values of the animal with its sensor. If physiological data array is full,
	 * then all data stored it is bulked to an external file and the array is reset.
	 * @return physiological values of the animal
	 */
	public PhysiologicalValues register() {
		boolean collected = false;
		PhysiologicalValues phyValues = null;
		
		if (length == MAX_VALUE) {
			storeValuesInFile();
			initWeek();
		}
		while (!collected) {
			try {
				phyValues = mySensor.collectValues();
				myValues[length] = phyValues;
				length++;
				collected = true;
			} catch(Sensor.CollectErrorException e) {
				// Omitting the print cause it would saturate the stdout
				// System.out.println(e.getMessage());
			}
		}
		
		return phyValues;
	}
	
	/**
	 * Stores all physiological values of myValues array in a file.
	 */
	private void storeValuesInFile() {
		try {
			FileWriter wr = new FileWriter(new File("./data/historicalValues.txt"), true);
			wr.write(toString() + "\n");
			for (int i = 0; i < MAX_VALUE; i++) wr.write(myValues[i].toString() + "\n");
			wr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resets myValues array
	 */
	private void initWeek() {
		length = 0;
	}
	
	/**
	 * Returns the average of all temperatures of the animal stored until now.
	 * @return the average of the temperatures registered in the external file
	 * if no temperature is registered, the returned value is 0
	 */
	public double avgTemperature() {
		double sum = 0;
		for (int i = 0; i < length; i++) {
			sum += myValues[i].getTemperature();
		}
		return length == 0 ? 0 : sum/length;
	}
	
	/**
	 * Overwritten from Object superclass in order to properly compare 2 farm animals.
	 */
	@Override
	public boolean equals(Object obj) {
		FarmAnimal animal = (FarmAnimal) obj;
		return ID.equals(animal.ID);
	}
}