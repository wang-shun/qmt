import com.lesports.qmt.transcode.client.TranscodeInternalApis;
import com.lesports.qmt.transcode.model.LiveToVideoSubTask;
import com.lesports.qmt.transcode.web.service.LiveToVideoTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2017/3/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext*.xml"})
public class ServiceTest {
    @Resource
    private LiveToVideoTaskService liveToVideoTaskService;
    @Test
    public void testA() {
        LiveToVideoSubTask subTask = TranscodeInternalApis.getLiveToVideoSubTask(10001301055L, 9);
        liveToVideoTaskService.createEncapsulationTask(subTask);
    }
}
