package no.knowit.couchdb.model;

import org.jcouchdb.document.BaseDocument;

public class Employee extends BaseDocument {
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;

	private String email;

	private String phone;
	
	private String department;

	public Employee() {
		// needed by svenson (the JSON library)
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
