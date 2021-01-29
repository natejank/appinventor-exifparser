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

import java.io.File;
import java.io.IOException;

@DesignerComponent(version = 1, 
                    category = ComponentCategory.EXTENSION, 
                    description = "An extension to allow for parsing of EXIF metadata within appinventor", 
                    nonVisible = true, iconName="")

@SimpleObject(external = true)

public class ExifParse extends AndroidNonvisibleComponent{
    public ExifParse(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleEvent(description = "Handle errors that occur from the library")
    public void ErrorOccured(String error) {
        EventDispatcher.dispatchEvent(this, "ErrorOccured", error);
    }

    @SimpleFunction(description = "Returns exif metadata as a dict")
    public YailDictionary getExifData(String filePath) throws IOException {
        ExifInterface ei = new ExifInterface(new File(filePath));
        YailDictionary yd = new YailDictionary();




        return yd;
    }
    @SimpleFunction
    public String getDate(String filePath) throws IOException {
        ExifInterface ei = new ExifInterface(new File(filePath));
        return ei.getAttribute(ExifInterface.TAG_DATETIME);
    }
}
