import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.Process;
import java.lang.ProcessBuilder;

import java.time.Duration;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
	static final String host = "https://consent.google.com/save";
	static final String host2 = "www.google.com";
	
	static ProcessBuilder pr = new ProcessBuilder();
	static String GoogleRequestReader;
	static List<String> osoitteet = new ArrayList<String>();

	public static void asuntoScraper(String asuntohaku) throws Exception {

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
				if(!asunto2.equals("Karttahaku")){
					WordScrambler(asunto2, add);
				}else{
					add--;
					continue;
				}
			}
					
			driver.quit();

		}
		catch(Exception err) {

			System.out.println(err);

		}
		

	}
	
	public static void sivumaara() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Host\\Documents\\Programming\\Programming\\chromedriver.exe");
		
		String sivulukema = "1";
		char sivujenmaara = 0;
		String asuntohaku = "https://asunnot.oikotie.fi/vuokra-asunnot?pagination="+sivulukema+"&locations=%5B%5B39,6,%22Espoo%22%5D%5D&price%5Bmax%5D=850&price%5Bmin%5D=690&roomCount%5B%5D=1&roomCount%5B%5D=2&roomCount%5B%5D=3&buildingType%5B%5D=1&buildingType%5B%5D=256&vendorType%5B%5D=private&cardType=101";
		
		WebDriver driver_2 = new ChromeDriver();
		
		driver_2.get(asuntohaku);
		
		TimeUnit.SECONDS.sleep(2);
		List<WebElement> sivut = driver_2.findElements(By.xpath("//pagination-indication[contains(@class, 'ng-isolate-scope')]"));
		
		for(WebElement a : sivut) {
			
			String sivu = a.getAttribute("textContent");
			String sivu2 = sivu.replaceAll("\\s+","");
			if(sivu2.contains("/")) {
				
				char[] sivuarr = sivu2.toCharArray();
				
				sivujenmaara = sivuarr[sivuarr.length-1];
				
			//System.out.println(sivujenmaara);
			}
		
		}
		Integer s = 2;
		int si = Character.getNumericValue(sivujenmaara);
		while(s <= si+1) {
			sivulukema = s.toString();
			asuntoScraper(asuntohaku);
			asuntohaku = "https://asunnot.oikotie.fi/vuokra-asunnot?pagination="+sivulukema+"&locations=%5B%5B39,6,%22Espoo%22%5D%5D&price%5Bmax%5D=850&price%5Bmin%5D=690&roomCount%5B%5D=1&roomCount%5B%5D=2&roomCount%5B%5D=3&buildingType%5B%5D=1&buildingType%5B%5D=256&vendorType%5B%5D=private&cardType=101";
			s++;
		}
		driver_2.quit();
		/*for(String osoite : osoitteet) {
			//DistanceEst.APIposter(osoite);
			//osoitteetDistanEst(osoitteet);
			//osoitteetDistanEst(osoite);
			System.out.println(osoite);
		}*/
		osoitteetDistanEst();
		
		
	}
	public static void WordScrambler(String TheAddress, int asunnot) throws Exception {
		try {

			StringBuilder sb = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			boolean found = false;
			boolean found2 = false;
			char[] karakterArray = TheAddress.toCharArray();

			float x = 1; 
			
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
				String osoite = sb2.toString() + "+" + sb.toString();
				
				
				if(!osoitteet.contains(osoite)){
					osoitteet.add(osoite);
				}
				
				else{
					break;
				}

			}while (x <= asunnot);
			TimeUnit.SECONDS.sleep(1);
	
		}


		catch(Exception err) {

			System.out.println(err);

		}
		


	}
	public static void osoitteetDistanEst() throws IOException, InterruptedException, Exception {


		String AcceptHeaderContentType = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*///*;q=0.8";
		String line;
		BufferedReader Googlereader;
		StringBuffer responseContent = new StringBuffer();

		for(String osoite : osoitteet) {
		
			try {
	
				if ((osoite != null)){
					
					URL url2 = new URL(mapshaku + osoite + ",Espoo");
	
					HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection(); 
					connection2.setRequestMethod("GET");
					connection2.setDoOutput(false);
	
					TimeUnit.SECONDS.sleep(2);
					
					Googlereader = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
	
					while((line = Googlereader.readLine()) != null) {
						responseContent.append(line);
						
	
	
					}
	
					connection2.disconnect();
	
					Googlereader.close();
	
					String googlemapsresponse2 = responseContent.toString();
					//System.out.println(googlemapsresponse2);
					//System.out.println(osoite);
					String[] kordinaatisto = regexFinder(googlemapsresponse2);
					//System.out.println(kordinaatit);
					int osoitteetlength = osoitteet.size();
					//System.out.println(osoitteetlength);
					//System.out.println(osoite);

					if(kordinaatisto !=null) {
						DistanceEst.APIposter(osoite, kordinaatisto, osoitteet);
					
					}else {
						continue;
					}
				}
	
	
			}
			catch(Exception e) {
				
				System.out.println(e);
				continue;
				}
			}
		DistanceEst.finalWriteOut_to_File(osoitteet); //Writing the final results to a text file
	}
	public static String[] regexFinder(String googlemapsresponse2) {

		final String REGEX = "@\\d\\d\\.\\d\\d\\d\\d\\d\\d\\d\\,\\d\\d\\.\\d\\d\\d\\d\\d\\d";
		String kordinaatti = null;

		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(googlemapsresponse2);   
		//System.out.println(googlemapsresponse2);
		while (m.find()) {
			
			kordinaatti = m.group(0).toString();
		}
		
		if(kordinaatti != null) {
			String[] koo1 = kordinaatti.split("\\,");
			String kordinaatti1 = koo1[0].toString();
			String kordinaatti1_1 = kordinaatti1.substring(1); // Removing the @ symbol from the 0th index
			String kordinaatti1_1_1 = kordinaatti1_1.substring(0,9);

			String kordinaatti2 = koo1[1].toString();
			String kordinaatti2_2_2 = kordinaatti2.substring(0,9);

			//System.out.println(kordinaatti1_1_1);
			//System.out.println(kordinaatti2_2_2);
			String[] kordinaatit = {kordinaatti1_1_1, kordinaatti2_2_2};
			
			return kordinaatit;

		}else{
			return null;
		}
	}

}
