package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.IntStream;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);
	private static DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

	public static void main(String[] args) {
		try {
			int[] monthYear = getMonthYear(args);
			printCalendar(monthYear[0], monthYear[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void printCalendar(int month, int year) {	
		printTitle(month, year);
		printWeekDays();
		printDates(month, year);
	}

	private static void printDates(int month, int year) {
		int weekDayNumber = getFirstDay(month, year);
		int offset = getOffset(weekDayNumber);
		int nDays = YearMonth.of(year, month).lengthOfMonth();
		
		System.out.printf("%s", " ".repeat(offset));
		for (int date = 1; date <= nDays; date++) {
			System.out.printf("%4d", date);
			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
	}

	private static int getOffset(int weekDayNumber) {
		return (weekDayNumber - 1) * WIDTH_FIELD;
	}

	private static int getFirstDay(int month, int year) {
		return ( LocalDate.of(year, month, 1).getDayOfWeek().getValue() + 7 - firstDayOfWeek.getValue() ) % 7 + 1;
	}

	private static void printWeekDays() {
		IntStream.range(firstDayOfWeek.getValue(), firstDayOfWeek.getValue() + 7)
		.mapToObj(el -> DayOfWeek.values()[(el - 1) % 7])
		.forEach(dw -> System.out.printf("%4s", dw.getDisplayName(TextStyle.SHORT, locale )));
		System.out.println();
		
	}

	private static void printTitle(int month, int year) {
		System.out.printf("%s%d, %s%n", " ".repeat(10), year, Month.of(month).getDisplayName(TextStyle.FULL, locale ));
		
	}

	private static int[] getMonthYear(String[] args) throws Exception {
		return args.length == 0 ? getCurrentMonthYear() : getMonthYearArgs(args);
	}

	private static int[] getMonthYearArgs(String[] args) throws Exception {
		getFirstDayOfWeekArgs(args);
		return new int[] {getMonthArgs(args), getYearArgs(args)};
	}

	private static void getFirstDayOfWeekArgs(String[] args) throws Exception {
		if (args.length > 2) {
			try {
				firstDayOfWeek = DayOfWeek.valueOf(args[2].toUpperCase());
			} catch (Exception e) {
				throw new Exception("First day of week should be valid english name of week day");
			}
		}
	}

	private static int getYearArgs(String[] args) throws Exception {
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("Year must be positive");
				}
			} catch (NumberFormatException e) {
				throw new Exception("Year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception {
		int res;
		try {
			res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month showld be from 1 to 12");
			}
		} catch (NumberFormatException e) {
			throw new Exception("Month showld be a number"); 
		}
		return res;
	}

	private static int[] getCurrentMonthYear() {
		LocalDate ld = LocalDate.now();
		int month = ld.getMonth().getValue();
		int year = ld.getYear();
		return new int[] {month, year};
	}

}
