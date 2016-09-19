package Timer;

import java.io.IOException;

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
import util.DataPredictAutomatic;


public class DoDataTransTask extends BaseTimerTask {

	private Logger log = Logger.getLogger(DoDataTransTask.class);

	private IMonitorItemTypeDao monitorItemTypeDao;
	private IDEFINSSORTDao definssortDao;
	private ICommonPredictDao commonPredictDao;
	private ISIN_PRE_AUTODao sin_preAutoDao;
	private IDataPredictedDao dataPredictedDao;
	private ICommonPreHandleDao commonPreHandleDao;
	private IModelPointDao modelPointDao;
	private IMidCharacteristicDao midCharacterDao;
	private ILstzzParasDao lstzzParasDao;
	private IW_CENodeMetMemDao ceNodeMetMemDao;
	private ISigamaDao sigamaDao;
	private IDataCollectionDao dataCollectionDao;
	private IT_Water_LevelDao t_water_levelDao;
	
	public void run() {
	
		DataPredictAutomatic dp = new DataPredictAutomatic (
				monitorItemTypeDao, definssortDao,
				commonPredictDao, sin_preAutoDao,
				dataPredictedDao, commonPreHandleDao,
				modelPointDao,midCharacterDao,lstzzParasDao,
				ceNodeMetMemDao,sigamaDao,dataCollectionDao,t_water_levelDao);
		try {
			dp.executeByDay();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void setSin_preAutoDao(ISIN_PRE_AUTODao sin_preAutoDao) {
		this.sin_preAutoDao = sin_preAutoDao;
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

	public ILstzzParasDao getLstzzParasDao() {
		return lstzzParasDao;
	}

	public void setLstzzParasDao(ILstzzParasDao lstzzParasDao) {
		this.lstzzParasDao = lstzzParasDao;
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

	public IDataCollectionDao getDataCollectionDao() {
		return dataCollectionDao;
	}

	public void setDataCollectionDao(IDataCollectionDao dataCollectionDao) {
		this.dataCollectionDao = dataCollectionDao;
	}

	public IT_Water_LevelDao getT_water_levelDao() {
		return t_water_levelDao;
	}

	public void setT_water_levelDao(IT_Water_LevelDao t_water_levelDao) {
		this.t_water_levelDao = t_water_levelDao;
	}
	
	}
