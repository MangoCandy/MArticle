package mc.mangocandy.marticle.Utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Administrator on 2016/6/16.
 */
public class ZhenDong {
    public static void zhendong(Context context,int time){
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(time);
    }
}
