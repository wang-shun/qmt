package com.lesports.qmt.cms.web.velocity;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.lesports.qmt.cms.api.dto.TColumnPage;
import com.lesports.qmt.cms.api.dto.TLayout;
import com.lesports.qmt.cms.api.dto.TWidget;
import com.lesports.qmt.cms.client.QmtCmsApis;
import com.lesports.utils.math.LeNumberUtils;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义的ResourceLoader, 通过velocityLRUCache取得模板信息
 *
 * @author jiangbo
 * @since 2013.11.27
 */
public class CmsResourceLoader extends ResourceLoader {

    private static final Logger LOG = LoggerFactory.getLogger(CmsResourceLoader.class);

    private static final String M_WEB_ROOT = "WEB-INF/views";

    /**
     * Get an InputStream so that the Runtime can build a template with it.
     *
     * @param name id of template
     * @return InputStream containing template
     * @throws org.apache.velocity.exception.ResourceNotFoundException
     */
    public InputStream getResourceStream(final String name) throws ResourceNotFoundException {
        if (StringUtils.isEmpty(name)) {
            throw new ResourceNotFoundException("CmsResourceLoader: resource name was empty or null");
        }
        if (StringUtils.contains(name, "spring.vm")) {
            //启动时spring会加载spring.vm，velocity会使用每个ResourceLoader都尝试加载
            return null;
        }
        if(StringUtils.contains(name, "ddata")){
            try {
                return FileUtils.openInputStream(new File(Resources.getResource("static/ddata.vm").getFile()));
            } catch (IOException e) {
                LOG.error("fail to getResourceStream for ddata, error : {}", e.getMessage(), e);
            }
        }
        if(name.endsWith("vm")){
            String fileName = "";
            if(name.startsWith("/")){
                fileName = M_WEB_ROOT + name;
            }else {
                fileName = M_WEB_ROOT + "/" + name;
            }
            try {

                return FileUtils.openInputStream(new File(Resources.getResource(fileName).getFile()));

            } catch (IOException e) {
                LOG.error("fail to getResourceStream for vm, error : {}", e.getMessage(), e);
            }
        }

        if(StringUtils.equals(name, "error")){
            return null;
        }

        if (StringUtils.isNumeric(name)) {
            //column page id
            Long id = LeNumberUtils.toLong(name);
            TColumnPage page = QmtCmsApis.getTColumnPageById(id);
            if (page == null) {
                return null;
            }
            return new ByteArrayInputStream(page.getVm().getBytes(Charsets.UTF_8));
        }

        if(StringUtils.contains(name, "layout")){
            TLayout layout = QmtCmsApis.getTLayoutByPath(name);
            if (layout == null) {
                return null;
            }
            return new ByteArrayInputStream(layout.getVm().getBytes(Charsets.UTF_8));
        }

        TWidget widget = QmtCmsApis.getTWidgetByPath(name);
        if (widget == null) {
            return null;
        }
        return new ByteArrayInputStream(widget.getVm().getBytes(Charsets.UTF_8));
    }

    /**
     * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#init(org.apache.commons.collections.ExtendedProperties)
     */
    @Override
    public void init(ExtendedProperties configuration) {
        // TODO Auto-generated method stub

    }

    /**
     * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#isSourceModified(org.apache.velocity.runtime.resource.Resource)
     */
    @Override
    public boolean isSourceModified(Resource resource) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#getLastModified(org.apache.velocity.runtime.resource.Resource)
     */
    @Override
    public long getLastModified(Resource resource) {
        // TODO Auto-generated method stub
        return 0;
    }
}
