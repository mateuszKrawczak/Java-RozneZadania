import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import javafx.geometry.Rectangle2D;

public class Ball {
	public void move(Rectangle rectangle) {
		x+=dx;
		y+=dy;
		if(x<rectangle.getMinX()) {
			x=rectangle.getMinX();
			dx=-dx;
			}
		if(x+XSIZE>=rectangle.getMaxX()) {
			x=rectangle.getMaxX()-XSIZE;
			dx=-dx;
			}
		if(y<rectangle.getMinY()) {
			y=rectangle.getMinY();
			dy=-dy;
			}
		if(y+YSIZE>=rectangle.getMaxY()) {
			y=rectangle.getMaxY()-YSIZE;
			dy=-dy;
			}
	}
	
	public Ellipse2D getShape() {
		return new Ellipse2D.Double(x,y,XSIZE,YSIZE);
	}
	private static final int XSIZE=15;
	private static final int YSIZE=15;
	private double x=0;
	private double y=0;
	private double dx=1;
	private double dy=1;
}
