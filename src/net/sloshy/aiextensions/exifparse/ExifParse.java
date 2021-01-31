package net.sloshy.aiextensions.exifparse;

import android.media.ExifInterface;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;

import java.io.File;
import java.io.IOException;


/*This extension is intended to be nothing more than a wrapper around android.media.ExifInterface, as to allow you to
* read and write exif metadata in appinventor.  This is why it might be lacking some nice to haves, as I wanted to keep
* as much logic as possible within appinventor, leaving only the things that wouldn't be possible to the
* extension.*/
@DesignerComponent(version = 1,
        category = ComponentCategory.EXTENSION,
        description = "An extension to allow for parsing of EXIF metadata within appinventor",
        nonVisible = true, iconName = "")
@SimpleObject(external = true)
public class ExifParse extends AndroidNonvisibleComponent {

    public static final String[] EXIF_ATTRIBUTES = {
            ExifInterface.TAG_DATETIME,
            ExifInterface.TAG_GPS_LATITUDE,
            ExifInterface.TAG_GPS_LONGITUDE,
            ExifInterface.TAG_EXPOSURE_TIME,
            ExifInterface.TAG_GPS_ALTITUDE,
            ExifInterface.TAG_GPS_ALTITUDE_REF,
            ExifInterface.TAG_GPS_LATITUDE,
            ExifInterface.TAG_GPS_LONGITUDE,
            ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION,
            ExifInterface.TAG_IMAGE_UNIQUE_ID
    };

    public ExifParse(ComponentContainer container) {
        super(container.$form());
    }

    /* I don't know if this is the *best* way to do this, I just needed some way to ensure that you can't be trying to
     * use a null file.  The best way I could find to do that was to always make the user input a filepath, rather than
     * trying to handle it in the class itself. */
    private static ExifInterface getExifInterface(String filePath) throws IOException {
        return new ExifInterface(new File(filePath));
    }

    @SimpleFunction(description = "gets the specified attribute.  " +
            "If the image doesn't have the attribute, returns an empty string.")
    public static String getAttribute(String tagName, String filePath) throws IOException {
        ExifInterface image = getExifInterface(filePath);
        String attr = image.getAttribute(tagName);
        if (attr == null)
            return "";
        return attr;
    }

    /*While this is more logic that I'd like, I don't see a better way of doing it, because you can't manipulate
    * ExifInterfaces directly in appinventor, and I've decided to pass in pathnames to all of these functions instead
    * of having a central ExifInterface variable, as to prevent the headache of forgetting to set one.*/
    @SimpleFunction(description = "Writes attributes.  Use this sparingly, because it involves " +
            "copying all the data from one file to another and deleting the old file and renaming the other.")
    public static void setAttributes(YailDictionary values, String filePath) throws IOException {
        ExifInterface image = getExifInterface(filePath);
        // I'm really not sure this is the best way of accomplishing this, but I'm probably going to use it unless it
        // really doesn't work
        var keys = values.keySet().toArray();
          for (Object key : keys) {
            image.setAttribute((String) key, (String) values.get(key));
        }
        image.saveAttributes();
    }

    @SimpleProperty(description = "Nets the possible tags that can be stored in an image's metadata." +
            "  Not all of this will be present in every image.")
    public static YailList exifAttributes() {
        YailList attributes = new YailList();
        for (String field : EXIF_ATTRIBUTES)
            attributes.add(field);
        return attributes;
    }

    @SimpleEvent(description = "Handle errors that occur from the library")
    public void ErrorOccurred(String error) {
        EventDispatcher.dispatchEvent(this, "ErrorOccured", error);
    }
}
