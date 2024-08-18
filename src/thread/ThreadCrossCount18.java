package thread;


/**
 * 題目: t1 t2 t3線程輪流 輸出 A B C
 *
 * t1 --> A
 * t2 --> B
 * t3 --> C
 * ...
 * t1 --> A
 * t2 --> B
 * t3 --> C
 * */
public class ThreadCrossCount18 {
    //共享對象 (t1 t2 t3線程共享的對象, 都去搶這把鎖
    private static final Object lock = new Object();

    //給一個初始值, 第一次t1先輸出
    private static boolean t1Output=true;

    private static boolean t2Output=false;

    private static boolean t3Output=false;
    public static void main(String[] args) {
        // 建3個線程

        // t1線程: 負責輸出A
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    for (int i = 0; i < 10; i++) {
                        while (!t1Output){// 只要不是 t1 輸出
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // 跑到這邊代表 t1輸出了, 且 t1線程被喚醒了
                        System.out.println(Thread.currentThread().getName()+"-->A");
                        //改 boolean 的值
                        t1Output = false;
                        t2Output = true;
                        t3Output = false;
                        //喚醒所有線程
                        lock.notifyAll();
                    }
                }
            }
        }).start();

        // t2線程: 負責輸出B
         new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (lock){
                            for (int i = 0; i < 10; i++) {
                                while (!t2Output){// 只要不是 t2 輸出
                                    try {
                                        lock.wait();
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                // 跑到這邊代表 t2輸出了, 且 t2線程被喚醒了
                                System.out.println(Thread.currentThread().getName()+"-->B");
                                //改 boolean 的值
                                t1Output = false;
                                t2Output = false;
                                t3Output = true;
                                //喚醒所有線程
                                lock.notifyAll();
                            }
                        }
                    }
                }).start();
        // t3線程: 負責輸出C
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    for (int i = 0; i < 10; i++) {
                        while (!t3Output){// 只要不是 t3 輸出
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // 跑到這邊代表 t3輸出了, 且 t3線程被喚醒了
                        System.out.println(Thread.currentThread().getName()+"-->C");
                        //改 boolean 的值
                        t1Output = true;
                        t2Output = false;
                        t3Output = false;
                        //喚醒所有線程
                        lock.notifyAll();
                    }
                }
            }
        }).start();

    }

}
