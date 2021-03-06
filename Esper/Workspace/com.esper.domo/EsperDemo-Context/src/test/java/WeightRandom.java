import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;    
    
public class WeightRandom {    
	public static List<WeightCategory>  categorys = new ArrayList<WeightCategory>(); 
    public static List<WeightCategory>  localCategorys = new ArrayList<WeightCategory>(); 
    private static Random random = new Random();    
        
    public static void initData() {    
        WeightCategory wc1 = new WeightCategory("A",20);    
        WeightCategory wc2 = new WeightCategory("B",80);    
       // WeightCategory wc3 = new WeightCategory("C",1);    
        categorys.add(wc1);    
        categorys.add(wc2);    
        //categorys.add(wc3);    
    }    
    
    public static void main(String[] args) throws ClassNotFoundException, IOException { 
    	
          initData();    
          localCategorys=deepCopy(categorys);  //调用该方法
        for(int x=0;x<10;x++) {
          // 计算权重总和
          Integer weightSum = 0;
          for (WeightCategory wc : localCategorys) {    
              weightSum += wc.getWeight();
          }
          
          // 如果权重之和等于0，重新加载一次categorys到本地
          if(weightSum == 0) {
        	  localCategorys=deepCopy(categorys);
        	  for (WeightCategory wc : localCategorys) {    
                  weightSum += wc.getWeight();
              }
          }
          // 根据权重构建一个数组,数组的每个元素都是0
          int [] a = new int [weightSum];
          for (int i=0;i<a.length;i++) {
        	  System.out.print(a[i]);
          }
          
          System.out.println();
          
          // 上一步构建的数组每一位都是0，现在将每个元素按照元素所属权重复制，值就是权重的值
          int b = 0;
          for (WeightCategory wc : localCategorys) {
        	  int weight = wc.getWeight();
        	  for(int j=b;j< b+weight;j++) {
        		  a[j] = weight;
        	  }
        	  b += weight;
          }
          for (int i=0;i<a.length;i++) {
        	  System.out.print(a[i]);
          }
          System.out.println();
          
          // 根据权重生成一个随机数，落到数组的哪个元素上就把元素置为0
          Integer n = random.nextInt(weightSum); // n in [0, weightSum)
          System.out.println(n);
          a[n] = 0;
          for (int i=0;i<a.length;i++) {
        	  System.out.print(a[i]);
          }
          System.out.println();
          
          Integer m = 0;    
          for (int c=0;c<localCategorys.size();c++) {
        	  WeightCategory wc = new WeightCategory();
        	  wc = localCategorys.get(c);
			  if (m <= n && n < m + wc.getWeight()) {    
		       System.out.println("This Random Category is "+wc.getCategory());
		       wc.setWeight(wc.getWeight()-1);
		       System.out.println(wc.getWeight());
		       break;    
			  }    
			   m += wc.getWeight();    
          }   
    	}
    }    
    
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
        out.writeObject(src);  

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
        ObjectInputStream in = new ObjectInputStream(byteIn);  
        @SuppressWarnings("unchecked")  
        List<T> dest = (List<T>) in.readObject();  
        return dest;  
    }  

   
}    
    

class WeightCategory implements Serializable {    
    private String category;    
    private Integer weight;    
        
    
    public WeightCategory() {    
        super();    
    }    
    
    public WeightCategory(String category, Integer weight) {    
        super();    
        this.setCategory(category);    
        this.setWeight(weight);    
    }    
    
    
    public Integer getWeight() {    
        return weight;    
    }    
    
    public void setWeight(Integer weight) {    
        this.weight = weight;    
    }    
    
    public String getCategory() {    
        return category;    
    }    
    
    public void setCategory(String category) {    
        this.category = category;    
    }    
}    
