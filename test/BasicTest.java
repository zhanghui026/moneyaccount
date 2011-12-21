import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

 @Before
    public void setup(){
        Fixtures.deleteDatabase();
    }
    @Test
    public void createAndRetrieveGuy() {
        Guy guy1 = new Guy("yanr", "yanr", "闫然", 17.9);
        Guy guy2 = new Guy("fangj", "fangj", "方晶", 59.66);
        guy1.save();
        guy2.save();
        assertEquals(2,Guy.count());
        assertNotNull(Guy.all().first());
    }
    @Test
    public void guysAndMoneyAction(){
        Guy guy1 = new Guy("yanr", "yanr", "闫然", 17.9);
        Guy guy2 = new Guy("fangj", "fangj", "方晶", 59.66);
        Guy guy3 = new Guy("wengxl","wengxl","翁晓磊",125.24);
        Guy guy4 = new Guy("yangq","yangq","杨茜",157.75);
        guy1.save();
        guy2.save();
        guy3.save();
        guy4.save();

        SpendAction.addMoney(guy1.name,100);
        SpendAction.spendMoney(guy2.name,10);
        SpendAction.guy2guy(guy3.name,guy4.name,10);

        assertNotNull(Guy.connect("yanr","yanr"));
        assertNull(Guy.connect("yanr","yan"));
        Guy theGuy1 = Guy.connect("yanr","yanr");
        Guy theGuy2 = Guy.connect("fangj","fangj");
        Guy theGuy3 = Guy.connect("wengxl","wengxl");
        Guy theGuy4 = Guy.connect("yangq","yangq");

        assertEquals(117.9,theGuy1.account,0.01);
        assertEquals(49.66,theGuy2.account,0.01);
        assertEquals(115.24,theGuy3.account,0.01);
        assertEquals(167.75,theGuy4.account,0.01);

        SpendAction.addMoney(guy1.name,100);
        assertEquals(217.9,theGuy1.account,0.01);


    }

}
