package com.lesports.qmt.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class Upload extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(Upload.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            String to = null;
            InputStream fileStream = null;

            try {
                upload.setFileSizeMax(50 * 1024 * 1024);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();

                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    String name = item.getFieldName();
                    InputStream stream = item.getInputStream();

                    if (item.isFormField()) {
                        if (name.equals("to")) {
                            to = Streams.asString(stream);
                        }
                    } else if (name.equals("file")) {
                        fileStream = stream;
                    }
                }

                if (to == null || fileStream == null) {
                    throw new RuntimeException("Params Error");
                }
                LOG.info("to : {}", to);


                File file = new File(to);

                LOG.info("file : {}", file);

                if (file.isDirectory()) {
                    throw new IllegalArgumentException("Can't upload to a folder `" + to + "`");
                }

                if (file.exists()) {
                    if (!file.delete() || !file.createNewFile()) {
                        LOG.error("file : {} fail to create.", file);
                        throw new Exception("Permission denied");
                    }
                } else {
                    if (!file.getParentFile().exists()) {
                        if (!file.getParentFile().mkdirs()) {
                            LOG.error("file : {} fail to create parent", file);
                            throw new Exception("Permission denied");
                        }
                    }
                    if (!file.createNewFile()) {
                        LOG.error("can not to create file : {}", file);
                        throw new Exception("Permission denied");
                    }

                }

                if (!file.canWrite()) {
                    LOG.error("file : {} can not write.", file);
                    throw new Exception("Permission denied.");
                }

                OutputStream dst = new FileOutputStream(file);
                Streams.copy(fileStream, dst, true);
                response.getWriter().write("0");
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                response.getWriter().write("1");

                throw new ServletException(e.getMessage(), e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(403, "forbidden");
    }
}
