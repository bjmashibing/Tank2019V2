import com.mashibing.tank.PropertyMgr;

import java.io.IOException;
import java.util.Properties;

public class TestConfig {
    public static void main(String[] args) {
        System.out.println(PropertyMgr.get("initTankCount"));

    }
}
