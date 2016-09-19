package predict.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import predict.fit.ExpFit;

/**
 * 指数函数
 * @author Administrator
 *
 */
public class ExpModel {
	
	
	public static List<Double> fit_result(double[] x_old, double[] y_old) {
		//定义计数器
		int count = 0;
		//记录最优值位置
		int flag = 0;
		//当前rmse最优值设置为极大值
		double rmse_best = Double.MAX_VALUE;
		//定义并初始化步长
		int step = 4;
		
		int n = x_old.length;
		
		//保存模型参数
		List<Double> args = null;
		
		//当连续7次rmse不在减小时结束
		while(count < 8)
		{
			if(step > n)
				break;
			//截取部分要拟合的数据
			double[] x = Arrays.copyOf(x_old, step);
			double[] y = Arrays.copyOf(y_old, step);
			
			//指数拟合
			double[] ratio = ExpFit.fit(y, x);
			//a b 分别为指数函数两个参数
			double a = ratio[0];
			double b = ratio[1];
			//求均方误差
			double rmse = ExpFit.rmse(y, x, a, b);
//			System.out.println("R²=" + FittingFunct.calRSquare_indexEST(x, y, a, b));
			//新求出的均方误差比最优的小
			if(rmse < rmse_best)
			{
				if(args != null)
					args.clear();
				args = new ArrayList<Double>();
				args.add(a);
				args.add(b);
				
				//替换最优值
				rmse_best = rmse;
				//重新开始计数
				count = 0;
				//保存最优值位置
				flag = x.length;
			}
			else
			{
				//计数器加1
				count++;
			}			

			step++;
		}
		if(args == null)
			return null;
		args.add(rmse_best);
		args.add((double)flag);
		return args;
	}
}
