package Timer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;


public class TimerManager {
	
	private Logger log = Logger.getLogger(TimerManager.class);
	
	/**
	 * ����һ����ʱ���� 
	 * @param task  ������
	 * @param interval �������
	 */
	public void addTask(BaseTimerTask task,Date date,long interval)
	{
		
		if(date.before(new Date()))
		{
			date = addDay(date,1);    //������������ʱ�� С�ڵ���ϵͳʱ��Ļ� �����������ִ�� ������Ҫ���
		}
		
		Timer timer = new Timer();
		timer.schedule(task,date,interval);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		
		System.out.println("定时器开始时间"+df.format(date)+" 时间间隔:"+interval/(60*60*1000)+"小时");
		log.error("定时器开始时间"+df.format(date)+" 时间间隔"+interval/(60*60*1000)+"小时");
		
	}
	
	/**
	 * ���ö�ʱ��ʱ��/ÿ��
	 * @param hour  
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getDate(int hour,int minute,int second)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);		
		return calendar.getTime();
	}
	
	/**
	 * �� date �Ļ��� ���� num ��
	 * @param date
	 * @param num
	 * @return
	 */
	private Date addDay(Date date,int num)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		return calendar.getTime();
	}

}
