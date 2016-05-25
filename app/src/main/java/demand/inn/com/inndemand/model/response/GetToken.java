package demand.inn.com.inndemand.model.response;

/**
 * Created by Akash
 */
public class GetToken {




    private boolean success;


    private ResultEntity result;
    private Object error;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultEntity getResult() {
        return result;
    }

    public Object getError() {
        return error;
    }

    public static class ResultEntity {
        private String message;
        private int createNewuser;
        private String token;

        public void setMessage(String message) {
            this.message = message;
        }

        public void setCreateNewuser(int createNewuser) {
            this.createNewuser = createNewuser;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public int getCreateNewuser() {
            return createNewuser;
        }

        public String getToken() {
            return token;
        }
    }
}
