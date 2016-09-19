package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {
	public static SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat myFmt3=new SimpleDateFormat("MM-dd");
	
	
	public static String format(Date tm,String fmt)
	{
		SimpleDateFormat myFmt = new SimpleDateFormat(fmt);
		return myFmt.format(tm);
	}
	
	public static String format1(Timestamp tm)
	{
		return myFmt1.format(tm);
	}

	public static String format2(Date tm)
	{
		return myFmt2.format(tm);
	}
	
	public static String format3(Date tm)
	{
		return myFmt3.format(tm);
	}
}
