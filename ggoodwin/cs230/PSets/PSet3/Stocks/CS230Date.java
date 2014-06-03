/** 
 * A class for modeling dates that might be used in stock quotes. 
 * The <code>CS230Date</code> class is a simple class used for 
 * representing dates consisting of a day, month, and year, for
 * years between 0 and 9999. The class is named <code>CS230Date</code>
 * to distinguish it from Java's standard <code>Date</code> class,
 * which has many more features but is also more complicated to use.
 */
public class CS230Date implements Comparable<CS230Date> {
  
  private int year; 
  private int month; // between 1 (Jan) and 12 (Dec)
  private int day;   // between 1 and 31 for Jan, Mar, May, Jul, Aug, Oct, Dec; 
                     // between 1 and 30 for Apr, Jun, Sep, Nov; 
                     // between 1 and 28 for Feb (29 except in leap years);

  /** 
   * Reads a date from three integers (year, month, day). 
   * Returns a date representing the given year (in the range 0-9999),
   * month (in the range 1=January to 12=December), and day
   * (in the range 1-31). 
   *
   * @param  y   an <code>int</code> representing the year
   * @param  m   an <code>int</code> representing the month
   * @param  d   an <code>int</code> representing the day
   * @throws RuntimeException if the date is invalid
   */
  public CS230Date (int y, int m, int d) {
    if ((y < 0) || (y >= 10000)) {
      throw new RuntimeException ("invalid year: " + y); 
    } else if ((m <= 0) || (m >= 13)) {
      throw new RuntimeException ("invalid month: " + m); 
    } else if (! isDayValidForMonth(d,m,y)) {
      throw new RuntimeException ("invalid day: " + d); 
    } else {
      this.year = y;
      this.month = m;
      this.day = d;
    }
  }

  /** 
   * Reads a date from a string of the form <code>YYYY-MM-DD</code>.
   * In this form, <code>YYYY</code>
   * are four digits specifying the year (in the range 0000-9999),
   * <code>MM</code> are two digits specifying the month (in the
   * range 01-12), and <code>DD</code> are two digits specifying
   * the day (in the range 01-31). Returns a <code>CS230Date</code>
   * instance representing the specified date.
   *
   * @param   s   a <code>String</code> of the form <code>YYYY-MM-DD</code>
   * @return  a <code>CS230Date</code>
   * @throws  RuntimeException if the string is not in the appropriate format
   * or is not an actual date
   */
  public static CS230Date fromString (String s) {
    if ((s.length() != 10) || s.charAt(4) != '-' || s.charAt(7) != '-') {
      throw new RuntimeException ("invalid date format: " + s); 
    } else {
      try {
	int y = Integer.parseInt(s.substring(0,4)); 
	int m = Integer.parseInt(s.substring(5,7)); 
	int d = Integer.parseInt(s.substring(8,10)); 
	return new CS230Date (y,m,d); 
      } catch (NumberFormatException ex) {
	throw new RuntimeException ("invalid date format: " + s); 
      }
    }
  }

  /** 
   * Determines if the given date is valid for the month and year.
   *
   * @param  y   an <code>int</code> representing the year
   * @param  m   an <code>int</code> representing the month
   * @param  d   an <code>int</code> representing the day
   * @return <code>true</code> is the day is a valid day in the given
   * month and year, and <code>false</code> otherwise
   */
  public static boolean isDayValidForMonth(int d, int m, int y) {
    return 
      // between 1 and 31 for Jan, Mar, May, Jul, Aug, Oct, Dec; 
      (d >= 1) && (d <= 31) 
      // between 1 and 30 for Apr, Jun, Sep, Nov; 
      && ( (!hasOnly30Days(m)) || (d != 31) )
      // between 1 and 28 for Feb (29 in leap years);
      && ( (m!=2) || ((d <= 28) || ((d == 29) && isLeapYear(y))));
  }

  private static boolean hasOnly30Days (int m) {
    // Apr, Jun, Sep, Nov have only 30 days.
    return (m==4) || (m==6) || (m==9) || (m==11); 
  }

  /** 
   * Determines if the given year is a leap year.
   * A year is a leap year if it is divisable by 4,
   * except for years divisable by 100 but not 400.
   * 
   * @param   y   an <code>int</code> representing the year
   * @return <code>true</code> if <code>y</code> is a leap year, and 
   * <code>false</code> otherwise.
   */
  public static boolean isLeapYear(int y) {
    if ((y % 400) == 0) 
      return true;
    else if ((y % 100) == 0) 
      return false;
    else 
      return (y % 4) == 0;
  }

  /** 
   * Returns the date that comes after this one.
   *
   * @return a <code>CS230Date</code>
   * @throws RuntimeException for Dec. 31, 9999
   */ 
  public CS230Date next () {
    if (day==31) {
      if (month==12) 
	return new CS230Date(year+1,1,1);
      else 
	return new CS230Date(year,month+1,1);
    } else if (day==30) {
      // between 1 and 30 for Apr, Jun, Sep, Nov; 
      if (hasOnly30Days(month))
	return new CS230Date(year,month+1,1);
      else 
	return new CS230Date(year,month,31);
    } else if ((month==2) && (day==29)) {
	return new CS230Date(year,3,1);
    } else if ((month==2) && (day==28)) {
      if (isLeapYear(year)) {
	return new CS230Date(year,2,29);
      }	else {
	return new CS230Date(year,3,1);
      }
    } else {
      return new CS230Date(year,month,day+1);
    }
  }

