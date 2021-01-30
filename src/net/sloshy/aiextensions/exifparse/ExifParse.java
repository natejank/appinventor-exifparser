package net.sloshy.aiextensions.exifparse;

import android.media.ExifInterface;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import com.google.appinventor.components.annotations.SimpleProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;


@DesignerComponent(version = 1,
        category = ComponentCategory.EXTENSION,
        description = "An extension to allow for parsing of EXIF metadata within appinventor",
        nonVisible = true, iconName="")
@SimpleObject(external = true)
public class ExifParse extends AndroidNonvisibleComponent {

    public ExifInterface image = null;
    public YailDictionary imageAttributes = null;

    public ExifParse(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleEvent(description = "Handle errors that occur from the library")
    public void ErrorOccured(String error) {
        EventDispatcher.dispatchEvent(this, "ErrorOccured", error);
    }

    @SimpleFunction()
    public void setImage(String filePath) throws IOException {
        if (new File(filePath).exists()) {
            image = new ExifInterface(new File(filePath));
            imageAttributes = getAttributes(image);
        } else {
            throw new FileNotFoundException("File could not be opened!");
        }
    }

    @SimpleFunction()
    public String getAttribute(String tagName) {
        return image.getAttribute(tagName);
    }

    @SimpleProperty
    public YailDictionary getImageAttributes() {
        return imageAttributes;
    }

    private YailDictionary getAttributes(ExifInterface image) {
//        HashMap<String, Boolean> attrs = new HashMap<String, Boolean>();
        YailDictionary attrs = new YailDictionary();
        //eyugh
        String[] fields = {
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
        for (String attr : fields)
            if (image.getAttribute(attr) != null)
                attrs.put(attr, image.getAttribute(attr));
//         // getting gps separately, so it becomes more usable
        float[] latLong = {0.0f, 0.0f};
        if (image.getLatLong(latLong))
            attrs.put("LatLong", latLong);

        return attrs;
    }
    @SimpleProperty
    public YailList EXIF_ATTRIBUTES() {
        // WHY WOULD YOU USE THESE DUMB ESOTERIC DATATYPES WHEN JAVA ALREADY HAS PERFECTLY ACCEPTABLE ONES BUILT IN ASD;ASDFJKL;ADFSJLK;ADFSJKL;ERSGW
        var attributes = new YailList();
        String[] fields = {
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
        for (String field : fields)
            attributes.add(field);
        return attributes;
    }
//
//    @SimpleFunction
//    public String getDate(ExifInterface image) {
//        return image.getAttribute(ExifInterface.TAG_DATETIME);
//    }
}
