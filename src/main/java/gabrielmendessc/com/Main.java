package gabrielmendessc.com;

import gabrielmendessc.com.view.AppFrame;
import gabrielmendessc.com.view.AppScreen;

public class Main {

    public static int FPS = 0;
    public static int FRAMES = 0;

    private static final int UPDATE_RATE = 90;
    private static final double UPDATE_TIME = (double) (1 * 1000000000) / UPDATE_RATE;

    private static AppScreen appScreen;
    private static AppFrame appFrame;

    public static void main(String[] args) {

        appScreen = new AppScreen();
        appFrame = new AppFrame(appScreen);

        new Thread(Main::renderLoop).start();
        new Thread(Main::simulationLoop).start();
        new Thread(Main::checkFpsLoop).start();


    }

    public static void renderLoop() {

        while (true) {

            appFrame.repaint();

            FRAMES++;

        }

    }

    public static void simulationLoop() {

        double lastFrameTime = System.nanoTime();
        double elapsedFrameTime = 0;

        while (true) {

            long currentFrameTime = System.nanoTime();

            elapsedFrameTime += calculateElapsedUpdateTime(lastFrameTime, currentFrameTime);
            lastFrameTime = currentFrameTime;

            if (elapsedFrameTime >= 1) {

                appScreen.simulate();

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

    private static double calculateElapsedUpdateTime(double lastUpdateTime, long currentUpdateTime) {

        return (currentUpdateTime - lastUpdateTime) / UPDATE_TIME;

    }

}
