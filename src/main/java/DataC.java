import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class DataC {
	
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		
		
		/*Runtime rt = Runtime.getRuntime();
		ProcessBuilder pr = new ProcessBuilder();
		//System.out.println("dfsdf");
		pr.command("C:\\Program Files\\Mozilla Firefox\\firefox.exe", "hello");
		pr.start();
		
		
		//digester.diges(text);
		
		/*
		Random rnd = new Random(System.currentTimeMillis());
		int randomNum = rnd.nextInt((10000 - 1) + 8) + 1;
		System.out.println(randomNum);*/
		
		try {
			dataScra.asuntoScraper();
			//DistanceEst.APIposter(null);
			//digester.diges(null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
