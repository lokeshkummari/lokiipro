package mobile;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class Test7
{
	public static void main(String[] args) throws Exception
	{
		//start appium server
		  Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
		  URL u=new URL("http://127.0.0.1:4723/wd/hub");
		  DesiredCapabilities dc=new DesiredCapabilities();
		  dc.setCapability(CapabilityType.BROWSER_NAME,"");
		  dc.setCapability("deviceName","YT910TQQPR");
		  dc.setCapability("platformName","android");
		  dc.setCapability("platformVersion","4.3");
		  dc.setCapability("appPackage","com.vodqareactnative");
		  dc.setCapability("appActivity","com.vodqareactnative.MainActivity");
		  //launch app in ARD using android driver
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
		  //oparate app
		  try
		  {
			  WebDriverWait w=new WebDriverWait(driver,20);
			  w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='LOG IN']")));
			  driver.findElement(By.xpath("//*[@text='LOG IN']")).click();
			  w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Back']")));
		  }
		  catch(Exception ex)
		  {
			  System.out.println(ex.getMessage());
		  }
		  //close app
		  driver.closeApp();
		  //stop server
		  Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		  Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
}
