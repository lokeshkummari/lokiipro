package mobile;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

public class Test9 
{
	public static void main(String[] args) throws Exception
	{
		//open image to get text in file
		File f=new File("G:\\lokeshseleniumjava\\profile.png");
		//point tess4j-3.0.0 related library folder
		File fo=LoadLibs.extractTessResources("tessdata");
		Tesseract obj=new Tesseract();
		obj.setDatapath(fo.getAbsolutePath());
		String txt=obj.doOCR(f);
		Thread.sleep(20000);
		System.out.println(txt);
	}
}
