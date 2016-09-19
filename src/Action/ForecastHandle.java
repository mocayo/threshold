package Action;

import java.io.IOException;

import net.sf.json.JSONArray;
import util.Action;

public class ForecastHandle extends Action{
	public void execute() throws IOException {
		String result1="C4-A04-PL-01,2010-06-21 23:59:00,-0.3069,-1.3592,2015-01-02 23:59:00,0";
		String result2="C4-A04-PL-01,2010-06-22 11:59:00,-0.1729,-1.381,2015-01-03 23:59:00,0";
		String [] reStrings1=result1.split(",");
		String [] reStrings2=result2.split(",");
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(reStrings1);jsonArray.add(reStrings2);
		response.getWriter().write(jsonArray.toString());
	}
}
