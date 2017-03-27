package floyd.com;

import android.app.Application;
import android.renderscript.RenderScript;

/**
 * Created by floyd on 15-12-2.
 */
public class ApplicationManager {

    public static Application mApplication;

    public static RenderScript renderScript;

    public static Application getApplication() {
        return mApplication;
    }

    public static void setApplication(Application application) {
        mApplication = application;
    }
}
