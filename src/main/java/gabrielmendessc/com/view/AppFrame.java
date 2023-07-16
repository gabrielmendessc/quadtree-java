package gabrielmendessc.com.view;

import javax.swing.JFrame;
import java.awt.Dimension;

public class AppFrame extends JFrame {

    public AppFrame(AppScreen appScreen) {

        setSize(800, 800);
        setPreferredSize(new Dimension(800, 800));
        addMouseListener(new MouseListenerImpl());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(appScreen);
        setVisible(true);
        pack();

    }

}
