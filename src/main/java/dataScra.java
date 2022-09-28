import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.Duration;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class dataScra {

	static String mapshaku = "https://www.google.com/maps/place/";


	public static void asuntoScraper() throws Exception {
		String[] asunnot = {
				"https://asunnot.oikotie.fi/vuokra-asunnot?pagination=1&locations=[[39,6,\"Espoo\"]]&price[max]=850&price[min]=690&roomCount[]=1&roomCount[]=2&roomCount[]=3&buildingType[]=1&buildingType[]=256&vendorType[]=private&cardType=101"
		};
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Host\\Documents\\Programming\\Programming\\chromedriver.exe");

		final String asuntohaku = "https://asunnot.oikotie.fi/vuokra-asunnot?pagination=1&locations=%5B%5B39,6,%22Espoo%22%5D%5D&price%5Bmax%5D=850&price%5Bmin%5D=690&roomCount%5B%5D=1&roomCount%5B%5D=2&roomCount%5B%5D=3&buildingType%5B%5D=1&buildingType%5B%5D=256&vendorType%5B%5D=private&cardType=101";


		try {


			WebDriver driver = new ChromeDriver();

			driver.get(asuntohaku);
			TimeUnit.SECONDS.sleep(3);

			List<WebElement> asunlista = driver.findElements(By.xpath("//a[@ng-href]"));

			int add = 1; //Object calculator
			for(WebElement i : asunlista) {
				add += 1; //Object calculator
				String asunto = i.getAttribute("textContent");
				String asunto2 = asunto.replaceAll("\\s+","");
				//System.out.println(asunto2);

				WordScrambler(asunto2, add);
				//System.out.println(add);

			}

			driver.quit();

		}
		catch(Exception err) {

			System.out.println(err);

		}

	}
	public static void WordScrambler(String TheAddress, int asunnot) throws Exception {
		try {

			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			boolean found = false;
			boolean found2 = false;
			char[] karakterArray = TheAddress.toCharArray();

			float x = 1; 
			List<String> osoitteet = new ArrayList<String>();
			// Using two loops nested inside do while, time complexity of isLetter and isDigit not given.
			// Because of two separate for loops, one getting the chars and other the digits - had to divide the main do while loop by incrementing x by 2 in the for loops simple solution
			do {
				for(char a : karakterArray) {
					if (Character.isLetter(a)) {

						sb2.append(a);
						found2 = true;
					}
					else if(found2){

						break;
					}
				} x += 2; 
				for(char c : karakterArray){

					if(Character.isDigit(c)){
						sb.append(c);

						found = true;


					} else if(found){

						break;

					} x += 2;
				}
				String osoite = sb2.toString() + " " + sb.toString();
				osoitteet.add(osoite);


			}while (x <= asunnot);

			osoitteetDistanEst(osoitteet);
			/*for(String osoite : osoitteet) {
						//DistanceEst.APIposter(osoite);
						osoitteetDistanEst(osoitteet);
						//System.out.println(osoite);
						}*/

		}




		catch(Exception err) {

			System.out.println(err);

		}

	}
	public static void osoitteetDistanEst(List<String> osoitteet) {

		WebDriver driver2 = new ChromeDriver();
		StringBuilder osoiteyhdistaja = new StringBuilder();
		try {


			ChromeOptions options = new ChromeOptions();
			

			for(String osoite : osoitteet) {

				
				driver2.get(mapshaku + osoite + "Espoo");
				driver2.navigate().to(mapshaku + osoite + "Espoo");

				System.out.println(osoite);
				TimeUnit.SECONDS.sleep(3);
				String urlkordinaateilla = driver2.getCurrentUrl();
				System.out.println(urlkordinaateilla);

			}

		}catch(Exception err2){

			System.out.println(err2);

		}
		finally {

			driver2.quit();
		}




	}

}
