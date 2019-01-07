package mobile;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;

public class Test5
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
		//get datails of launched app specified app in capabilities
		System.out.println(driver.getCurrentPackage());
		System.out.println(driver.currentActivity());
		//work with other app
		if(driver.isAppInstalled("com.whatsapp"))
		{
			System.out.println("watsapp is ready");
			driver.activateApp("com.whatsapp");
		}
		else
		{
			driver.installApp("path of watsapp.apk");
			System.out.println("phone app is ready");
			driver.activateApp("com.whatsapp");
		}
		Thread.sleep(10000);
		//get status of other launched app
		ApplicationState as=driver.queryAppState("com.whatsapp");
		System.out.println(as.toString());
		//get other launched app details
		System.out.println(driver.getCurrentPackage());
		System.out.println(driver.currentActivity());
		//close that other launched app
		driver.terminateApp("com.whatsapp");
		//back to work with app specified in capabilities
		driver.launchApp();
		driver.runAppInBackground(Duration.ofSeconds(10));
		driver.closeApp();
		//stop server
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
}
