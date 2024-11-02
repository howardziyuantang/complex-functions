import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Complex Functions");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double unitLength = 20d;
        int frames = 300;
        ComplexPlane plane = new ComplexPlane(screenSize, unitLength, frames, false);
        frame.add(plane);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }

}
