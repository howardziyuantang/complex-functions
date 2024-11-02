import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ComplexPlane extends JPanel implements /*KeyListener, */MouseListener, ActionListener {

    private Dimension dimension;
    //private char function;
    private double unitLength, p = 1, a, b = 0;
    private int frames, iBounds, jBounds, origFrames;
    private ComplexNum[][] points;
    private JLabel pLabel, aLabel, bLabel;
    private double[][][] shifts;
    private Timer timer;
    private boolean showGrid, shift;

    public ComplexPlane(Dimension dimension, double unitLength,/* char f,*/ int frames, boolean showGrid) {

        setBackground(Color.BLACK);
        this.dimension = dimension;
        setPreferredSize(dimension);
        this.unitLength = unitLength;
        
        iBounds = (int) (dimension.width / unitLength / 2 + 1);
        jBounds = (int) (dimension.height / unitLength / 2 + 1);

        /*this.function = f;
        
        if(f == 'p') {
            pLabel = new JLabel("p: " + p);
            pLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
            pLabel.setBackground(Color.BLACK);
            pLabel.setForeground(Color.WHITE);
            this.add(pLabel);
        } else {
            a = (f == 'a' || f == 's') ? 0: 1;
            aLabel = new JLabel("a: " + a);
            aLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
            aLabel.setBackground(Color.BLACK);
            aLabel.setForeground(Color.WHITE);
            aLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
            bLabel = new JLabel("b: " + b);
            bLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
            bLabel.setBackground(Color.BLACK);
            bLabel.setForeground(Color.WHITE);
            bLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
            this.add(aLabel); this.add(bLabel);
        }*/
        
        this.frames = frames;
        origFrames = frames;
        this.showGrid = showGrid;
        shift = false;
        setVisible(true);
        addMouseListener(this);
        setFocusable(true);
        //addKeyListener(this);
        initPoints();

    }

    public void initPoints() {
        this.points = new ComplexNum[iBounds * 2 + 1][jBounds * 2 + 1];
        shifts = new double[iBounds*2+1][jBounds*2+1][2];
        for(int i = -iBounds; i <= iBounds; i++) {
            for (int j = -jBounds; j <= jBounds; j++) {
                ComplexNum current = new ComplexNum(i, j);

                ComplexNum newNum = current.power(2);
//                ComplexNum newNum = current.power(3);
//                ComplexNum newNum = current.power(1d/2d);
//                ComplexNum newNum = current.power(4d/5d);
//                ComplexNum newNum = current.multiply(new ComplexNum(4, 2));
//                ComplexNum newNum = current.multiply(new ComplexNum(-2, -.7));

                points[i + iBounds][j + jBounds] = current;
                shifts[i + iBounds][j + jBounds] = new double[]{(newNum.real - current.real) / frames, (newNum.imaginary - current.imaginary) / frames};
//                switch(function) {
//                    case 'p':
//                        newNum = current.power(p);
//                        break;
//                    case 'a':
//                        newNum = current.add(new ComplexNum(a, b));
//                        break;
//                    case 's':
//                        newNum = current.subtract(new ComplexNum(a, b));
//                        break;
//                    case 'm':
//                        newNum = current.multiply(new ComplexNum(a, b));
//                        break;
//                    case 'd':
//                        newNum = current.divide(new ComplexNum(a, b));
//                        break;
//                    default:
//                        return;
//                }
            }
        }
        repaint();
    }

//    @Override
//    public void keyTyped(KeyEvent e) {
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        char c = e.getKeyChar();
//        if(c == 'i') {
//            unitLength *= 1.02;
//            repaint();
//            return;
//        }
//        if(c == 'o') {
//            unitLength *= 0.98;
//            repaint();
//            return;
//        }
        /*switch(c) {
            case '+':
                p += 1d/99;
                break;
            case '-':
                p -= 1d/99;
                break;
            case 'a':
                a -= 0.1;
                break;
            case 'd':
                a += 0.1;
                break;
            case 'w':
                b += 0.1;
                break;
            case 's':
                b -= 0.1;
                break;
            default:
                return;
        }
        updateLabels();
        initPoints(false);*/
//    }

//    private void updateLabels() {
//        if(function == 'p') pLabel.setText("p: " + p);
//        else {
//            aLabel.setText("a: " + a);
//            bLabel.setText("b: " + b);
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if(showGrid) {
            g2d.setPaint(new Color(255, 255, 255, 150));
            g2d.setStroke(new BasicStroke(1));
            double centerX = dimension.width / 2d, centerY = dimension.height / 2d;
            double lineX = centerX - (centerX / unitLength) * unitLength, lineY = centerY - (centerY / unitLength) * unitLength;
            while(lineX < dimension.width) {
                if(lineX == centerX) g2d.setStroke(new BasicStroke(3));
                g2d.drawLine((int) lineX, 0, (int) lineX, dimension.height);
                lineX += unitLength;
                g2d.setStroke(new BasicStroke(1));
            }
            while(lineY < dimension.height) {
                if(lineY == centerY) g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(0, (int) lineY, dimension.width, (int) lineY);
                lineY += unitLength;
                g2d.setStroke(new BasicStroke(1));
            }
        }

        if(points != null) {
            g2d.setPaint(Color.RED);
            g2d.setStroke(new BasicStroke(2));
            for(int i = 0; i < points.length; i++) {
                for(int j = 0; j < points[0].length; j++) {
                    if(points[i][j] == null) continue;
                    int[] current = getCoordinates(points[i][j]);
                    try {
                        int[] right = getCoordinates(points[i + 1][j]);
                        g2d.drawLine(current[0], current[1], right[0], right[1]);
                    } catch(Exception ignored) {}
                    try {
                        int[] down = getCoordinates(points[i][j + 1]);
                        g2d.drawLine(current[0], current[1], down[0], down[1]);
                    } catch(Exception ignored) {}
                }
            }
        }

    }

    private int[] getCoordinates(ComplexNum n) {

        return new int[]{(int) (n.real * unitLength + dimension.width / 2d), (int) (dimension.height / 2d - n.imaginary * unitLength)};

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        timer = new Timer(4000 / frames, this);
        timer.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0; i < points.length; i++) {
            for(int j = 0; j < points[0].length; j++) {
                if(points[i][j] == null) continue;
                points[i][j].real += shifts[i][j][0];
                points[i][j].imaginary += shifts[i][j][1];
//                points[i][j] = (new ComplexNum(i-iBounds, j-jBounds)).power(1+2d*(origFrames-frames)/origFrames);
            }
        }
        repaint();
        frames--;
        if(frames == 0) timer.stop();

    }

}
