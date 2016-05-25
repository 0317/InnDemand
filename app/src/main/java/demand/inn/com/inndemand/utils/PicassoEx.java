package demand.inn.com.inndemand.utils;

import android.content.Context;
import com.squareup.picasso.Picasso;

/**
 * Created by VJ
 */
public class PicassoEx {
    private static Picasso mPicasso;

    public static Picasso getPicasso(Context context) {
        if (null == mPicasso) {
         //   Picasso.Builder builder = new Picasso.Builder(context);
          //  builder.executor(PicassoThreadExecutor.getThreadPoolExecutor());
          //  mPicasso = builder.build();
            mPicasso=Picasso.with(context);

        }
        return mPicasso;
    }

}
