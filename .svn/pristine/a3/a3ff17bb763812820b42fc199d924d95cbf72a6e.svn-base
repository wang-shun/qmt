import com.lesports.qmt.config.api.dto.MenuResourceType;
import com.lesports.qmt.config.api.dto.TActivity;
import com.lesports.qmt.config.api.dto.TMenu;
import com.lesports.qmt.config.client.QmtConfigApis;
import org.junit.Test;

import java.util.List;

/**
 * Created by denghui on 2016/12/21.
 */
public class QmtConfigApisTest {

    @Test
    public void testActivity() {
        List<TActivity> tActivities = QmtConfigApis.getTActivitiesByEid(1034543005L);
        System.out.println(tActivities);
    }

    @Test
    public void testMenu() {
        TMenu tMenu = QmtConfigApis.getTMenuById(33501018L);
        System.out.println(tMenu);
    }
}
