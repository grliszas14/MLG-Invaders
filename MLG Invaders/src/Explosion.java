import java.awt.*;

public class Explosion extends Engine{

	private double x;
	private double y;
	private int r;
	private int maxRadius;
	
	public Explosion(double x, double y, int r, int max){
		this.x = x;
		this.y = y;
		this.r = r;
		this.maxRadius = max;
		
	}
	
	public boolean update(){
		r++;
		if(r >= maxRadius){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g, double factorWidth, double factorHeight){
		g.setColor(new Color(255,255,255,128));
		g.setStroke(new BasicStroke(3));
		g.drawOval((int) ((x-r)*factorWidth), (int) ((y-r)*factorHeight), (int) (2 * r * factorWidth), (int) (2 * r * factorHeight));
	}
}