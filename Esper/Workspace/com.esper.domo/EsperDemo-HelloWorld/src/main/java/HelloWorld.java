import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class Apple {
	
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

class AppleListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			Double avg = (Double) newEvents[0].get("avg(price)");
			System.out.println("Apple's average price is " + avg);
		}
	}

}

public class HelloWorld {

	public static void main(String[] args) throws InterruptedException {

		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Apple.class.getName();
		// length_batch(2)表示监听到两个Apple对象就出发，求平均值
		String epl = "select avg(price) from " + product + ".win:length_batch(2)";

		EPStatement state = admin.createEPL(epl);
		state.addListener(new AppleListener());

		EPRuntime runtime = epService.getEPRuntime();

		Apple apple1 = new Apple();
		apple1.setId(1);
		apple1.setPrice(5);
		runtime.sendEvent(apple1);

		Apple apple2 = new Apple();
		apple2.setId(2);
		apple2.setPrice(2);
		runtime.sendEvent(apple2);

		Apple apple3 = new Apple();
		apple3.setId(3);
		apple3.setPrice(5);
		runtime.sendEvent(apple3);
	}
}
