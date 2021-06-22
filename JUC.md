##  JUC

##### 1.JMM

1. 说明：

   Java Memory Model，Java内存模型

2. 需要满足：

   - 可见性：
   - 原子性：
   - 有序性：

3. 分

##### 2.volatile关键字

1. 说明：

   修饰在变量上，能够保证多个线程对此变量的操作是立即可见的。例如主内存中变量volatile a = 25，线程1和线程2都从主内存中读取了变量a到各自的工作内存，此时如果线程1修改了变量a，并把值重新写回主内存，线程2时能立刻知道值被修改了

2. volatile特性：

   - 保证可见性：主内存的值一旦被修改，其他各线程都会被通知到
   - 保证有序性：禁止指令重排
   - 不保证原子性：例如i++其实不是一个原子操作，多个线程对volatile修饰的i进行i++操作，最终得到的结果不是想要的正确结果

3. 如何保证原子性？

   方法一：加锁，保证线程安全

   方法二：使用JUC下的原子类，例如AtomicInteger，他的getAndIncrement方法等同于i++操作，但是保证了原子性

4. 为何AtomicInteger的getAndIncrement方法保证了原子性？

   getAndIncrement方法使用了CAS算法

5. 

##### 3.CAS

1. 说明：

   CAS，Compare And Swap，即比较并交换

2. AtomicInteger的getAndIncrement源码：

   ```java
   public final int getAndIncrement() {
           return unsafe.getAndAddInt(this, valueOffset, 1);
       }
   ```

   - this：当前对象
   - valueOffset：当前对象内存偏移量，也就是内存地址
   - 1：固定+1

3. UnSafe类

   ```java
   public final int getAndAddInt(Object var1, long var2, int var4) {
           int var5;
           do {
               var5 = this.getIntVolatile(var1, var2);
           } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
   
           return var5;
       }
   ```

   getAndIncrement其实是调用UnSafe类的getAndAddInt，UnSafe类各个方法都是native方法，直接操作内存。Unsafe类的CAS方法，它是一条CPU并发原语，完全依赖于硬件，属于操作系统用语范畴，由若干条指令组成，用于完成某个功能，原语的执行必须是连续的，在执行过程中不允许被中断，所以一定是原子操作

4. 