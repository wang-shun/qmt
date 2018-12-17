package com.lesports.qmt.web.i18n;

import com.lesports.LeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User: ellios
 * Time: 16-7-11 : 下午3:14
 */
public class LesportsLocaleResolver extends AbstractLocaleContextResolver {

    private static final Logger LOG = LoggerFactory.getLogger(LesportsLocaleResolver.class);

    private static final String  LOCALE_PARAM_NAME = "lelocale";

    /**
     * Create a default LesportsLocaleResolver, exposing a configured default
     * locale (or the JVM's default locale as fallback).
     * @see #setDefaultLocale
     * @see #setDefaultTimeZone
     */
    public LesportsLocaleResolver() {
        setDefaultLocale(Locale.getDefault());
    }

    /**
     * Create a LesportsLocaleResolver that exposes the given locale.
     * @param locale the locale to expose
     */
    public LesportsLocaleResolver(Locale locale) {
        setDefaultLocale(locale);
    }

    /**
     * Create a LesportsLocaleResolver that exposes the given locale and time zone.
     * @param locale the locale to expose
     * @param timeZone the time zone to expose
     */
    public LesportsLocaleResolver(Locale locale, TimeZone timeZone) {
        setDefaultLocale(locale);
        setDefaultTimeZone(timeZone);
    }


    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lecocale = request.getParameter(LOCALE_PARAM_NAME);
        if(!StringUtils.isEmpty(lecocale)){
            //优先获取locale参数
            return StringUtils.parseLocaleString(lecocale);
        }
        if(request.getServerName().toUpperCase().contains(LeConstants.LESPORTS_EN_HK_DOMAIN_PREFIX)){
            return StringUtils.parseLocaleString(LeConstants.LESPORTS_EN_HK_DOMAIN_PREFIX);
        }
        Locale locale = getDefaultLocale();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    @Override
    public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
        return new TimeZoneAwareLocaleContext() {
            @Override
            public Locale getLocale() {
                return resolveLocale(request);
            }
            @Override
            public TimeZone getTimeZone() {
                return getDefaultTimeZone();
            }
        };
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
        throw new UnsupportedOperationException("Cannot change fixed locale - use a different locale resolution strategy");
    }

}
