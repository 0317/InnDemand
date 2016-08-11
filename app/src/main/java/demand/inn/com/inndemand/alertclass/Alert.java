package demand.inn.com.inndemand.alertclass;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by akash
 */
public class Alert{

    ProgressDialog mProgressDialog;
    Context mContext;

    // alert Dialog for loading details on the screen initially
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("loading details....");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    //Hide the current running alert dialog after data gets loaded
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
