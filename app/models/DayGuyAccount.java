package models;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangh
 * Date: 11-11-17
 * Time: 下午1:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DayGuyAccount extends Model{
    public String day;  //YYYYMMDD
    @ManyToOne
    public Guy guy;
    public double account;
    private DayGuyAccount(String day,Guy guy,double account){
        this.day = day;
        this.guy= guy;
        this.account=account;
    }

    public static void saveDayAccount(String day,Guy guy,double account){
        DayGuyAccount dayGuyAccount= DayGuyAccount.find("byGuyAndDay",guy,day).first();
        if(dayGuyAccount==null){
        new DayGuyAccount(day,guy,account).save();
        }else{
            dayGuyAccount.account=account;
            dayGuyAccount.save();
        }
    }


}
