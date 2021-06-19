package packcheck;
import packfarm.FarmAnimal;
import packfarm.Sensor;

public class CheckFarmAnimal {
	public static void main(String[] args) {
		FarmAnimal animal1 = new FarmAnimal("BOV727");
		FarmAnimal animal2 = new FarmAnimal("BOV728", 5, 652.11, new Sensor("ID441"));
		
		// NullPointerException exception when calling toString method		
		System.out.println("Animal 1: " + animal1.toString());
		System.out.println("Animal 2" + animal1.toString());
	
		for (int i = 0; i < 7; i++) {
			// TODO Change FarmClass class in order to prevent collectValues from throwing 
			animal1.register();
			animal2.register();
		}
		
		System.out.println("Average temperature of animal 1: " + animal1.avgTemperature());
		System.out.println("Average temperature of animal 2: " + animal2.avgTemperature());
		
		System.out.println(animal2.equals(new FarmAnimal("BOV728")));
		System.out.println(animal2.equals(new FarmAnimal("BOV729")));
	}
}