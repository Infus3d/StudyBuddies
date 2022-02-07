package coms309.schools;

/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class School {

	private String name;

	private String address;

	public School() {

	}

	public School(String name, String address) {
		this.name = name;

		this.address = address;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	@Override
	public String toString() {
		return name + " " + address + " ";
	}
}
