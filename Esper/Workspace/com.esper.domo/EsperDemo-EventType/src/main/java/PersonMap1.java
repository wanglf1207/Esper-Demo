import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;

/**
 * Map����Ƕ����Ķ���Ƚ��ر����Ƕ�׵�����POJO���Ǿ���������ʾ�����Ƕ�׵Ļ���Map��
 * ��ô���巽ʽ����Ҫ�ı䡣����ΪPerson����Address��ʾ������ 
 * ������ʾ���������ؼ��㣺 
 * 1.Person�ڶ���Address����ʱ��map��value����Address.class������Address�ַ���������ʹ����������Address��Ӧ��Map�ṹ����
 * 2.�¼�����ע�������Address����Person����ΪPerson�õ���Address���������Ǹ���Addressע��ʱ�õ�����ȥ����Address����ģ������������д��������Ҳ���Address��
 * ���Person�ж��Address���������鷽ʽ����Person�Ķ��Addressʱ�������ֱ�������������
 * person.put("addresses", "Address[]");  
 * @author wanglf1207
 *
 */
public class PersonMap1 {
	
	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// Address����
		Map<String, Object> address = new HashMap<String, Object>();
		address.put("road", String.class);
		address.put("street", String.class);
		address.put("houseNo", int.class);

		// Person����
		Map<String, Object> person = new HashMap<String, Object>();
		person.put("name", String.class);
		person.put("age", int.class);
		person.put("children", List.class);
		person.put("phones", Map.class);
		person.put("address", "Address");

		// ע��Address��Esper
		admin.getConfiguration().addEventType("Address", address);
		// ע��Person��Esper
		admin.getConfiguration().addEventType("Person", person);
		
		// ����һ��gender����  
		person.put("gender", int.class);  
		admin.getConfiguration().updateMapEventType("Person", person);  
		
		/** �������� 
		 * Person props: [address, age, name, children, phones, gender] 
		 */  
		EventType event = admin.getConfiguration().getEventType("Person");  
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));  
		

	}
}