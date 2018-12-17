package com.lesports.qmt.image.web.api.job;

import com.lesports.qmt.image.web.api.core.Constants;
import com.lesports.utils.LeDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 * Created by denghui on 2017/2/28.
 */
@Component
public class DestFileCleanJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(DestFileCleanJob.class);

    /**
     * 删除前一天的dest文件
     */
    @Scheduled(cron="0 0 5 * * ?")
    public void exec() {
        try {
            String yesterday = LeDateUtils.formatYYYYMMDDHHMMSS(LeDateUtils.addDay(new Date(), -1)).substring(0,8);
            String[] arr = new String[]{Constants.IMAGE_WEB_2, Constants.IMAGE_WEB_3};
            for (String p : arr) {
                Path path = Paths.get(Constants.IMAGE_DEST_PATH + p + "/" + yesterday);
                if (Files.notExists(path)) {
                    continue;
                }
                Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        if (file.getFileName().toString().endsWith(".tar.gz")) {
                            Files.delete(file);
                            LOGGER.info("delete dest file:{}", file);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("fail to delete dest file", e);
        }
    }
}
