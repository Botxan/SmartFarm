package packcheck;
import java.util.ArrayList;

import packfarm.Farm;
import packfarm.FarmAnimal;
import packfarm.Sensor;

public class CheckFarm {

	public static void main(String[] args) {
		Farm farm = Farm.getInstance();
		farm.addSensor(new Sensor("ID448"));
		farm.addSensor(new Sensor("ID449"));
		
		try {
			farm.addFarmAnimal("BOV728", 2, 652.11);
			farm.addFarmAnimal("BOV729", 6, 530.01);
			farm.addFarmAnimal("BOV730", 3, 502.11);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error while adding the animal: no sensors available");
		}
		
		System.out.println("Total of animals added: " + farm.howManyAnimals());
		System.out.println("Total of sensors available: " + farm.howManySensor());
		
		for (int i = 0; i < 6; i++) farm.register();
		
		ArrayList<FarmAnimal> possibleSickAnimals = farm.obtainPossiblySick(102);
		System.out.println("Possibly sick animals: ");
		for (FarmAnimal animal: possibleSickAnimals) System.out.println(animal.toString());
		
		System.out.println("\nAnimal with the id BOV728: ");
		System.out.println(farm.obtainFarmAnimal("BOV728"));
		
		System.out.println("\nRemoving animal with the id BOV728...");
		System.out.println("Removed: " + farm.removeFarmAnimal("BOV728"));
		
		System.out.println("\nAnimals older than 5:");
		ArrayList<String> olderAnimalIds = farm.obtainFarmAnimalOlder(5);
		for (String id: olderAnimalIds) System.out.println(farm.obtainFarmAnimal(id).toString());
		
		System.out.println("\nRemoving animals that weigh more than 500kg and are not sick...");
		ArrayList<String> departureListIds = farm.farmAnimalDeparture(500, 102);
		
		System.out.println("Amount of removed animals: " + departureListIds.size());
		System.out.println("Animals on the farm at the end: " + farm.howManyAnimals());
		System.out.println("Total of sensors available: " + farm.howManySensor());
	}

}
