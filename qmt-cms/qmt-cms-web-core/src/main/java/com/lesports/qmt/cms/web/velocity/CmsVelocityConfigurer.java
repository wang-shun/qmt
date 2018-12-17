package com.lesports.qmt.cms.web.velocity;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * CMSVelocityConfigurer主要继承VelocityConfigurer，
 * 覆盖initVelocityResourceLoader两个方法
 * 
 * @author jiangbo
 * @since 2013.11.25
 */
public class CmsVelocityConfigurer extends VelocityConfigurer {

	private static final Logger LOG = LoggerFactory.getLogger(CmsVelocityConfigurer.class);

	private String resourceLoaderType;

	/**
	 * 
	 * @see org.springframework.ui.velocity.VelocityEngineFactory#initVelocityResourceLoader(org.apache.velocity.app.VelocityEngine,
	 *      String)
	 */
	@Override
	protected void initVelocityResourceLoader(VelocityEngine velocityEngine, String resourceLoaderPath) {

		if (StringUtils.isNotEmpty(resourceLoaderType) && resourceLoaderType.equalsIgnoreCase("CMSDataSourceResourceLoader") && isPreferFileSystemAccess()) {
            LOG.info("start cmsResourceLoader VelocityEngine");
		} else {
			super.initVelocityResourceLoader(velocityEngine, resourceLoaderPath);
		}
	}

	public String getResourceLoaderType() {
		return resourceLoaderType;
	}

	public void setResourceLoaderType(String resourceLoaderType) {
		this.resourceLoaderType = resourceLoaderType;
	}

}