  /** 
   * Returns the date that comes before this one.
   *
   * @return a <code>CS230Date</code>
   * @throws RuntimeException for Jan. 1, 0000
   */ 
  public CS230Date prev () {
    if (day==1) {
      if (month==1) 
	return new CS230Date(year-1,12,31);
      else {
	int prevMonth = month-1;
	if (prevMonth==2)
	  return new CS230Date(year,prevMonth,isLeapYear(year)?29:28);
	else 
	  return new CS230Date(year,prevMonth,hasOnly30Days(prevMonth) ? 30 : 31);
      }
    } else {
      return new CS230Date(year,month,day-1);
    }
  }

  /**
   * Returns the day of this date (an integer in the range 0-31).
   *
   * @return an <code>int</code>
   */
  public int day () {
    return day;
  }

  /**
   * Returns the month of this date (an integer in the range
   * 1=January to 12=December).
   *
   * @return an <code>int</code>
   */
  public int month () {
    return month;
  }

  /**
   * Returns the year of this date (an integer in the range 0-9999).
   *
   * @return an <code>int</code>
   */
  public int year () {
    return year;
  }

  /**
   * Returns a string representation of this date having the 
   * form <code>YYYY-MM-DD</code>. In this form, <code>YYYY</code> are
   * four digits specifying the year (in the range 0000-9999), 
   * <code>MM</code> are two digits specifying the month (in the
   * range 01-12), and <code>DD</code> are two digits specifying the 
   * day (in the range 01-31).
   *
   * @return a <code>String</code> in the format <code>YYYY-MM-DD</code>
   * representing the date
   */
  public String toString () {
    return 
      ((year <= 999) ? "0" : "") 
      + ((year <= 99) ? "0" : "") 
      + ((year <= 9) ? "0" : "") 
      + year
      + "-" 
      + ((month <= 9) ? "0" : "") + month
      + "-" 
      + ((day <= 9) ? "0" : "") + day;
  }
 
  /**
   * Returns <code>true</code> if <code>o</code> is a <code>CS230Date</code>
   * representing the same date as <code>this</code> date, and <code>false</code>
   * otherwise.
   *
   * @param   o   an <code>Object</code> to be compared to <code>this</code> date
   * @return a <code>boolean</code> indicating whether the argument
   * <code>Object</code> is equal to <code>this</code> date
   */
  public boolean equals (Object o) {
    if (! (o instanceof CS230Date)) {
      return false; 
    } else {
      CS230Date d = (CS230Date) o;
      return (d.year == year)
	&& (d.month == month)
	&& (d.day == day);
    }
  }

  /**
   * Returns a negative number if <code>this</code> date comes before
   * <code>d</code>, <code>0</code> if this date is the same as <code>d</code>,
   * and a positive number if <code>this</code> date comes after <code>d</code>.
   *
   * @param   d   a <code>CS230Date</code> to be compared to <code>this</code> date
   * @return an <code>int</code> indicating whether the argument is greater than,
   * equal to, or less than <code>this</code> date
   */
  public int compareTo (CS230Date d) {
    return (10000*year + 100*month + day)
      - (10000*d.year + 100*d.month + d.day);
  }

  /**
   * Returns the earlier of the dates <code>d1</code> and <code>d2</code>.
   *
   * @param d1 a <code>CS230Date</code>
   * @param d2 a <code>CS230Date</code>
   * @return a <code>CS230Date</code>
   */
  public static CS230Date min (CS230Date d1, CS230Date d2) {
    return (d1.compareTo(d2) < 0) ? d1 : d2;
  }

  /**
   * Returns the later of the dates <code>d1</code> and <code>d2</code>.
   *
   * @param d1 a <code>CS230Date</code>
   * @param d2 a <code>CS230Date</code>
   * @return a <code>CS230Date</code>
   */
  public static CS230Date max (CS230Date d1, CS230Date d2) {
    return (d1.compareTo(d2) > 0) ? d1 : d2;
  }

  /**
   * The minimal representable date = Jan 1, 0000.
   */
  public static final CS230Date MIN_DATE = new CS230Date(0,1,1);
 
  /**
   * The maximal representable date = Dec 31, 9999.
   */
  public static final CS230Date MAX_DATE = new CS230Date(9999,12,31);

  /**
   * <code>main</code> method containing code to test the methods
   * of the <code>CS230Date</code> class.
   */
  public static void main (String[] args) {
    if ((args.length == 2) && args[0].equals("next")) {
      String date = args[1];
      System.out.println(fromString(date).next().toString());
    } else if ((args.length == 3) && args[0].equals("next")) {
      CS230Date start = fromString(args[1]);
      CS230Date stop = fromString(args[2]);
      for (CS230Date d = start; d.compareTo(stop) <= 0; d = d.next()) {
	System.out.println(d.toString());
      }
    } else if ((args.length == 2) && args[0].equals("prev")) {
      String date = args[1];
      System.out.println(fromString(date).prev().toString());
    } else if ((args.length == 3) && args[0].equals("prev")) {
      CS230Date start = fromString(args[1]);
      CS230Date stop = fromString(args[2]);
      for (CS230Date d = stop; d.compareTo(start) >=0; d = d.prev()) {
	System.out.println(d.toString());
      }
    } else if ((args.length == 3) && args[0].equals("min")) {
      System.out.println(min(fromString(args[1]), fromString(args[2])));
    } else if ((args.length == 3) && args[0].equals("max")) {
      System.out.println(max(fromString(args[1]), fromString(args[2])));
    } else {
      System.out.println("usage:\n"
			 + "java CS230Date next <date-string>\n"
			 + "java CS230Date next <start-date> <stop-date>\n"
			 + "java CS230Date prev <date-string>\n"
			 + "java CS230Date prev <start-date> <stop-date>\n"
			 + "java CS230Date min <date1> <date2>\n"
			 + "java CS230Date max <date1> <date2>\n"
			 );

    }
    
  }

}
