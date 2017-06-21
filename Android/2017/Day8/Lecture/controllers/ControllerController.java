package com.c4cheats.easycontroller.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by kyledencker on 6/21/17.
 */

public class ControllerController implements ControllerListener {

    public static float[] axisValue = new float[4];
    public static boolean[] buttonPressed = new boolean[10];

    @Override
    public void connected(Controller controller) {
        System.out.println("New controller added!");
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("Controller disconnected");
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        buttonPressed[buttonCode] = true;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        buttonPressed[buttonCode] = false;
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        axisValue[axisCode] = value;
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
