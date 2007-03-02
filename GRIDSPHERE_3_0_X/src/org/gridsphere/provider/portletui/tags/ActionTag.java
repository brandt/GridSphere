/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ActionTag.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.PortletURLImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.DefaultPortletAction;
import org.gridsphere.portletcontainer.DefaultPortletPhase;
import org.gridsphere.portletcontainer.DefaultPortletRender;
import org.gridsphere.provider.portletui.beans.ImageBean;
import org.gridsphere.provider.portletui.beans.ParamBean;

import javax.portlet.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The abstract <code>ActionTag</code> is used by other Action tags to contain <code>DefaultPortletAction</code>s
 * and possibly <code>ActionParamTag</code>s
 */
public abstract class ActionTag extends BaseComponentTag {

    protected String action = null;
    protected String render = null;
    protected String anchor = null;
    protected String var = null;
    protected String onClick = null;
    protected String onSubmit = null;
    protected String onMouseOver = null;
    protected String onMouseOut = null;
    protected String onReset = null;
    protected boolean useAjax = false;

    protected boolean isSecure = false;

    protected String windowState = null;
    protected String portletMode = null;

    protected DefaultPortletPhase portletPhase = null;
    protected List<ParamBean> paramBeans = new ArrayList<ParamBean>();
    protected String label = null;
    protected String layout = null;
    protected ImageBean imageBean = null;
    protected boolean paramPrefixing = true;

    public static class TEI extends TagExtraInfo {

        public VariableInfo[] getVariableInfo(TagData tagData) {
            VariableInfo vi[] = null;
            String var = tagData.getAttributeString("var");
            if (var != null) {
                vi = new VariableInfo[1];
                vi[0] = new VariableInfo(var, "java.lang.String", true, VariableInfo.AT_BEGIN);
            }
            return vi;
        }
    }

    /**
     * Sets the name of the variable to export as a RenderURL object
     *
     * @param var the name of the variable to export as a RenderURL object
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Returns the name of the exported RenderURL object
     *
     * @return the exported variable
     */
    public String getVar() {
        return var;
    }

    /**
     * Sets the text that should be added at the end of generated URL
     *
     * @param anchor the action link key
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * Returns the anchor used to identify text that should be added at the end of generated URL
     *
     * @return the anchor
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * Returns the action specified by the onClick attribute
     *
     * @return the action specified by the onClick attribute
     */
    public String getOnClick() {
        return onClick;
    }

    /**
     * Sets the action specified by the onClick attribute
     *
     * @param onClick the javascript action to perform
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * Returns the action specified by the onReset attribute
     *
     * @return the action specified by the onReset attribute
     */
    public String getOnReset() {
        return onReset;
    }

    /**
     * Sets the action specified by the onReset attribute
     *
     * @param onReset the javascript action to perform
     */
    public void setOnReset(String onReset) {
        this.onReset = onReset;
    }

    /**
     * Returns the action specified by the onMouseOver attribute
     *
     * @return onMouseOver the javascript onMouseOver event
     */
    public String getOnMouseOver() {
        return onMouseOver;
    }

    /**
     * Sets the action specified by the onMouseOver event
     *
     * @param onMouseOver the javascript onMouseOver event
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * Returns the action specified by the onMouseOut attribute
     *
     * @return onMouseOver the javascript onMouseOut event
     */
    public String getOnMouseOut() {
        return onMouseOut;
    }

    /**
     * Sets the action specified by the onMouseOut event
     *
     * @param onMouseOut the javascript onMouseOut event
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * Returns the action specified by the onSubmit attribute
     *
     * @return the action specified by the onSubmit attribute
     */
    public String getOnSubmit() {
        return onSubmit;
    }

    /**
     * Sets the action specified by the onSubmit attribute
     *
     * @param onSubmit the javascript action to perform
     */
    public void setOnSubmit(String onSubmit) {
        this.onSubmit = onSubmit;
    }

    /**
     * Sets the label identified with the portlet component to link to
     *
     * @param label the action link key
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label identified with the portlet component to link to
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the layout id that identifies a layout descriptor to target
     *
     * @return the layout id that identifies a layout descriptor to target
     */
    public String getLayout() {
        return layout;
    }

    /**
     * Sets the layout id that identifies a layout descriptor to target
     *
     * @param layout the layout id that identifies a layout descriptor to target
     */
    public void setLayout(String layout) {
        this.layout = layout;
    }

    /**
     * If secure is true, then use https, otherwise use http
     *
     * @param isSecure true if this actiontag is secure e.g. https, false otherwise
     */
    public void setSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

