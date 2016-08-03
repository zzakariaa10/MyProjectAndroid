package tab.androiddev.com.menuu3;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Zakariaa on 28/07/2016.
 */
public class Font {
    private static Hashtable<String, Typeface> cached_icons = new Hashtable<>();
    public static Typeface get_icons(String path,Context context)
    {
        Typeface icons = cached_icons.get(path);
        if(icons==null)
        {
            icons=Typeface.createFromAsset(context.getAssets(),path);
            cached_icons.put(path,icons);
        }
        return icons;
    }
}






















