package demand.inn.com.inndemand.model.response.error.signup;

import java.util.ArrayList;

public class SignupError {

	private ArrayList<String> error;
	private Validate validate;

	public ArrayList<String> getError() {
		return error;
	}

	public void setError(ArrayList<String> error) {
		this.error = error;
	}

	public Validate getValidate() {
		return validate;
	}

	public void setValidate(Validate validate) {
		this.validate = validate;
	}

}
