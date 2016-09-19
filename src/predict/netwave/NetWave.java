package predict.netwave;

import static java.lang.Math.sin;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.cos;

import java.awt.print.Printable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.ReadExcel;

/**
 * 小波神经网络
 * @author Administrator
 *
 */
public class NetWave {

	public static void main(String[] args) throws Exception {
		NetWave nw = new NetWave();
		File file = new File("xls\\C4-A29-C-01.xls");
		List<Double> a = ReadExcel.readExcel2(file);
		
				
		double[] y = new double[a.size()];
		
		for (int i = 0; i < y.length; i++) {
			y[i] = a.get(i);
		}
for (int i = 0; i < 10; i++) {
	long st = System.currentTimeMillis();
	
	nw.wave(y);
	System.out.println(System.currentTimeMillis() - st);
}
	}
	
	public void wave(double[] y) {
		
		int step = 6; //选择不同的步长即输入层节点个数
		
		int maxgen = 1500; //选择不同的迭代次数，结果的精度不同
		int n = 8; //隐形节点个数
		// 网络参数配置
		int len0 = y.length;//统计应力变量的个数
		//测试数据的个数
		int test_num = 200;
		int len1 = len0 - test_num;//选取样本中最后的100组数据作为测试数据，前面的则作为训练数据
		double[][] input,input_test;
		double[][] output,output_test;
		//input output 初始化
		input = new double[len1-step][step];	
		output = new double[len1-step][1];
		for (int i = 0; i < len1-step; i++) {
			for (int j = 0; j < step; j++) {
				input[i][j] = y[i + j];
			}
			output[i][0] = y[i + step];
		}
		
		//input_test output_test 初始化
		input_test = new double[test_num-step][step];	
		output_test = new double[test_num-step][1];
		for (int i = 0; i < input_test.length; i++) {
			for (int j = 0; j < step; j++) {
				input_test[i][j] = y[len1 + i + j];
			}
			output_test[i][0] = y[len1 + i + step];
		}
	
		
		int M = step; //输入节点个数
		int N = 1; //输出节点个数
		
		double lr1 = 0.01; //学习概率
		double lr2 = 0.001; //学习概率
		
		//权值初始化  wjk,wjk_1,wjk_2
		double[][] wjk,wjk_1,wjk_2;
		wjk = new double[n][M];
		
		//初始化  wjk
		randn(wjk);
		while(Math.abs(checkRandn(wjk)) > 0.02) 
		{			
			randn(wjk);
		}
		System.out.println("wjk" + checkRandn(wjk));
		
		//把wjk复制到 wjk1
		wjk_1 = new double[wjk.length][];
		arraycopy(wjk, wjk_1);
		
		//把wjk复制到 wjk2
		wjk_2 = new double[wjk.length][];
		arraycopy(wjk, wjk_2);
	
		
//		print(wjk);
		
//		checkRandn(wjk_1);
//		checkRandn(wjk_2);
		
		//权值初始化  wij,wij_1,wij_2
		double[][] wij,wij_1,wij_2;
		wij = new double[N][n];
		
		//初始化  wij
		randn(wij);
		while(Math.abs(checkRandn(wij)) > 0.02)
			randn(wij);
		
		System.out.println("wij" + checkRandn(wij));
		
		//把wij复制到 wij1
		wij_1 = new double[wij.length][];
		arraycopy(wij, wij_1);
		
		//把wij复制到 wij2
		wij_2 = new double[wij.length][];
		arraycopy(wij, wij_2);
	
		double[][] a,a_1,a_2;
		a = new double[1][n];
		randn(a);
		while(Math.abs(checkRandn(a)) > 0.02)
			randn(a);
		System.out.println("a:" + checkRandn(a));
		
		//把a复制到a1
		a_1 = new double[a.length][];
		arraycopy(a, a_1);
		
		//把a复制到a2
		a_2 = new double[a.length][];
		arraycopy(a, a_2);
		
//		print(a);
//		checkRandn(a_2);
		
		double[][] b,b_1,b_2;
		b = new double[1][n];
		randn(b);
		while(Math.abs(checkRandn(b)) > 0.02)
			randn(b);
		System.out.println("b:" + checkRandn(b));
		
		//把b复制到b1
		b_1 = new double[b.length][];
		arraycopy(b, b_1);
		
		//把b复制到b2
		b_2 = new double[b.length][];
		arraycopy(b, b_2);
		
		System.out.println("--------");
		
		//节点初始化
		double p = 0;
		double[][] net = new double[1][n];
		double[][] net_ab = new double[1][n];
		
		//权值学习增量初始化
		double[][] d_Wjk = new double[n][M];
		double[][] d_Wij = new double[N][n];
		double[][] d_a = new double[1][n];
		double[][] d_b = new double[1][n];

		//输入输出数据归一化
		//先将input转置然后归一化保存到inputn_re最后再对inputn_re转置保存到inputn
		double[][] inputn_re = new double[step][]; 
		double[][] inputps = mapminmax(reverse(input), inputn_re);
		double[][] inputn = reverse(inputn_re);
//		System.out.println("inputn_re");
//		print(inputn_re);
//		System.out.println("inputps");
//		print(inputps);
		
		double[][] outputn_re = new double[step][output.length];
		double[][] outputps = mapminmax(reverse(output), outputn_re);
		double[][] outputn = reverse(outputn_re);
		
		/*System.out.println("outputps");
		print(outputps);*/
		//误差累计
		double[] error = new double[maxgen];

		//网络训练
		for (int i = 0; i < maxgen; i++) {
		    
		    //循环训练
		    for(int kk =0; kk < input.length; kk++) {
		    	double[] q = inputn[kk];
		    	double yqw = outputn[kk][0];
		    	
		    	for (int j = 0; j < n; j++) {
		    		
					for (int k = 0; k < M; k++) {
						net[0][j] = net[0][j] + wjk[j][k] * q[k];
		                net_ab[0][j] = (net[0][j] - b[0][j])/a[0][j];
					}

		            double temp = mymorlet(net_ab[0][j]);
		            
		            for (int k = 0; k < N; k++) {
		            	p = p + wij[k][j] * temp;   //小波函数
					}
				}

		        //计算误差和
		    	double sum = Math.abs(yqw - p);
				
		    	
		        error[i]=error[i] + sum;
		        
		        //权值调整
		        for (int j = 0; j < n; j++) {
		        	//计算d_Wij
		        	double temp = mymorlet(net_ab[0][j]);
		            for (int k = 0; k < N; k++) {
		            	d_Wij[k][j] = d_Wij[k][j] - (yqw - p)*temp;
		            }
		            //计算d_Wjk
		            temp = d_mymorlet(net_ab[0][j]);
		            for (int k = 0; k < M; k++) {
		            	for (int l = 0; l < N; l++) {
		            		d_Wjk[j][k] = d_Wjk[j][k] + (yqw - p)* wij[l][j] ;
						}
		            	d_Wjk[j][k] = -d_Wjk[j][k] * temp * q[k] / a[0][j];
		            }		            
		            
		            //计算d_b
		            for (int k = 0; k < N; k++) {
		            	d_b[0][j]=d_b[0][j] + (yqw - p) * wij[k][j];
		            }
		            d_b[0][j]=d_b[0][j] * temp / a[0][j];
		            
		            //计算d_a
		            for (int k = 0; k < N; k++) {
		                d_a[0][j] = d_a[0][j] + (yqw - p)* wij[k][j];
		            }
		            d_a[0][j] =d_a[0][j] * temp * ((net[0][j] - b[0][j])/b[0][j]) / a[0][j];
		        }
		        
		        //权值参数更新      
		        for (int k = 0; k < N; k++) {
		        	for (int j = 0; j < n; j++) {
		        		
			        	wij[k][j] = wij[k][j] - lr1 * d_Wij[k][j];						
			        	
			        	b[k][j] = b[k][j] - lr2 * d_b[k][j];
			        	a[k][j] = a[k][j] - lr2 * d_a[k][j];
					}
				}
		        for (int k = 0; k < n; k++) {
					for (int j = 0; j < M; j++) {
						wjk[k][j] = wjk[k][j] - lr1 * d_Wjk[k][j] ;
					}
				}
		    
		        d_Wjk = new double[n][M];
				d_Wij = new double[N][n];
				d_a = new double[N][n];
				d_b = new double[N][n];

		        p = 0;
				net = new double[1][n];
				net_ab = new double[1][n];
	       
		        
		        arraycopy(wjk, wjk_1);
		        arraycopy(wjk, wjk_2);

		        arraycopy(wij, wij_1);
		        arraycopy(wij, wij_2);
		        
		        
		        arraycopy(a, a_1);
		        arraycopy(a, a_2);
		        arraycopy(b, b_1);
		        arraycopy(b, b_2);
		    }
		
		}	
		
		//网络预测
		//预测输入归一化
		
		
		System.out.println("------");
//		print(input_test);
		double[][] input_test_re = reverse(input_test);
		double[][] q = reverse(mapminmax_apply(input_test_re, inputps));
		
		//网络预测
		double[][] yuce = new double[N][test_num-step] ;
		
		for (int ii = 0; ii < N; ii++) {
			for (int i = 0; i < yuce[ii].length; i++) {
				double[] x_test = q[i];
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < M; k++) {
						net[0][j] = net[0][j] + wjk[j][k] * x_test[k];
						net_ab[0][j] = (net[0][j] -b[0][j]) / a[0][j];
					}
					double temp = mymorlet(net_ab[0][j]);
					for (int k = 0; k < N; k++) {
						p = p + wij[k][j] * temp ;
					}
				}
				
				yuce[ii][i] = p;
				
				p = 0;
			    net = new double[1][n];
			    net_ab = new double[1][n];

			}
			
		}
		
		//预测输出反归一化
		double[][] ynn = reverse(mapminmax_reverse(yuce, outputps));
		List<Double> errorList = new ArrayList<Double>();
		for (int i = 0; i < ynn.length; i++) {
			for (int j = 0; j < ynn[0].length; j++) {
//				System.out.println(output_test[i][j] +"\t"+ ynn[i][j] + "\t" + (ynn[i][j]-output_test[i][j]));
				errorList.add(Math.abs(ynn[i][j]-output_test[i][j]));
			}
		}
		errorPer(errorList);
	}
	
	public void errorPer(List<Double> errorList ) {
		Map<Double, Integer> errorMap = new HashMap<Double, Integer>();
		for (Double error : errorList) {
			if(error <= 0.02) {
				errorMap.put(0.02, errorMap.get(0.02) == null? 1: errorMap.get(0.02)+1);				
			} 
			if(error <= 0.04) {				
				errorMap.put(0.04, errorMap.get(0.04) == null? 1: errorMap.get(0.04)+1);				
			} 
			if(error <= 0.06) {				
				errorMap.put(0.06, errorMap.get(0.06) == null? 1: errorMap.get(0.06)+1);				
			}
		}
		System.out.println("Total"+errorList.size());
		System.out.println("<0.02"+"\t"+errorMap.get(0.02)+"\t"+(errorMap.get(0.02)*1.0/errorList.size()));
		System.out.println("<0.04"+"\t"+errorMap.get(0.04)+"\t"+(errorMap.get(0.04)*1.0/errorList.size()));
		System.out.println("<0.06"+"\t"+errorMap.get(0.06)+"\t"+(errorMap.get(0.06)*1.0/errorList.size()));
	}
	
	
	public void print(double[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] +"\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * 产生标准正态分布伪随机数
	 * @param arr
	 */
	public void randn(double[][] arr) {
		Random random = new Random();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = random.nextGaussian();
			}
		}
	}
	
	public double checkRandn(double[][] wjk) {
		double sum = 0;
		for (int i = 0; i < wjk.length; i++) {
			for (int j = 0; j < wjk[i].length; j++) {
				sum += wjk[i][j];
			}
		}
		return sum/(wjk.length*wjk[0].length);
	}
	
	/**
	 * 实现二维数组复制
	 * @param src
	 * @param des
	 */
	public void arraycopy(double[][] src, double[][] des) {
//		des = new double[src.length][];
		for (int i = 0; i < src.length; i++) {
			des[i] = new double[src[i].length];
			System.arraycopy(src[i], 0, des[i], 0, src[i].length);
		}
	}
	
	/**
	 * 将数据归一化
	 * y = (ymax-ymin)*(x-xmin)/(xmax-xmin) + ymin
	 * ymax=1
	 * ymin=-1
	 * 如果有一行的元素都相同比如xt = [1 1 1],此时xmax = xmin = 1,把此时的变换变为y = ymin
	 * @param input
	 */
	private double[][] mapminmax(double[][] input, double[][] output) {
		double[][] ps = new double[input.length][2];
		for (int i = 0; i < input.length; i++) {
			double[] x = input[i];
			
			//找最大最小值
			double xmax = Double.MIN_VALUE;
			double xmin = Double.MAX_VALUE;
			
			for (int j = 0; j < x.length; j++) {
				if(x[j] > xmax) 
				{
					xmax = x[j];
				} 
				if(x[j] < xmin)
				{
					xmin = x[j];
				}
			}

			ps[i][0] = xmin;
			ps[i][1] = xmax;
			output[i] = new double[input[i].length];
			for (int j = 0; j < x.length; j++) {
				if(xmax == xmin)
					output[i][j] = -1;
				else
				{
					output[i][j] = 2 * (input[i][j]-xmin)/(xmax-xmin) - 1;					
				}
			}
			
		}
		return ps;
	}
	
	private double[][] mapminmax_apply(double[][] input, double[][] ps) {
		double[][] output = new double[input.length][];
		for (int i = 0; i < input.length; i++) {
			output[i] = new double[input[i].length];
			for (int j = 0; j < input[i].length; j++) {
				output[i][j] = 2 * (input[i][j] - ps[i][0])/(ps[i][1] - ps[i][0]) - 1;	
			}
		}
		
		return output;
	} 
	
	/**
	 * 将数据反归一化
	 * @param input
	 * @param ps [][] [i][0]min [i][1]max
	 * @return
	 */
	private double[][] mapminmax_reverse(double[][] input, double[][] ps) {
		double[][] output = new double[input.length][];
		for (int i = 0; i < input.length; i++) {
			output[i] = new double[input[i].length];
			for (int j = 0; j < input[i].length; j++) {
				output[i][j] = (input[i][j]  + 1) * (ps[i][1] - ps[i][0]) / 2 + ps[i][0];
			}
		}
		
		return output;
	}
	
	public double[][] reverse(double[][] arr) {
		double[][] output = new double[arr[0].length][arr.length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				output[j][i] = arr[i][j];
			}
		}
		return output;
	}
//	y = -1.75*sin(1.75*t).*exp(-(t.^2)/2)-t* cos(1.75*t).*exp(-(t.^2)/2);
	private double d_mymorlet(double t) {
		double y = -1.75 * sin(1.75*t) * exp(-(pow(t, 2))/2)
			- cos(1.75*t)*exp(-(pow(t, 2))/2);
		return y;
	}
	
	private double mymorlet(double t) {
		return exp(-pow(t, 2)/2) * cos(1.75*t);
	}
	
	private double[] mymorlet(double[] t) {
		double[] a = new double[t.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = exp(-pow(t[i], 2)/2) * cos(1.75*t[i]);
		}
		return a;
	}
}
