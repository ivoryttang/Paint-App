package Paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;
import java.util.ArrayList;



/**
 * Component for drawing
 */
public class DrawingArea extends JComponent {

    //image in which we're going to draw
    private Image image;
    // Graphics 2D object
    private Graphics g;
    //Mouse Coordinate
    private int currentX, currentY, oldX, oldY, lastX, lastY;
    public float thickness;
    public static Color color;
    public static boolean clear = false;
    public static boolean undo = false;
    public ArrayList<Line> drawnLines = new ArrayList<Line>();
    public int countUndo = 0;

    public DrawingArea() {

        if (image == null) {
            //if image to draw null, we create
            //image = createImage(getSize().width,getSize().height);
            image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB_PRE);
            g = image.getGraphics();
            //clear drawing area
            color = Color.BLACK;
        }

        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                oldX = e.getX();
                oldY = e.getY();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                lastX = e.getX();
                lastY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                //coord x, y when drag mouse
                currentX = e.getX();
                currentY = e.getY();
                if (g != null) {
                    //draw line if g context is not null
                    g.setColor(color);
                    g.drawLine(oldX, oldY, currentX, currentY);
                    drawnLines.add(new Line(oldX, oldY, currentX, currentY, color, thickness));
                    //refresh drawing area so repaint
                    repaint();
                    //store current coordinates x, y, as old x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (clear){
            System.out.println("Cleared....");
            this.g.setColor(getBackground());
            for (Line l : drawnLines){
                this.g.drawLine(l.oldX,l.oldY,l.currentX,l.currentY);
            }
            drawnLines.clear();
        }
        else if (undo){
            ArrayList<Line> drawnLinesCopy = drawnLines;
            System.out.println("undo...");
            this.g.setColor(getBackground());
            g.setColor(getBackground());
            for (Line l : drawnLinesCopy){
                if (drawnLinesCopy.indexOf(l) >= (drawnLinesCopy.size() - 50)) {
                    g2.setStroke(new BasicStroke(l.thickness));
                    g2.drawLine(l.oldX, l.oldY, l.currentX, l.currentY);
                    //g.drawLine(l.oldX, l.oldY, l.currentX, l.currentY);
                }
            }
            for (Line l : drawnLines){
                this.g.setColor(l.color);
                g.setColor(l.color);
                if (drawnLinesCopy.indexOf(l) < (drawnLinesCopy.size() - 50)){
                    g2.setStroke(new BasicStroke(l.thickness));
                    g2.drawLine(l.oldX, l.oldY, l.currentX, l.currentY);
                    //g.drawLine(l.oldX, l.oldY, l.currentX, l.currentY);
                }
            }
            //get rid of erased/undid lines in drawnlines
            int size = drawnLinesCopy.size();
            if (size >= 50){
                for (int i = (size - 1); i >= (size - 50); i--) {
                    drawnLines.remove(i);
                }
            }
            else{
                for (int i = (size - 1); i >= 0; i--) {
                    drawnLines.remove(i);
                }
            }
        }
        else { //draw
            for (Line l : drawnLines){
                this.g.setColor(l.color);
                g.setColor(l.color);
                g2.setStroke(new BasicStroke(l.thickness));
                g2.drawLine(l.oldX, l.oldY, l.currentX, l.currentY);
            }
            //g2.drawImage(image, 0,0,null);
            //this.g.drawImage(image, 0, 0,null);
            //g.drawImage(image, 0, 0,null);
        }
    }

    public void setColor(Color c) {
        DrawingArea.color = c;
    }

    public void setThickness(Line l, int i) {
        l.thickness = i;
    }

    public float getThickness(Line l){
        return l.thickness;
    }
}
