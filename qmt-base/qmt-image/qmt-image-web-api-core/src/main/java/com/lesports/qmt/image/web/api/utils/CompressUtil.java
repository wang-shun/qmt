package com.lesports.qmt.image.web.api.utils;

import com.google.common.collect.Maps;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

/**
 * 文件归档压缩工具类
 *
 * Author: chengen
 * Date: 2014/7/8
 * Time: 11:30
 */
public class CompressUtil {
    private static final Logger logger = LoggerFactory.getLogger(CompressUtil.class);

    private static final ArchiveStreamFactory archiveFactory = new ArchiveStreamFactory();
    private static final ArchiveEntryFactory archiveEntryFactory = new ArchiveEntryFactory();
    private static final CompressorStreamFactory compressorFactory = new CompressorStreamFactory();
    private static final String EMPTY_PATH = "";
    private static final String FILE_DOT = ".";
    private static final String FILE_SEPARATOR = "/";
    private static final String FILE_LINE = "_";
    private static final int ZIP_BUFFER_SIZE = 512;

    public static enum Format{
        ZIP("zip"), TAR("tar"), GZIP("gz");
        private String value;
        private Format(String s) {
            value = s;
        }
        public String getValue(){
            return value;
        }
    }

    public static String createTarGz(String[] inputPaths, String outputPath) throws Exception {
        String tarPath = archive(inputPaths, outputPath, Format.TAR);
        String gzPath = compress(tarPath, Format.GZIP);
        File tarFile = new File(tarPath);
        if(tarFile.exists()){
            tarFile.delete();
        }
        return gzPath;
    }

    /**
     * 归档成.tar文件
     *
     * @param dirPath   要归档的目录
     * @return          归档后的文件路径，文件名为目录名.tar
     * eg: /data/test => /data/test.tar
     */
    public static String createTar(String dirPath) throws Exception{
        return archive(dirPath, dirPath, Format.TAR, false);
    }

    /**
     * 归档并压缩成.tar.gz文件
     *
     * @param dirPath   要压缩的目录
     * @return          压缩后的文件路径，文件名为目录名.tar.gz
     * eg: /data/test => /data/test.tar.gz
     */
    public static String createTarGz(String dirPath) throws Exception{
        return createTarGz(dirPath, dirPath);
    }

    public static String createTarGz(String dirPath, String outputPath) throws Exception {
        String tarPath = archive(dirPath, outputPath, Format.TAR, false);
        String gzPath = compress(tarPath, Format.GZIP);
        File tarFile = new File(tarPath);
        if(tarFile.exists()){
            tarFile.delete();
        }
        return gzPath;
    }

    public static String archive(String[] inputPaths, String outputPath, Format format) throws Exception {
        File[] files = new File[inputPaths.length];
        for(int i = 0; i < inputPaths.length; i++){
            files[i] = new File(inputPaths[i]);
        }
        return archive(files, outputPath, format, EMPTY_PATH);
    }

    public static String archive(String dirPath, String outputPath, Format format, boolean isKeepDir) throws Exception {
        File dir = new File(dirPath);
        if(dir.exists() && dir.isDirectory()){
            String dirPathName = isKeepDir ? dir.getName() : EMPTY_PATH;
            File[] files = dir.listFiles();
            return archive(files, outputPath, format, dirPathName);
        }else {
            logger.error("{} is not dir!",dirPath);
            return null;
        }
    }

    private static String archive(File[] inputFiles, String outputPath, Format format, String dirPath) throws Exception{
        String outputFilePath = outputPath + FILE_DOT + format.getValue();
        ArchiveOutputStream os = null;
        OutputStream output = null;
        InputStream is = null;
        try{
            output = new FileOutputStream(outputFilePath);
            os = archiveFactory.createArchiveOutputStream(format.getValue(), output);
            for(File file : inputFiles){
                os.putArchiveEntry(archiveEntryFactory.getArchiveEntry(format, dirPath + FILE_SEPARATOR + file.getName(), file.length()));
                is = new FileInputStream(file);
                IOUtils.copy(is, os);
                os.closeArchiveEntry();
                IOUtils.closeQuietly(is);
            }
            os.finish();
            return outputFilePath;
        }finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(output);
        }
    }

    public static String compress(String inputPath, Format format) throws Exception{
        return compress(inputPath, inputPath, format);
    }

    public static String compress(String inputPath, String outputPath, Format format) throws Exception{
        String outputFilePath = outputPath + FILE_DOT + format.getValue();
        CompressorOutputStream os = null;
        OutputStream output = null;
        InputStream is = null;
        try{
            is = new FileInputStream(inputPath);
            output = new FileOutputStream(outputFilePath);
            os = compressorFactory.createCompressorOutputStream(format.getValue(), output);
            IOUtils.copy(is, os);
            return outputFilePath;
        }finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(output);
        }
    }

    public static void dearchiveZip(String inputPath, String outputPath) throws Exception {
        File inputFile = new File(inputPath);
        Map<String, byte[]> fileMap = dearchiveZip(inputFile);
        File pathFile = new File(outputPath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        for(Map.Entry<String, byte[]> entry : fileMap.entrySet()){
            OutputStream os = new FileOutputStream(outputPath + FILE_SEPARATOR + entry.getKey());
            os.write(entry.getValue());
            os.flush();
            IOUtils.closeQuietly(os);
        }
    }

    public static Map<String, byte[]> dearchiveZip(File inputFile) throws Exception {
        return dearchive(inputFile, Format.ZIP, ZIP_BUFFER_SIZE);
    }

    public static Map<String, byte[]> dearchive(File inputFile, Format format, int bufferSize) throws Exception{
        Map<String, byte[]> fileMap = Maps.newHashMap();
        ArchiveEntry archiveEntry = null;
        InputStream is =null;
        ArchiveInputStream ais = null;
        try {
            is = new FileInputStream(inputFile);
            archiveFactory.setEntryEncoding("utf-8");
            ais = archiveFactory.createArchiveInputStream(format.getValue(), is);
            while ((archiveEntry = ais.getNextEntry()) != null){
                String entryFileName = archiveEntry.getName();
                int fileSize = (int)archiveEntry.getSize();
                byte[] content = new byte[fileSize];
                int count = 0;
                int offset = 0;
                int newBufSize = bufferSize;
                while(count != -1){
                    count = ais.read(content, offset, newBufSize);
                    offset += count;
                    if((fileSize - offset) < bufferSize){
                        newBufSize = fileSize - offset;
                    }
                }
                fileMap.put(entryFileName, content);
            }
            return fileMap;
        }finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(ais);
        }
    }

    static class ArchiveEntryFactory{
        ArchiveEntry getArchiveEntry(Format format, String fileName, long fileLength){
            switch (format){
                case TAR:
                    TarArchiveEntry archiveEntry = new TarArchiveEntry(fileName);
                    archiveEntry.setSize(fileLength);
                    return archiveEntry;
                case ZIP:
                    return new ZipArchiveEntry(fileName);
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        dearchiveZip("D:\\player.zip", "D:\\player");
    }
}
