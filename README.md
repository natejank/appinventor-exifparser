# ExifParse Appinventor Extension
This was an extension primarily created for a midyear project for my AP Computer Science Principles class.  Anyone is 
free to use it, but this wasn't intended for general use, so it might not work as expected.

## Usage:
There are only a few procedures that are added with this extension.  They have been kept rather simple, so as much of
the code I would use would take place in appinventor instead of the extension.  They are as follows:

### getAttribute
 - this method takes two arguments
     - a tag name (use the constants provided)
     - a file path (you can get this using something like ImagePicker.Selection)
 - as one might think, this method gets the value from a specified tag.  If the tag doesn't exist, it returns an empty
string
   
### setAttributes
 - This method takes in two arguments
     - A dictionary of tag names and values (tagname: value)
        - while this probably seems like an odd choice, it was the easiest way to do this.  In order to actually write 
          values to a file, android will write a new file with the tags, delete the old one, and copy it back.  As one 
          might imagine, this is a very costly operation.  This essentially meant that if I wanted to write 1 singular 
          method to both change and write (which I had to, because I decided to avoid having a permanent ExifInterface
          object for separate reasons), it would have to be done in a batch instead of one by one.
      - A file path (you can get this from something like ImagePicker.Selection)
 - This doesn't return anything, but it will write the file to where you choose

### setAttributesCopy
 - This is essentially the same as setAttribute, but instead it also allows you to copy the output to a new file.  
   This needed to be a new method because AppInventor doesn't support overloading.
 - This method takes in one new argument
    - An output file path, which is where the new metadata will be written to.
   
### Constants
 - As you may see, there are a lot of constants in ExifParse.  There are no nonstatic variables in this extension, so
   if it wasn't mentioned above, it's a constant.  These are all possible exif tag names, which may or may not exist in 
   your specific image.  Each one needs to be set to something different.  To learn more about a specific tag, search 
   the [ExifInterface Documentation](https://developer.android.com/reference/android/media/ExifInterface). Each variable
   name was copied directly from the Java class, so each name should be the same.

## How to build:
Because this doesn't use anything other than Android and Java's standard library,
all you need is to build it and add it to your project.
```shell
ant extensions
```
This will put an .aix file in `out`, at which point you can import it into appinventor, and use away!