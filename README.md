# ExifParse Appinventor Extension

This was an extension primarily created for personal use in a project in my AP Computer Science Principles class. Anyone is
free to use it if they find it useful.

## How to use

This plugin is designed to be used in conjunction with the ImagePicker or FilePicker blocks. Once you have imported
the extension (using Extension → Import extension in the sidebar), drag the ExifParse component to the screen.
It should display as a non-visible component.

Then, add a code block for ImagePicker.AfterPicking, feeding ImagePicker.Selection and
the EXIF tag you want into ExifParse.getAttribute/setAttribute. For more information
about what the a specific tag means, see the Android Documentation for
[ExifInterface](https://developer.android.com/reference/android/media/ExifInterface),
or [General EXIF Tag Information](https://exiftool.org/TagNames/EXIF.html).

![Layout](/example/layout.png)
![Code snippet](/example/codesnippet.png)

### App Demo

[App demo](/example/demo.mp4)

## Methods

There are only a few procedures that are added with this extension. They have been kept rather simple, so as much of
the code I would use would take place in appinventor instead of the extension. They are as follows:

### getAttribute

- this method takes two arguments
  - a tag name (use the constants provided)
  - a file path (you can get this using something like ImagePicker.Selection)
- It returns the value of the specified tag. If the tag doesn't exist, it returns an empty string.

### setAttributes

- This method takes in two arguments
  - A dictionary of tag names and values (tagname: value)
  - A file path (you can get this from something like ImagePicker.Selection)
- This method will write the new set of attributes to the file provided.

### setAttributesCopy

- This is the same as setAttribute, but it will write to a new file instead.
- This method takes one new argument
  - An output file path, which is where the new metadata will be written to.

### Constants

- All other member attributes are constants. They all represent different EXIF tags, which all need to be set to something
  different. To learn more about a specific tag, look in the [ExifInterface Documentation](https://developer.android.com/reference/android/media/ExifInterface#constants_1).
  Each constant here should mirror the constants in ExifInterface.

## How to build:

```shell
ant extensions
```

This will put an .aix file in `out`, at which point you can import it into appinventor and use it like any other code block.
