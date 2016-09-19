package Action;

import java.util.Arrays;

import predict.fit.ExpFit;
import predict.fit.PolynomialFit;




public class Fit {
	
	public static void main(String[] args) {
		
	}
	
	
	public String[] fit_result(double[] x_old, double[] y_old) {
		//定义两个计数器
		int count1 = 0, count2 = 0;
		//记录最优值位置
		int flag1 = 0, flag2 = 0;
		//当前rmse最优值设置为极大值
		double rmse1_best = Double.MAX_VALUE, rmse2_best = Double.MAX_VALUE;
		//定义并初始化步长
		int step = 4;
		
		//当连续7次rmse不在减小时结束
		while(count1 < 8 || count2 < 8)
		{
			//截取部分要拟合的数据
			double[] x = Arrays.copyOf(x_old, step);
			double[] y = Arrays.copyOf(y_old, step);
			
			//指数拟合
			double[] ratio = ExpFit.fit(y, x);
			//a b 分别为指数函数两个参数
			double a = ratio[0];
			double b = ratio[1];
			//求均方误差
			double rmse1 = ExpFit.rmse(y, x, a, b);
//			System.out.println("R²=" + FittingFunct.calRSquare_indexEST(x, y, a, b));
			//新求出的均方误差比最优的小
			if(rmse1 < rmse1_best)
			{
				//替换最优值
				rmse1_best = rmse1;
				//重新开始计数
				count1 = 0;
				//保存最优值位置
				flag1 = x.length;
			}
			else
			{
				//计数器加1
				count1++;
			}
			
			//二次多项式拟合
			double[] ratio2 = PolynomialFit.fit(y, x);
			//a b c 分别为二次多项式函数三个参数
			double a2 = ratio2[0];
			double b2 = ratio2[1];
			double c2 = ratio2[2];
			//求均方误差
			double rmse2 = PolynomialFit.rmse(y, x, a2, b2,c2);		
			//新求出的均方误差比最优的小
			if(rmse2 < rmse2_best)
			{
				//替换最优值
				rmse2_best = rmse2;
				//重新开始计数
				count2 = 0;
				//保存最优值位置
				flag2 = x.length;
			}
			else
			{
				//计数器加1
				count2++;
			}

			step++;
		}
		
		String fitname;
		double best_rmse;
		double best_step;
System.out.println("指数拟合："+rmse1_best + "线性拟合：" + rmse2_best);		
		//比较
		//指数拟合 比较优
		if(rmse1_best < rmse2_best)
		{
			fitname = "指数拟合";
			best_rmse = rmse1_best;
			best_step = flag1;
		}
		else
		{
			fitname = "二次多项式";
			best_rmse = rmse2_best;
			best_step = flag2;
		}
		String[] re = {fitname,best_rmse+"",best_step+""}; 
		return re;
	}
}
