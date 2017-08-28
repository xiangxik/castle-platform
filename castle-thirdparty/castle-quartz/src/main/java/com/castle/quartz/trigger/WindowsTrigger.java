package com.castle.quartz.trigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.quartz.Calendar;
import org.quartz.CronExpression;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.ScheduleBuilder;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CoreTrigger;
import org.springframework.util.Assert;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class WindowsTrigger extends AbstractTrigger<WindowsTrigger> implements CoreTrigger {

	private static final long serialVersionUID = 5520418011891755085L;

	public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;
	public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

	protected static final int YEAR_TO_GIVEUP_SCHEDULING_AT = CronExpression.MAX_YEAR;

	private Locale locale;
	private Date startTime = new Date();
	private SchedType schedType = SchedType.Once;
	private Integer dayInterval = 1;
	private Integer weekInterval = 1;
	private List<Integer> weekDays = new ArrayList<>();
	private List<Integer> months = new ArrayList<>();
	private MonthSchedType monthSchedType = MonthSchedType.ByDay;
	private List<Integer> monthDays = new ArrayList<>();
	private List<Integer> monthWeeks = new ArrayList<>();
	private List<Integer> monthWeekDays = new ArrayList<>();
	private IntervalUnit repeatIntervalUnit;
	private Integer repeatInterval;
	private IntervalUnit durationUnit;
	private Integer duration;
	private Date endTime;
	private Date nextFireTime;
	private Date previousFireTime;

	@Override
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	@Override
	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	@Override
	public boolean hasAdditionalProperties() {
		return true;
	}

	@Override
	public void triggered(Calendar calendar) {
		setPreviousFireTime(getNextFireTime());

		setNextFireTime(getFireTimeAfter(getNextFireTime()));

		while (getNextFireTime() != null && calendar != null && !calendar.isTimeIncluded(getNextFireTime().getTime())) {
			setNextFireTime(getFireTimeAfter(getNextFireTime()));
		}
	}

	@Override
	public Date computeFirstFireTime(Calendar calendar) {
		setNextFireTime(getStartTime());
		while (getNextFireTime() != null && calendar != null && calendar.isTimeIncluded(getNextFireTime().getTime())) {
			setNextFireTime(getFireTimeAfter(getNextFireTime()));
			if (getNextFireTime() == null)
				break;

			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(getNextFireTime());

			if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
				return null;
			}
		}
		return getNextFireTime();
	}

	@Override
	public boolean mayFireAgain() {
		return getNextFireTime() != null;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		Assert.notNull(startTime, "开始时间不能为空");

		this.startTime = startTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public Date getNextFireTime() {
		return nextFireTime;
	}

	@Override
	public Date getPreviousFireTime() {
		return previousFireTime;
	}

	@Override
	public Date getFireTimeAfter(Date after) {
		DateTime startTime = new DateTime(getStartTime());
		DateTime fireTime = startTime;
		DateTime endTime = getEndTime() == null ? null : new DateTime(getEndTime());
		DateTime afterTime = after == null ? startTime.minusSeconds(1) : new DateTime(after);

		Integer repeatIntervalMillis = null;
		Integer dayEndMillis = null;

		if (getRepeatInterval() != null) {
			if (Objects.equal(getRepeatIntervalUnit(), IntervalUnit.MINUTE)) {
				repeatIntervalMillis = getRepeatInterval() * 60 * 1000;
			} else {
				repeatIntervalMillis = getRepeatInterval() * 60 * 60 * 1000;
			}
			if (getDuration() == null) {
				dayEndMillis = -1;
			} else {
				if (Objects.equal(getDurationUnit(), IntervalUnit.MINUTE)) {
					dayEndMillis = startTime.getMillisOfDay() + getDuration() * 60 * 1000;
				} else if (Objects.equal(getDurationUnit(), IntervalUnit.HOUR)) {
					dayEndMillis = startTime.getMillisOfDay() + getDuration() * 60 * 60 * 1000;
				} else if (Objects.equal(getDurationUnit(), IntervalUnit.DAY)) {
					dayEndMillis = startTime.getMillisOfDay() + getDuration() * 24 * 60 * 60 * 1000;
				}
				dayEndMillis = Math.min(dayEndMillis, 24 * 60 * 59 * 1000);
			}
		}

		while ((fireTime.isBefore(afterTime.getMillis()) || fireTime.isEqual(afterTime.getMillis())) || !inFireDay(fireTime)) {
			if (repeatIntervalMillis != null && (dayEndMillis == -1 || isSameDay(fireTime.getMillis(), afterTime.getMillis()))) {
				DateTime time = fireTime;
				while (time.isBefore(afterTime.getMillis()) || time.isEqual(afterTime.getMillis())) {
					time = time.plusMillis(repeatIntervalMillis);
				}

				if (dayEndMillis == -1 || (isSameDay(time.getMillis(), afterTime.getMillis()) && time.getMillisOfDay() < dayEndMillis)) {
					fireTime = time;
					break;
				}
			}

			if (Objects.equal(getSchedType(), SchedType.ByDay)) {
				fireTime = fireTime.plusDays(getDayInterval());
			} else if (Objects.equal(getSchedType(), SchedType.ByWeek)) {
				fireTime = fireTime.plusWeeks(getWeekInterval());
			} else {
				fireTime = fireTime.plusDays(1);
			}

		}
		return endTime == null || fireTime.isBefore(endTime.getMillis()) ? fireTime.toDate() : null;
	}

	private boolean inFireDay(DateTime fireTime) {
		if (isDayBefore(fireTime.getMillis(), getStartTime().getTime()))
			return false;

		if (getDurationUnit() == null) {
			return true;
		} else {
			if (Objects.equal(getSchedType(), SchedType.Once)) {
				return DateUtils.isSameDay(getStartTime(), fireTime.toDate());
			} else if (Objects.equal(getSchedType(), SchedType.ByDay)) {
				DateTime offsetTime = new DateTime(getStartTime());
				while (isDayBefore(offsetTime.getMillis(), fireTime.getMillis())) {
					offsetTime = offsetTime.plusDays(getDayInterval());
				}
				return isSameDay(offsetTime.getMillis(), fireTime.getMillis());
			} else if (Objects.equal(getSchedType(), SchedType.ByWeek)) {
				DateTime offsetTime = new DateTime(getStartTime());
				while (isDayBefore(offsetTime.getMillis(), fireTime.getMillis())) {
					offsetTime = offsetTime.plusWeeks(getWeekInterval());
				}
				return isSameDay(offsetTime.getMillis(), fireTime.getMillis());
			} else {
				if (Objects.equal(getMonthSchedType(), MonthSchedType.ByDay)) {
					return getMonths().contains(fireTime.getMonthOfYear()) && getMonthDays().contains(fireTime.getDayOfMonth());
				} else {
					return getMonths().contains(fireTime.getMonthOfYear())
							&& getMonthWeeks().contains(fireTime.toCalendar(getLocale()).get(java.util.Calendar.WEEK_OF_MONTH))
							&& getMonthDays().contains(fireTime.getDayOfWeek());
				}
			}
		}
	}

	private boolean isDayBefore(long time1, long time2) {
		return !isSameDay(time1, time2) && time1 < time2;
	}

	private boolean isSameDay(long time1, long time2) {
		return DateUtils.isSameDay(new Date(time1), new Date(time2));
	}

	@Override
	public Date getFinalFireTime() {
		return getFireTimeBefore(MoreObjects.firstNonNull(getEndTime(), getStartTime()));
	}

	@Override
	protected boolean validateMisfireInstruction(int candidateMisfireInstruction) {
		if (candidateMisfireInstruction < MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY) {
			return false;
		}
		if (candidateMisfireInstruction > MISFIRE_INSTRUCTION_DO_NOTHING) {
			return false;
		}
		return true;
	}

	@Override
	public void updateAfterMisfire(Calendar cal) {
		int instr = getMisfireInstruction();
		if (instr == MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY) {
			return;
		}
		if (instr == MISFIRE_INSTRUCTION_SMART_POLICY) {
			instr = MISFIRE_INSTRUCTION_FIRE_ONCE_NOW;
		}
		if (instr == MISFIRE_INSTRUCTION_DO_NOTHING) {
			Date newFireTime = getFireTimeAfter(new Date());
			while (newFireTime != null && cal != null && !cal.isTimeIncluded(newFireTime.getTime())) {
				newFireTime = getFireTimeAfter(newFireTime);
			}
			setNextFireTime(newFireTime);
		} else if (instr == MISFIRE_INSTRUCTION_FIRE_ONCE_NOW) {
			setNextFireTime(new Date());
		}
	}

	@Override
	public void updateWithNewCalendar(Calendar cal, long misfireThreshold) {
		setNextFireTime(getFireTimeAfter(getPreviousFireTime()));
		if (getNextFireTime() == null || cal == null) {
			return;
		}

		Date now = new Date();
		while (getNextFireTime() != null && !cal.isTimeIncluded(getNextFireTime().getTime())) {
			setNextFireTime(getFireTimeAfter(getNextFireTime()));

			if (getNextFireTime() == null)
				break;

			java.util.Calendar c = new GregorianCalendar();
			c.setTime(getNextFireTime());
			if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
				setNextFireTime(null);
			}
			if (getNextFireTime() != null && getNextFireTime().before(now)) {
				long diff = now.getTime() - getNextFireTime().getTime();
				if (diff >= misfireThreshold) {
					setNextFireTime(getFireTimeAfter(getNextFireTime()));
					continue;
				}
			}
		}

	}

	@Override
	public ScheduleBuilder<WindowsTrigger> getScheduleBuilder() {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public SchedType getSchedType() {
		return schedType;
	}

	public void setSchedType(SchedType schedType) {
		this.schedType = schedType;
	}

	public Integer getDayInterval() {
		return dayInterval;
	}

	public void setDayInterval(Integer dayInterval) {
		this.dayInterval = dayInterval;
	}

	public Integer getWeekInterval() {
		return weekInterval;
	}

	public void setWeekInterval(Integer weekInterval) {
		this.weekInterval = weekInterval;
	}

	public List<Integer> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<Integer> weekDays) {
		this.weekDays = weekDays;
	}

	public List<Integer> getMonths() {
		return months;
	}

	public void setMonths(List<Integer> months) {
		this.months = months;
	}

	public MonthSchedType getMonthSchedType() {
		return monthSchedType;
	}

	public void setMonthSchedType(MonthSchedType monthSchedType) {
		this.monthSchedType = monthSchedType;
	}

	public List<Integer> getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(List<Integer> monthDays) {
		this.monthDays = monthDays;
	}

	public List<Integer> getMonthWeeks() {
		return monthWeeks;
	}

	public void setMonthWeeks(List<Integer> monthWeeks) {
		this.monthWeeks = monthWeeks;
	}

	public List<Integer> getMonthWeekDays() {
		return monthWeekDays;
	}

	public void setMonthWeekDays(List<Integer> monthWeekDays) {
		this.monthWeekDays = monthWeekDays;
	}

	public IntervalUnit getRepeatIntervalUnit() {
		return repeatIntervalUnit;
	}

	public void setRepeatIntervalUnit(IntervalUnit repeatIntervalUnit) {
		this.repeatIntervalUnit = repeatIntervalUnit;
	}

	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public IntervalUnit getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(IntervalUnit durationUnit) {
		this.durationUnit = durationUnit;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public static enum SchedType {
		Once, ByDay, ByWeek, ByMonth
	}

	public static enum MonthSchedType {
		ByDay, ByWeek
	}

	public Date getFireTimeBefore(Date beforeTime) {
		Date fireTime = getFireTimeAfter(null);
		while (getFireTimeAfter(fireTime).before(beforeTime)) {
			fireTime = getFireTimeAfter(fireTime);
		}
		if (fireTime != null && getStartTime() != null && fireTime.before(getStartTime())) {
			fireTime = null;
		}
		return fireTime;
	}

}
