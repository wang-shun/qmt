package com.lesports.qmt.image.web.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.image.web.api.controller.support.AbstractUploadController;
import com.lesports.qmt.image.web.api.core.Constants;
import com.lesports.qmt.image.web.api.utils.ImageCutUtils;
import com.lesports.qmt.image.web.api.utils.ImageUtils;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LJSONP;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.spring.boot.mvc.NoEnvelopeResponse;
import com.lesports.utils.ImageCutApis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by denghui on 2016/10/27.
 */
@RestController
@RequestMapping(value="/upload")
public class UploadController extends AbstractUploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping(value = "/single", method= RequestMethod.OPTIONS)
    @ResponseBody
    public boolean singleOptions() {
        // some upload plugin (WebUploader) may send OPTIONS request before POST to detect server
        // http://blog.csdn.net/wuhaifeng558/article/details/17583245
        return true;
    }

    /**
     * 原图文件上传
     * @param multipartFile
     * @param type 图片类型, 默认def表示未指定
     * @param imagePrefixHeader 用于指定本机的存储路径前缀, 在ngx端配置
     * @return
     */
    @RequestMapping(value = "/single", method= RequestMethod.POST, consumes = "multipart/form-data",
            produces="application/json;charset=utf-8")
    public ImageUrlExt singleUpload(@RequestParam(value = "file", required = true) MultipartFile multipartFile,
                                    @RequestParam(value = "type", required = false, defaultValue = "def") String type,
                                    @RequestHeader(value = "Image-Prefix", required = false) String imagePrefixHeader) {
        try {
            LOG.info("[single upload start]");
            Stopwatch stopwatch = Stopwatch.createStarted();
            Preconditions.checkNotNull(multipartFile, "file part is null");

            String prefix = parseImagePrefixHeader(imagePrefixHeader);
            //生成源文件路径
            String imagePath = genImagePath(prefix, type, multipartFile);
            //将上传的文件复制到本地
            String localPath = copyFileToLocal(multipartFile, imagePath);
            //上传文件
            String imageUrl = ImageCutApis.uploadSingleFile(localPath, Constants.UPLOAD_CHANNEL);

            ImageUrlExt imageUrlExt = buildResponse(imagePath, imageUrl);
            stopwatch.stop();
            LOG.info("[single upload success][response:{}][elapsed:{}]", imageUrlExt, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return imageUrlExt;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/gz", method= RequestMethod.OPTIONS)
    public boolean gzOptions() {
        // some upload plugin (WebUploader) may send OPTIONS request before POST to detect server
        // http://blog.csdn.net/wuhaifeng558/article/details/17583245
        return true;
    }

    /**
     * 截图上传, 优先级originalPath > file > url
     * @param originalPath 图片原图路径
     * @param params 裁剪参数
     * @param multipartFile
     * @param url 图片url
     * @param type 图片类型, 默认def表示未指定
     * @param imagePrefixHeader 用于指定本机的存储路径前缀, 在ngx端配置
     * @return
     */
    @RequestMapping(value = "/gz", method= RequestMethod.POST, consumes = {"multipart/form-data","application/x-www-form-urlencoded"},
            produces="application/json;charset=utf-8")
    public ImageUrlExt gzUpload(@RequestParam(value = "originalPath", required = false) String originalPath,
                                @RequestParam(value = "params", required = true) String params,
                                @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                @RequestParam(value = "url", required = false) String url,
                                @RequestParam(value = "type", required = false, defaultValue = "def") String type,
                                @RequestHeader(value = "Image-Prefix", required = false) String imagePrefixHeader) {
        try {
            LOG.info("[gz upload start]");
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
            LOG.info("[gz upload success][response:{}][elapsed:{}]", imageUrlExt, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return imageUrlExt;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ueditor富文本编辑器获取配置
     * @return
     */
    @RequestMapping(value = "/adConfig", method= RequestMethod.GET,produces="application/json;charset=utf-8")
    @LJSONP
    @NoEnvelopeResponse
    public String adConfig() {
        try {
            byte[] config = Files.readAllBytes(Paths.get(new ClassPathResource("config.json").getFile().getAbsolutePath()));
            return new String(config);
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ueditor富文本编辑器上传图片
     * @param action
     * @return
     */
    @RequestMapping(value = "/adConfig", method= RequestMethod.POST,
            consumes = {"multipart/form-data", "application/x-www-form-urlencoded"},produces="application/json;charset=utf-8")
    @LJSONP
    @NoEnvelopeResponse
    public String adConfig(String action,
                           @RequestParam(value = "upfile", required = false) MultipartFile multipartFile,
                           @RequestParam(value = "source[]", required = false) String[] sources,
                           @RequestHeader(value = "Image-Prefix", required = false) String imagePrefixHeader) {
        try {
            JSONObject result = new JSONObject();
            if ("uploadimage".equals(action)) {
                LOG.info("[adConfig upload start]");
                Stopwatch stopwatch = Stopwatch.createStarted();
                Preconditions.checkNotNull(multipartFile, "file part is null");

                String prefix = parseImagePrefixHeader(imagePrefixHeader);
                //生成源文件路径
                String imagePath = genImagePath(prefix, "new", multipartFile);//news
                //将上传的文件复制到本地
                String localPath = copyFileToLocal(multipartFile, imagePath);
                //上传文件
                String imageUrl = ImageCutApis.uploadSingleFile(localPath, Constants.UPLOAD_CHANNEL);

                BufferedImage bufferedImage = ImageIO.read(Paths.get(localPath).toFile());
                int width = bufferedImage.getWidth();
                int height = bufferedImage.getHeight();
                result.put("data_height", height);
                result.put("data_width", width);
                result.put("original", "");
                result.put("size", multipartFile.getSize());
                result.put("state", "SUCCESS");
                result.put("title", "");
                String originalFileName = multipartFile.getOriginalFilename();
                String originalFileType = originalFileName.substring(originalFileName.lastIndexOf(Constants.FILE_DOT));
                result.put("type", originalFileType);
                result.put("url", imageUrl);
                stopwatch.stop();
                LOG.info("[adConfig upload success][response:{}][elapsed:{}]", result, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            } else if ("catchimage".equals(action)) {
                result.put("state", "SUCCESS");
                JSONArray list = new JSONArray();
                for (String source : sources) {
                    JSONObject item = new JSONObject();
                    item.put("size", "");
                    item.put("source", source);
                    item.put("state", "SUCCESS");
                    item.put("title", "");
                    item.put("url", source);
                    list.add(item);
                }
                result.put("list", list);
                LOG.info("[adConfig catch success][response:{}]", result);
            }
            return result.toJSONString();
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/compress", method= RequestMethod.POST)
    @NoEnvelopeResponse
    public String compress(@RequestParam(value = "originalPath", required = true) String originalPath,
                                @RequestParam(value = "quality", required = false, defaultValue = "85") Double quality) {
        try {
            LOG.info("[compress start]");
            Stopwatch stopwatch = Stopwatch.createStarted();

            String src = Constants.IMAGE_SOURCE_PATH + originalPath;
            String dest = src.substring(0, src.lastIndexOf('.')) + "_" + quality.intValue() + src.substring(src.lastIndexOf('.'));
            ImageUtils.compressImage(src, dest, quality);

            stopwatch.stop();
            LOG.info("[compress success][response:{}][elapsed:{}]", dest, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return dest;
        } catch (LeWebApplicationException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new LeWebApplicationException(e.getMessage(), LeStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
