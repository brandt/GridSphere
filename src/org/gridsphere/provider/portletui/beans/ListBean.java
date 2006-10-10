/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ListBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.provider.portletui.beans;

import java.util.List;

/**
 * A <code>ListBoxBean</code> represents a visual list box element
 */
public class ListBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "ul";

    protected List list = null;

    /**
     * Constructs a default list box bean
     */
    public ListBean() {
        super(NAME);
    }

    /**
     * Constructs a list box bean with the supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public ListBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    public void setListModel(List list) {
        this.list = list;
    }

    public List getListModel() {
        return list;
    }

    public String toStartString() {
        return "";
    }

    public String toListString(List myList) {
        StringBuffer sb = new StringBuffer();
        if (!myList.isEmpty()) {
            sb.append("<ul>");
            for (int i = 0; i < myList.size(); i++) {
                sb.append("<li>");
                Object o = myList.get(i);
                if (o instanceof String) {
                    String s = (String) o;
                    sb.append(s);
                } else if (o instanceof List) {
                    List l = (List) o;
                    sb.append(toListString(l));
                }
                sb.append("</li>");
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

    public String toEndString() {
        return toListString(list);
    }

}
