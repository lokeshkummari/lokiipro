package mobile;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Test6 
{
	public static void main(String[] args)throws Exception
	{
		//start appium server
				Runtime.getRuntime().exec("cmd.exe /c start cmd /k \"appium -a 127.0.0.1 -p 4723\"");
				URL u=new URL("http://127.0.0.1:4723/wd/hub");
				DesiredCapabilities dc=new DesiredCapabilities();
				dc.setCapability(CapabilityType.BROWSER_NAME,"");
				dc.setCapability("deviceName","YT910TQQPR");
				dc.setCapability("platformName","android");
				dc.setCapability("platformVersion","4.3");
				dc.setCapability("appPackage","com.sonyericsson.conversations");
				dc.setCapability("appActivity","com.sonyericsson.conversations.ui.ConversationListActivity");
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
				//operate ARD
				String x=driver.getOrientation().name();
				System.out.println(x);//PORTRAIT
				if(x.equals("PORTRAIT"))
				{
					driver.rotate(ScreenOrientation.LANDSCAPE);
				}
				Thread.sleep(10000);
				String y=driver.getOrientation().name();
				System.out.println(y);
				if(y.equals("LANDSCAPE"))
				{
					driver.rotate(ScreenOrientation.PORTRAIT);
				}
				Thread.sleep(10000);
				//operate app specified in capabilities
				WebDriverWait w=new WebDriverWait(driver,20);
				w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@content-desc='New conversation']")));
				driver.findElement(By.xpath("//*[@content-desc='New conversation']")).click();
				Thread.sleep(5000);
				if(driver.isKeyboardShown())
				{
					driver.hideKeyboard();
					Thread.sleep(10000);
				}
				//come to home screen without closing app
				KeyEvent k=new KeyEvent(AndroidKey.HOME);
				driver.pressKey(k);
				Thread.sleep(10000);
				//back to work with app specified in capabilities
				driver.launchApp();
				driver.closeApp();
				//stop server
				Runtime.getRuntime().exec("taskkill /F /IM node.exe");
				Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
}
