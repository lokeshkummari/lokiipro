package mobile;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class Test4 
{
	public static void main(String[] args) throws Exception
	{
		//get data from keybpord
				Scanner sc=new Scanner(System.in);
				System.out.println("Enter input1");
				String x=sc.nextLine();
				System.out.println("Enter input2");
				String y=sc.nextLine();
				System.out.println("Enter add/substract/multiply/divide");
				String z=sc.nextLine();
				//start appium server
				Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
				URL u=new URL("http://127.0.0.1:4723/wd/hub");
				DesiredCapabilities dc=new DesiredCapabilities();
				dc.setCapability(CapabilityType.BROWSER_NAME,"");
				dc.setCapability("deviceName","YT910TQQPR");
				dc.setCapability("platformName","android");
				dc.setCapability("platformVersion","4.3");
				dc.setCapability("appPackage","com.android.calculator2");
				dc.setCapability("appActivity","com.android.calculator2.Calculator");
				AndroidDriver driver;
				while(true)
				{
					try
					{
						driver=new AndroidDriver(u,dc);
						break;
					}
					catch(Exception ex)
					{
						
					}
				}
				//app automation
				try
				{
					//wait for app launching
					WebDriverWait w=new WebDriverWait(driver,20);
					w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='DELETE']")));
					//Enter input1
					for(int i=0;i<x.length();i++)
					{
						char d=x.charAt(i);
						if(d=='-')
						{
							driver.findElement(By.xpath("//*[@content-desc='minus']")).click();
						}
						else if(d=='.')
						{
							driver.findElement(By.xpath("//*[@content-desc='point']")).click();
						}
						else
						{
							driver.findElement(By.xpath("//*[@text='"+d+"']")).click();
						}
					}
					//click button for operation
					if(z.equalsIgnoreCase("add"))
					{
						driver.findElement(By.xpath("//*[@content-desc='plus']")).click();
					}
					else if(z.equalsIgnoreCase("substract"))
					{
						driver.findElement(By.xpath("//*[@content-desc='minus']")).click();
					}
					else if(z.equalsIgnoreCase("multiply"))
					{
						driver.findElement(By.xpath("//*[@content-desc='multiply']")).click();
					}
					else
					{
						driver.findElement(By.xpath("//*[@content-desc='divide'")).click();
					}
					//input2
					for(int i=0;i<y.length();i++)
					{
						char d=y.charAt(i);
						if(d=='-')
						{
							driver.findElement(By.xpath("//*[@content-desc='minus']")).click();
						}
						else if(d=='.')
						{
							driver.findElement(By.xpath("//*[@content-desc='point']")).click();
						}
						else
						{
							driver.findElement(By.xpath("//*[@text='"+d+"']")).click();
						}
					}
					//click equals
					driver.findElement(By.xpath("//*[@content-desc='equals']")).click();
					//get output
					String temp=driver.findElement(By.xpath("//*[@class='android.widget.EditText']")).getAttribute("text");
					System.out.println(temp);
					if(temp.contains("minus"))
					{
						temp=temp.replace("minus","-");
					}
					else if(temp.contains("point"))
					{
						temp=temp.replace("point",".");
						Thread.sleep(5000);
						String var[]=temp.split(".");
						for(String r:var)
						{
							System.out.println(r);
						}
						String temp1="";
						temp1=temp1.join("",var);
						//temp=temp1;
						System.out.println(temp1);
					}
					//vlidation
						float i1=Float.parseFloat(x);
						float i2=Float.parseFloat(y);
						float o=Float.parseFloat(temp);
						if(z.equalsIgnoreCase("add") && o==i1+i2)
						{
							System.out.println(o);
							System.out.println(i1+i2);
							System.out.println("test passed");
						}
						else if(z.equalsIgnoreCase("substract") && o==i1-i2)
						  {
							  System.out.println(o);
							  System.out.println(i1-i2);
							  System.out.println("test passed");
						  }
						else if(z.equalsIgnoreCase("multiply") && o==i1*i2)
						   {
							  System.out.println(o);
							  System.out.println(i1*i2);
							  System.out.println("test passed");
						   }
						else if(z.equalsIgnoreCase("divide") && o==i1/i2)
						{
							System.out.println(o);
							System.out.println(i1/i2);
							System.out.println("test passed");
						}
						else
						{
							Date d=new Date();
							SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
							String f=sf.format(d)+".png";
							File src=driver.getScreenshotAs(OutputType.FILE);
							File dest=new File(f);
							FileHandler.copy(src, dest);
							System.out.println("test failed");
						}	
					
					//close app
					driver.closeApp();
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
				//stop appium server
				Runtime.getRuntime().exec("taskkill /F /IM node.exe");
				Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		

	}

}
