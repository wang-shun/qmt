package com.lesports.qmt.image.web.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtils {
	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);
	private static final Double DEFAULT_QUALITY = 100.0;

	/**
	 * 图片格式转为小写，jpeg转为jpg
	 * @param type
	 * @return
	 */
	public static String transFormatTypetoLowerCase(String type){
		if (StringUtils.isNotBlank(type)){
			type = type.toLowerCase();
			if (type.contains("jpeg")){
				type = type.replace("jpeg", "jpg");
			}
		}
		return type;
	}

	public static String getImgCompress(String path){
		String compress = "85";
		File file = new File(path);
		long size = file.length();
		String formatName = getFormatName(file);
		if (formatName.equals("GIF") || formatName.equals("gif") || size > 1572864){
			compress = "50";
		}
		return compress;
	}


	/**
	 * 获取图片类型
	 * @param o
	 * @return
	 */
	public static String getFormatName(Object o) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(o);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			ImageReader reader = iter.next();
			iis.close();
			return reader.getFormatName();
		} catch (IOException e) {

		}
		return null;
	}

	/**
	 * 根据坐标裁剪图片
	 *
	 * @param srcPath   要裁剪图片的路径
	 * @param newPath   裁剪图片后的路径
	 * @param x   起始横坐标
	 * @param y   起始纵坐标
	 * @param width
	 * @param height
	 */
	public static void cutImage(String srcPath, String newPath, int x, int y, int width,int height){
		cutImage(srcPath, newPath, x, y, width, height, DEFAULT_QUALITY);
	}

	/**
	 * 根据坐标裁剪图片
	 *
	 * @param srcPath   要裁剪图片的路径
	 * @param newPath   裁剪图片后的路径
	 * @param x   起始横坐标
	 * @param y   起始纵坐标
	 * @param width
	 * @param height
	 * @param quality 压缩比例 0.0~100.0
	 */
	public static void cutImage(String srcPath, String newPath, int x, int y, int width,int height, Double quality){
		try{
			GMOperation op = new GMOperation();
			op.addImage(srcPath);
			/*if(quality != null){
				op.quality(quality);
			}*/
			/**  width：裁剪的宽度    * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标  */
			op.crop(width, height, x, y);
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
		}catch (Exception ex){
			logger.error("cut image error! path = {}", srcPath);
			ex.printStackTrace();
		}
	}

	/**
	 * 根据尺寸缩放图片
	 * @param srcPath   源图片路径
	 * @param newPath   缩放后图片的路径
	 * @param width  缩放后的图片宽度
	 * @param height  缩放后的图片高度
	 */
	public static void zoomImage(String srcPath, String newPath, Integer width, Integer height){
		zoomImage(srcPath, newPath, width, height, DEFAULT_QUALITY);
	}

	/**
	 * 根据尺寸缩放图片
	 * @param srcPath   源图片路径
	 * @param newPath   缩放后图片的路径
	 * @param width  缩放后的图片宽度
	 * @param height  缩放后的图片高度
	 * @param quality  压缩比例 0.0~100.0
	 */
	public static void zoomImage(String srcPath, String newPath, Integer width, Integer height, Double quality){
		try{
			GMOperation op = new GMOperation();
			op.addImage(srcPath);
			if(quality != null){
				op.quality(quality);
			}
			if(width != null || height != null){
				op.resize(width, height);
			}
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
		}catch (Exception ex){
			logger.error("zoom image error! path = {}", srcPath);
			ex.printStackTrace();
		}
	}

	/**
	 * 压缩图片
	 * @param srcPath   源图片路径
	 * @param newPath   缩放后图片的路径
	 * @param quality  压缩比例 0.0~100.0
	 */
	public static void compressImage(String srcPath, String newPath, Double quality){
		try{
			GMOperation op = new GMOperation();
			op.addImage(srcPath);
			if(quality != null){
				op.quality(quality);
			}
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
		}catch (Exception ex){
			logger.error("compress image error! path = {}", srcPath);
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String src  =   "d:/1.jpg" ;
		String sub1 = "d:/2.jpg";
		String sub2 = "d:\\3.jpg";
		String sub3 = "d:\\4.jpg";
		String sub4 = "d:\\5.jpg";
		String sub5 = "d:\\22.jpg";
		cutImage(src,sub1,0,0,200,200);
		cutImage(src,sub5,0,0,200,200,50.0);
		zoomImage(src,sub2,200,200);
		zoomImage(src,sub3,200,200,50.0);
		compressImage(src,sub4,50.0);
	}
}
