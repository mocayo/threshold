package Timer;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;



public class MyScheduleListener implements ServletContextListener{
	
	private Logger log = Logger.getLogger(MyScheduleListener.class);
	
	private Timer timer;
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("===========定时器取消cancel===========");
		//timer.cancel();
	}

	public void contextInitialized(ServletContextEvent sce) {
		
//		
//		TimerManager tm = new  TimerManager();
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 13);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		
//		tm.addTask(new DoDataTransTask(),calendar.getTime(), 10 * 60 * 1000 );
		
	}
	

}
