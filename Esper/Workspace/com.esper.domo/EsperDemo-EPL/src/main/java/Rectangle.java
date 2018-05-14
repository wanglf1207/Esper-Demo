import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

// Rectangle类  
public class Rectangle {
	private int length;
	private int width;

	/** 省略getter/setter方法 **/
	// 外部传入参数计算面积
	public int getArea(int l, int w) {
		return l * w;
	}

	// 计算该对象的面积
	public int getArea() {
		return length * width;
	}
	
	public static void main(String[] args) {
		EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider();
		
		EPAdministrator admin = provider.getEPAdministrator();
		
		String eplStatement  = "select r.getArea() from Rectangle.win:length_batch(2) as r";
		
		EPStatement statement = admin.createEPL(eplStatement);
		
		statement.addListener(new RectangleListener());
		
		Rectangle r1 = new Rectangle();
		r1.setLength(100);
		r1.setWidth(20);
		
		EPRuntime runtime = provider.getEPRuntime();
		runtime.sendEvent(r1);
		
		Rectangle r2 = new Rectangle();
		r2.setLength(100);
		r2.setWidth(20);
		
		runtime.sendEvent(r2);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}

class RectangleListener implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println(newEvents[0].get("getArea()"));
	}
	
}
