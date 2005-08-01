/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * A <code>SpecialBean</code> is just a demonstration of how to create a tag/bean pair
 * that can dynamically choose to use JavaScript or not based on browser detection
 */
public class SpecialBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "sp";

    public static final String TEXTFIELD_STYLE = "portlet-frame-text";

    public SpecialBean() {
        super(NAME);
        this.cssClass = TEXTFIELD_STYLE;
    }

    public SpecialBean(String name) {
        super(name);
        this.cssClass = TEXTFIELD_STYLE;
    }

    public SpecialBean(String name, String id) {
        super(name);
        this.beanId = id;
        this.cssClass = TEXTFIELD_STYLE;
    }

    public String toStartString() {
        if (supportsJS) {
            return "<b>a javascript interface</b>";
        } else {
            return "<b>a non-javascript interface</b>";
        }
    }

    public String toEndString() {
        return "";
    }

}
