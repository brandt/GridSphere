/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 22, 2003
 * Time: 11:07:24 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletService;

public class PortletBean {

    protected PortletConfig config = null;
    protected PortletContext context = null;
    protected PortletRequest request = null;
    protected PortletResponse response = null;
    protected User user = null;
    protected PortletSession session = null;

    public PortletBean() {
    }

    public PortletBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        init(config, request, response);
    }

    public void init(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        this.config = config;
        this.context = config.getContext();
        this.request = request;
        this.response = response;
        this.user = request.getUser();
        this.session = request.getPortletSession();
    }

    protected void initServices(User user, PortletConfig config)
            throws PortletException {
    }

    public PortletConfig getPortletConfig() {
        return this.config;
    }

    public PortletRequest getPortletRequest() {
        return this.request;
    }

    public void setPortletRequest(PortletRequest request) {
        this.request = request;
    }

    public PortletResponse getPortletResponse() {
        return this.response;
    }

    public void setPortletResponse(PortletResponse response) {
        this.response = response;
    }

    public PortletSession getPortletSession() {
        return this.session;
    }

    public User getPortletUser() {
        return this.user;
    }

    public String getPortletUserID() {
        return this.user.getID();
    }

    public String getPortletUserName() {
        return this.user.getUserName();
    }

    public PortletService getPortletService(Class serviceClass)
            throws PortletException {
        PortletService service = (PortletService)this.session.getAttribute(serviceClass.getName());
        if (service == null) {
            service = this.context.getService(serviceClass, user);
            this.session.setAttribute(serviceClass.getName(), service);
        }
        return service;
    }

    public PortletURI getPortletActionURI(String action) {
        PortletURI actionURI = response.createReturnURI();
        actionURI.addAction(new DefaultPortletAction(action));
        return actionURI;
    }

    public String getParameter(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return "";
        else
            return value;
    }

    public String getParameter(String param, String defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        else
            return value;
    }

    public String[] getParameterValues(String param) {
        String values[] = this.request.getParameterValues(param);

        if (values == null)
            return new String[0];
        else
            return values;
    }

    public int getParameterAsInt(String param) {
        return getParameterAsInt(param, 0);
    }

    public int getParameterAsInt(String param, int defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Integer(value)).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public int[] getParameterValuesAsInt(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new int[0];
        } else {
            int objs[] = new int[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Integer(value)).intValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public long getParameterAsLng(String param) {
        return getParameterAsLng(param, 0);
    }

    public long getParameterAsLng(String param, long defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Long(value)).longValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public long[] getParameterValuesAsLng(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new long[0];
        } else {
            long objs[] = new long[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Long(value)).longValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public float getParameterAsFlt(String param) {
        return getParameterAsFlt(param, (float)0.0);
    }

    public float getParameterAsFlt(String param, float defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Float(value)).floatValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public float[] getParameterValuesAsFlt(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new float[0];
        } else {
            float objs[] = new float[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Float(value)).floatValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public double getParameterAsDbl(String param) {
        return getParameterAsDbl(param, (double)0.0);
    }

    public double getParameterAsDbl(String param, double defaultValue) {
        String value = this.request.getParameter(param);
        if (value == null)
            return defaultValue;
        if (value.equals(""))
            return defaultValue;
        try {
            return (new Double(value)).doubleValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public double[] getParameterValuesAsDbl(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new double[0];
        } else {
            double objs[] = new double[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Double(value)).doubleValue();
                } catch (Exception e) {
                    objs[ii] = 0;
                }
            }
            return objs;
        }
    }

    public boolean getParameterAsBool(String param) {
        String value = this.request.getParameter(param);
        if (value == null) return false;
        if (value.equals("")) return false;
        if (value.equals("true")) return true;
        return false;
    }

    public boolean[] getParameterValuesAsBool(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new boolean[0];
        } else {
            boolean objs[] = new boolean[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                if (value.equals("")) objs[ii] = false;
                if (value.equals("true")) objs[ii] = true;
                objs[ii] = false;
            }
            return objs;
        }
    }

    public Integer getParameterAsInteger(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return (new Integer(0));
        if (value.equals("")) value = "0";
        try {
            return (new Integer(value));
        } catch (Exception e) {
            return (new Integer(0));
        }
    }

    public Integer[] getParameterValuesAsInteger(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Integer[0];
        } else {
            Integer objs[] = new Integer[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Integer(value));
                } catch (Exception e) {
                    objs[ii] = (new Integer(0));
                }
            }
            return objs;
        }
    }

    public Float getParameterAsFloat(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return (new Float(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Float(value));
        } catch (Exception e) {
            return (new Float(0.0));
        }
    }

    public Float[] getParameterValuesAsFloat(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Float[0];
        } else {
            Float objs[] = new Float[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Float(value));
                } catch (Exception e) {
                    objs[ii] = (new Float(0.0));
                }
            }
            return objs;
        }
    }

    public Double getParameterAsDouble(String param) {
        String value = this.request.getParameter(param);
        if (value == null)
            return (new Double(0.0));
        if (value.equals("")) value = "0";
        try {
            return (new Double(value));
        } catch (Exception e) {
            return (new Double(0.0));
        }
    }

    public Double[] getParameterValuesAsDouble(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Double[0];
        } else {
            Double objs[] = new Double[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                try {
                    objs[ii] = (new Double(value));
                } catch (Exception e) {
                    objs[ii] = (new Double(0.0));
                }
            }
            return objs;
        }
    }

    public Boolean getParameterAsBoolean(String param) {
        String value = this.request.getParameter(param);
        if (value == null) return new Boolean(false);
        if (value.equals("")) return new Boolean(false);
        if (value.equals("true")) return new Boolean(true);
        return new Boolean(false);
    }

    public Boolean[] getParameterValuesAsBoolean(String param) {
        String values[] = this.request.getParameterValues(param);
        if (values == null) {
            return new Boolean[0];
        } else {
            Boolean objs[] = new Boolean[values.length];
            for (int ii = 0; ii < values.length; ++ii) {
                String value = values[ii];
                if (value.equals("")) objs[ii] = new Boolean(false);
                if (value.equals("true")) objs[ii] = new Boolean(true);
                objs[ii] = new Boolean(false);
            }
            return objs;
        }
    }
}
