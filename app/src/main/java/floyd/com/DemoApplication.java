package floyd.com;

import android.app.Application;

/**
 * Created by chenxiaofeng on 2017/3/27.
 */
public class DemoApplication extends Application {

    public void onCreate() {
        super.onCreate();
        ApplicationManager.setApplication(this);
    }
}
