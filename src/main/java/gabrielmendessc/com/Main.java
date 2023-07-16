package gabrielmendessc.com;

import gabrielmendessc.com.view.AppFrame;
import gabrielmendessc.com.view.AppScreen;

public class Main {

    private static final Object object = new Object();

    public static void main(String[] args) {

        AppFrame appFrame = new AppFrame(new AppScreen());

        while (true) {

            appFrame.repaint();

        }

    }

}
