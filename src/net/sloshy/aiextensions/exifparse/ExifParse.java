package net.sloshy.aiextensions.exifparse;

import android.media.ExifInterface;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.YailDictionary;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/*This extension is intended to be nothing more than a wrapper around android.media.ExifInterface, as to allow you to
* read and write exif metadata in appinventor.  This is why it might be lacking some nice to haves, as I wanted to keep
* as much logic as possible within appinventor, leaving only the things that wouldn't be possible to the
* extension.*/
@DesignerComponent(version = 1,
        category = ComponentCategory.EXTENSION,
        description = "An extension to allow for parsing of EXIF metadata within appinventor",
        nonVisible = true, iconName = "")
@UsesPermissions()
@SimpleObject(external = true)
public class ExifParse extends AndroidNonvisibleComponent {

    public ExifParse(ComponentContainer container) {
        super(container.$form());
    }

    /* I don't know if this is the *best* way to do this, I just needed some way to ensure that you can't be trying to
     * use a null file.  The best way I could find to do that was to always make the user input a filepath, rather than
     * trying to handle it in the class itself. The only other way I could think would be to make a subclass of ImagePicker,
     * but finding documentation for extensions is hard enough, and the amount of effort that would take would probably be more
     * than I would actually care to do for this class*/
    private static ExifInterface getExifInterface(String filePath) throws IOException {
        return new ExifInterface(new File(filePath));
    }

    @SimpleFunction(description = "Gets the specified attribute.  " +
            "If the image doesn't have the attribute, returns an empty string.")
    public static String getAttribute(String tagName, String filePath) throws IOException {
        ExifInterface image = getExifInterface(filePath);
        String attr = image.getAttribute(tagName);
        return (attr == null ? "" : attr);
    }

    /*While this is more logic that I'd like, I don't see a better way of doing it, because you can't manipulate
    * ExifInterfaces directly in appinventor, and I've decided to pass in pathnames to all of these functions instead
    * of having a central ExifInterface variable, as to prevent the headache of forgetting to set one.*/
    @SimpleFunction(description = "Writes attributes.  Use this sparingly, because it involves " +
            "copying all the data from one file to another and deleting the old file and renaming the other.")
    public static void setAttributes(YailDictionary values, String filePath) throws IOException {
        ExifInterface image = getExifInterface(filePath);
        // I'm really not sure this is the best way of accomplishing this, but I'm probably going to use it unless it really doesn't work
        Object[] keys = values.keySet().toArray();
          for (Object key : keys) {
            image.setAttribute((String) key, (String) values.get(key));
        }
        image.saveAttributes();
    }

    @SimpleFunction(description = "Writes attributes.  Use this sparingly, because it involves " +
            "copying all the data from one file to another and deleting the old file and renaming the other." +
            "This writes the attributes to a new file.  Be careful though, as it will not try to create" +
            " a new directory if it doesn't already exist.")
    public static void setAttributesCopy(YailDictionary values, String inputPath, String outputPath) throws IOException {
        Path input = Paths.get(inputPath);
        Path output = Paths.get(outputPath);
        Files.copy(input, output, StandardCopyOption.REPLACE_EXISTING);

        setAttributes(values, outputPath);
    }

    @SimpleEvent(description = "Handle errors that occur from the library")
    public void ErrorOccurred(String error) {
        EventDispatcher.dispatchEvent(this, "ErrorOccured", error);
    }

