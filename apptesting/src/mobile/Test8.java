package mobile;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.ElementOption;

public class Test8
{
	public static void main(String[] args) throws Exception
	{
		//start appium server
		  Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
		  URL u=new URL("http://127.0.0.1:4723/wd/hub");
		  DesiredCapabilities dc=new DesiredCapabilities();
		  dc.setCapability(CapabilityType.BROWSER_NAME,"");
		  dc.setCapability("deviceName","HGEPKBDN");
		  dc.setCapability("platformName","android");
		  dc.setCapability("platformVersion","6.0");
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
		  WebDriverWait w=new WebDriverWait(driver,20);
		  Thread.sleep(10000);
		  KeyEvent k=new KeyEvent(AndroidKey.HOME);
		  driver.pressKey(k);
		  Thread.sleep(10000);
		  //get device lock details
		  if(driver.isDeviceLocked()==false)
		  {
			  driver.lockDevice();
			  Thread.sleep(10000);
			  driver.unlockDevice();
			  Thread.sleep(10000);
			  WebElement e1=driver.findElement(By.xpath("//*[@content-desc='Unlock']"));
			  TouchAction ta=new TouchAction(driver);
			  ta.press(ElementOption.element(e1)).moveTo(ElementOption.point(306,690)).release().perform();
			  Thread.sleep(10000);
			  w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@resource-id='com.android.systemui:id/key5']")));
			  driver.findElement(By.xpath("//*[@resource-id='com.android.systemui:id/key5']")).click();
			  Thread.sleep(5000);
			  driver.findElement(By.xpath("//*[@resource-id='com.android.systemui:id/key9']")).click();
			  Thread.sleep(5000);
			  driver.findElement(By.xpath("//*[@resource-id='com.android.systemui:id/key1']")).click();
			  Thread.sleep(5000);
			  driver.findElement(By.xpath("//*[@resource-id='com.android.systemui:id/key9']")).click();
			  Thread.sleep(5000);
			  driver.findElement(By.xpath("//*[@resource-id='com.android.systemui:id/key_enter']")).click();
			  Thread.sleep(10000);
		  }
		  //back to prevoius app
		  driver.launchApp();
		  Thread.sleep(5000);
		  driver.closeApp();
		  //stop appium server
		  Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		  Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
}
