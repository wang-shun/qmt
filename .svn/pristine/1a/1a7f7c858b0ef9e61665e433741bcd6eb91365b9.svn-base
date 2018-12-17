package com.lesports.qmt.image.web.api.utils;

import com.alibaba.fastjson.JSON;
import com.lesports.qmt.image.web.api.core.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by yangyu on 2015/11/20.
 */
public class ImageCutUtils {

	private static final Logger logger = LoggerFactory.getLogger(ImageCutUtils.class);



	public static String createTarGzWithCut(String imagePath, String params){
		InputStream is = null;
		OutputStream destOs = null;
		OutputStream destBakOs = null;
		try{
			String[] paths = imagePath.split(Constants.FILE_SEPARATOR);
			String imageDestDirPath = Constants.IMAGE_DEST_PATH + StringUtils.join(paths, Constants.FILE_SEPARATOR, 0, paths.length - 1);
			imageDestDirPath = checkDirPath(imageDestDirPath);
			String imageSourcePath = Constants.IMAGE_SOURCE_PATH + imagePath;
			String imageDestPath = null;
			String imageDestBakPath = null;
			String fileFormat = imagePath.substring(imagePath.lastIndexOf(Constants.FILE_DOT));

			Map<String, Object> paramMap = JSON.parseObject(params, Map.class);
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				imageDestPath = imageDestDirPath + Constants.FILE_SEPARATOR + entry.getKey() + fileFormat;
				imageDestBakPath = imageDestDirPath + Constants.FILE_SEPARATOR + entry.getKey()
						+ Constants.IMAGE_BAK + fileFormat;
				Map<String, Integer> param = JSON.parseObject(entry.getValue().toString(), Map.class);
				ImageUtils.cutImage(imageSourcePath, imageDestPath, param.get("x"), param.get("y"),
						param.get("w"), param.get("h"));
				is = new FileInputStream(imageDestPath);
				destBakOs = new FileOutputStream(imageDestBakPath);
				IOUtils.copy(is, destBakOs);
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(destBakOs);
			}
			//新版本传tar文件
			String imageGzPath = CompressUtil.createTarGz(imageDestDirPath);
			return imageGzPath;
		}catch (Exception ex){
			logger.error("create tar.gz error!", ex);
			return null;
		}finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(destOs);
			IOUtils.closeQuietly(destBakOs);
		}
	}

	public static String createTarGzWithoutCut(String[] imagePaths){
		InputStream is = null;
		OutputStream destOs = null;
		OutputStream destBakOs = null;
		String imageGzPath = null;
		try{
			//以第一个文件目录为目的目录
			String firstPath = imagePaths[0];
			String[] paths = firstPath.split(Constants.FILE_SEPARATOR);
			String imageDestDirPath = Constants.IMAGE_DEST_PATH + StringUtils.join(paths, Constants.FILE_SEPARATOR, 0, paths.length - 1);
			imageDestDirPath = checkDirPath(imageDestDirPath);
			for(String imagePath : imagePaths){
				String imageSourcePath = Constants.IMAGE_SOURCE_PATH + imagePath;
				String imageDestPath = null;
				String imageDestBakPath = null;
				String fileFormat = imagePath.substring(imagePath.lastIndexOf(Constants.FILE_DOT));

				String imageName = imagePath.substring(imagePath.lastIndexOf(Constants.FILE_SEPARATOR) + 1);

				imageDestPath = imageDestDirPath + Constants.FILE_SEPARATOR + imageName;
				imageDestBakPath = imageDestDirPath + Constants.FILE_SEPARATOR + imageName.substring(0, imageName.lastIndexOf(Constants.FILE_DOT))
						+ Constants.IMAGE_BAK + fileFormat;
				is = new FileInputStream(imageSourcePath);
				destOs = new FileOutputStream(imageDestPath);
				destBakOs = new FileOutputStream(imageDestBakPath);
				IOUtils.copy(is, destOs);
				IOUtils.closeQuietly(is);
				is = new FileInputStream(imageSourcePath);
				IOUtils.copy(is, destBakOs);
			}
			//新版本传tar文件
			imageGzPath = CompressUtil.createTarGz(imageDestDirPath);
			return imageGzPath;
		}catch (Exception ex){
			logger.error("create tar.gz error!", ex);
			return null;
		}finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(destOs);
			IOUtils.closeQuietly(destBakOs);
		}
	}

	private static synchronized String checkDirPath(String path) throws IOException {
		Path path1 = Paths.get(path);
		if (Files.notExists(path1)) {
			path1.toFile().mkdirs();
		}
		return path;
//		File file = new File(path);
//		if(file.exists()){
//			int flag = path.lastIndexOf(Constants.FILE_SEPARATOR);
//			String lastPath = StringUtils.substring(path, flag + 1);
//			int lastPathFlag = lastPath.lastIndexOf(Constants.FILE_LINE);
//			if(lastPathFlag != -1){
//				lastPath = StringUtils.substring(lastPath, 0, lastPathFlag);
//			}
//			final String pathPrefix = lastPath;
//			File parentFile = file.getParentFile();
//			String[] listFile = parentFile.list(DirectoryFileFilter.INSTANCE);
//			Arrays.sort(listFile, new Comparator<String>() {
//				@Override
//				public int compare(String o1, String o2) {
//					if (o1.contains(pathPrefix) && o2.contains(pathPrefix)) {
//						return getPathNum(o2) - getPathNum(o1);
//					} else if (o1.contains(pathPrefix)) {
//						return -1;
//					} else if (o2.contains(pathPrefix)) {
//						return 1;
//					}
//					return 0;
//				}
//			});
//			int pathNum = getPathNum(listFile[0]) + 1;
//			path = StringUtils.substring(path, 0, flag + 1) + pathPrefix + Constants.FILE_LINE + pathNum;
//			file = new File(path);
//		}
//		file.mkdirs();
//		return path;
	}

	private static int getPathNum(String path){
		int flag = path.lastIndexOf(Constants.FILE_LINE);
		if(flag == -1){
			return 0;
		}else{
			return Integer.parseInt(StringUtils.substring(path, flag + 1));
		}
	}
}
