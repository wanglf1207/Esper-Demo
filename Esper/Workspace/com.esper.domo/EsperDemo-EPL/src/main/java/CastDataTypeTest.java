import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * ����cast��������������ֵ��������תΪBigDecimal��
 * 
 * @author luonanqin
 *
 */
class Banana {
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

class CastDataTypeListener1 implements UpdateListener {
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			// cast(avg(price),
			// int)�м�Ŀո���EPL�п��Բ�д������event.get��ʱ�������ϣ�������asһ������������ת�����ֵ
			System.out.println("Average Price: " + event.get("cast(avg(price), int)") + ", DataType is "
					+ event.get("cast(avg(price), int)").getClass().getName());
		}
	}
}

class CastDataTypeListener2 implements UpdateListener {
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			System.out.println("Average Price: " + event.get("avg(price)") + ", DataType is "
					+ event.get("avg(price)").getClass().getName());
		}
	}
}

public class CastDataTypeTest {
	public static void main(String[] args) throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String banana = Banana.class.getName();
		String epl1 = "select cast(avg(price),int) from " + banana + ".win:length_batch(2)";
		String epl2 = "select avg(price) from " + banana + ".win:length_batch(2)";

		EPStatement state1 = admin.createEPL(epl1);
		state1.addListener(new CastDataTypeListener1());
		EPStatement state2 = admin.createEPL(epl2);
		state2.addListener(new CastDataTypeListener2());

		EPRuntime runtime = epService.getEPRuntime();

		Banana b1 = new Banana();
		b1.setPrice(1);
		runtime.sendEvent(b1);

		Banana b2 = new Banana();
		b2.setPrice(2);
		runtime.sendEvent(b2);
	}
}