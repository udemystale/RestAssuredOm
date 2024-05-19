import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class tred {

	@Test
	void test() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C://Users//Welcome//Downloads//chromedriver-win64//chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://demo.seleniumeasy.com/basic-first-form-demo.html");
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@id='user-message']")).sendKeys("Hello");
		
		
	}
}