    /*begin constants
    I just copied all of these from the documentation and then created a python script to generate these because
    appinventor requires every variable/constant to be accessed through a getter method.
    I am not psychotic enough to do all of these manually*/
    @SimpleProperty()
    public static int ORIENTATION_FLIP_HORIZONTAL() {
        return ExifInterface.ORIENTATION_FLIP_HORIZONTAL;
    }
    @SimpleProperty()
    public static int ORIENTATION_FLIP_VERTICAL() {
        return ExifInterface.ORIENTATION_FLIP_VERTICAL;
    }
    @SimpleProperty()
    public static int ORIENTATION_NORMAL() {
        return ExifInterface.ORIENTATION_NORMAL;
    }
    @SimpleProperty()
    public static int ORIENTATION_ROTATE_180() {
        return ExifInterface.ORIENTATION_ROTATE_180;
    }
    @SimpleProperty()
    public static int ORIENTATION_ROTATE_270() {
        return ExifInterface.ORIENTATION_ROTATE_270;
    }
    @SimpleProperty()
    public static int ORIENTATION_ROTATE_90() {
        return ExifInterface.ORIENTATION_ROTATE_90;
    }
    @SimpleProperty()
    public static int ORIENTATION_TRANSPOSE() {
        return ExifInterface.ORIENTATION_TRANSPOSE;
    }
    @SimpleProperty()
    public static int ORIENTATION_TRANSVERSE() {
        return ExifInterface.ORIENTATION_TRANSVERSE;
    }
    @SimpleProperty()
    public static int ORIENTATION_UNDEFINED() {
        return ExifInterface.ORIENTATION_UNDEFINED;
    }
    @SimpleProperty()
    public static String TAG_APERTURE_VALUE() {
        return ExifInterface.TAG_APERTURE_VALUE;
    }
    @SimpleProperty()
    public static String TAG_ARTIST() {
        return ExifInterface.TAG_ARTIST;
    }
    @SimpleProperty()
    public static String TAG_BITS_PER_SAMPLE() {
        return ExifInterface.TAG_BITS_PER_SAMPLE;
    }
    @SimpleProperty()
    public static String TAG_BRIGHTNESS_VALUE() {
        return ExifInterface.TAG_BRIGHTNESS_VALUE;
    }
    @SimpleProperty()
    public static String TAG_CFA_PATTERN() {
        return ExifInterface.TAG_CFA_PATTERN;
    }
    @SimpleProperty()
    public static String TAG_COLOR_SPACE() {
        return ExifInterface.TAG_COLOR_SPACE;
    }
    @SimpleProperty()
    public static String TAG_COMPONENTS_CONFIGURATION() {
        return ExifInterface.TAG_COMPONENTS_CONFIGURATION;
    }
    @SimpleProperty()
    public static String TAG_COMPRESSED_BITS_PER_PIXEL() {
        return ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL;
    }
    @SimpleProperty()
    public static String TAG_COMPRESSION() {
        return ExifInterface.TAG_COMPRESSION;
    }
    @SimpleProperty()
    public static String TAG_CONTRAST() {
        return ExifInterface.TAG_CONTRAST;
    }
    @SimpleProperty()
    public static String TAG_COPYRIGHT() {
        return ExifInterface.TAG_COPYRIGHT;
    }
    @SimpleProperty()
    public static String TAG_CUSTOM_RENDERED() {
        return ExifInterface.TAG_CUSTOM_RENDERED;
    }
    @SimpleProperty()
    public static String TAG_DATETIME() {
        return ExifInterface.TAG_DATETIME;
    }
    @SimpleProperty()
    public static String TAG_DATETIME_DIGITIZED() {
        return ExifInterface.TAG_DATETIME_DIGITIZED;
    }
    @SimpleProperty()
    public static String TAG_DATETIME_ORIGINAL() {
        return ExifInterface.TAG_DATETIME_ORIGINAL;
    }
    @SimpleProperty()
    public static String TAG_DEFAULT_CROP_SIZE() {
        return ExifInterface.TAG_DEFAULT_CROP_SIZE;
    }
    @SimpleProperty()
    public static String TAG_DEVICE_SETTING_DESCRIPTION() {
        return ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION;
    }
    @SimpleProperty()
    public static String TAG_DIGITAL_ZOOM_RATIO() {
        return ExifInterface.TAG_DIGITAL_ZOOM_RATIO;
    }
    @SimpleProperty()
    public static String TAG_DNG_VERSION() {
        return ExifInterface.TAG_DNG_VERSION;
    }
    @SimpleProperty()
    public static String TAG_EXIF_VERSION() {
        return ExifInterface.TAG_EXIF_VERSION;
    }
    @SimpleProperty()
    public static String TAG_EXPOSURE_BIAS_VALUE() {
        return ExifInterface.TAG_EXPOSURE_BIAS_VALUE;
    }
    @SimpleProperty()
    public static String TAG_EXPOSURE_INDEX() {
        return ExifInterface.TAG_EXPOSURE_INDEX;
    }
    @SimpleProperty()
    public static String TAG_EXPOSURE_MODE() {
        return ExifInterface.TAG_EXPOSURE_MODE;
    }
    @SimpleProperty()
    public static String TAG_EXPOSURE_PROGRAM() {
        return ExifInterface.TAG_EXPOSURE_PROGRAM;
    }
    @SimpleProperty()
    public static String TAG_EXPOSURE_TIME() {
        return ExifInterface.TAG_EXPOSURE_TIME;
    }
    @SimpleProperty()
    public static String TAG_FILE_SOURCE() {
        return ExifInterface.TAG_FILE_SOURCE;
    }
    @SimpleProperty()
    public static String TAG_FLASH() {
        return ExifInterface.TAG_FLASH;
    }
    @SimpleProperty()
    public static String TAG_FLASHPIX_VERSION() {
        return ExifInterface.TAG_FLASHPIX_VERSION;
    }
    @SimpleProperty()
    public static String TAG_FLASH_ENERGY() {
        return ExifInterface.TAG_FLASH_ENERGY;
    }
    @SimpleProperty()
    public static String TAG_FOCAL_LENGTH() {
        return ExifInterface.TAG_FOCAL_LENGTH;
    }
    @SimpleProperty()
    public static String TAG_FOCAL_LENGTH_IN_35MM_FILM() {
        return ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM;
    }
    @SimpleProperty()
    public static String TAG_FOCAL_PLANE_RESOLUTION_UNIT() {
        return ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT;
    }
    @SimpleProperty()
    public static String TAG_FOCAL_PLANE_X_RESOLUTION() {
        return ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION;
    }
    @SimpleProperty()
    public static String TAG_FOCAL_PLANE_Y_RESOLUTION() {
        return ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION;
    }
    @SimpleProperty()
    public static String TAG_F_NUMBER() {
        return ExifInterface.TAG_F_NUMBER;
    }
    @SimpleProperty()
    public static String TAG_GAIN_CONTROL() {
        return ExifInterface.TAG_GAIN_CONTROL;
    }
    @SimpleProperty()
    public static String TAG_GPS_ALTITUDE() {
        return ExifInterface.TAG_GPS_ALTITUDE;
    }
    @SimpleProperty()
    public static String TAG_GPS_ALTITUDE_REF() {
        return ExifInterface.TAG_GPS_ALTITUDE_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_AREA_INFORMATION() {
        return ExifInterface.TAG_GPS_AREA_INFORMATION;
    }
    @SimpleProperty()
    public static String TAG_GPS_DATESTAMP() {
        return ExifInterface.TAG_GPS_DATESTAMP;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_BEARING() {
        return ExifInterface.TAG_GPS_DEST_BEARING;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_BEARING_REF() {
        return ExifInterface.TAG_GPS_DEST_BEARING_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_DISTANCE() {
        return ExifInterface.TAG_GPS_DEST_DISTANCE;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_DISTANCE_REF() {
        return ExifInterface.TAG_GPS_DEST_DISTANCE_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_LATITUDE() {
        return ExifInterface.TAG_GPS_DEST_LATITUDE;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_LATITUDE_REF() {
        return ExifInterface.TAG_GPS_DEST_LATITUDE_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_LONGITUDE() {
        return ExifInterface.TAG_GPS_DEST_LONGITUDE;
    }
    @SimpleProperty()
    public static String TAG_GPS_DEST_LONGITUDE_REF() {
        return ExifInterface.TAG_GPS_DEST_LONGITUDE_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_DIFFERENTIAL() {
        return ExifInterface.TAG_GPS_DIFFERENTIAL;
    }
    @SimpleProperty()
    public static String TAG_GPS_DOP() {
        return ExifInterface.TAG_GPS_DOP;
    }
    @SimpleProperty()
    public static String TAG_GPS_IMG_DIRECTION() {
        return ExifInterface.TAG_GPS_IMG_DIRECTION;
    }
    @SimpleProperty()
    public static String TAG_GPS_IMG_DIRECTION_REF() {
        return ExifInterface.TAG_GPS_IMG_DIRECTION_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_LATITUDE() {
        return ExifInterface.TAG_GPS_LATITUDE;
    }
    @SimpleProperty()
    public static String TAG_GPS_LATITUDE_REF() {
        return ExifInterface.TAG_GPS_LATITUDE_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_LONGITUDE() {
        return ExifInterface.TAG_GPS_LONGITUDE;
    }
    @SimpleProperty()
    public static String TAG_GPS_LONGITUDE_REF() {
        return ExifInterface.TAG_GPS_LONGITUDE_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_MAP_DATUM() {
        return ExifInterface.TAG_GPS_MAP_DATUM;
    }
    @SimpleProperty()
    public static String TAG_GPS_MEASURE_MODE() {
        return ExifInterface.TAG_GPS_MEASURE_MODE;
    }
    @SimpleProperty()
    public static String TAG_GPS_PROCESSING_METHOD() {
        return ExifInterface.TAG_GPS_PROCESSING_METHOD;
    }
    @SimpleProperty()
    public static String TAG_GPS_SATELLITES() {
        return ExifInterface.TAG_GPS_SATELLITES;
    }
    @SimpleProperty()
    public static String TAG_GPS_SPEED() {
        return ExifInterface.TAG_GPS_SPEED;
    }
    @SimpleProperty()
    public static String TAG_GPS_SPEED_REF() {
        return ExifInterface.TAG_GPS_SPEED_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_STATUS() {
        return ExifInterface.TAG_GPS_STATUS;
    }
    @SimpleProperty()
    public static String TAG_GPS_TIMESTAMP() {
        return ExifInterface.TAG_GPS_TIMESTAMP;
    }
    @SimpleProperty()
    public static String TAG_GPS_TRACK() {
        return ExifInterface.TAG_GPS_TRACK;
    }
    @SimpleProperty()
    public static String TAG_GPS_TRACK_REF() {
        return ExifInterface.TAG_GPS_TRACK_REF;
    }
    @SimpleProperty()
    public static String TAG_GPS_VERSION_ID() {
        return ExifInterface.TAG_GPS_VERSION_ID;
    }
    @SimpleProperty()
    public static String TAG_IMAGE_DESCRIPTION() {
        return ExifInterface.TAG_IMAGE_DESCRIPTION;
    }
    @SimpleProperty()
    public static String TAG_IMAGE_LENGTH() {
        return ExifInterface.TAG_IMAGE_LENGTH;
    }
    @SimpleProperty()
    public static String TAG_IMAGE_UNIQUE_ID() {
        return ExifInterface.TAG_IMAGE_UNIQUE_ID;
    }
    @SimpleProperty()
    public static String TAG_IMAGE_WIDTH() {
        return ExifInterface.TAG_IMAGE_WIDTH;
    }
    @SimpleProperty()
    public static String TAG_INTEROPERABILITY_INDEX() {
        return ExifInterface.TAG_INTEROPERABILITY_INDEX;
    }
    @SimpleProperty()
    public static String TAG_ISO() {
        return ExifInterface.TAG_ISO;
    }
    @SimpleProperty()
    public static String TAG_ISO_SPEED_RATINGS() {
        return ExifInterface.TAG_ISO_SPEED_RATINGS;
    }
    @SimpleProperty()
    public static String TAG_JPEG_INTERCHANGE_FORMAT() {
        return ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT;
    }
    @SimpleProperty()
    public static String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH() {
        return ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH;
    }
    @SimpleProperty()
    public static String TAG_LIGHT_SOURCE() {
        return ExifInterface.TAG_LIGHT_SOURCE;
    }
    @SimpleProperty()
    public static String TAG_MAKE() {
        return ExifInterface.TAG_MAKE;
    }
    @SimpleProperty()
    public static String TAG_MAKER_NOTE() {
        return ExifInterface.TAG_MAKER_NOTE;
    }
    @SimpleProperty()
    public static String TAG_MAX_APERTURE_VALUE() {
        return ExifInterface.TAG_MAX_APERTURE_VALUE;
    }
    @SimpleProperty()
    public static String TAG_METERING_MODE() {
        return ExifInterface.TAG_METERING_MODE;
    }
    @SimpleProperty()
    public static String TAG_MODEL() {
        return ExifInterface.TAG_MODEL;
    }
    @SimpleProperty()
    public static String TAG_NEW_SUBFILE_TYPE() {
        return ExifInterface.TAG_NEW_SUBFILE_TYPE;
    }
    @SimpleProperty()
    public static String TAG_OECF() {
        return ExifInterface.TAG_OECF;
    }
    @SimpleProperty()
    public static String TAG_ORF_ASPECT_FRAME() {
        return ExifInterface.TAG_ORF_ASPECT_FRAME;
    }
    @SimpleProperty()
    public static String TAG_ORF_PREVIEW_IMAGE_LENGTH() {
        return ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH;
    }
    @SimpleProperty()
    public static String TAG_ORF_PREVIEW_IMAGE_START() {
        return ExifInterface.TAG_ORF_PREVIEW_IMAGE_START;
    }
    @SimpleProperty()
    public static String TAG_ORF_THUMBNAIL_IMAGE() {
        return ExifInterface.TAG_ORF_THUMBNAIL_IMAGE;
    }
    @SimpleProperty()
    public static String TAG_ORIENTATION() {
        return ExifInterface.TAG_ORIENTATION;
    }
    @SimpleProperty()
    public static String TAG_PHOTOMETRIC_INTERPRETATION() {
        return ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION;
    }
    @SimpleProperty()
    public static String TAG_PIXEL_X_DIMENSION() {
        return ExifInterface.TAG_PIXEL_X_DIMENSION;
    }
    @SimpleProperty()
    public static String TAG_PIXEL_Y_DIMENSION() {
        return ExifInterface.TAG_PIXEL_Y_DIMENSION;
    }
    @SimpleProperty()
    public static String TAG_PLANAR_CONFIGURATION() {
        return ExifInterface.TAG_PLANAR_CONFIGURATION;
    }
    @SimpleProperty()
    public static String TAG_PRIMARY_CHROMATICITIES() {
        return ExifInterface.TAG_PRIMARY_CHROMATICITIES;
    }
    @SimpleProperty()
    public static String TAG_REFERENCE_BLACK_WHITE() {
        return ExifInterface.TAG_REFERENCE_BLACK_WHITE;
    }
    @SimpleProperty()
    public static String TAG_RELATED_SOUND_FILE() {
        return ExifInterface.TAG_RELATED_SOUND_FILE;
    }
    @SimpleProperty()
    public static String TAG_RESOLUTION_UNIT() {
        return ExifInterface.TAG_RESOLUTION_UNIT;
    }
    @SimpleProperty()
    public static String TAG_ROWS_PER_STRIP() {
        return ExifInterface.TAG_ROWS_PER_STRIP;
    }
    @SimpleProperty()
    public static String TAG_RW2_ISO() {
        return ExifInterface.TAG_RW2_ISO;
    }
    @SimpleProperty()
    public static String TAG_RW2_JPG_FROM_RAW() {
        return ExifInterface.TAG_RW2_JPG_FROM_RAW;
    }
    @SimpleProperty()
    public static String TAG_RW2_SENSOR_BOTTOM_BORDER() {
        return ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER;
    }
    @SimpleProperty()
    public static String TAG_RW2_SENSOR_LEFT_BORDER() {
        return ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER;
    }
    @SimpleProperty()
    public static String TAG_RW2_SENSOR_RIGHT_BORDER() {
        return ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER;
    }
    @SimpleProperty()
    public static String TAG_RW2_SENSOR_TOP_BORDER() {
        return ExifInterface.TAG_RW2_SENSOR_TOP_BORDER;
    }
    @SimpleProperty()
    public static String TAG_SAMPLES_PER_PIXEL() {
        return ExifInterface.TAG_SAMPLES_PER_PIXEL;
    }
    @SimpleProperty()
    public static String TAG_SATURATION() {
        return ExifInterface.TAG_SATURATION;
    }
    @SimpleProperty()
    public static String TAG_SCENE_CAPTURE_TYPE() {
        return ExifInterface.TAG_SCENE_CAPTURE_TYPE;
    }
    @SimpleProperty()
    public static String TAG_SCENE_TYPE() {
        return ExifInterface.TAG_SCENE_TYPE;
    }
    @SimpleProperty()
    public static String TAG_SENSING_METHOD() {
        return ExifInterface.TAG_SENSING_METHOD;
    }
    @SimpleProperty()
    public static String TAG_SHARPNESS() {
        return ExifInterface.TAG_SHARPNESS;
    }
    @SimpleProperty()
    public static String TAG_SHUTTER_SPEED_VALUE() {
        return ExifInterface.TAG_SHUTTER_SPEED_VALUE;
    }
    @SimpleProperty()
    public static String TAG_SOFTWARE() {
        return ExifInterface.TAG_SOFTWARE;
    }
    @SimpleProperty()
    public static String TAG_SPATIAL_FREQUENCY_RESPONSE() {
        return ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE;
    }
    @SimpleProperty()
    public static String TAG_SPECTRAL_SENSITIVITY() {
        return ExifInterface.TAG_SPECTRAL_SENSITIVITY;
    }
    @SimpleProperty()
    public static String TAG_STRIP_BYTE_COUNTS() {
        return ExifInterface.TAG_STRIP_BYTE_COUNTS;
    }
    @SimpleProperty()
    public static String TAG_STRIP_OFFSETS() {
        return ExifInterface.TAG_STRIP_OFFSETS;
    }
    @SimpleProperty()
    public static String TAG_SUBFILE_TYPE() {
        return ExifInterface.TAG_SUBFILE_TYPE;
    }
    @SimpleProperty()
    public static String TAG_SUBJECT_AREA() {
        return ExifInterface.TAG_SUBJECT_AREA;
    }
    @SimpleProperty()
    public static String TAG_SUBJECT_DISTANCE() {
        return ExifInterface.TAG_SUBJECT_DISTANCE;
    }
    @SimpleProperty()
    public static String TAG_SUBJECT_DISTANCE_RANGE() {
        return ExifInterface.TAG_SUBJECT_DISTANCE_RANGE;
    }
    @SimpleProperty()
    public static String TAG_SUBJECT_LOCATION() {
        return ExifInterface.TAG_SUBJECT_LOCATION;
    }
    @SimpleProperty()
    public static String TAG_SUBSEC_TIME() {
        return ExifInterface.TAG_SUBSEC_TIME;
    }
    @SimpleProperty()
    public static String TAG_SUBSEC_TIME_DIG() {
        return ExifInterface.TAG_SUBSEC_TIME_DIG;
    }
    @SimpleProperty()
    public static String TAG_SUBSEC_TIME_DIGITIZED() {
        return ExifInterface.TAG_SUBSEC_TIME_DIGITIZED;
    }
    @SimpleProperty()
    public static String TAG_SUBSEC_TIME_ORIG() {
        return ExifInterface.TAG_SUBSEC_TIME_ORIG;
    }
    @SimpleProperty()
    public static String TAG_SUBSEC_TIME_ORIGINAL() {
        return ExifInterface.TAG_SUBSEC_TIME_ORIGINAL;
    }
    @SimpleProperty()
    public static String TAG_THUMBNAIL_IMAGE_LENGTH() {
        return ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH;
    }
    @SimpleProperty()
    public static String TAG_THUMBNAIL_IMAGE_WIDTH() {
        return ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH;
    }
    @SimpleProperty()
    public static String TAG_TRANSFER_FUNCTION() {
        return ExifInterface.TAG_TRANSFER_FUNCTION;
    }
    @SimpleProperty()
    public static String TAG_USER_COMMENT() {
        return ExifInterface.TAG_USER_COMMENT;
    }
    @SimpleProperty()
    public static String TAG_WHITE_BALANCE() {
        return ExifInterface.TAG_WHITE_BALANCE;
    }
    @SimpleProperty()
    public static String TAG_WHITE_POINT() {
        return ExifInterface.TAG_WHITE_POINT;
    }
    @SimpleProperty()
    public static String TAG_XMP() {
        return ExifInterface.TAG_XMP;
    }
    @SimpleProperty()
    public static String TAG_X_RESOLUTION() {
        return ExifInterface.TAG_X_RESOLUTION;
    }
    @SimpleProperty()
    public static String TAG_Y_CB_CR_COEFFICIENTS() {
        return ExifInterface.TAG_Y_CB_CR_COEFFICIENTS;
    }
    @SimpleProperty()
    public static String TAG_Y_CB_CR_POSITIONING() {
        return ExifInterface.TAG_Y_CB_CR_POSITIONING;
    }
    @SimpleProperty()
    public static String TAG_Y_CB_CR_SUB_SAMPLING() {
        return ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING;
    }
    @SimpleProperty()
    public static String TAG_Y_RESOLUTION() {
        return ExifInterface.TAG_Y_RESOLUTION;
    }

}
