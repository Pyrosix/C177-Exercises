package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class StateCapitalsApp {
	
final static String filename = "C:\\Users\\malcu\\eclipse-workspace\\MoreStateCapitals\\src\\main\\MoreStateCapitals.txt";
	
	public static void main(String[] args) throws IOException {
		
		TreeMap<String, Capital> fileMap = new TreeMap<>();
		
		
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		try {
			
			String read = null;
			
			while ((read = br.readLine()) != null) {
				
				String[] split = read.split("::");
				
				String state = split[0].trim();
				String capital = split[1].trim();
				String pop = split[2].trim();
				String area = split[3].trim();
				
				if(!"".equals(state) && !"".equals(capital) && !"".equals(pop) && !"".equals(area)) {
					
					Capital cap = new Capital(capital, Integer.parseInt(pop), Double.parseDouble(area));
					fileMap.put(state, cap);
					
					
					
					
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println(fileMap.size() + " STATE/CAPITAL PAIRS LOADED.");
		System.out.println("=============================================");
		fileMap.forEach((k, v) -> {
			
			System.out.println(k + " - " + v.getCapital() + " | " + v.getPop() + " | " + v.getArea());
		});
		
		System.out.println("\nPlease enter the lower limit for capital city population: ");
		Scanner s = new Scanner(System.in);
		int bound = s.nextInt();
		
		System.out.println("\nLISTING CAPITALS WITH POPULATIONS GREATER THAN"  + bound + ": ");
		System.out.println();
		
		fileMap.forEach((k, v) -> {
			
			if (v.getPop() > bound) {
				System.out.println(k + " - " + v.getCapital() + " | " + v.getPop() + " | " + v.getArea());
			}
		});
		
		System.out.println("\nPlease enter the upper limit for capital city sq mileage: ");
		int bound1 = s.nextInt();
		
		System.out.println("\nLISTING CAPITALS WITH AREAS LESS THAN " + bound1 + ": ");
		System.out.println();
		
		fileMap.forEach((k, v) -> {
			
			if (v.getArea() < bound1) {
				System.out.println(k + " - " + v.getCapital() + " | " + v.getPop() + " | " + v.getArea());
			}
		});
		
		s.close();
		br.close();
	}
}
