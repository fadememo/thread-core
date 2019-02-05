package com.bfxy.thread.core.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

public class UseUnsafe {

	private static int byteArrayBaseOffset;
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafe.setAccessible(true);
		Unsafe UNSAFE = (Unsafe) theUnsafe.get(null);
		System.err.println(UNSAFE);
		
		byte[] data = new byte[10];
		System.err.println(Arrays.toString(data));
		
		byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);
		
		System.err.println("byte[]数组的第一个元素的偏移地址: " + byteArrayBaseOffset);
		
		//设置指定的字节数组, 修改其内存字段的位置:  
		// 1. 在 data 这个数组对象设置 第一个位置 为 1
		UNSAFE.putByte(data, byteArrayBaseOffset, (byte)1);
		// 2. 在 data 这个数组对象设置 第六个位置 为8
		UNSAFE.putByte(data, byteArrayBaseOffset + 6, (byte)8);
		
		System.err.println(Arrays.toString(data));
		
	}
	
}
