package com.jcwx.utils;
/**
 * @author ZhangLiMing
 * 2017年7月29日
 * 随机指定范围内N个不重复的数 
 */
public class RandomUtil {

	/**
	 * 随机指定范围内N个不重复的数 
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n 随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n) {
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}
}
