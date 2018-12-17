import com.lesports.qmt.config.client.QmtConfigMenuInternalApis;
import com.lesports.qmt.config.model.Menu;
import org.junit.Test;

/**
 * Created by denghui on 2017/1/11.
 */
public class QmtConfigInternalApisTest {

    @Test
    public void menu(){
        Menu menu = QmtConfigMenuInternalApis.getMenuById(33501018L);
        QmtConfigMenuInternalApis.saveMenu(menu);
    }
}
