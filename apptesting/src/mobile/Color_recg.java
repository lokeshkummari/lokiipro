package mobile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Color_recg 
{
	public static void main(String[] args) throws Exception
	{
		Color ec=Color.BLACK;
		File f=new File("G:\\lokeshseleniumjava\\stevesir.png");
		BufferedImage bif=ImageIO.read(f);
		int w=bif.getWidth();
		int h=bif.getHeight();
		int count=0;
		for(int i=0;i<w;i++)
		{
			for(int j=0;j<h;j++)
			{
				Color ac=new Color(bif.getRGB(i,j));
				if(ac.getRed()==ec.getRed() && ac.getBlue()==ec.getBlue() && ac.getGreen()==ec.getGreen())
				{
					count=count+1;
				}
			}
		}
		System.out.println("total pixels are :"+(w*h));
		System.out.println(count);
		double percentage=count*100.0/(w*h);
		System.out.println(percentage);
	}
}
