package predict.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import predict.fit.ExpFit;
import predict.fit.PolynomialFit;

/**
 * 二次多项式函数
 * @author Administrator
 *
 */
public class PolynomialModel {
	
	
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
			double[] x_part = Arrays.copyOf(x_old, step);
			double[] y_part = Arrays.copyOf(y_old, step);
			
			//二次多项式拟合
			double[] ratio2 = PolynomialFit.fit(y_part, x_part);
			//a b c 分别为二次多项式函数三个参数
			double a2 = ratio2[0];
			double b2 = ratio2[1];
			double c2 = ratio2[2];
			//求均方误差
			double rmse = PolynomialFit.rmse(y_part, x_part, a2, b2,c2);		
			//新求出的均方误差比最优的小
			if(rmse < rmse_best)
			{
				if(args != null)
					args.clear();
				args = new ArrayList<Double>();
				args.add(a2);
				args.add(b2);
				args.add(c2);
				
				//替换最优值
				rmse_best = rmse;
				//重新开始计数
				count = 0;
				//保存最优值位置
				flag = x_part.length;
			}
			else
			{
				//计数器加1
				count++;
			}

			step++;
		}
		
		args.add(rmse_best);
		args.add((double)flag);
		
		return args;
	}
}
