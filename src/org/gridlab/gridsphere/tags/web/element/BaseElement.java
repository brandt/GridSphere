/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class BaseElement implements Element {

    protected String id;
    protected String color;
    protected String backgroundcolor;
    protected String cssStyle;
    protected String font;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String style) {
        this.cssStyle = style;
    }


}
