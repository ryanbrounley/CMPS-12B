//================================================================//
//  Author:      Ryan Brounley		                              //
//  Date:        2/27/14      					                  //
//  Program:     Prog 4                                           //
//  Description: Using the formula for imaginary numbers and julia//
//               numbers, the program will create a image using   //
//				 the iterating color value in the formula.	      //
//================================================================//

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

public class Julia{
	
	 //method with information to save the image created
	 public static void saveImage( BufferedImage img, File file ) throws IOException {

	        ImageWriter      writer = null;
	        java.util.Iterator iter = ImageIO.getImageWritersByFormatName("jpg");

	        if( iter.hasNext()) 
	        {
	            writer = (ImageWriter)iter.next();
	        }

	        ImageOutputStream ios = ImageIO.createImageOutputStream( file );
	        writer.setOutput(ios);

	        ImageWriteParam param = new JPEGImageWriteParam( java.util.Locale.getDefault() );
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
	        param.setCompressionQuality(0.98f);

	        writer.write(null, new IIOImage( img, null, null ), param);

	    }
	
	//this uses the inputed complexnumber and calculates the Zn^2 value as a new complex number called c
	public static ComplexNumbers sqrEquation(ComplexNumbers cmplx)
	{
		double newA = ((cmplx.a * cmplx.a) - (cmplx.b * cmplx.b)) ;
		double newB =  2 * (cmplx.a * cmplx.b);
		ComplexNumbers number = new ComplexNumbers(newA, newB);
		return (number);
	} 
	
	//this makes the function that will add the C and Zn^2 values together
	public static ComplexNumbers add(ComplexNumbers Z1, ComplexNumbers C1)
	{
		double newZ1 = Z1.a + C1.a;
		double newC1 = Z1.b + C1.b;
		return new ComplexNumbers(newZ1, newC1);
	}
	
    //This is what finds the value of idx
	public static int getIterations(ComplexNumbers Z0, ComplexNumbers c)
	{
		int idx = 0;
		ComplexNumbers Zn = Z0;
		//if false then the loop will run, will become true if certain parameters are broken
		boolean doneIterations = false;
		while (!doneIterations)
		{	
			ComplexNumbers Znplus1 = add(sqrEquation(Zn), c);
			double mag = Znplus1.getMagnitude();
			//if greater than 255 then out of range and end loop
			if (idx == 255)
			{
				doneIterations = true;
			}
			//this tests to see if the value is not a runaway or prisoner
			else if (mag < 2)
			{
				//if true then the new Zn will equal the Znplus1 value reinputing it into the sqrEquation to make the next Zn+1
				Zn = Znplus1;
				//add to idx every time to calculate how many times it ran
				idx++;
			}
			//this will end the loop if the value isnt a julia value
			else
			{
				doneIterations = true;
			}
		}
	return (idx);
	}
	
	//function to give integer to idx value
	public static int getHSBColor(int idx)
    {
		return Color.getHSBColor((float)(idx/255.0), 1.0f, 1.0f).getRGB();
    }
	
	public static void main( String [] args ) throws Exception{
		
		Scanner scan = new Scanner(System.in);	
		System.out.println("Input: C(real) C(imag) Xmin Xmax Ymin Ymax fname");
		double Creal = scan.nextDouble();
		double Cimag = scan.nextDouble();
		double Xmin  = scan.nextDouble();
		double Xmax  = scan.nextDouble();
		double Ymin  = scan.nextDouble();
		double Ymax  = scan.nextDouble();
		String fname = scan.next();
		
		//finds the distance between each pixel using the coordinates
		double yDelta = (Ymax - Ymin) / 512.0;
		double xDelta = (Xmax - Xmin) / 512.0;
		//start at xmin and ymax in top left corner of grid
		double x = Xmin;
		double y = Ymax;
		
		//Z0 is used above in the equation and the first value of it is the x, y coordinates it starts with
		ComplexNumbers Z0 = new ComplexNumbers(x,y);
		//the c values are the real and imag inputs
		ComplexNumbers c = new ComplexNumbers(Creal, Cimag);
		
		//creates the image
		BufferedImage design = new BufferedImage(512, 512, BufferedImage.TYPE_3BYTE_BGR);

		//this loop runs through each pixel and acquires the idx and puts it in the color method then resets the color with that integer
		for(int row = 511; row > 0; row--)
		{
			x = Xmin;	
			for (int column = 0; column < 512; column++)
			{
				//reset Z0 everytime to the coordinate
				Z0 = new ComplexNumbers(x, y);
				//find idx using above equations
				int idx = getIterations(Z0, c);
				//use the given formula to set idx as the new color for that pixel
				int color = getHSBColor(idx);
				design.setRGB(column, row, color);
				//move to the right xDelta
				x += xDelta;
			}
			//move down yDelta with each full run of the column for loop
			y -= yDelta;
		}
		//save the image as fname.jpg
		saveImage( design, new File( fname + ".jpg" ));
		
	}
}

class ComplexNumbers
{	
		double a; // real
		double b; // imaginary

	public ComplexNumbers(double inA, double inB)
	{
		a = inA;
		b = inB;
	}

	public double getMagnitude()
	{
		double mag = Math.sqrt((a * a) + (b * b));
		return mag;
	}
	
}

