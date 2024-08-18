package thread;
/**
 * 線程安全問題
 * 	1.甚麼情況下要考慮線程安全問題?
 * 		1.多現成的併發狀態下
 * 		2.有共享數據
 * 		3.共享數據涉及到修改的操作
 *
 *  2.一般情況下
 *  	局部變量不存在安全問題 (尤其是基本數據變量不存在【在棧中,棧不是共享的】, 如果是引用數據類型就不一定了)
 *  	實例變量可能存在線程安全問題, 實例變量在堆中, 堆是多線程共享的
 *      靜態變量可能存在線程安全問題, 靜態變量在堆中, 堆是多線程共享的
 *
 *
 *  3.實際例子
 *  	同時取同帳戶的錢
 *          解決辦法: 把線程排隊執行, 叫做同步機制
 *
 *                  *如果不排隊, 叫做異步機制
 *
 *                  *異步: 效率高, 不安全
 *                  *同步: 效率低, 安全
 *
 *   4.以下方法存在安全問題, t1 和 t2 同時對存款操作, 餘額是錯的
 *
 * */
public class ThreadSecurity12 {

    public static void main(String[] args) {
        //創建帳號對象: account-001
        Account account = new Account("account-001",1000);
        //創建線程對象: t1
        Thread t1 = new Thread(new Withdraw(account));
        //創建線程對象: t2
        Thread t2 = new Thread(new Withdraw(account));
        //啟動線程
        t1.start();
        t2.start();
    }

}

//取款的線程類
class Withdraw implements Runnable{

    // 實例變量
    private Account account;

    public Withdraw() {
    }

    public Withdraw(Account account) {
        this.account = account;
    }
    @Override
    public void run() {
        account.withdraw(100);
    }
}


//銀行帳號
class Account{
    private String account;
    private Integer balance;

    public Account() {
    }

    public Account(String account, Integer balance) {
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




