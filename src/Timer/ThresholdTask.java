package Timer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

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
import util.DataPredictAutomatic2;

public class ThresholdTask extends TimerTask {
	private ICommonPredictDao commonPredictDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private IDataPredictedDao dataPredictedDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IModelPointDao modelPointDao;
	private IMidCharacteristicDao midCharacterDao;
	private ILstzzParasDao lstzzParasDao;
	private IW_CENodeMetMemDao ceNodeMetMemDao;
	private ISigamaDao sigamaDao;
	private IDataCollectionDao dataCollectionDao;
	private IT_Water_LevelDao t_water_levelDao;
	
	public boolean isCollected = false;
	
	private Logger logger = Logger.getLogger(ThresholdTask.class);
	
	public ThresholdTask(ICommonPredictDao commonPredictDao, ISIN_PRE_AUTODao sin_preAutoDao,IMonitorItemTypeDao monitorItemTypeDao,
			IDEFINSSORTDao definssortDao,
			IDataPredictedDao dataPredictedDao,
			ICommonPreHandleDao commonPreHandleDao,
			IModelPointDao modelPointDao,
			IMidCharacteristicDao midCharacterDao,
			ILstzzParasDao lstzzParasDao,
			IW_CENodeMetMemDao ceNodeMetMemDao,
			ISigamaDao sigamaDao,
			IDataCollectionDao dataCollectionDao,
			IT_Water_LevelDao t_water_levelDao){
		this.commonPredictDao = commonPredictDao;
		this.sin_preAutoDao = sin_preAutoDao;
		this.monitorItemTypeDao = monitorItemTypeDao;
		this.definssortDao  =definssortDao;
		this.dataPredictedDao = dataPredictedDao;
		this.commonPreHandleDao = commonPreHandleDao;
		this.modelPointDao = modelPointDao;
		this.midCharacterDao = midCharacterDao;
		this.lstzzParasDao = lstzzParasDao;
		this.ceNodeMetMemDao = ceNodeMetMemDao;
		this.sigamaDao = sigamaDao;
		this.dataCollectionDao = dataCollectionDao;
		this.t_water_levelDao = t_water_levelDao;
	}
	
	@Override
	public void run() {
		//输出重定向
		try {
			System.setOut(new PrintStream("D://threshold-logs//thresholdtask-stdout-log.log"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//设置凌晨3点前不执行
		Calendar now = Calendar.getInstance();
		System.out.println(now.get(Calendar.HOUR_OF_DAY) + "时" + now.get(Calendar.MINUTE) + "分" + now.get(Calendar.SECOND) + "秒");
		
		if(now.get(Calendar.HOUR_OF_DAY) < 3)return;
		
		// 获取阈值计算时间
		String day = commonPredictDao.getCurrentTDay();//"2016-08-01";
		logger.debug("获取阈值计算时间为:" + day);
		System.out.println("获取阈值计算时间为:" + day);
		String tday = addDays(day, 1);
		System.out.println("需要计算的时间为:" + tday);
		// 检查是否完成数据汇集
		isCollected = checkDatacollect(tday);
		logger.debug(tday + "数据已经汇集 = " + isCollected);
		System.out.println(tday + "数据已经汇集 = " + isCollected);
		System.out.println(new Date());
		if(isCollected){
			//如果需要计算的数据已经汇集好
			System.out.println("开始阈值计算。。。");
			long thresholdstart = System.currentTimeMillis();
			
			//调用阈值计算模块
			System.out.println("调用阈值计算模块。。。");
			DataPredictAutomatic2 dpa = new DataPredictAutomatic2(monitorItemTypeDao, definssortDao,
					commonPredictDao, sin_preAutoDao, dataPredictedDao,
					commonPreHandleDao, modelPointDao, midCharacterDao,
					lstzzParasDao, ceNodeMetMemDao, sigamaDao,
					dataCollectionDao, t_water_levelDao);
			try {
				dpa.executePerDay(tday);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("计算出错。。。");
			}
			System.out.println("阈值计算模块调用结束。。。");
			long thresholdend = System.currentTimeMillis();
			System.out.println("阈值计算需要时间:" + (thresholdend - thresholdstart) + "ms");
		}
		
		System.out.println(this.scheduledExecutionTime() + "实际执行时间");
	}
	
	// 检查是否完成数据汇集
	private boolean checkDatacollect(String day) {
		boolean isOK = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caijiTime-start", day + " 00:00:00.000");
		map.put("caijiTime-end", day + " 23:59:59.000");
		map.put("name", "datacollect");
		map.put("state", 1);
		int result = sin_preAutoDao.checkWorkstate(map);
		if (result != 0)
			isOK = true;
		return isOK;
	}

	private static String addDays(String day,int n){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(day));
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			System.out.println("格式转换异常");
			return null;
		}
	}
	
}
