package batallatech.catchphrase;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Brennan on 3/13/2015.
 */
public class GlobalMethods {

    private Context context;

    public GlobalMethods(Context con){
        this.context = con;
    }

    public void cheers(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
