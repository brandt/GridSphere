/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.locale;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridlab.gridsphere.services.core.cache.CacheService;
import org.gridlab.gridsphere.services.core.locale.LocaleService;

import javax.servlet.UnavailableException;
import java.util.Locale;

public class LocalePortlet extends ActionPortlet {

    public static final String VIEW_JSP = "/jsp/locale/view.jsp";
    private LocaleService localeService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.localeService = (LocaleService) config.getContext().getService(LocaleService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        DEFAULT_VIEW_PAGE = "showLocale";
    }


    private ListBoxItemBean makeLocaleBean(String language, String name, Locale locale) {
        ListBoxItemBean bean = new ListBoxItemBean();
        String display;
        display = language.substring(0, 1).toUpperCase() + language.substring(1);

        bean.setValue(display);
        bean.setName(name);

        if (locale.getLanguage().equals(name)) {
            bean.setSelected(true);
        }
        return bean;
    }

    public void showLocale(FormEvent event) throws PortletException {
        PortletRequest request = event.getPortletRequest();
        Locale locale = request.getLocale();

        request.setAttribute("locale", locale);

        ListBoxBean localeSelector = event.getListBoxBean("localeLB");
        localeSelector.clear();
        localeSelector.setOnChange("GridSphere_SelectSubmit( this.form )");
        localeSelector.setSize(1);


        Locale[] locales = localeService.getSupportedLocales();

        for (int i = 0; i < locales.length; i++) {
            Locale displayLocale = locales[i];
            ListBoxItemBean localeBean = makeLocaleBean(displayLocale.getDisplayLanguage(displayLocale), displayLocale.getLanguage(), locale);
            localeSelector.addBean(localeBean);
        }

        request.setAttribute(CacheService.NO_CACHE, CacheService.NO_CACHE);
        setNextState(request, "locale/viewlocale.jsp");
    }

    public void selectLang(FormEvent event) throws PortletException {
        ListBoxBean localeSelector = event.getListBoxBean("localeLB");
        PortletSession session = event.getPortletRequest().getPortletSession(true);
        String loc = localeSelector.getSelectedValue();
        // Javascript exploit found by PSNC and Tomek Kuczynski, check the loc to not allow a javascript attack
        if (loc != null) {
            Locale[] locales = localeService.getSupportedLocales();
            boolean valid = false;
            for (Locale l : locales) {
                if (loc.equals(l.toString())) valid = true;
            }
            if (valid) {
                Locale locale = new Locale(loc, "", "");
                session.setAttribute(User.LOCALE, locale);
            }
        }
    }

}
