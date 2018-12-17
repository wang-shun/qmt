package com.lesports.qmt.image.web.api.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.image.web.api.controller.support.AbstractUploadController;
import com.lesports.qmt.image.web.api.core.Constants;
import com.lesports.qmt.image.web.api.utils.ImageCutUtils;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.ImageCutApis;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by denghui on 2017/3/10.
 */
@RestController
@RequestMapping(value="/upload")
public class TempUploadController extends AbstractUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(TempUploadController.class);
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    @RequestMapping(value = "/gz2", method= RequestMethod.POST, consumes = {"multipart/form-data","application/x-www-form-urlencoded"},
            produces="application/json;charset=utf-8")
    public ImageUrlExt gzUpload2(@RequestParam(value = "originalPath", required = false) String originalPath,
                                 @RequestParam(value = "params", required = true) String params,
                                 @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                 @RequestParam(value = "url", required = false) String url,
                                 @RequestParam(value = "type", required = false, defaultValue = "def") String type,
                                 @RequestHeader(value = "Image-Prefix", required = false) String imagePrefixHeader) {
        try {
            LOG.info("[gz2 upload start]");
            Stopwatch stopwatch = Stopwatch.createStarted();
            String imagePath = null;

            String prefix = parseImagePrefixHeader(imagePrefixHeader);
            if (StringUtils.isNotEmpty(originalPath)) {
                Preconditions.checkArgument(originalPath.startsWith("/image"), "invalid originalPath");
                String[] result = deleteRatioIfNecessary(params, originalPath, prefix, type);
                params = result[0];
                imagePath = result[1];
            } else {
                if (multipartFile != null) {
                    //生成源文件路径
                    imagePath = genImagePath(prefix, type, multipartFile);
                    //将上传的文件复制到本地
                    copyFileToLocal(multipartFile, imagePath);
                } else if (StringUtils.isNotEmpty(url)) {
                    //生成源文件路径
                    imagePath = genImagePath(prefix, type, url);
                    //将上传的文件复制到本地
                    copyFileToLocal(url, imagePath);
                } else {
                    throw new IllegalArgumentException("file part and url are null");
                }
            }

            String imageGzPath = ImageCutUtils.createTarGzWithCut(imagePath, params);
            String imageUrl = null;
            if(StringUtils.isNotEmpty(imageGzPath)) {
                imageUrl = ImageCutApis.uploadGzFile(imageGzPath, Constants.UPLOAD_CHANNEL);
                if (StringUtils.isEmpty(imageUrl)) {
                    throw new LeWebApplicationException("fail to gz upload", LeStatus.SERVER_EXCEPTION);
                }
                imageUrl += imagePath.substring(imagePath.lastIndexOf(Constants.FILE_DOT));
            } else {
                throw new LeWebApplicationException("fail to create tar", LeStatus.SERVER_EXCEPTION);
            }

            ImageUrlExt imageUrlExt = buildResponse(imagePath, imageUrl);
            stopwatch.stop();
            LOG.info("[gz2 upload success][response:{}][elapsed:{}]", imageUrlExt, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            deleteSrcAndDestFiles(imagePath);
            return imageUrlExt;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void deleteSrcAndDestFiles(final String imagePath) {
        try {
            EXECUTOR_SERVICE.submit((Runnable) () -> {
                String postFix = imagePath.substring(0, imagePath.lastIndexOf('/'));
                Path[] paths = new Path[]{Paths.get(Constants.IMAGE_SOURCE_PATH + postFix),Paths.get(Constants.IMAGE_DEST_PATH + postFix)};
                for (Path path : paths) {
                    try {
                        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                    throws IOException {
                                Files.delete(file);
                                return FileVisitResult.CONTINUE;
                            }

                            @Override
                            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                                    throws IOException {
                                if (exc != null)
                                    throw exc;
                                Files.delete(dir);
                                return FileVisitResult.CONTINUE;
                            }
                        });
                    } catch (IOException e) {
                        LOG.error("fail to walkFileTree", e);
                    }
                }
                try {
                    Files.deleteIfExists(Paths.get(Constants.IMAGE_DEST_PATH + postFix + ".tar.gz"));
                } catch (IOException e) {
                    LOG.error("fail to delete tar.gz", e);
                }
            });
        } catch (Exception e) {
            LOG.error("fail to deleteSrcAndDestFiles", e);
        }
    }
}
