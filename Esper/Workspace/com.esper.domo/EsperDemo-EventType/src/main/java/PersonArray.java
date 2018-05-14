import java.util.Arrays;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;

public class PersonArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// Address����
		String[] addressPropNames = { "road", "street", "houseNo" };
		Object[] addressPropTypes = { String.class, String.class, int.class };

		// Child����
		String[] childPropNames = { "name", "age" };
		Object[] childPropTypes = { String.class, int.class };

		// Person����
		String[] personPropNames = { "name", "age", "children", "phones", "address" };
		Object[] personPropTypes = { String.class, int.class, "Child[]", Map.class, "Address" };

		// ע��Address��Esper
		admin.getConfiguration().addEventType("Address", addressPropNames, addressPropTypes);
		// ע��Child��Esper
		admin.getConfiguration().addEventType("Child", childPropNames, childPropTypes);
		// ע��Person��Esper
		admin.getConfiguration().addEventType("Person", personPropNames, personPropTypes);
		EventType event = admin.getConfiguration().getEventType("Person");
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));
		// ����һ��gender����
		admin.getConfiguration().updateObjectArrayEventType("Person", new String[] { "gender" },
				new Object[] { int.class });

		/**
		 * �������� Person props: [name, age, children, phones, address, gender]
		 */
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));
	}
}