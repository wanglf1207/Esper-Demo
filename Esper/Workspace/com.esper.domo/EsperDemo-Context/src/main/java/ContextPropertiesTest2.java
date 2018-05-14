import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class ESB {

	private int id;
	private int price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}

class ContextPropertiesListener2 implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			System.out.println("context.name " + event.get("name") + ", context.id " + event.get("id")
					+ ", context.key1 " + event.get("key1") + ", context.key2 " + event.get("key2"));
		}
	}
}

public class ContextPropertiesTest2 {
	
	public static void main(String[] args) {
		
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String esb = ESB.class.getName();
		// ����context
		String epl1 = "create context esbtest partition by id,price from " + esb;
		// context.id��Բ�ͬ��esb��id,price����һ��context������¼���id��price��ͬ����context.idҲ��ͬ���������¼�������ͬһ��context
		String epl2 = "context esbtest select context.id,context.name,context.key1,context.key2 from " + esb;

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new ContextPropertiesListener2());

		ESB e1 = new ESB();
		e1.setId(1);
		e1.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e1);

		ESB e2 = new ESB();
		e2.setId(2);
		e2.setPrice(30);
		System.out.println("sendEvent: id=2, price=30");
		runtime.sendEvent(e2);

		ESB e3 = new ESB();
		e3.setId(1);
		e3.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e3);

		ESB e4 = new ESB();
		e4.setId(4);
		e4.setPrice(20);
		System.out.println("sendEvent: id=4, price=20");
		runtime.sendEvent(e4);
		
		ESB e5 = new ESB();
		e5.setId(5);
		e5.setPrice(50);
		System.out.println("sendEvent: id=5,price=50");
		runtime.sendEvent(e5);
	}
	
	
}