package thread;
/**
 *  使用線程同步機制, 保證多線程併發的數據安全
 *      1.線程同步就是: 線程排隊執行
 *      2.語法格式:
 *          synchronized(必須是 需要排隊的這幾個線程 的 共享的對象){
 *              // 同步程式碼
 *          }
 *      3.原理
 *          synchronized(obj){
 *             // 同步程式碼
 *          }
 *          假設 obj是 t1 t2這兩個線程共享
 *          t1 t2執行這裡的程式碼的時候, 一定是有一個先搶到cpu的時間片段, 一定會有先後順序
 *          假設 t1先搶到 CPU時間片段, t1線程找共享 obj的對象鎖, 找到之 後則占有, 只要佔有那把鎖, 就有權力進入同步程式碼區塊那邊執行
 *          當 t1線程執行完同步程式碼區塊, 會釋放對象鎖 (歸還鎖), t2會在同步程式碼區塊外面等, 等到有鎖可以用才能執行
 *
 *          *同步程式碼區塊越小效率越高, 不要無故擴大
 * */
public class ThreadSecurity13 {

    public static void main(String[] args) {
        //創建帳號對象: account-001
        Account1 account = new Account1("account-001",1000);
        //創建線程對象: t1
        Thread t1 = new Thread(new Withdraw1(account));
        //創建線程對象: t2
        Thread t2 = new Thread(new Withdraw1(account));
        //啟動線程
        t1.start();
        t2.start();
    }

}

//取款的線程類
class Withdraw1 implements Runnable{

    // 實例變量
    private Account1 account;

    public Withdraw1() {
    }

    public Withdraw1(Account1 account) {
        this.account = account;
    }
    @Override
    public void run() {
        account.withdraw(100);
    }
}


//銀行帳號
class Account1 {
    private String account;
    private Integer balance;

    public Account1() {
    }

    public Account1(String account, Integer balance) {
        this.account = account;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    /**
     * 取款方法
     * */
    public void withdraw(int money){
        //this 是當前帳號的對象
        synchronized (this){
            //第一步: 拿到取款前的餘額
            Integer before = this.getBalance();
            System.out.println(Thread.currentThread().getName()+"線程正在取款"+money+",當前帳號"+this.getAccount()+"當前餘額為"+before);

            //保證範例一定出問題
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //第二步: 修改取款後的餘額
            this.setBalance(before-money);
            System.out.println(Thread.currentThread().getName()+"線程取款成功,當前帳號"+this.getAccount()+"取款後餘額為"+this.getBalance());

        }
        }
}




