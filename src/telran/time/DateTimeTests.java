package telran.time;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Locale;

import org.junit.jupiter.api.*;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test 
	@Disabled
	void localDate() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM, YYYY, d", Locale.forLanguageTag("ru"));
		assertEquals(1812, birthDateAS.plusYears(13).getYear());
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.MONTHS;
		System.out.printf("Number of %s between %s and %s is %d%n", unit, birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));
	}

	@Test
	@Disabled
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAjuster()));
	}
	
	@Test
	void timeZonesCanadian() {
		System.out.println("All the Zones in tha Canada are:");
		ZonedDateTime zdt = ZonedDateTime.now();
		ZoneId.getAvailableZoneIds().stream()
			.filter(el -> el.contains("Canada"))
			.map(el -> ZoneId.of(el) )
			.map(el -> zdt.withZoneSameInstant(el))
			.map(el -> el.toString())
			.sorted(Comparator.reverseOrder())
			.forEach(System.out::println);
	}

	@Test
	void next13FridayTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAjuster()));
//		System.out.println(current.with(new NextFriday13()));
		assertEquals(LocalDate.parse("2023-10-13"), current.with(new NextFriday13()));
//		System.out.println(current.with(new NextFriday13()).with(new NextFriday13()));
		assertEquals(LocalDate.parse("2024-09-13"), current.with(new NextFriday13()).with(new NextFriday13()));
	}
	
	@Test
	void workingDaysTest() {
		LocalDate startDate = LocalDate.parse("2023-02-15");
		
		DayOfWeek[] dayOffsSaturday = new DayOfWeek[]{DayOfWeek.SATURDAY};
		assertEquals(startDate.plus(1, ChronoUnit.DAYS), startDate.with(new WorkingDays(1, dayOffsSaturday)));
		assertEquals(startDate.plus(2, ChronoUnit.DAYS), startDate.with(new WorkingDays(2, dayOffsSaturday)));
		assertEquals(startDate.plus(4, ChronoUnit.DAYS), startDate.with(new WorkingDays(3, dayOffsSaturday)));
		assertEquals(startDate.plus(5, ChronoUnit.DAYS), startDate.with(new WorkingDays(4, dayOffsSaturday)));
		assertEquals(startDate.plus(6, ChronoUnit.DAYS), startDate.with(new WorkingDays(5, dayOffsSaturday)));
		assertEquals(startDate.plus(7, ChronoUnit.DAYS), startDate.with(new WorkingDays(6, dayOffsSaturday)));
		assertEquals(startDate.plus(8, ChronoUnit.DAYS), startDate.with(new WorkingDays(7, dayOffsSaturday)));
		assertEquals(startDate.plus(9, ChronoUnit.DAYS), startDate.with(new WorkingDays(8, dayOffsSaturday)));
		assertEquals(startDate.plus(11, ChronoUnit.DAYS), startDate.with(new WorkingDays(9, dayOffsSaturday)));
		assertEquals(startDate.plus(12, ChronoUnit.DAYS), startDate.with(new WorkingDays(10, dayOffsSaturday)));
		assertEquals(startDate.plus(13, ChronoUnit.DAYS), startDate.with(new WorkingDays(11, dayOffsSaturday)));
		assertEquals(startDate.plus(14, ChronoUnit.DAYS), startDate.with(new WorkingDays(12, dayOffsSaturday)));

		DayOfWeek[] dayOffsFrSa = new DayOfWeek[]{DayOfWeek.FRIDAY, DayOfWeek.SATURDAY};
		assertEquals(startDate.plus(1, ChronoUnit.DAYS), startDate.with(new WorkingDays(1, dayOffsFrSa)));
		assertEquals(startDate.plus(4, ChronoUnit.DAYS), startDate.with(new WorkingDays(2, dayOffsFrSa)));
		assertEquals(startDate.plus(5, ChronoUnit.DAYS), startDate.with(new WorkingDays(3, dayOffsFrSa)));
		assertEquals(startDate.plus(6, ChronoUnit.DAYS), startDate.with(new WorkingDays(4, dayOffsFrSa)));
		assertEquals(startDate.plus(7, ChronoUnit.DAYS), startDate.with(new WorkingDays(5, dayOffsFrSa)));
		assertEquals(startDate.plus(8, ChronoUnit.DAYS), startDate.with(new WorkingDays(6, dayOffsFrSa)));
		assertEquals(startDate.plus(11, ChronoUnit.DAYS), startDate.with(new WorkingDays(7, dayOffsFrSa)));
		assertEquals(startDate.plus(12, ChronoUnit.DAYS), startDate.with(new WorkingDays(8, dayOffsFrSa)));
		assertEquals(startDate.plus(13, ChronoUnit.DAYS), startDate.with(new WorkingDays(9, dayOffsFrSa)));
		assertEquals(startDate.plus(14, ChronoUnit.DAYS), startDate.with(new WorkingDays(10, dayOffsFrSa)));
		assertEquals(startDate.plus(15, ChronoUnit.DAYS), startDate.with(new WorkingDays(11, dayOffsFrSa)));
		assertEquals(startDate.plus(18, ChronoUnit.DAYS), startDate.with(new WorkingDays(12, dayOffsFrSa)));
	}
}
