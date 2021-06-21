package com.fhsps.clicker.tools;

import com.badlogic.gdx.utils.Pool;
import com.fhsps.clicker.Timer;

public class TimerPool extends Pool<Timer> {
    @Override
    protected Timer newObject() {
        return new Timer(0, 0);
    }
}
