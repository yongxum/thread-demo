package demo;


/**
 *
 * 为什么wait()和notify()必须和synchronized一起使用?
 * 两个线程之间要通讯, 对于一个对象来说, 一个线程调用wait(), 另一个调用notify(), 该对象本身就需要同步
 * 所以, 在调用wait()和notify()之前, 要先通过synchronized关键字同步给对象, 也就是给该对象加锁.
 *
 * @author myx
 * @date 2021年04月29日 10:09
 */

public class ThreadDemo_1_1 {
    private Object object = new Object();
    public void f1() throws InterruptedException{
        synchronized (object){
            System.out.println("进入阻塞");
            object.wait();
            /**
             * 这里是wait()函数的伪代码
             * wait(){
             *     释放锁
             *     阻塞, 等待被其他线程notify
             *     重新拿锁
             * }
             * 如果不这样设计, 锁永远不会被释放, 就会形成死锁
             */
            System.out.println("被f2唤醒");
        }
    }
    public void f2(){
        synchronized (object){
            System.out.println("唤醒程序");
            object.notify();
        }
    }
    public static void main(String[] args) {
        final ThreadDemo_1_1 threadDemo_1_1 = new ThreadDemo_1_1();
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    threadDemo_1_1.f1();
                }catch (Exception ex){
                    ex.getMessage();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                threadDemo_1_1.f2();
            }
        });
        thread.start();
        thread2.start();
    }

}
