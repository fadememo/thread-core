package com.majoy.multi.thread.cas_3;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * unsafe类,主要功能能有内存操作,字段的定位和修改,挂起与恢复,CAS操作
 * 内存方法 :allocateMemory();reallocateMemory();freeMemory()
 * 字段的定位和修改 arrayBaseOffset(),objectFieldOffset()
 * 挂起与恢复:park() unPark()
 *
 */
class Vo{
    public int a = 10;
    public long b = 20;
    public static String c = "123";
    public static  Object d = null;
    public static  int e = 300;

}
public class UseUnsafe_2 {
    private static int byteArrayBaseOffset;
    private static int byteArrayBaseOffset2;
    private static int byteArrayBaseOffset3;

    private static int byteArrayBaseOffset4;
    private static int byteArrayBaseOffset5;
    private static int byteArrayBaseOffset6;
    private static int byteArrayBaseOffset7;
    private static int byteArrayBaseOffset8;
    private static int byteArrayBaseOffset9;
    private static int byteArrayBaseOffset10;

    public static void main(String[] args) {
        //Unsafe类的使用较为套路,分三步
        Unsafe UNSAFE = null;
        Field f = null;
        Vo vo = new Vo();
        try {
            //1,获取域
            f = Unsafe.class.getDeclaredField("theUnsafe");
            //2,获取可读
            f.setAccessible(true);
            //3,获取对象
            UNSAFE = (Unsafe) f.get(null);
            System.out.println(UNSAFE);

            byte[] data = new byte[10];
            int[] data2 = new int[20];
            double[] data3 = new double[]{1,2,3,4,5,6};
            String s = " i say i will ,can i ";
            System.out.println(Arrays.toString(data));
            System.out.println(Arrays.toString(data3));
            //获取数组的头部大小和元素大小
            byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);
            byteArrayBaseOffset2 = UNSAFE.arrayBaseOffset(int[].class);
            byteArrayBaseOffset3 = UNSAFE.arrayBaseOffset(double[].class);
            byteArrayBaseOffset10 = UNSAFE.arrayIndexScale(data3.getClass());
            //获取实例字段的偏移地址,获取实例字段的偏移地址,偏移最小的那个字段(仅挨着头部)就是对象头的大小
            byteArrayBaseOffset4 = (int) UNSAFE.objectFieldOffset(Vo.class.getDeclaredField("a") );
            byteArrayBaseOffset5 = (int) UNSAFE.objectFieldOffset(Vo.class.getDeclaredField("b"));
            //获取类的静态字段偏移
            byteArrayBaseOffset6 = (int) UNSAFE.staticFieldOffset(Vo.class.getDeclaredField("c"));
            byteArrayBaseOffset7 = (int) UNSAFE.staticFieldOffset(Vo.class.getDeclaredField("d"));
            byteArrayBaseOffset8 = (int) UNSAFE.staticFieldOffset(Vo.class.getDeclaredField("e"));
            byteArrayBaseOffset9 = (int) UNSAFE.staticFieldOffset(f);
            //获取操作系统位数
            System.out.println(UNSAFE.addressSize());//返回4意味着32位,8意味着64位
            System.out.println(System.getProperty("sun.arch.data.model"));

            System.out.println("byte[]数组的第一个元素的偏移地址: " + byteArrayBaseOffset);
            System.out.println("int[]数组的第一个元素的偏移地址: " + byteArrayBaseOffset2);
            System.out.println("double[]的第一个元素的偏移地址: " + byteArrayBaseOffset3);
            System.out.println("double[]的每个元素的大小: " + byteArrayBaseOffset10);

            System.out.println("Unsafe类的 theUnsafe 元素 的偏移地址: " + byteArrayBaseOffset9);
            System.out.println("Vo的数组头元素 的偏移地址: " + byteArrayBaseOffset4);
            System.out.println("Vo的第二个元素 的偏移地址: " + byteArrayBaseOffset5);//那么
            System.out.println("Vo的第三个元素 的偏移地址: " + byteArrayBaseOffset6);
            System.out.println("Vo的第四个元素 的偏移地址: " + byteArrayBaseOffset7);
            System.out.println("Vo的第五个元素 的偏移地址: " + byteArrayBaseOffset8);


            int offset = (int) UNSAFE.objectFieldOffset(Vo.class.getDeclaredField("a"));
            //输出对象的成员变量值
            int val = UNSAFE.getInt(vo,offset);
            int value = Vo.class.getDeclaredField("a").getInt(vo);
            //输出静态成员变量的值
            Object base = UNSAFE.staticFieldBase(Vo.class.getDeclaredField("e"));
            long offset2 = (int) UNSAFE.staticFieldOffset(Vo.class.getDeclaredField("e"));
            int val2 = UNSAFE.getInt(base,offset2);
            int val3 = UNSAFE.getIntVolatile(base,offset2);
            //输出数组的指定位置
            //double val6 = UNSAFE.getDouble(data3,8);
            double val13 = UNSAFE.getDouble(data3,0);//因为偏移位置是16,所以0开头的是数组头

            double val7 = UNSAFE.getDouble(data3,16);
            double val8 = UNSAFE.getDouble(data3,24);
            double val9 = UNSAFE.getDouble(data3,32);
            double val10 = UNSAFE.getDouble(data3,40);
            double val11 = UNSAFE.getDouble(data3,48);
            double val12 = UNSAFE.getDouble(data3,56);

            System.out.println("vo成员变量 a 值为 "+val);
            System.out.println("vo成员变量 a 值为 "+value);
            System.out.println("vo的静态变量e 偏移位移为" +offset2);
            System.out.println("vo的静态变量e 值为" +val2);
            System.out.println("vo的静态变量e 值为" +val3);

            System.out.println("数组 值为" +val13);

            System.out.println("数组索引0 值为" +val7);
            System.out.println("数组索引1 值为" +val8);
            System.out.println("数组索引2 值为" +val9);
            System.out.println("数组索引3 值为" +val10);
            System.out.println("数组索引4 值为" +val11);
            System.out.println("数组索引5 值为" +val12);
            ///使用获取的内存偏移地址修改值
            UNSAFE.putDouble(data3,16,100);
            UNSAFE.putDouble(data3,24,200);
            UNSAFE.putDouble(data3,32,300);
            //输出修改后的值
            System.out.println(UNSAFE.getDouble(data3,16));
            System.out.println(UNSAFE.getDouble(data3,24));
            System.out.println(UNSAFE.getDouble(data3,32));
            System.out.println(Arrays.toString(data3));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }




    }






}
