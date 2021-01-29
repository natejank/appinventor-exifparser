package net.sloshy.aiextensions.exifparse;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.Directory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Iterable;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.YailDictionary;

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
    public YailDictionary getExifData(String filePath) throws IOException, ImageProcessingException {
        Metadata md = ImageMetadataReader.readMetadata(new FileInputStream(filePath));
        YailDictionary yd = new YailDictionary();
        for (Directory directory : md.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                yd.put(directory, tag);
            }
        }

        return yd;
    }
}
