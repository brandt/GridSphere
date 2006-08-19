package org.gridsphere.provider.portletui.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DataGridColumnBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */

public class DataGridColumnBean extends BeanContainer {

    private String header = null;
    private Object data = null;
    private String var = null;
    private String paramName = null;
    private String paramValue = null;
    private DataGridAttributes attributes = null;

    public DataGridAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(DataGridAttributes attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns addtl. dynamic Parametervalue. This is a method name which will be called on the object to be included.
     *
     * @return methodname
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * Sets the Methodname which should be called on the data object while cycling though the list.
     *
     * @param paramValue methodname to be called on the data object
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * Returns the parametername.
     *
     * @return parametername
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * Sets the parametername for an addtl. parameter to be included.
     *
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
     *
     * @return methodname
     */
    public String getVar() {
        return var;
    }

    /**
     * Sets the methodname to be called on the object data to set (usually) the value of an included
     * TagBean. Depends on the type of the TagBean.
     *
     * @param var of the method
     */

    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Returns the Header for the column.
     *
     * @return header of the column
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the header of the column
     *
     * @param header of the column
     */
    public void setHeader(String header) {
        this.header = header;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<th class=\"ui-datagrid-header\">");
        if (header != null) {
            sb.append(header);
        } else {
            sb.append("&nbsp;");
        }
        sb.append("</th>");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < container.size(); i++) {

            // so far only textfield, text, actionlink and checkbox are supported, need to add
            // support in the appropriate tag to be added to the datagridcolumnbean container

            BaseComponentBean bean = (BaseComponentBean) container.get(i);
            bean.setValue(this.getData(data, this.var));

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

            if (attributes != null && bean instanceof SelectElementBean) {
                SelectElementBean seBean = (SelectElementBean) bean;
                seBean.setSelected(attributes.isSelected(this.getData(data, this.var)));
                seBean.setDisabled(attributes.isDisabled(this.getData(data, this.var)));

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
     *
     * @param data   Object to be examined
     * @param method methodname to be called
     * @return result of data.method() or null
     */
    private String getData(Object data, String method) {
        String result = null;

        System.out.println("\n\n\n\n Try to call method '" + method + "' on object " + data.getClass().getName() + "\n\n\n");

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
