package Paint;

import java.awt.*;

public class Line {
    int oldX, oldY, currentX, currentY;
    Color color;
    public float thickness;

    public Line(int ox, int oy, int cx, int cy, Color c, float t){
        oldX = ox;
        oldY = oy;
        currentX = cx;
        currentY = cy;
        color = c;
        thickness = t;
    }
}
