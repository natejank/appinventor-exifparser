package net.sloshy.aiextensions.ExifParseNonStatic;

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


@DesignerComponent(version = 1,
        category = ComponentCategory.EXTENSION,
        description = "An extension to allow for parsing of EXIF metadata within app inventor.  This version has" +
                "nonstatic member variables.",
        nonVisible = true, iconName = "")
@UsesPermissions()
@SimpleObject(external = true)
public class ExifParseNonStatic extends AndroidNonvisibleComponent {
    protected ExifInterface metadataInterface = null;
    private String activeFilePath = null;
    private File cacheDir;

    public ExifParseNonStatic(ComponentContainer container) {
        super(container.$form());
        cacheDir = container.$form().getCacheDir();
    }

    private static ExifInterface getExifInterface(String filePath) throws IOException {
        return new ExifInterface(new File(filePath));
    }

    /*Over here on the Non-static side of ExifParse, we like to live it large.  You do something dumb like forget
     * to initialize your ExifInterface, you deal with the consequences ¯\_(ツ)_/¯*/

    @SimpleFunction(description = "Sets the active file.  This is required, because all other functions rely on the " +
            "objects initialized here.  If you don't use this first, you'll see a whole lotta null.")
    public void setActiveFile(String filePath) throws IOException {
        metadataInterface = new ExifInterface(new File(filePath));
        activeFilePath = filePath;
    }

    @SimpleFunction(description = "Gets the current file path")
    public String getActiveFilePath() {
        return activeFilePath;
    }

    @SimpleFunction(description = "Gets the specified attribute.  " +
            "If the image doesn't have the attribute, returns an empty string.")
    public String getAttribute(String tagName) throws IOException {
        String attr = metadataInterface.getAttribute(tagName);
        return (attr == null ? "" : attr);
    }
    @SimpleFunction(description = "Sets a given attribute.  This only changes the attribute in memory," +
            "so to make it permanent YOU ACTUALLY HAVE TO WRITE IT TO A FILE")
    public void setAttribute(String tag, String value) {
        metadataInterface.setAttribute(tag, value);
    }

    @SimpleFunction(description = "Writes the new attributes to a file.")
    public void saveAttributes() throws IOException {
        metadataInterface.saveAttributes();
    }

    @SimpleFunction(description = "Writes the new attributes to a file.  It first backs up the original file.  " +
            "Incredibly costly in terms of reading and writing, but we like to live it large around here 8)")
    public void saveAttributesCopy(String filePath) throws IOException {
        // Holy heck welcome to jank city, I'm Nathan Jankowski how may I help you?
        // TODO don't overwrite old files
        Path activeFile = Paths.get(activeFilePath);
        Path backupFile = Paths.get(cacheDir.getPath() + activeFile.getName(-1));

        Files.copy(activeFile, backupFile);
        saveAttributes();
        Files.move(activeFile, Paths.get("washed_"+activeFilePath), StandardCopyOption.REPLACE_EXISTING);
        Files.move(backupFile, activeFile);
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
