package thread;


/**
 * 題目: t1 t2線程輪流 數到100
 *
 * 1.內容是關於線程通信
 * 2.線程通信涉及到3個方法 (三個方法都是Object類的方法)
 *      1.wait()
 *      2.notify()
 *      3.notifyAll()
 *
 * 3.其中wait()有3個重載方法
 *      1.wait(): 調用此方法,線程進入 "等待狀態"
 *      2.wait(毫秒): 調用此方法,線程進入 "超時等待狀態"
 *      3.wait(毫秒,奈秒): 調用此方法,線程進入 "超時等待狀態"
 *
 * 4.調用 wait方法 和 notify相關方法, 不是透過線程對象去調用, 是透過共享對象去調用
 *
 * 5.例: 調用 obj.wait()
 *      obj是多線程共享的對象
 *      當調用 obj.wait()之後, 所有在 obj對象活躍的線程都進入無期限等待, 直到調用該共享對象的 obj.notify()方法進行喚醒
 *      而且喚醒後,會接著上一次調用 wait()方法的位置向下執行
 *
 * 6.obj.wait()方法調用之後, 會釋放之前占用的對象鎖
 *
 * 7.notify()和 notifyAll()方法:
 *      共享對象.notify(): 調用之後會喚醒 優先級最高的等待線程, 如果優先級一樣, 則隨機喚醒一個
 *      共享對象.notifyAll(): 調用之後會喚醒 所有 在該共享對象上等待的線程
 *
 * */
public class ThreadCrossCount17 {
    public static void main(String[] args) {
        MyRunnable2 runnable = new MyRunnable2();
        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        Thread t2 = new Thread(runnable);
        t2.setName("t2");

        t1.start();
        t2.start();

    }

}

class  MyRunnable2 implements Runnable{
    //實例變量 (多線程共享)
    private int ticketTotal = 0;

    @Override
    public void run() {
        while (true){
            synchronized (this){
                // t2線程執行過程中把t1喚醒了, 但是由於目前鎖在t2線程, 所以即使 t1被喚醒也不會往下執行
                // 喚醒t1線程
                this.notify();
                if (ticketTotal>=100){
                    break; // 停止循環
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"-->"+(++ticketTotal));

                try {
                    //讓其中一個線程等待, 可能是t1 也可能是t2
                    //這邊先假設是 t1
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
