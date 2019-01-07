package mobile;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class Pattern 
{
	public static void main(String[] args) throws Exception
	{
		//start appium server
		  Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
		  URL u=new URL("http://127.0.0.1:4723/wd/hub");
		  DesiredCapabilities dc=new DesiredCapabilities();
		  dc.setCapability(CapabilityType.BROWSER_NAME,"");
		  dc.setCapability("deviceName","ZY2235FW4N");
		  dc.setCapability("platformName","android");
		  dc.setCapability("platformVersion","7.1.1");
		  dc.setCapability("appPackage","com.android.dialer");
		  dc.setCapability("appActivity","com.android.dialer.HQDialtactsActivity");
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
		

	}

}
