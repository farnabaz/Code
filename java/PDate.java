/**
 *	
 *	
 *	Based on code from http://farsiweb.info
 *	Version 0.1 - 2013/3/20
 *	@author Ahad Birang
 */
import java.util.Date;
import java.util.Calendar;

public class PDate extends Date {
	
	private static int[] gregorian_days_in_month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static int[] jalali_days_in_month    = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

	public PDate() {
		super();
	}

	public PDate(int year, int month, int day, boolean isJalali) {
		this();
		_setDate(year, month, day, isJalali);
	}

	/**
	 *	set jalali year, month and day
	 */
	public void setJalaliDate(int year, int month, int day) {
		_setDate(year, month, day, true);
	}

	/**
	 *	return jalali Date elements in int array
	 *	<code>{Year, Month, Day}</code>
	 */
	public int[] getJalaliDateElements() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		return _gregorianToJalali(year, month, day);
	}

	/**
	 *	return jalali year
	 */
	public int getJalaliYear() {
		int jalali[] = getJalaliDateElements();
		return jalali[0];
	}

	/**
	 *	return jalali month
	 */
	public int getJalaliMonth() {
		int jalali[] = getJalaliDateElements();
		return jalali[1];
	}

	/**
	 *	return jalali day of month
	 */
	public int getJalaliDay() {
		int jalali[] = getJalaliDateElements();
		return jalali[2];
	}

	/**
	 *	return jalali month name
	 */
	public String getJalaliYearSyambol() {
		return _jalaliYearSymbol(this.getJalaliYear());
	}

	/**
	 *	return jalali month name
	 */
	public String getJalaliMonthName() {
		return _jalaliMonthName(this.getJalaliMonth());
	}

	/**
	 *	return jalali month name
	 */
	public String getJalaliSeasonName() {
		return _jalaliSeasonName(this.getJalaliMonth());
	}

	/**
	 *	change date
	 */
	private void _setDate(int year, int month, int day, boolean isJalali) {
		int date[] = new int[]{year, month, day};
		Calendar cal = Calendar.getInstance();
		cal.setTime(this);
		if (isJalali) {
			date = _jalaliToGregorian(year, month, day);
		}
		cal.set(Calendar.YEAR, date[0]);
		cal.set(Calendar.MONTH, date[1]);
		cal.set(Calendar.DAY_OF_MONTH, date[2]);
		this.setTime(cal.getTime().getTime());
	}

	/**
	 *	Convert jalali date to gregorian
	 *	
	 *	@param y jalali year
	 *	@param m jalali month
	 *	@param d jalali day
	 */
	private int[] _jalaliToGregorian(int y, int m, int d) {
		int year  = y - 979;
		int month = m - 1;
		int day   = d - 1;

		int j_day_no = 365*year + (year/33)*8 + ((year%33+3)/4);
		for (int i = 0; i < month; ++i) {
			j_day_no += jalali_days_in_month[i];
		}

		j_day_no += day;

		int g_day_no = j_day_no + 79;

		int gyear = 1600 + 400 * (g_day_no/ 146097);
		g_day_no = g_day_no % 146097;

		boolean leap = true;
		if (g_day_no >= 36525) {
			g_day_no--;
			gyear += 100 * (g_day_no % 36524);
			g_day_no = g_day_no % 36524;

			if (g_day_no >= 365) {
				g_day_no++;
			} else {
				leap = false;
			}
		}

		gyear += 4 *(g_day_no/1461);
		g_day_no = g_day_no % 1461;

		if (g_day_no >= 366) {
			leap = false;

			g_day_no--;
			gyear += (g_day_no/365);
			g_day_no = g_day_no % 365;
		}

		int gmonth = 0;
		for (gmonth = 0; g_day_no >= gregorian_days_in_month[gmonth] + ((gmonth==1 && leap)?1:0); gmonth++) {
			g_day_no -= gregorian_days_in_month[gmonth] + ((gmonth==1 && leap)?1:0);
		}
		gmonth ++;
		int gday = g_day_no + 1;

		return new int[]{gyear, gmonth, gday};
	}

	private int[] _gregorianToJalali(int y, int m, int d) {
		int year  = y - 1600;
		int month = m - 1 ;
		int day   = d - 1;

		int g_day_no = 365*year + ((year+3)/4) - ((year+99)/100) + ((year+399)/400);
		for (int i = 0; i < month; ++i) {
			g_day_no += gregorian_days_in_month[i];
		}
		if (month > 1 && ((year%4 == 0 && year%100 != 0) || (year%400 == 0))) {
			g_day_no++;
		}
		g_day_no += day;

		int j_day_no = g_day_no - 79;
		int j_np = j_day_no / 12053;
		j_day_no %= 12053;

		int jyear = 979 + 33*j_np + 4*(j_day_no/1461);
		j_day_no %= 1461;

		if (j_day_no >= 366) {
			jyear += ((j_day_no - 1)/365);
			j_day_no = (j_day_no - 1)/ 365;
		}
		int jmonth = 0;
		for (jmonth = 0; jmonth<11 && j_day_no >= jalali_days_in_month[jmonth]; ++jmonth) {
			j_day_no -= jalali_days_in_month[jmonth];
		}

		jmonth++;
		int jday = j_day_no+1;

		return new int[]{jyear, jmonth, jday};
	}

	/*
	 *	jalali day of week name in fa_IR locale
	 *	@param day jalali day of week
	 */
	private String _jalaliDayOfWeekName(int day) {
		switch (day) {
			case 1: return "شنبه";
			case 2: return "یک شنبه";
			case 3: return "دو شنبه";
			case 4: return "سه شنبه";
			case 5: return "چهار شنبه";
			case 6: return "پنج شنبه";
			case 7: return "جمعه";
			default: return null;
		}
	}

	/*
	 *	jalali month name in fa_IR locale
	 *	@param month jalali month
	 */
	private String _jalaliMonthName(int month) {
		switch (month) {
			case 1:	return "فروردین";
			case 2: return "اردیبهشت";
			case 3: return "خرداد";
			case 4: return "تیر";
			case 5: return "مرداد";
			case 6: return "شهریور";
			case 7: return "مهر";
			case 8: return "آبان";
			case 9: return "آذر";
			case 10: return "دی";
			case 11: return "بهمن";
			case 12: return "اسفند";
			default: return null;
		}
	}

	/*
	 *	jalali year symbol in fa_IR locale
	 *	@param year jalali year
	 */
	private String _jalaliYearSymbol(int year) {
		switch ((year-6) % 12) {
			case 0: return "خوک";
			case 1:	return "موش";
			case 2: return "گاو";
			case 3: return "پلنگ";
			case 4: return "خرگوش";
			case 5: return "نهنگ";
			case 6: return "مار";
			case 7: return "اسب";
			case 8: return "گوسفند";
			case 9: return "میمون";
			case 10: return "مرغ";
			case 11: return "سگ";
			default: return null;
		}
	}

	/*
	 *	jalali season name in fa_IR locale
	 *	@param month jalali month
	 */
	private String _jalaliSeasonName(int month) {
		switch ((month-1) / 3) {
			case 0: return "بهار";
			case 1: return "تابستان";
			case 2: return "پاییز";
			case 3: return "زمستان";
			default: return null;
		}
	}

	public static void main(String[] args) {
		PDate p = new PDate(1392, 1, 1, true);
		int []a = p._jalaliToGregorian(1391, 12, 30);
		System.out.println(a[0] + "-" + a[1] + "-" + a[2]);
		System.out.println(p.getJalaliMonthName());
		System.out.println(p.getJalaliYearSyambol());
	}
}