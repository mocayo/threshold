package Timer;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import Dao.ICommonPreHandleDao;
import Dao.ICommonPredictDao;
import Dao.IDEFINSSORTDao;
import Dao.IDataCollectionDao;
import Dao.IDataPredictedDao;
import Dao.ILstzzParasDao;
import Dao.IMidCharacteristicDao;
import Dao.IModelPointDao;
import Dao.IMonitorItemTypeDao;
import Dao.ISIN_PRE_AUTODao;
import Dao.ISigamaDao;
import Dao.IT_Water_LevelDao;
import Dao.IW_CENodeMetMemDao;

public class ThresholdScheduleListener implements ServletContextListener {

	private Timer timer;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("阈值任务定时器 cancel");
		if (timer != null)
			timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()); 
		if (springContext != null) {
			ICommonPredictDao commonPredictDao = (ICommonPredictDao) springContext.getBean("commonPredictDao");
			ISIN_PRE_AUTODao sin_preAutoDao = (ISIN_PRE_AUTODao) springContext.getBean("sin_preAutoDao");
			IMonitorItemTypeDao monitorItemTypeDao = (IMonitorItemTypeDao) springContext.getBean("monitorItemTypeDao");
			IDEFINSSORTDao definssortDao = (IDEFINSSORTDao) springContext.getBean("definssortDao");
			IDataPredictedDao dataPredictedDao = (IDataPredictedDao) springContext.getBean("dataPredictedDao");
			ICommonPreHandleDao commonPreHandleDao = (ICommonPreHandleDao) springContext.getBean("commonPreHandleDao");
			IModelPointDao modelPointDao = (IModelPointDao) springContext.getBean("modelPointDao");
			IMidCharacteristicDao midCharacterDao = (IMidCharacteristicDao) springContext.getBean("midCharacterDao");
			ILstzzParasDao lstzzParasDao = (ILstzzParasDao) springContext.getBean("lstzzParasDao");
			IW_CENodeMetMemDao ceNodeMetMemDao = (IW_CENodeMetMemDao) springContext.getBean("ceNodeMetMemDao");
			ISigamaDao sigamaDao = (ISigamaDao) springContext.getBean("sigamaDao");
			IDataCollectionDao dataCollectionDao = (IDataCollectionDao) springContext.getBean("dataCollectionDao");
			IT_Water_LevelDao t_water_levelDao = (IT_Water_LevelDao) springContext.getBean("t_water_levelDao");
			
			ThresholdTask task = new ThresholdTask(commonPredictDao, sin_preAutoDao,monitorItemTypeDao,definssortDao,dataPredictedDao,commonPreHandleDao
					,modelPointDao,midCharacterDao,lstzzParasDao,ceNodeMetMemDao,sigamaDao,dataCollectionDao,t_water_levelDao);
			timer = new Timer("开始定时扫描是否进行阈值计算，扫描间隔为10min", true);
			timer.schedule(task, 0, 1000 * 60 * 10);
//			if(task.isCollected){
//				//如果需要计算的数据已经汇集好
//				System.out.println("开始阈值计算。。。");
//				timer.cancel();
//				long thresholdstart = System.currentTimeMillis();
//				int i=0;
//				while(i<2000){
//					i++;
//					System.out.println(i);
//				}
//				long thresholdend = System.currentTimeMillis();
//				System.out.println("阈值计算需要时间:" + (thresholdend - thresholdstart) + "ms");
//				timer.schedule(task, 0, 1000 * 60 * 1000);
//			}
		} else {
			System.out.println("获取应用程序上下文失败!");
			contextDestroyed(sce);
			return;
		}
	}
	
}
