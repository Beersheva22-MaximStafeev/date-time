package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.stream.Stream;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		return Stream.iterate(temporal.plus(1, ChronoUnit.DAYS), tp -> tp.plus(1, ChronoUnit.DAYS))
				.filter(tp -> tp.get(ChronoField.DAY_OF_MONTH) == 13 && tp.get(ChronoField.DAY_OF_WEEK) == 5)
				.limit(1).findFirst().get();
//		Temporal res = temporal;
//		ChronoUnit addUnit;
//		do {
//			addUnit = res.get(ChronoField.DAY_OF_MONTH) == 13 ? ChronoUnit.MONTHS : ChronoUnit.DAYS;
//			res = res.plus(1, addUnit);
//		} while (res.get(ChronoField.DAY_OF_MONTH) != 13 || res.get(ChronoField.DAY_OF_WEEK) != 5);
//		return res;
	}

}
