MainScreen.java is the Music Player we made during Recitation

Remember: Music and Sound are different in libGDX. Check the example for more details.


Custom Sprites
----------------------------

Itch.IO game assets -> https://itch.io/game-assets/free


Piskel Sprite Editor -> https://www.piskelapp.com/


Sound Effects
----------------------------

Cool sound effects -> https://freesound.org/


ChipTone sound generation tool -> https://sfbgames.itch.io/chiptone

```
// Declare Sound object
Sound sound;

...

//Initialize it!
sound = Gdx.audio.newSound(Gdx.files.internal("sound_file_name.wav"));

...

// This is probably all you'll need, but there are other helpful methods
sound.play();

```


Music
------------------------------

Copyright free music -> https://incompetech.com/music/royalty-free/music.html


Beepbox music maker!!! -> https://www.beepbox.co/

```
//Declare Music object
Music song;

...

// Initialize and set characteristics
song = Gdx.audio.newMusic(Gdx.files.internal("music_file_name.mp3"));
song.setVolume(volume);
song.setLooping(true);

...

// Song functions (Very easy to use)
song.play();
song.pause();
song.stop();
song.setVolume(float between 0 and 1)

```



