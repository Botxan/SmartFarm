package packcheck;
import packfarm.PhysiologicalValues;

public class checkPhysiologicalValues {
	public static void main(String[] args) {
		PhysiologicalValues phyValue = new PhysiologicalValues(78, 100, 3);
		System.out.println("Physiological values: " + phyValue.toString());
	}
}
