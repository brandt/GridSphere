/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

public class SingleJobDescription extends JobDescription {

    private ResourceDescription resourceDescription = null;
    private ApplicationDescription applicationDescription = null;

    public SingleJobDescription() {
        super();
        init();
    }

    private void init() {
        this.resourceDescription = new ResourceDescription();
        this.applicationDescription = new ApplicationDescription();
    }

    public ResourceDescription getResourceDescription() {
        return this.resourceDescription;
    }

    public void setResourceDescription(ResourceDescription description) {
        this.resourceDescription = description;
    }

    public ApplicationDescription getApplicationDescription() {
        return this.applicationDescription;
    }

    public void setApplicationDescription(ApplicationDescription description) {
        this.applicationDescription = description;
    }
}
