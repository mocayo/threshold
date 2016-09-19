package Action;

import java.io.IOException;

import Dao.IT_Water_LevelDao;
import Entity.T_Water_Level;

import util.Action;

public class WaterLevel extends Action{
	private IT_Water_LevelDao t_water_levelDao;
	
	public void getWaterLevel() throws IOException{
		T_Water_Level t_Water_Level=t_water_levelDao.getWL_latest();
		response.getWriter().write(String.valueOf(t_Water_Level.getDt())+","+t_Water_Level.getWl());
	}

	public IT_Water_LevelDao getT_water_levelDao() {
		return t_water_levelDao;
	}

	public void setT_water_levelDao(IT_Water_LevelDao t_water_levelDao) {
		this.t_water_levelDao = t_water_levelDao;
	}
	
}
