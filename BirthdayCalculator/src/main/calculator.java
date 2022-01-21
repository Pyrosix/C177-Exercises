package main;

import java.util.Calendar;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class calculator {
	
	
	public static void main(String[] args) throws Exception {
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println("Welcome to the Birthday Calculator!");
		System.out.println();
		
		String bd;
		System.out.println("Please enter your birthday using the following format: mm-dd-yyyy");
		Scanner s = new Scanner(System.in);
		bd = s.nextLine();
		
		String dob = bd.replace("-", "");
		
		int month = Integer.parseInt(dob.substring(0, 2));
		int day = Integer.parseInt(dob.substring(2, 4));
		int year = Integer.parseInt(dob.substring(4, 8));
		
		LocalDate today = LocalDate.now();
		LocalDate birthdate = LocalDate.of(year, month, day);
		LocalDate bdcurrent = LocalDate.of(currentYear, month, day);
		
		DayOfWeek dow = DayOfWeek.from(birthdate);
		DayOfWeek dowcurrent = DayOfWeek.from(bdcurrent);
		
		long daysbetween = ChronoUnit.DAYS.between(today, bdcurrent);
		long yearsbetween = ChronoUnit.YEARS.between(birthdate, today);
		
		yearsbetween = Long.sum(yearsbetween, 1);
		
		System.out.println("That means you were born on a " + dow + "!");
		System.out.println("This year it falls on a " + dowcurrent + "...");
		System.out.println("And since today is " + today + ",");
		System.out.println("there's only " + daysbetween + " more days until the next one when you turn " + yearsbetween);
		
		
		s.close();
	}
}
