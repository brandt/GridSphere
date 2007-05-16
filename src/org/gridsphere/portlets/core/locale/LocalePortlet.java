/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LocalePortlet.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlets.core.locale;

import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridsphere.services.core.locale.LocaleService;
import org.gridsphere.services.core.user.User;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import java.util.Locale;

public class LocalePortlet extends ActionPortlet {

    private LocaleService localeService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        localeService = (LocaleService) createPortletService(LocaleService.class);
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

    public void showLocale(RenderFormEvent event) throws PortletException {
        PortletRequest request = event.getRenderRequest();
        Locale locale = request.getLocale();
        request.setAttribute("locale", locale);

        ListBoxBean localeSelector = event.getListBoxBean("localeLB");
        localeSelector.clear();
        localeSelector.setOnChange("this.form.submit()");
        localeSelector.setSize(1);

        Locale[] locales = localeService.getSupportedLocales();

        for (int i = 0; i < locales.length; i++) {
            Locale displayLocale = locales[i];
            ListBoxItemBean localeBean = makeLocaleBean(displayLocale.getDisplayLanguage(displayLocale), displayLocale.getLanguage(), locale);
            localeSelector.addBean(localeBean);
        }
        setNextState(request, "locale/viewlocale.jsp");
    }

    public void selectLang(ActionFormEvent event) throws PortletException {
        ListBoxBean localeSelector = event.getListBoxBean("localeLB");
        PortletSession session = event.getActionRequest().getPortletSession(true);
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
                session.setAttribute(User.LOCALE, locale, PortletSession.APPLICATION_SCOPE);
            }
        }
    }

}
