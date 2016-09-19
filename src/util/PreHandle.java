package util;

import java.util.Date;

import org.json.JSONObject;

import PreTreatNewset.PreTreat;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWComplexity;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

public class PreHandle {
	//data为原始数据，空值为double.NAN。返回值类型为JSONObject,预处理后的数据存在Key为‘data’,标记值Key为‘bool’
	public JSONObject preHandle(double[] data,PreTreat preTreat) {
		double[] result = null;
		double[][] value;
		double[][] valueBool;
		Date start = new Date();
		Object[] preTreatResult = null;
		JSONObject json=new JSONObject();
		MWNumericArray temp = null;
		
//			preTreat = new PreTreat();
			try {
				preTreatResult = preTreat.PreTreat(2, data);
			} catch (MWException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("data[]:");
				for(int k=0;k<data.length;k++)
					System.out.println("data["+k+"]"+data[k]);
			}
			/*int[] dims = { 6, 1 };
			MWNumericArray tmp1 = MWNumericArray.newInstance( dims, MWClassID.DOUBLE,  
                    MWComplexity.REAL);
			tmp1.set(1, 1);
			tmp1.set(2, 1);
			tmp1.set(3, 1);
			tmp1.set(4, 1);
			tmp1.set(5, 1);
			tmp1.set(6, 0);
			
			MWNumericArray tmp2 = MWNumericArray.newInstance( dims, MWClassID.DOUBLE,  
                    MWComplexity.REAL);
			tmp2.set(1, 19.2805);
			tmp2.set(2, 19.2805);
			tmp2.set(3, 19.2780);
			tmp2.set(4, 19.2820);
			tmp2.set(5, 19.2766);
			tmp2.set(6, 19.2397);
			
			preTreatResult = new Object[2];
			preTreatResult[0] = tmp2;
			preTreatResult[1] = tmp1;*/
		try {
			temp = (MWNumericArray) preTreatResult[0];
			value = (double[][]) temp.toDoubleArray();
			temp = (MWNumericArray) preTreatResult[1];
			valueBool = (double[][]) temp.toDoubleArray();
			result = new double[value.length];
			for (int i = 0; i < result.length; i++) {
				result[i] = value[i][0];
			}
			json.put("data", result);
			result = new double[value.length];
			for (int i = 0; i < result.length; i++) {
				result[i] = valueBool[i][0];
			}
			json.put("bool", result);
			Date end = new Date();
			System.out.println("--------本次预处理消耗时间为"
					+ (end.getTime() - start.getTime()) / 1000+"s----------------");
//			preTreat.dispose();
//			preTreat = null;
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MWArray.disposeArray(temp);
			result = null;
			preTreatResult = null;
			value = null;
			valueBool = null;
		}
		return json;
	}
}
