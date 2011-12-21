package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: zhangh
 * Date: 11-10-13
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */

/**
 * Key:Day - Guy: -  Account
 */
@Entity
public class Guy extends Model{

    public String name;

    public String password;

    public String showname;

    public double account;

    public Guy(String name, String password, String showname, double account) {
        this.name = name;
        this.password = password;
        this.showname = showname;
        this.account = account;
    }

    /**
     * return null，为没有连接上，否则连接上
     * @param name
     * @param password
     * @return
     */
    public static Guy connect(String name,String password) {
        return find("byNameAndPassword",name,password).first();
    }

}
