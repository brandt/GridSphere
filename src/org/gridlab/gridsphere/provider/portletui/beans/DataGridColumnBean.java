package org.gridlab.gridsphere.provider.portletui.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class DataGridColumnBean extends BeanContainer {

    private String header = null;
    private Object data = null;
    private String source = null;
    private String paramName = null;
    private String paramValue = null;

    /**
     * Returns addtl. dynamic Parametervalue. This is a method name which will be called on the object to be included.
     * @return methodname
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * Sets the Methodname which should be called on the data object while cycling though the list.
     * @param paramValue methodname to be called on the data object
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * Returns the parametername.
     * @return parametername
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * Sets the parametername for an addtl. parameter to be included.
     * @param paramName name of the parameter
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    protected Object getData() {
        return data;
    }

    protected void setData(Object data) {
        this.data = data;
    }

    /**
     * Returns the methodname to be called on the object data to set (usually) the value of an included
     * TagBean. Depends on the type of the TagBean.
     * @return methodname
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the methodname to be called on the object data to set (usually) the value of an included
     * TagBean. Depends on the type of the TagBean.
     * @param source of the method
     */

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Returns the Header for the column.
     * @return header of the column
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the column
     * @param header of the column
     */
    public void setHeader(String header) {
        this.header = header;
    }

    public String toStartString() {
        String result = new String();
        if (header != null) result = "<th class=\"ui-datagrid-header\">" + header + "</th>";
        return result;
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < container.size(); i++) {

            // so far only textfield, text, actionlink and checkbox are supported, need to add
            // support in the appropriate tag to be added to the datagridcolumnbean container

            BaseComponentBean bean = (BaseComponentBean) container.get(i);
            bean.setValue(this.getData(data, this.source));

            if (bean instanceof TextFieldBean) {
                TextFieldBean tfBean = (TextFieldBean) bean;
                String prefixName = this.getData(data, tfBean.getBeanIdSource());
                tfBean.setBeanId(prefixName);
            }

            // check if we fot dynamic parameters
            if (paramName != null && paramValue != null) {
                if (bean instanceof ActionLinkBean) {
                    ActionLinkBean alBean = (ActionLinkBean) bean;
                    alBean.addParamBean(this.paramName, this.getData(data, this.paramValue));
                }
            }

            sb.append(bean.toStartString());
            sb.append(bean.toEndString());


        }
        return sb.toString();
    }

    public String toString() {
        return header;
    }

    /**
     * Returns the result of the method call 'method' on object data or null if error occured
     * @param data Object to be examined
     * @param method methodname to be called
     * @return result of data.method() or null
     */
    private String getData(Object data, String method) {
        String result = null;

        Class dataClass = data.getClass();
        Method m = null;
        try {
            m = dataClass.getMethod(method, new Class[]{});
            result = (String) m.invoke(data, new Object[]{});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

}
