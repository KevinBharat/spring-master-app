package com.util;
import java.time.LocalDate;
public class RnD {
	public static void main(String[] arge) {
		LocalDate l= LocalDate.now();
			System.out.println(l);
			System.out.println(l.toString());
			System.out.println(l.plusDays(3));
			System.out.println(l.plusMonths(8));
			System.out.println(l.plusYears(3));
			System.out.println(l.plusWeeks(4));
			
			System.out.println(l.minusDays(9));
			System.out.println(l.minusMonths(3));
			System.out.println(l.minusYears(2));
			System.out.println(l.minusWeeks(4));
	




	}
}
