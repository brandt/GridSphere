/**
 * Copyright (c) 2004 Grad-Soft Ltd, Kiev, Ukraine
 * http://www.gradsoft.ua
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.gridlab.gridsphere.filters;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * RequestEncodingFilter - set the encoding of request to UTF-8,
 * if it was not set by web-client.
 *@author Ruslan Shevchenko &lt;rssh@gradsoft.ua&gt;
 *
 */
public class RequestEncodingFilter implements Filter {


	public void init(FilterConfig filterConfig) {
		filterConfig_ = filterConfig;
                String enabledString=filterConfig.getInitParameter("enabled");
                if (enabledString!=null) {
                   if (enabledString.equals("true")) {
                      enabled_=true;
                   }else if(enabledString.equals("false")) {
                      enabled_=false;
                   }else{
                      filterConfig.getServletContext().log("RequestEncodingFilter: enabled must be true or false, set to true");
                      enabled_=true;
                   }
                }
                String encoding=filterConfig.getInitParameter("encoding");
                if (encoding!=null) {
                   encoding_=encoding;
                }
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain)
		throws IOException, ServletException 
        {
           	if (enabled_) {
                        String originalEncoding=request.getCharacterEncoding();
                        if (originalEncoding==null) {
                           request.setCharacterEncoding(encoding_);
                        }else{
                           getFilterConfig().getServletContext().log("RequestEncodingFilter:keep original"+originalEncoding);
                        }
                }
	        chain.doFilter(request, response);
	}

	public FilterConfig getFilterConfig() {
		return filterConfig_;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		init(filterConfig);
	}


	private FilterConfig filterConfig_;
        private boolean      enabled_ = true;
        private String       encoding_ = "UTF-8";

}