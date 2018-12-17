package com.lesports.qmt.image.web.api.controller.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesports.api.common.ImageUrlExt;
import com.lesports.qmt.image.web.api.core.Constants;
import com.lesports.qmt.image.web.api.param.Coordinate;
import com.lesports.qmt.image.web.api.utils.ImageUtils;
import com.lesports.spring.boot.exception.LeWebApplicationException;
import com.lesports.spring.boot.mvc.LeStatus;
import com.lesports.utils.LeDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by denghui on 2016/12/28.
 */
public abstract class AbstractUploadController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractUploadController.class);
    private static Map<String, List<Integer>> ratioMap = Maps.newHashMap();
    private static String IMAGE_PREFIX = null;

    static {
        //1610(16:10) 169(16:9) 43(4:3) 34(3:4) 21(2:1) 11(1:1)
        ratioMap.put("1610", Lists.newArrayList(16,10));
        ratioMap.put("169", Lists.newArrayList(16,9));
        ratioMap.put("43", Lists.newArrayList(4,3));
        ratioMap.put("34", Lists.newArrayList(3,4));
        ratioMap.put("21", Lists.newArrayList(2,1));
        ratioMap.put("11", Lists.newArrayList(1,1));
    }

    protected static synchronized String parseImagePrefixHeader(String prefixHeader) {
        if (StringUtils.isNotEmpty(IMAGE_PREFIX)) {
            return IMAGE_PREFIX;
        }
        //10.185.30.236=/image2,10.185.30.230=/image
        if (StringUtils.isEmpty(prefixHeader)) {
            // 无Header时认为是单机 默认/image2
            LOG.info("header is empty, default prefix is used");
            IMAGE_PREFIX = Constants.IMAGE_WEB_2;
            return IMAGE_PREFIX;
        }
        Map<String,String> prefixMap = Maps.newHashMap();
        for (String pair : prefixHeader.split(",")) {
            String[] arr = pair.split("=");
            if (arr.length == 2) {
                prefixMap.put(arr[0], arr[1]);
            }
        }
        String prefix = prefixMap.get(getIpAddress());
        if (StringUtils.isEmpty(prefix) || !prefix.startsWith("/image")) {
            throw new IllegalArgumentException("invalid prefix header: " + prefixHeader);
        }
        IMAGE_PREFIX = prefix;
        LOG.info("parse header : {}, result: {}",prefixHeader, prefix);
        return IMAGE_PREFIX;
    }

    protected String copyFileToLocal(MultipartFile multipartFile, String imagePath) throws IOException {
        //生成本地路径
        java.nio.file.Path localDir = Paths.get(Constants.IMAGE_SOURCE_PATH + imagePath.substring(0,imagePath.lastIndexOf(Constants.FILE_SEPARATOR)));
        if (!Files.exists(localDir)) {
            Files.createDirectories(localDir);
        }
        //复制文件
        java.nio.file.Path localPath = Paths.get(Constants.IMAGE_SOURCE_PATH + imagePath);
        Files.copy(multipartFile.getInputStream(), localPath);
        return localPath.toString();
    }

    protected String genImagePath(String prefix, String type, MultipartFile multipartFile) {
        String filename = genFilename(multipartFile);
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        return prefix + Constants.FILE_SEPARATOR + now.substring(0,8) + Constants.FILE_SEPARATOR
                + type + RandomStringUtils.randomAlphanumeric(9).toLowerCase() + Constants.FILE_SEPARATOR + filename;
    }

    protected String genFilename(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String originalFileType = originalFileName.substring(originalFileName.lastIndexOf(Constants.FILE_DOT));
        return UUID.randomUUID().toString() + ImageUtils.transFormatTypetoLowerCase(originalFileType);
    }

    protected String copyFileToLocal(String url, String imagePath) throws IOException {
        //生成本地路径
        java.nio.file.Path localDir = Paths.get(Constants.IMAGE_SOURCE_PATH + imagePath.substring(0,imagePath.lastIndexOf(Constants.FILE_SEPARATOR)));
        if (!Files.exists(localDir)) {
            Files.createDirectories(localDir);
        }
        //复制文件
        java.nio.file.Path localPath = Paths.get(Constants.IMAGE_SOURCE_PATH + imagePath);
        String format = url.substring(url.lastIndexOf(Constants.FILE_DOT) + 1); //jpg, png, gif
        ImageIO.write(ImageIO.read(new URL(url)), format, localPath.toFile());
        return localPath.toString();
    }

    protected String genImagePath(String prefix, String type, String url) {
        String filename = genFilename(url);
        String now = LeDateUtils.formatYYYYMMDDHHMMSS(new Date());
        return prefix + Constants.FILE_SEPARATOR + now.substring(0,8) + Constants.FILE_SEPARATOR
                + type + RandomStringUtils.randomAlphanumeric(9).toLowerCase() + Constants.FILE_SEPARATOR + filename;
    }

    protected String genFilename(String url) {
        String originalFileType = url.substring(url.lastIndexOf(Constants.FILE_DOT));
        return UUID.randomUUID().toString() + ImageUtils.transFormatTypetoLowerCase(originalFileType);
    }

    protected ImageUrlExt buildResponse(String imagePath, String imageUrl) {
        if (StringUtils.isEmpty(imageUrl) || StringUtils.isEmpty(imagePath)) {
            throw new LeWebApplicationException("fail to upload", LeStatus.SERVER_EXCEPTION);
        }
        ImageUrlExt imageUrlExt = new ImageUrlExt();
        imageUrlExt.setUrl(imageUrl);
        imageUrlExt.setPath(imagePath);
        return imageUrlExt;
    }

    protected String getValidParams(String params, int width, int height) {
        Map<String, Object> map = JSON.parseObject(params, Map.class);
        for (Map.Entry<String, Object> param : map.entrySet()) {
            String ratio = param.getKey();
            if (ratioMap.containsKey(ratio)) {
                Coordinate coordinate = JSON.parseObject(param.getValue().toString(), Coordinate.class);
                if (coordinate.getX() == 0 && coordinate.getY() == 0
                        && coordinate.getW() == 0 && coordinate.getH() == 0) {
                    param.setValue(JSON.toJSONString(getAutoCoordinate(ratio, width, height)));
                }
            } else {
                LOG.warn("unknown ratio, width:{}, height:{}, ratio:{}", width, height, ratio);
            }
        }
        return JSON.toJSONString(map);
    }

    protected Coordinate getAutoCoordinate(String ratio, int width, int height) {
        // (x:y) (w:h)
        int x = 0, y = 0, w = width, h = height;
        int unitW = ratioMap.get(ratio).get(0);
        int unitH = ratioMap.get(ratio).get(1);
        int candidateH = unitH*width/unitW;
        int candidateW = unitW*height/unitH;
        if (candidateH <= height) {
            x = 0;
            y = (height - candidateH) / 2;
            w = width;
            h = candidateH;
        } else if (candidateW <= width) {
            x = (width - candidateW) / 2;
            y = 0;
            w = candidateW;
            h = height;
        } else {
            LOG.warn("should not happen here, width:{}, height:{}, ratio:{}", width, height, ratio);
        }
        Coordinate auto = new Coordinate(x,y,w,h);
        LOG.info("auto coordinate: {}", auto);
        return auto;
    }

    protected static String getIpAddress() {
        try {
            InetAddress inetAddress = null;
            NetworkInterface eth0 = NetworkInterface.getByName("eth0");
            if (null != eth0 && null != eth0.getInetAddresses()) {
                Enumeration e2 = eth0.getInetAddresses();
                while (e2.hasMoreElements()) {
                    inetAddress = (InetAddress) e2.nextElement();
                }
            }
            return inetAddress == null? null : inetAddress.getHostAddress();
        } catch (Exception e) {
            LOG.warn("fail to get localhost address, error : {}", e.getMessage(), e);
        }
        return null;
    }

    protected String[] deleteRatioIfNecessary(String params, String originalPath, String prefix, String type) throws IOException {
        List<String> toDeletedRatios = Lists.newArrayList();
        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            Map<String, Integer> param = JSON.parseObject(entry.getValue().toString(), Map.class);
            //都为0是删除这个比例
            if (param.get("x").equals(0) && param.get("y").equals(0) &&
                    param.get("w").equals(0) && param.get("h").equals(0)) {
                toDeletedRatios.add(entry.getKey());
            }
        }

        if (CollectionUtils.isEmpty(toDeletedRatios)) {
            return new String[]{params, originalPath};
        }

        String imagePath = genImagePath(prefix, type, originalPath);
        Path sourcePath = Paths.get(Constants.IMAGE_SOURCE_PATH + originalPath);
        Path destDir = Paths.get(Constants.IMAGE_DEST_PATH + originalPath.substring(0, originalPath.lastIndexOf('/')));
        Path newSourceDir = Paths.get(Constants.IMAGE_SOURCE_PATH + imagePath.substring(0, imagePath.lastIndexOf('/')));
        Path newDestDir = Paths.get(Constants.IMAGE_DEST_PATH + imagePath.substring(0, imagePath.lastIndexOf('/')));
        if (Files.notExists(newSourceDir)) {
            newSourceDir.toFile().mkdirs();
        }
        //将原图拷贝过来
        Files.copy(sourcePath, newSourceDir.resolve(Paths.get(imagePath).getFileName()), StandardCopyOption.REPLACE_EXISTING);
        if (Files.notExists(newDestDir)) {
            newDestDir.toFile().mkdirs();
        }
        Files.walkFileTree(destDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                //不复制要删除的ratio对应的图片
                for (String deletedRatio : toDeletedRatios) {
                    if (file.getFileName().toString().startsWith(deletedRatio)) {
                        return FileVisitResult.CONTINUE;
                    }
                }
                Files.copy(file, newDestDir.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
        JSONObject newParams = JSON.parseObject(params);
        toDeletedRatios.forEach(newParams::remove);
        return new String[]{newParams.toJSONString(), imagePath};
    }
}
