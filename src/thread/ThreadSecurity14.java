package thread;
/**
 * 	在實例方法上添加 synchronized 關鍵字
 * 	    1.整個方法是一個同步程式碼區塊
 * 	    2.共享方法的對象鎖一定是 this的
 * 	    3.如果方法上有 static 這個時候找的是 類鎖 不是對象鎖
 * 	        類鎖只有一把, 不管你創建多少對象
 *
 *
 * 	這種方式相對於 ThreadSecurity13所寫的 局部同步程式碼區塊 的方式差一些
 * 	    缺點原因:
 * 	        1.局部程式碼區塊的共享對象可以隨便調整
 * 	        2.局部程式碼區塊範圍可以隨便調整
 * 	    優點原因:
 * 	        1.寫法簡單
 *
 * */
public class ThreadSecurity14 {

    public static void main(String[] args) {
        //創建帳號對象: account-001
        Account2 account = new Account2("account-001",1000);
        //創建線程對象: t1
        Thread t1 = new Thread(new Withdraw2(account));
        //創建線程對象: t2
        Thread t2 = new Thread(new Withdraw2(account));
        //啟動線程
        t1.start();
        t2.start();
    }

}

//取款的線程類
class Withdraw2 implements Runnable{

    // 實例變量
    private Account2 account;

    public Withdraw2() {
    }

    public Withdraw2(Account2 account) {
        this.account = account;
    }
    @Override
    public void run() {
        account.withdraw(100);
    }
}


//銀行帳號
class Account2{
    private String account;
    private Integer balance;

    public Account2() {
    }

    public Account2(String account, Integer balance) {
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
    public synchronized void withdraw(int money){
        //演示出多線程併發帶來的安全問題, 建議分成兩步去完成提款操作

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




