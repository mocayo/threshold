package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;














import Dao.ICommonPreHandleDao;
import Dao.ICommonPredictDao;
import Dao.IDEFINSSORTDao;
import Dao.IDataPredictedDao;
import Dao.IMidCharacteristicDao;
import Dao.IModelPointDao;
import Dao.IMonitorItemTypeDao;
import Dao.ISIN_PRE_AUTODao;
import Dao.ISigamaDao;
import Dao.IW_CENodeMetMemDao;
import Entity.ModelPoint;
import Entity.MonitorItemType;
import Entity.T_SIN_PRE_AUTO;

public class DataPredictAutomaticSigamaTEST extends Action{

	private String genId;
	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private ICommonPredictDao commonPredictDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private IDataPredictedDao dataPredictedDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IModelPointDao modelPointDao;
	private IMidCharacteristicDao midCharacterDao;
	private IW_CENodeMetMemDao ceNodeMetMemDao;
	private ISigamaDao sigamaDao;
	
		public void execute() throws IOException {
			jobTimer();
		}

		public void jobTimer() {
			System.out.println("定时器启动");
			long st = System.nanoTime() / 1000000L;

			genId = new IdGenerator().generateId();

			Map<String, Object> map = new HashMap<String, Object>();

			String str = "2012-5-30";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			
//			List<String> tableNameList = monitorItemTypeDao.getTableNames();// new
//			for (String tableName : tableNameList) {
			String tableName = "T_ZB_PL";
				/*List<ModelPoint> iswhereList = modelPointDao
						.getCaculatePoints(tableName);*/
//				for (int i = 0; i < iswhereList.size(); i++) {
			for (int i = 1; i < 3; i++) {
					try {
						String id = "C4-A22-PL-0"+i;
						method6(map, tableName,id,date,calendar, sdf);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
//			}
			System.out.println("共需要时间" + (System.nanoTime() / 1000000L - st)
					/ 1000L);
		}
		
		/**
		 * sigema判定法
		 * @param tableName
		 */
		private void method6(Map<String, Object> map, String tableName,
				String id,Date date, Calendar calendar,
				SimpleDateFormat sdf){
			map.put("tableName",tableName);
			calendar.setTime(date);
			for (int j = 0; j < 1411; j++) {
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
				String dt = sdf.format(calendar.getTime());
			map.put("dt",dt);
			if (id == null) {
				return;
			}
			map.clear();
			map.put("tableName", tableName);
			map.put("instr_no", id);
			map.put("modelId", "7");

			long sTime = System.nanoTime() / 1000000L;
			// map.clear();
			if (id.contains("'")) {
				id = id.replaceAll("'", "''");
			}
			map.put("dt", dt);

			List<T_SIN_PRE_AUTO> liR1 = null;
			List<T_SIN_PRE_AUTO> liR2 = null;
			List<T_SIN_PRE_AUTO> liR3 = null;
				liR1 = sin_preAutoDao.getDataByIdTmR1(map);
				liR2 = sin_preAutoDao.getDataByIdTmR2(map);

			MonitorItemType itemType = monitorItemTypeDao
					.getItemTypeByTableName(tableName);
			
			if (((!itemType.getR1().equals("0"))
					&& (liR1 != null && liR1.size() == 0))&&
					((!itemType.getR2().equals("0"))
							&& (liR2 != null && liR2.size() == 0))){
				try {
					DataPredictUtil.predictMethod5(commonPredictDao,sin_preAutoDao, map);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			/*for(int i=1 ; i< 4;i++){
				
			}*/
		}
		
		
	public IMonitorItemTypeDao getMonitorItemTypeDao() {
		return monitorItemTypeDao;
	}


	public void setMonitorItemTypeDao(IMonitorItemTypeDao monitorItemTypeDao) {
		this.monitorItemTypeDao = monitorItemTypeDao;
	}


	public IDEFINSSORTDao getDefinssortDao() {
		return definssortDao;
	}


	public void setDefinssortDao(IDEFINSSORTDao definssortDao) {
		this.definssortDao = definssortDao;
	}


	public ICommonPredictDao getCommonPredictDao() {
		return commonPredictDao;
	}


	public void setCommonPredictDao(ICommonPredictDao commonPredictDao) {
		this.commonPredictDao = commonPredictDao;
	}


	public ISIN_PRE_AUTODao getSin_preAutoDao() {
		return sin_preAutoDao;
	}


	public void setSin_preAutoDao(ISIN_PRE_AUTODao sinPreAutoDao) {
		sin_preAutoDao = sinPreAutoDao;
	}

	public IDataPredictedDao getDataPredictedDao() {
		return dataPredictedDao;
	}

	public void setDataPredictedDao(IDataPredictedDao dataPredictedDao) {
		this.dataPredictedDao = dataPredictedDao;
	}

	public ICommonPreHandleDao getCommonPreHandleDao() {
		return commonPreHandleDao;
	}

	public void setCommonPreHandleDao(ICommonPreHandleDao commonPreHandleDao) {
		this.commonPreHandleDao = commonPreHandleDao;
	}

	public IModelPointDao getModelPointDao() {
		return modelPointDao;
	}

	public void setModelPointDao(IModelPointDao modelPointDao) {
		this.modelPointDao = modelPointDao;
	}

	public IMidCharacteristicDao getMidCharacterDao() {
		return midCharacterDao;
	}

	public void setMidCharacterDao(IMidCharacteristicDao midCharacterDao) {
		this.midCharacterDao = midCharacterDao;
	}

	public IW_CENodeMetMemDao getCeNodeMetMemDao() {
		return ceNodeMetMemDao;
	}

	public void setCeNodeMetMemDao(IW_CENodeMetMemDao ceNodeMetMemDao) {
		this.ceNodeMetMemDao = ceNodeMetMemDao;
	}

	public ISigamaDao getSigamaDao() {
		return sigamaDao;
	}

	public void setSigamaDao(ISigamaDao sigamaDao) {
		this.sigamaDao = sigamaDao;
	} 

}
