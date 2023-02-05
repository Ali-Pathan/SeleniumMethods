package seleniumsessions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLink {
	static WebDriver driver;

	public static void main(String[] args) throws MalformedURLException, IOException {
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));

		driver.get("http://heatclinic.shiftedtech.com/");

		List<WebElement> linklist = driver.findElements(By.tagName("a"));
		linklist.addAll(driver.findElements(By.tagName("href")));

		System.out.println("Total Links and Images" + "------------->" + linklist.size());

		List<WebElement> activelist = new ArrayList<WebElement>();
		for (int i = 0; i < linklist.size(); i++) {
			if (linklist.get(i).getAttribute("href") != null
					&& (!linklist.get(i).getAttribute("href").contains("javascript"))) {
				activelist.add(linklist.get(i));
			}
		}
//		for (int i = 0; i < linklist.size(); i++) {
//			if (linklist.get(i).getAttribute("href") != null) {
//				activelist.add(linklist.get(i));
//			}
//		}
		System.out.println("Total Number of Active Links and Images" + "------------->" + linklist.size());
		for (int j = 0; j < activelist.size(); j++) {
			HttpURLConnection connection = (HttpURLConnection) new URL(activelist.get(j).getAttribute("href"))
					.openConnection();
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();
			System.out.println(activelist.get(j).getAttribute("href") + "-------->" + response);
		}

		System.out.println("Test Complete");

	}

}
