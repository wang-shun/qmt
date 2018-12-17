package com.lesports.qmt.cms.web.controller;

import com.lesports.utils.LeProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * js&css文件上传controller
 * User: ellios
 * Time: 16-12-9 : 上午10:31
 */
@RestController
public class ComboUploadController {

    private static final String UPLOAD_ROOT_DIR = LeProperties.getString("le.upload.root.dir", "/opt");

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String upload(@RequestParam(value = "file") MultipartFile[] files,
                         @RequestParam("toDir") String toDir,
                         @RequestParam(value = "version", required = true) String version,
                         @RequestParam(value = "type", defaultValue = "pc")  String type) {
        if(ArrayUtils.isEmpty(files)){
            return "Unable to upload. No files found.";
        }

        type = StringUtils.lowerCase(type);
        String fileName = null;
        String msg = "";
        for (int i = 0; i < files.length; i++) {
            try {
                fileName = files[i].getOriginalFilename();
                if(StringUtils.isEmpty(fileName)){
                    continue;
                }
                byte[] bytes = files[i].getBytes();
                InputStream bais = new ByteArrayInputStream(bytes);
                File dir = FileUtils.getFile(UPLOAD_ROOT_DIR, type, version);
                if (StringUtils.isNotEmpty(toDir)) {
                    dir = FileUtils.getFile(UPLOAD_ROOT_DIR, type, toDir, version);
                }
                if(!dir.exists()){
                    FileUtils.forceMkdir(dir);
                }
                File file = FileUtils.getFile(dir, fileName);
                FileUtils.copyInputStreamToFile(bais, file);
                msg += "You have successfully uploaded " + fileName + "<br/>";
            } catch (Exception e) {
                return "You failed to upload " + fileName + ": " + e.getMessage() + "<br/>";
            }
        }
        return msg;
    }
}
