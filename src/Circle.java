
public class Circle 
{
	static int circCount = 0;
	double radius, xCoord, yCoord;
	
	public Circle()
	{
		radius = xCoord = yCoord = 0;	
	}
	
	public Circle(double r, double x, double y)
	{
		radius = r;
		xCoord = x;
		yCoord = y;
	}
	
	public Circle(Circle other)
	{
		radius = other.radius;
		xCoord = other.xCoord;
		yCoord = other.yCoord;
		circCount++;
	}
	
	public void printCircle()
	{
		System.out.println("(" + xCoord + ", " + yCoord + ") " + "radius = " + radius);
	}
	
	public double getX()
	{
		return xCoord;
	}
	
	public double getY()
	{
		return yCoord;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public void setX(double x)
	{
		this.xCoord = x;
	}
	
	public void setY(double y)
	{
		this.yCoord = y;
	}
	
	public void setRadius(double rad)
	{
		if(rad >= 0)
			this.radius = rad;
	}
	
	public static int getCount()
	{
		return circCount;
	}
}
