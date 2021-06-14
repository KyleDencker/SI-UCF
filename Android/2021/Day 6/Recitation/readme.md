Tile Map Collision, Sprite Animation, and Camera Magic -
----------------------------------------------------------------

A continuation from Friday's Lecture (day 5) where we introduced Tile Maps, and Sprite Sheets.

***Helpful Links:***

TILED Map Editor - https://www.mapeditor.org/

Tile Sets for download - https://itch.io/game-assets/tag-tileset


Animating the Player, continued
-------------------------------------

We set up column variable depending on the player's orientation / velocity, and a row variable that would increment at a timed interval

![Sprite Sheet](https://user-images.githubusercontent.com/51721851/121941015-c1da0000-cd1c-11eb-9037-de028e9a5b7a.png)


***We don't want to use setRegion() until after all the logic is done! Calling setRegion() multiple times in a single frame is problematic***

Collision
-----------------------------------

We need an object layer in order for our player to interact with the Tile Map

![image](https://user-images.githubusercontent.com/51721851/121941420-4036a200-cd1d-11eb-8553-e4d780d74c8e.png)

We extract only the RectangleMapObject objects from the Object Layer, to reduce search time.


We pass in a LibGDX Array of Rectangle Map objects into our player, so it can determine if it is colliding with anything.

If we're touching a box, let's go back to where we were at the beginning of the frame.

```

 for (RectangleMapObject rectangleObject : array)
 {
            Rectangle rectangle = rectangleObject.getRectangle();

            if (rectangle.overlaps(hitBox))
            {
                sprite.setPosition(oldX, oldY);
                hitBox.setPosition(oldX, oldY);
            }
  }
        
 ```
 
 
 Camera Stuff
 ---------------------------
 We made another room on TILED, and I wanted to be able to navigate between the two rooms.
 
 I showed off two ways we could accomplish this: 
 1.) The camera follows the player, clamped to the contraints of the map
 
 You may want to do this in the y direction as well
 `camera.position.x = player.getPosition().x;`
 `camera.position.x = MathUtils.clamp(camera.position.x, min, max);`
 
 2.) The camera remains static, and moves when we walk through a door, like in Binding of Issac.
 
 ```
 
 if (player.foundDoor())
		{
			System.out.println(player.getPosition().x);
			if (player.getPosition().x < camera.viewportWidth)
			{
				camera.position.x += camera.viewportWidth;
				player.setPosition(other side of the door);

			}

			else
			{
				camera.position.x -= camera.viewportWidth;
				player.setPosition(opposite side of the door);
			}

			player.foundDoor = false;
		}
  ```

The code is separated in two folders, one for each method.

The static camera method is a bit more complicated, because we have to handle a separate object layer for doors.






