package gabrielmendessc.com;

import gabrielmendessc.com.view.AppFrame;
import gabrielmendessc.com.view.AppScreen;
import gabrielmendessc.com.view.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static int FPS = 0;
    public static int FRAMES = 0;
    private static final int FRAME_RATE = 100;
    private static final double FRAME_TIME = (double) (1 * 1000000000) / FRAME_RATE;

    public static void main(String[] args) {

        AppScreen appScreen = new AppScreen();
        AppFrame appFrame = new AppFrame(appScreen);

        double lastFrameTime = System.nanoTime();
        double elapsedFrameTime = 0;

        new Thread(Main::checkFpsLoop).start();

        while (true) {

            long currentFrameTime = System.nanoTime();

            elapsedFrameTime += calculateElapsedFrameTime(lastFrameTime, currentFrameTime);
            lastFrameTime = currentFrameTime;

            if (elapsedFrameTime >= 1) {

                appScreen.simulate();

                appFrame.repaint();

                FRAMES++;

                elapsedFrameTime--;

            }

        }

    }

    public static void checkFpsLoop() {

        double lastCheckTime = System.nanoTime();

        while (true) {

            if (hasOneSecondElapsed(lastCheckTime)) {

                FPS = FRAMES;
                FRAMES = 0;
                lastCheckTime = System.nanoTime();

            }

        }

    }

    private static boolean hasOneSecondElapsed(double lastCheckTime) {

        return System.nanoTime() - lastCheckTime >= 1 * 1000000000;

    }

    private static double calculateElapsedFrameTime(double lastFrameTime, long currentFrameTime) {

        return (currentFrameTime - lastFrameTime) / FRAME_TIME;

    }

}