    /**
     * Returns true if this actiontag is secure e.g. https, false otherwise
     *
     * @return true if this actiontag is secure, false otherwise
     */
    public boolean getSecure() {
        return isSecure;
    }

    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    public String getPortletMode() {
        return portletMode;
    }

    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    public String getWindowState() {
        return windowState;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

    public void addParamBean(ParamBean paramBean) {
        paramBeans.add(paramBean);
    }

    public void removeParamBean(ParamBean paramBean) {
        paramBeans.remove(paramBean);
    }

    public List getParamBeans() {
        return paramBeans;
    }

    public boolean isUseAjax() {
        return useAjax;
    }

    public void setUseAjax(boolean useAjax) {
        this.useAjax = useAjax;
    }

    protected String createURI(PortletURL url) throws JspException {
        // Builds a URI containing the actin and associated params
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        //RenderRequest req = (RenderRequest) pageContext.getAttribute(SportletProperties.RENDER_REQUEST, PageContext.REQUEST_SCOPE);
        // action is a required attribute except for FormTag
        if (label != null) {
            res.setProperty("label", label);
            ((PortletURLImpl) url).setLabel(label);
        }
        if (layout != null) {
            ((PortletURLImpl) url).setLayout(layout);
        }

        if (windowState != null) {
            WindowState state = new WindowState(windowState);
            try {
                //System.err.println("set state to:" + state);
                url.setWindowState(state);
            } catch (WindowStateException e) {
                throw new JspException("Unknown window state in renderURL tag: " + windowState);
            }
        }
        if (portletMode != null) {
            PortletMode mode = new PortletMode(portletMode);
            try {
                url.setPortletMode(mode);
                //System.err.println("set mode to:" + mode + " url=" + url);
            } catch (PortletModeException e) {
                throw new JspException("Unknown portlet mode in renderURL tag: " + portletMode);
            }
        }

        String compId = (String) pageContext.findAttribute(SportletProperties.GP_COMPONENT_ID);

        if (action != null) {
            if (compId == null) {
                ((PortletURLImpl) url).setAction(action);
                portletPhase = new DefaultPortletAction(action);
            } else {
                ((PortletURLImpl) url).setAction(compId + "%" + action);
                portletPhase = new DefaultPortletAction(compId + "%" + action);
            }
        } else {
            if (render == null) render = "";
            if (compId == null) {
                ((PortletURLImpl) url).setRender(render);
                portletPhase = new DefaultPortletRender(render);
            } else {
                ((PortletURLImpl) url).setRender(compId + "%" + render);
                portletPhase = new DefaultPortletRender(compId + "%" + render);
            }
        }

        /*
        else {
            if (compId == null) {
                // since action is NULL at this point, make it an empty string
                action = "";
                portletAction = new DefaultPortletAction(action);
            } else {
                portletAction = new DefaultPortletAction(compId + "%" + action);
            }
        }
        */

        if (!paramBeans.isEmpty()) {
            String id = createUniquePrefix(2);
            Iterator it = paramBeans.iterator();
            if (paramPrefixing) {
                url.setParameter(SportletProperties.PREFIX, id);
                portletPhase.addParameter(SportletProperties.PREFIX, id);
            }
            while (it.hasNext()) {
                ParamBean pbean = (ParamBean) it.next();
                //System.err.println("have param bean name= " + pbean.getName() + " value= " + pbean.getValue());
                if (paramPrefixing) {
                    url.setParameter(id + "_" + pbean.getName(), pbean.getValue());
                    portletPhase.addParameter(id + "_" + pbean.getName(), pbean.getValue());
                } else {
                    url.setParameter(pbean.getName(), pbean.getValue());
                    portletPhase.addParameter(pbean.getName(), pbean.getValue());
                }
            }
        }
        //System.err.println("printing action  URL = " + url.toString());
        return url.toString();
    }

    public String createActionURI() throws JspException {
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        return createURI(res.createActionURL());
    }

    public String createRenderURI() throws JspException {
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        return createURI(res.createRenderURL());
    }

    public void release() {
        super.release();
        action = null;
        anchor = null;
        var = null;
        onClick = null;
        onSubmit = null;
        onMouseOut = null;
        onMouseOver = null;
        useAjax = false;
        isSecure = false;
        windowState = null;
        portletMode = null;
        portletPhase = null;
        paramBeans.clear();
        label = null;
        layout = null;
        imageBean = null;
        paramPrefixing = true;
    }

    /**
     * A string utility that produces a string composed of
     * <code>numChars</code> number of characters
     *
     * @param numChars the number of characters in the resulting <code>String</code>
     * @return the <code>String</code>
     */
    protected String createUniquePrefix(int numChars) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i <= numChars; i++) {
            int nextChar = (int) (Math.random() * 62);
            if (nextChar < 10) //0-9
                s.append(nextChar);
            else if (nextChar < 36) //a-z
                s.append((char) (nextChar - 10 + 'a'));
            else
                s.append((char) (nextChar - 36 + 'A'));
        }
        return s.toString();
    }

}
