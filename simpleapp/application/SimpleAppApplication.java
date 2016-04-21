package nantha91.com.simpleapp.application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by nantha on 23/4/15.
 */
public class SimpleAppApplication extends Application {
 //  public static final String APP_ID = "l9O6rR6AHHyBqlhjEMltaDhUzl2C3VZtnuQYFsZ0";
   // public static final String CLIENT_KEY = "yd7Z4riWCnIGOY9BiSmDt66SgaGyeo2mj7gGTRsi";
    public static final String APP_ID="MW0SzE2XjpFKA72JRTVgoVrWI9PRisPVWcuXgqqq";
    public static final String CLIENT_KEY = "wdSCb7ojCwlHz0CACd22RD4adOCiSAccJT7mJm3p";

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialization of application
        Parse.initialize(this, APP_ID, CLIENT_KEY);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

    }
}
