package com.c4cheats.mathyfish.tools;

import com.badlogic.gdx.utils.Pool;
import com.c4cheats.mathyfish.objects.BasicFish;
import com.c4cheats.mathyfish.objects.Fish;

/**
 * Created by Kyle Dencker on 6/16/16.
 */
public class FishPool extends Pool<Fish>{
    @Override
    protected Fish newObject() {
        return new BasicFish(0, 0);
    }
}
