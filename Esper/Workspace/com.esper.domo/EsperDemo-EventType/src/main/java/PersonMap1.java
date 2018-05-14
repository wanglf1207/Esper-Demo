import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;

/**
 * Map对于嵌套类的定义比较特别。如果嵌套的类是POJO，那就如上面所示。如果嵌套的还是Map，
 * 那么定义方式就需要改变。我们为Person加上Address，示例如下 
 * 如上所示，有两个关键点： 
 * 1.Person在定义Address属性时，map的value不是Address.class，而是Address字符串，而这就代表引擎里的Address对应的Map结构定义
 * 2.事件定义注册必须是Address先于Person，因为Person用到了Address，而引擎是根据Address注册时用的名字去查找Address定义的，所以如果名字写错，引擎就找不到Address了
 * 如果Person有多个Address，则以数组方式定义Person的多个Address时，代码又变成下面的样子了
 * person.put("addresses", "Address[]");  
 * @author wanglf1207
 *
 */
public class PersonMap1 {
	
	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// Address定义
		Map<String, Object> address = new HashMap<String, Object>();
		address.put("road", String.class);
		address.put("street", String.class);
		address.put("houseNo", int.class);

		// Person定义
		Map<String, Object> person = new HashMap<String, Object>();
		person.put("name", String.class);
		person.put("age", int.class);
		person.put("children", List.class);
		person.put("phones", Map.class);
		person.put("address", "Address");

		// 注册Address到Esper
		admin.getConfiguration().addEventType("Address", address);
		// 注册Person到Esper
		admin.getConfiguration().addEventType("Person", person);
		
		// 新增一个gender属性  
		person.put("gender", int.class);  
		admin.getConfiguration().updateMapEventType("Person", person);  
		
		/** 输出结果： 
		 * Person props: [address, age, name, children, phones, gender] 
		 */  
		EventType event = admin.getConfiguration().getEventType("Person");  
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));  
		

	}
}