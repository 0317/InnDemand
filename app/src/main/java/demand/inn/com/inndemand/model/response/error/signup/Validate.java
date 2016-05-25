package demand.inn.com.inndemand.model.response.error.signup;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Validate {

	@SerializedName("first_name")
	private ArrayList<String> firstName;
	@SerializedName("last_name")
	private ArrayList<String> lastName;
	private ArrayList<String> email;
	private ArrayList<String> password;
	private ArrayList<String> phone;
	@SerializedName("is_newsletter_subscribed")
	private ArrayList<String> newsletterSub;

	public ArrayList<String> getFirstName() {
		return firstName;
	}

	public void setFirstName(ArrayList<String> firstName) {
		this.firstName = firstName;
	}

	public ArrayList<String> getLastName() {
		return lastName;
	}

	public void setLastName(ArrayList<String> lastName) {
		this.lastName = lastName;
	}

	public ArrayList<String> getEmail() {
		return email;
	}

	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}

	public ArrayList<String> getPassword() {
		return password;
	}

	public void setPassword(ArrayList<String> password) {
		this.password = password;
	}

	public ArrayList<String> getPhone() {
		return phone;
	}

	public void setPhone(ArrayList<String> phone) {
		this.phone = phone;
	}

	public ArrayList<String> getNewsletterSub() {
		return newsletterSub;
	}

	public void setNewsletterSub(ArrayList<String> newsletterSub) {
		this.newsletterSub = newsletterSub;
	}

}
