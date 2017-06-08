import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main 
{
	static double PI = 3.1415;
	
	static String readFile(String path, Charset encoding) throws IOException
	{
		byte[] encoded;
		try 
		{
			encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		} 
		catch (IOException e) 
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			String errorMsg;
			errorMsg = sw.toString();
			
			return errorMsg;
		}
		
	}
	
	static double[] areaOfTriangleAndSector(Circle a, Circle b)
	{
		double [] retArr;
		retArr = new double[4];
		
		double r1 = a.getRadius(), r2 = b.getRadius(), d = 0.0, theta1 = 0.0, theta2 = 0.0;
		double a1 = 0.0, s1 = 0.0, s2 = 0.0;
		
		d = calcDistance(a.getX(), a.getY(), b.getX(), b.getY());
		
		a1 = Math.sqrt((-d + r1 + r2) * (d - r1 + r2) * (d + r1 - r2) * (d + r1 + r2)) / d;
		
		s1 = (a1 + 2.0 * r1) / 2.0;
		s2 = (a1 + 2.0 * r2) / 2.0;
		
		retArr[0] = Math.sqrt(s1 * (s1 - a1) * (s1 - r1) * (s1 - r1));
		retArr[1] = Math.sqrt(s2 * (s2 - a1) * (s2 - r2) * (s2 - r2));
		
		theta1 = 2 * Math.asin(a1 / (2 * r1));
		theta2 = 2 * Math.asin(a1 / (2 * r2));
		
		retArr[2] = 0.5 * Math.pow(r1, 2) * theta1;
		retArr[3] = 0.5 * Math.pow(r2, 2) * theta2;
		
		return retArr;
	}
	
	static double calcDistance(double x1, double y1, double x2, double y2)
	{
		double distance = 0.0;
		distance = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		
		return distance;
	}
	
	static boolean circlesIntersect(Circle a, Circle b)
	{
		double distance = 0.0;
		
		distance = calcDistance(a.getX(), a.getY(), b.getX(), b.getY());
		
		if(distance < (a.getRadius() + b.getRadius()))
			return true;
		
		else
			return false;
	}
	
	static boolean isInside(Circle a, Circle b)
	{
		double distance = 0.0;
		
		distance = calcDistance(a.getX(), a.getY(), b.getX(), b.getY());
		
		if(distance <= Math.max(a.getRadius(), b.getRadius()))
			return true;
		else
			return false;
	}
	
	static boolean isTangent(Circle a, Circle b)
	{
		double distance = 0.0;
		distance = calcDistance(a.getX(), a.getY(), b.getX(), b.getY());
		
		if(a.getRadius() + b.getRadius() == distance)
			return true;
		
		else
			return false;
	}
	
	public static void main(String args[]) throws IOException
	{
		int dimension, arrCount = 1, circleCount = 0;
		double totalArea = 0.0, overlap = 0.0;
		double [] intersectData;
		
		intersectData = new double [4];
		char flag = 'j';
		String path;
		String [] line, circData;
		Scanner input = new Scanner(System.in);
		
		Circle [] allCircle;
		Circle myCircle;
		
		System.out.println("Please enter the path to text file: ");
		
		path = input.next();
		
		String content = readFile(path, Charset.defaultCharset());
		input.close();
		
		if(content.charAt(0) == flag)
		{
			System.out.println("File not Found! Please make sure the file exist!");
			System.out.println();
			System.out.println(content);
		}
		
		else
		{
			line = content.replaceAll("\r\n", "\n").replaceAll("\r", "\n").split("\n");
			dimension = Integer.parseInt(line[arrCount]);
			arrCount++;
			
			allCircle = new Circle[dimension];
			
			while(arrCount < line.length)
			{
				myCircle = new Circle();
				circData = line[arrCount].split(" ");
				arrCount++;
				
				myCircle.setX(Double.parseDouble(circData[0]));
				myCircle.setY(Double.parseDouble(circData[1]));
				
				if(Double.parseDouble(circData[2]) >= 0)
					myCircle.setRadius(Double.parseDouble(circData[2]));
				else
				{
					System.out.println("Negative radius found! please check your radius entry!");
					System.exit(0);
				}
				
				allCircle[circleCount] = myCircle;
				circleCount++;
			}
			
			for(int i = 0; i < circleCount; i++)
				allCircle[i].printCircle();
			
			System.out.println();
			
			for(int i = 0; i < circleCount; i++)
			{
				totalArea += PI * Math.pow(allCircle[i].getRadius(), 2);
				
				if(i != circleCount - 1)
				{
					for(int j = i + 1; j < circleCount; j++)
					{
						if(circlesIntersect(allCircle[i], allCircle[j]) && i != j)
						{
							System.out.println("Circles");
							allCircle[i].printCircle();
							System.out.print(" and ");
							allCircle[j].printCircle();
							System.out.printf(" intersect\n");
							
							if(isInside(allCircle[i], allCircle[j]))
							{
								if(allCircle[i].getRadius() > allCircle[j].getRadius())
									overlap += PI * Math.pow(allCircle[j].getRadius(), 2);
								
								else
									overlap += PI * Math.pow(allCircle[i].getRadius(), 2);
							}
						
							else if(!isTangent(allCircle[i], allCircle[j]))
							{
								intersectData = areaOfTriangleAndSector(allCircle[i], allCircle[j]);
							
								overlap += Math.abs(intersectData[0] - intersectData[2]);
								overlap += Math.abs(intersectData[1] - intersectData[3]);
							}
						}
					}
				}
			}
			
			totalArea += Math.pow(allCircle[circleCount - 1].getRadius(), 2);
			
			System.out.println("Total area = " + totalArea);
			System.out.println("Total overlap area = " + overlap);
			System.out.println("Total area after overlap = " + (totalArea - overlap));
		}
	}

}
