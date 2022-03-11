package Questions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Assesment {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "D:\\Selenium Code\\chromedriver_win32 (2)\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.flipkart.com");
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		WebElement cancelButton = driver.findElement(By.cssSelector("button[class='_2KpZ6l _2doB4z']"));
		cancelButton.click();
		WebElement searchBox = driver.findElement(By.cssSelector("input[name=q]"));
		
		Actions action = new Actions(driver);
		action.sendKeys(searchBox, "iphone 13 pro max").sendKeys(Keys.ENTER).build().perform();
		
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_1YokD2 _3Mn1Gg']>div:nth-of-type(4)")));
		WebElement thirdResult = driver.findElement(By.cssSelector("div[class='_1YokD2 _3Mn1Gg']>div:nth-of-type(4)"));
		thirdResult.click();
		
		Set<String> set = driver.getWindowHandles();
		List<String> list = new LinkedList(set);
		
		for(String window : list)
		{
			if(window == list.get(1))
				driver.switchTo().window(window);
		}
				
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[class='col col-6-12']:first-of-type")));
		WebElement AddToCartButton = driver.findElement(By.cssSelector("li[class='col col-6-12']:first-of-type"));
		AddToCartButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='Ob17DV _3X7Jj1'] span")));
		String InitialTotal = driver.findElement(By.cssSelector("div[class='Ob17DV _3X7Jj1'] span")).getText();
		
		InitialTotal = InitialTotal.replaceAll(",", "");
		InitialTotal = InitialTotal.substring(1);
		System.out.println("Initial Total Cost = "+InitialTotal);
		
		WebElement addMore = driver.findElement(By.xpath("//button[text()='+']"));
		System.out.println("Adding same item again to cart");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", addMore);
		
		
		Thread.sleep(2000);
		String FinalTotal = driver.findElement(By.cssSelector("div[class='Ob17DV _3X7Jj1'] span")).getText();
		
		FinalTotal = FinalTotal.replaceAll(",", "");
		FinalTotal = FinalTotal.substring(1);
		System.out.println("Final Total Cost = "+FinalTotal);
		
		int initialCost = Integer.parseInt(InitialTotal);
		int expectedCost = 2*initialCost;
		
		int finalCost = Integer.parseInt(FinalTotal); 
		
		Assert.assertEquals(finalCost, expectedCost, "Incorrect final cost");
		System.out.println("FinalCost is correct, test passed");
		
		driver.quit();
	}
}
