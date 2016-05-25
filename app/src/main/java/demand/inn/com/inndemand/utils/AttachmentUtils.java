package demand.inn.com.inndemand.utils;

import android.content.Context;
import android.os.Build;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VJ.
 */
public class AttachmentUtils {

    private File fileToBeAttached;
    public String createFileForAttachment(Context paramContext){
        String pathofFileAttached = "";
        try {

            String versionName = paramContext.getPackageManager().getPackageInfo("com.printvenue.android", 0).versionName;
            String osVersionName =  Build.VERSION.RELEASE;
            String networkType = Utils.getNetworkClass(paramContext);
            String identifier = Utils.getDeviceUniqueIdentifier(paramContext);
            File root = getDir(paramContext);

            fileToBeAttached = new File(root, createFileName());
            if(fileToBeAttached.exists()){
                fileToBeAttached.delete();
                fileToBeAttached = new File(root, createFileName());
            }
            FileWriter fw = new FileWriter(fileToBeAttached);
            BufferedWriter out = new BufferedWriter(fw);
            out.write("App Version : " + versionName);
            out.write("\n");
            out.write("User Agent : " + osVersionName);
            out.write("\n");
            out.write("Connection : " + networkType);
            out.write("\n");
            out.write("Identifier : " + identifier);
            out.write("\n");
            out.close();
            pathofFileAttached = fileToBeAttached.getPath();
      //      Log.v(getClass().getName(), fileToBeAttached.getPath());



        } catch (Exception e) {
            e.printStackTrace();
        }
        return pathofFileAttached;
    }

    private String createFileName(){
        String fileName = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        fileName = "Log_"+date+".txt";
        return fileName;

    }

    public File getDir(Context context) {
        File cacheDir = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
        {
            cacheDir =  android.os.Environment.getExternalStorageDirectory();
        }
        else
            cacheDir = context.getCacheDir();
        return cacheDir;
    }
}
