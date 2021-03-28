package packcheck;
import packfarm.Sensor;

public class CheckSensor {
	public static void main(String[] args) {
		Sensor sensor = new Sensor("ID452");
		
		System.out.println("Sensor: " + sensor.toString());
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println(sensor.collectValues().toString());	
			} catch (Sensor.CollectErrorException e) {
				System.out.println("Error while collecting physiological values");
			}
		}
	}
}
