package demo;

/**
 * @author myx
 * @date 2021年04月29日 10:15
 */
public class ThreadDemo_1_1_1 extends Thread {
    private boolean stopped = false;
    public void run(){
        while(!stopped){
            System.out.println("thread is execute");
        }
    }
//    public void stop(){
//        this.stopped = true;
//    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo_1_1_1 threadDemo_1_1_1 = new ThreadDemo_1_1_1();
        threadDemo_1_1_1.start();
        threadDemo_1_1_1.start(); // 通知线程threadDemo_1_1_1关闭
        threadDemo_1_1_1.join();  // 等待线程threadDemo_1_1_1退出while循环, 自行退出
    }
}
