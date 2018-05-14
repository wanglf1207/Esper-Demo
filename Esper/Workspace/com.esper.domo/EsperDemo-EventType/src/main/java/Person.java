import java.util.List;
import java.util.Map;

public class Person {
	
	String name;
	int age;
	List<Child> children;
	Map<String, Integer> phones;
	Address address;

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Child getChildren(int index)
	{
		return children.get(index);
	}

	public void setPhones(String name, Integer number){
		phones.put(name, number);
	}

	public int getPhones(String name)
	{
		return phones.get(name);
	}

	public Address getAddress() {
		return address;
	}

}

class Child {
	String name;
	int gender;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
}

class Address {
	String road;
	String street;
	int houseNo;
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(int houseNo) {
		this.houseNo = houseNo;
	}
}
