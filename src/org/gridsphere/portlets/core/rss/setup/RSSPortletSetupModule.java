package org.gridsphere.portlets.core.rss.setup;

import org.gridsphere.services.core.setup.modules.impl.BasePortletsSetupModule;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleDefinition;
import org.gridsphere.services.core.setup.modules.impl.descriptor.PortletsSetupModuleStateDescriptor;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDefinition;
import org.gridsphere.portletcontainer.impl.descriptor.Preference;
import org.gridsphere.portletcontainer.impl.descriptor.Value;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.HashSet;
import java.util.Enumeration;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public class RSSPortletSetupModule extends BasePortletsSetupModule {
    public RSSPortletSetupModule(PortletsSetupModuleDefinition moduleDef) {
        super(moduleDef);
    }

    public void fillPreInitStateDescriptor(PortletsSetupModuleStateDescriptor portletsSetupModuleStateDescriptor, PortletDefinition portletDefinition) throws IllegalArgumentException {
        //Fill PortletsSetupModuleStateDescriptor with values (use default values for title, description, jsp - check portletssetupmodules.xml for details)
        //PortletDefinition represents deployment descriptor of the portlet (bound with portlet-name portlet.xml <-> portletssetupmodules.xml)
        Set set = new HashSet();
        Preference[] preference = portletDefinition.getPortletPreferences().getPreference();
        for (int i = 0; i < preference.length; i++) {
            Preference preference1 = preference[i];
            if (preference1.getName().getContent().equals("feedurl")) {
                Value[] value = preference1.getValue();
                for (int j = 0; j < value.length; j++) {
                    Value value1 = value[j];
                    set.add(value1.getContent());
                }
            }
        }
        portletsSetupModuleStateDescriptor.setAttribute("preferences", set);
    }

    public void invokePreInit(HttpServletRequest request, PortletDefinition portletDefinition) throws IllegalArgumentException {
        //Any change to PortletDefinition object is applies before portlet initialization process so you can update here f.e. init-parameters, preferences a.s.o.
        String setupOperation = getOperation(request);
        if (setupOperation.equals("add")) {
            //Add feed operation
            String newFeed = request.getParameter("newFeed");
            if (null != newFeed && !"".equals(newFeed)) {
                Value[] values = portletDefinition.getPortletPreferences().getPreference(0).getValue();
                Value[] newValues = new Value[values.length + 1];
                for (int i = 0; i < values.length; i++) {
                    Value value1 = values[i];
                    newValues[i] = value1;
                }
                Value newFeedValue = new Value();
                newFeedValue.setContent(newFeed);
                newValues[values.length] = newFeedValue;
                portletDefinition.getPortletPreferences().getPreference(0).setValue(newValues);
            }else
                throw new IllegalArgumentException("emptyValueError"); //throw IllegalArgumentException with error key (check portletssetupmodules.xml for details)
        } else if (setupOperation.equals("remove")) {
            //Remove feed operation
            Enumeration parameterNames = request.getParameterNames();
            Value[] values = portletDefinition.getPortletPreferences().getPreference(0).getValue();
            Set feedsToRemove = new HashSet();
            while (parameterNames.hasMoreElements()) {
                String paramName = (String) parameterNames.nextElement();
                if(paramName.startsWith("feed_")){
                    String feedName = paramName.substring("feed_".length());
                    feedsToRemove.add(feedName);
                }
            }
            Value[] newValues = new Value[values.length - feedsToRemove.size()];
            for (int i = 0, j = 0; i < values.length; i++) {
                Value value1 = values[i];
                if(!feedsToRemove.contains(value1.getContent())){
                    newValues[j] = value1;
                    ++j;
                }
            }
            portletDefinition.getPortletPreferences().getPreference(0).setValue(newValues);
        } else {
            //non operation - mark module as processed
            setPreInitPhaseProcessed(true);
        }
    }

    private String getOperation(HttpServletRequest request){
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            if(parameterName.startsWith("operation="))
                return parameterName.substring("operation=".length()).toLowerCase();
        }
        return "";
    }
}
