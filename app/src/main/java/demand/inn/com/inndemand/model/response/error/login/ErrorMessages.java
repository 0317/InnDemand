package demand.inn.com.inndemand.model.response.error.login;

import java.util.ArrayList;

public class ErrorMessages {
	private ArrayList<String> error;
	private ArrayList<Validate> validate;

	public ArrayList<String> getError() {
		return error;
	}

	public void setError(ArrayList<String> error) {
		this.error = error;
	}

	public ArrayList<Validate> getValidate() {
		return validate;
	}

	public void setValidate(ArrayList<Validate> validate) {
		this.validate = validate;
	}

}
