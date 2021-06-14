Bit-Map Fonts are really simple!
----------------------------------

For text that you need to change on the fly, this is your best bet.

You can use sprites of fancier text for titles, menus, etc.

```
// Declare
BitMapFont someName;

// Initialize
someName = new BitMapFont();


// In the render method
BitMapFont.getData().setScale(float to scale by)
BitMapFont.draw(batch, "string goes here", xCoord, yCoord);

```



