package thread;
/**
 *  寫一個死鎖
 *      互相拿對方的鎖, 都不釋放就會造成死鎖, 而且不會報錯誤訊息
 * */
public class ThreadDeadlock15 {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        Thread t1 = new Thread(new MyRunnable(o1,o2));
        Thread t2 = new Thread(new MyRunnable(o1,o2));
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}

class MyRunnable implements Runnable {
    private Object o1;

    private Object o2;

    public MyRunnable(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public void run() {
        if ("t1".equals(Thread.currentThread().getName())){
            synchronized (o1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (o2){

                }
            }
        }else if("t2".equals(Thread.currentThread().getName())){
            synchronized (o2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (o1){

                }
            }
        }

    }
}






