/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BeanContainer.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.provider.portletui.beans;

import java.util.*;

/**
 * The abstract <code>BeanContainer</code> is a container for other visual beans
 */
public abstract class BeanContainer extends BaseComponentBean {

    public List container = new Vector();

    /**
     * Constructs a default bean container
     */
    public BeanContainer() {
    }

    /**
     * Constructs a bean container with the supplied name
     *
     * @param name the bean container name
     */
    public BeanContainer(String name) {
        super(name);
    }

    /**
     * Adds a visual bean to the bean container
     *
     * @param bean a base component bean
     */
    public void addBean(BaseComponentBean bean) {
        container.add(bean);
    }

    /**
     * Adds a visual bean to the bean container
     *
     * @param bean a base component bean
     */
    public void setBean(int index, BaseComponentBean bean) {
        container.set(index, bean);
    }

    /**
     * Removes a visual bean from the bean container
     *
     * @param bean the visual bean to remove
     */
    public void removeBean(BaseComponentBean bean) {
        container.remove(bean);
    }

    /**
     * Clears the list of visual beans in this container
     */
    public void clear() {
        container.clear();
    }

    /**
     * Returns the visual beans as a list of <code>BaseComponentBean</code>s
     *
     * @return the list of visual beans
     */
    public List getBeans() {
        return Collections.unmodifiableList(container);
    }

    /**
     * Sorts the List by the value of the basecomponentbeans.
     *
     * @see BaseComponentBean
     */
    public void sortByValue() {
        SortedSet sorted = new TreeSet();
        for (int i = 0; i < container.size(); i++) {
            sorted.add(container.get(i));
        }
        Vector result = new Vector();
        Iterator i = sorted.iterator();
        while (i.hasNext()) {
            result.add(i.next());
        }
        container = result;
    }

}
