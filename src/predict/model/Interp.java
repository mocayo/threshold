package predict.model;

import java.util.ArrayList;
import java.util.List;


//import com.mathworks.toolbox.javabuilder.MWArray;
//import com.mathworks.toolbox.javabuilder.MWCellArray;
//import com.mathworks.toolbox.javabuilder.MWCharArray;
//import com.mathworks.toolbox.javabuilder.MWClassID;
//import com.mathworks.toolbox.javabuilder.MWException;
//import com.mathworks.toolbox.javabuilder.MWNumericArray;


/**
 * 插值函数
 * @author Administrator
 *
 */
public class Interp {
	
	/**
	 * 
	 * @param y_old
	 * @return 预测值、最优步长、插值方法
	 */
	public static List<Object> fit_result(double[] y_old) {
			
//		double y[]= new double[y_old.length];
//		for (int i = 0; i < y.length; i++) {
//			y[i] = y_old[i];
//		}
//		MWNumericArray ay = null; // 用于保存矩阵
//		ay = new MWNumericArray(y_old,MWClassID.DOUBLE);
//			
//		Object[] result = null; // 用于保存计算结果
//		
//		singlepre.Interp anlys2 = null;
//		try {
//			anlys2 = new singlepre.Interp();
//			result = anlys2.Interp(1,ay);
//		} catch (MWException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			MWArray.disposeArray(ay);
//			anlys2.dispose();
//		}
//		
//		MWCellArray msa = null;
//		List<MWArray> ma = null;
//		//保存模型参数
//		List<Object> args = new ArrayList<Object>();
//		
//		for (int i = 0; i < result.length; i++) {
//			msa = (MWCellArray) result[i];
//			ma = msa.asList();
//			//System.out.println(ma.size());
//			for (MWArray mwArray : ma) {
//				int[] index = mwArray.columnIndex();
//				
//				if(index.length == 1)
//				{
//					args.add(mwArray.get(1));
//					System.out.println(mwArray.get(1));
//				}
//				else
//				{
//					String str = "";
//					for (int j = 0; j < index.length; j++) {
//						str = str + mwArray.get(j+1);				
//					}
//					args.add(str);
//					System.out.println(str);
//				}
//			}
//			System.out.println("-------------");
//		}
//		MWArray.disposeArray(msa);
		return new ArrayList<Object>();
	}
	
	
}
