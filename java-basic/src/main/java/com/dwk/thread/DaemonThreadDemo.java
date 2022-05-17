package com.dwk.thread;

/**
 * @describe 守护线程
 * @date 2021/10/15
 */
public class DaemonThreadDemo {

    /**
     * 创建一个守护线程
     * @return
     */
    public Thread creatOneDaemonThread(){
        Thread daemonThread = new Thread(()->{
            System.out.println("守护线程");
            //让守护线程死循环
            while (true){}
        });
        //设置线程为守护线程
        daemonThread.setDaemon(true);
        return daemonThread;
    }

    /**
     * 测试守护线程和用户线程
     */
    public void test() throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("用户线程");
        });

        Thread daemonThread = this.creatOneDaemonThread();

        daemonThread.start();
        thread.start();
    }

    public static void main(String[] args) throws InterruptedException {
        DaemonThreadDemo daemonThreadTest = new DaemonThreadDemo();
        daemonThreadTest.creatOneDaemonThread().start();
        System.out.println("程序结束");
    }

}
