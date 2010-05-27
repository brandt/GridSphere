package org.gridsphere.services.core.shibboleth;

public class ShibbolethUser {

	private String ID;
	private String ShibUsername = "";
	private String ShibCN = "";
	private String ShibLastname = "";
	private String ShibFirstname = "";
	private String ShibEmail = "";
	private String ShibRole = "";
	private String ShibAttr1 = "";
	private String ShibAttr2 = "";
	private String ShibAttr3 = "";
	private String ShibAttr4 = "";

	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getShibUsername() {
		return ShibUsername;
	}
	public void setShibUsername(String shibUsername) {
		ShibUsername = shibUsername;
	}
	public String getShibCN() {
		return ShibCN;
	}
	public void setShibCN(String shibCN) {
		ShibCN = shibCN;
	}
	public String getShibEmail() {
		return ShibEmail;
	}
	public void setShibEmail(String shibEmail) {
		ShibEmail = shibEmail;
	}
	public String getShibFirstname() {
		return ShibFirstname;
	}
	public void setShibFirstname(String shibFirstname) {
		ShibFirstname = shibFirstname;
	}
	public String getShibLastname() {
		return ShibLastname;
	}
	public void setShibLastname(String shibLastname) {
		ShibLastname = shibLastname;
	}
	public String getShibRole() {
		return ShibRole;
	}
	public void setShibRole(String shibRole) {
		ShibRole = shibRole;
	}
	public String getShibAttr1() {
		return ShibAttr1;
	}
	public void setShibAttr1(String shibAttr1) {
		ShibAttr1 = shibAttr1;
	}
	public String getShibAttr2() {
		return ShibAttr2;
	}
	public void setShibAttr2(String shibAttr2) {
		ShibAttr2 = shibAttr2;
	}
	public String getShibAttr3() {
		return ShibAttr3;
	}
	public void setShibAttr3(String shibAttr3) {
		ShibAttr3 = shibAttr3;
	}
	public String getShibAttr4() {
		return ShibAttr4;
	}
	public void setShibAttr4(String shibAttr4) {
		ShibAttr4 = shibAttr4;
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("User Name: ").append(ShibUsername).append("\n");
        sb.append("Id: ").append(getID()).append("\n");
        sb.append("First Name: ").append(ShibFirstname).append("\n");
        sb.append("Last Name: ").append(ShibLastname).append("\n");
        sb.append("Email Address: ").append(ShibEmail).append("\n");
        
        return sb.toString();
    }

}
