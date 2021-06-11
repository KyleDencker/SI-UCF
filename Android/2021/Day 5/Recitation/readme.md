Intro to TileMaps, TileSets and Sprite Sheets!
-----------------------------------------
We started talking about tile maps and will continue them on Monday

It would be super tedious to hard code every single sprite into a large game like Pokemon, Legend of Zelda, or Celeste

Instead, we can draw out a map on TILED -> https://www.mapeditor.org/


On TILED, we take elements from a tile set and paint them on various layers of the map.

You can find premade TileSets to download here -> https://itch.io/game-assets/tag-tileset



***SAVE EVERYTHING INTO THE ASSETS FOLDER OF YOUR PROJECT!!***


In LibGDX, we use the TiledMap and TiledMapRenderer object to draw the map to the screen, BEFORE we render the sprite so the sprite can be on top.

camera viewport width and height may need to be adjusted to fit the map.













