Bitmap Fonts are really simple!
----------------------------------

For text that you need to change on the fly, this is your best bet.

You can use sprites of fancier text for titles, menus, etc.

```
// Declare
BitMapFont someName;

// Initialize
someName = new BitMapFont();


// In the render method
someName.getData().setScale(float to scale by)
someName.draw(batch, "string goes here", xCoord, yCoord);

```



