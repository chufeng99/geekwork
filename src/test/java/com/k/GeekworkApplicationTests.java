package com.k;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeekworkApplicationTests {

	@Test
	void contextLoads() {
	}

	public static void main(String[] args) {

	}

	public static void combineArrays(int[] a1, int[] a2) {
		a1 = reverseArray(a1);
		a2 = reverseArray(a2);

	}

	private static int[] reverseArray(int[] arr) {
		if (arr == null || arr.length == 0) return arr;
		int size = arr.length;
		int low = 0;
		int high = size -1;
		while (low < high) {
			if (arr[low] < arr[high]) {
				int temp = arr[high];
				arr[high] = arr[low];
				arr[low] = temp;
			}
			low++;
		}
		return arr;
	}
}
