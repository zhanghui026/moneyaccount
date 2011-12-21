package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangh
 * Date: 11-10-13
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class SpendAction extends Model {
    public static final short SPEND = 0;
    public static final short CHONGZHI = 1;
    public static final short ZHUANZHANG = 2;
    /**
     * type
     * 0, 花费
     * 1，充值
     * 2，转账  转账 记录2次 1次为谁钱多了 2次为谁钱少了
     */
    public int type;

    @ManyToOne
    public Guy fromGuy; //花钱 fromGuy -

    @ManyToOne
    public Guy toGuy;  //存钱 +

    public double money;

    public String day;



    public String description;

    private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SpendAction(int type, Guy fromGuy, Guy toGuy, double money,String day) {
        this.type = type;
        this.fromGuy = fromGuy;
        this.toGuy = toGuy;
        this.money = money;
        this.day = day;

        if(type==SPEND){
            description ="消费:"+ fromGuy.showname +"-"+ Math.abs(money);
        }else if(type==CHONGZHI){
            description ="充值:"+ toGuy.showname +"+"+ Math.abs(money);
        }else  if(type == ZHUANZHANG){
            description = "转账:" + fromGuy.showname+"-"+Math.abs(money)+","+toGuy.showname+"+"+Math.abs(money);
        }
    }

    public SpendAction(int type, Guy fromGuy, Guy toGuy, double money){
        this(type,fromGuy,toGuy,money,sdf.format(new Date()));
    }


    public static void addMoney(String guyname, double money) {
        Guy guy = Guy.find("byName", guyname).first();
        SpendAction sa=new SpendAction(CHONGZHI, null,guy, Math.abs(money)).save();
        guy.account = guy.account + Math.abs(money);
        guy.save();
        DayGuyAccount.saveDayAccount(sa.day,guy,guy.account);
    }

    public static void spendMoney(String guyname, double money) {
        Guy guy = Guy.find("byName", guyname).first();
         SpendAction sa=new SpendAction(SPEND, guy,null, -Math.abs(money)).save();
        guy.account = guy.account - Math.abs(money);
        guy.save();
        DayGuyAccount.saveDayAccount(sa.day,guy,guy.account);
    }

    public static void guy2guy(String fromGuy, String toGuy, double money) {
        Guy guy1 = Guy.find("byName", fromGuy).first();
        Guy guy2 = Guy.find("byName", toGuy).first();
        SpendAction sa = new SpendAction(ZHUANZHANG, guy1,guy2,Math.abs(money));
        sa.save();
        guy1.account-=Math.abs(money);
        guy2.account+=Math.abs(money);
        guy1.save();
        guy2.save();
        DayGuyAccount.saveDayAccount(sa.day,guy1,guy1.account);
        DayGuyAccount.saveDayAccount(sa.day,guy2,guy2.account);
    }

    //TO-DO 查询当天的钱数和当天的消费
    //TO-DO 查询某人的交易
    //TO-DO 查询所有人一周的交易


}
