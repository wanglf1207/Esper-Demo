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

		// Address定义
		String[] addressPropNames = { "road", "street", "houseNo" };
		Object[] addressPropTypes = { String.class, String.class, int.class };

		// Child定义
		String[] childPropNames = { "name", "age" };
		Object[] childPropTypes = { String.class, int.class };

		// Person定义
		String[] personPropNames = { "name", "age", "children", "phones", "address" };
		Object[] personPropTypes = { String.class, int.class, "Child[]", Map.class, "Address" };

		// 注册Address到Esper
		admin.getConfiguration().addEventType("Address", addressPropNames, addressPropTypes);
		// 注册Child到Esper
		admin.getConfiguration().addEventType("Child", childPropNames, childPropTypes);
		// 注册Person到Esper
		admin.getConfiguration().addEventType("Person", personPropNames, personPropTypes);
		EventType event = admin.getConfiguration().getEventType("Person");
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));
		// 新增一个gender属性
		admin.getConfiguration().updateObjectArrayEventType("Person", new String[] { "gender" },
				new Object[] { int.class });

		/**
		 * 输出结果： Person props: [name, age, children, phones, address, gender]
		 */
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));
	}
}