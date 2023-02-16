package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class WorkingDays implements TemporalAdjuster {

	private int dayPlus;
	private HashSet<Integer> dayOffs = new HashSet<>();
	
	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;
		if (dayOffs.size() < 7) {
			res = Stream.iterate(temporal.plus(1, ChronoUnit.DAYS), tp -> tp.plus(1, ChronoUnit.DAYS))
					.filter( tp -> !dayOffs.contains(tp.get(ChronoField.DAY_OF_WEEK)) )
					.limit(dayPlus).skip(dayPlus - 1).findFirst().get();
		}
		return res;
	}
	
	public WorkingDays(int dayPlus, DayOfWeek[] dayOffs) {
		this.dayPlus = dayPlus;
		Arrays.stream(dayOffs)
				.map(el -> el.getValue())
				.forEach(this.dayOffs::add);
	}

}
