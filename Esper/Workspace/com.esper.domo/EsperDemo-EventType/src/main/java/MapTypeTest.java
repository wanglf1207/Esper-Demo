import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Esper处理Map类型的数据
 * @author wanglf1207
 *
 */
public class MapTypeTest {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {  
		
        EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider();  
        EPAdministrator admin = provider.getEPAdministrator();  
          
        Map<String,Object> personMap = new HashMap<String,Object>();  
        personMap.put("name", String.class);  
        personMap.put("age", int.class);  
        personMap.put("children", List.class);  
        personMap.put("phones", Map.class);  
         
        admin.getConfiguration().addEventType("Person",personMap);
        String epl = "select age,children from Person1 where name='wanglf'";  
          
        EPStatement statement = admin.createEPL(epl);  
        statement.addListener(new PersonMapListener()); 
        
        
        Map<String,Object> person1=new HashMap<String,Object>();  
          
        List<String> children=new ArrayList<String>();  
        children.add("x");  
        children.add("y");  
        children.add("z");  
        
        Map<String,Integer> phones = new HashMap<String,Integer>();  
        
        phones.put("a", 123);  
        phones.put("b", 234);  
          
        person1.put("name","wanglf");  
        person1.put("age",12);  
        person1.put("children", children);  
        person1.put("phones", phones);  
        
        EPRuntime runtime = provider.getEPRuntime();  
        runtime.sendEvent(person1, "Person");  
          
    }  
}

/**
 * 
 * @author wanglf1207
 *
 */
class PersonMapListener implements UpdateListener {  
	  
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {  
        if (newEvents != null) {  
            Integer age = (Integer) newEvents[0].get("age");  
            System.out.println(newEvents[0].get("children"));
            System.out.println("age is:"+age);  
        }  
    }  
  
}  
