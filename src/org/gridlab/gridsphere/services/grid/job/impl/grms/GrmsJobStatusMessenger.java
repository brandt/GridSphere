/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job.impl.grms;

import java.util.Date;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import org.gridlab.gridsphere.services.grid.job.Job;
import org.gridlab.gridsphere.services.grid.job.JobSpecification;
import org.gridlab.gridsphere.services.grid.job.JobStatus;
import org.gridlab.gridsphere.services.grid.job.JobStatusMessenger;

public class GrmsJobStatusMessenger extends JobStatusMessenger {

    private static GrmsJobStatusMessenger _instance = null;
    private PortletLog logger = null;

    public static GrmsJobStatusMessenger getInstance() {
        if (_instance == null) {
            _instance = new GrmsJobStatusMessenger();
        }
        return _instance;
    }

    public GrmsJobStatusMessenger() {
    }

    public void sendMessage(Job job) {
        sendEmailMessage(job);
    }

    public void sendEmailMessage(Job job) {
        // Retrieve user information
        User user = job.getUser();
        String emailTo = user.getEmailAddress();
        // Attempt to email user
        if (!emailTo.equals("")) {
            // Retrieve job information
            String userId = user.getID();
            String jobId = job.getID();
            JobStatus jobStatus = job.getJobStatus();
            JobSpecification jobSpecification = job.getJobSpecification();
            Date dateStatusChanged = job.getDateStatusChanged();
            Date dateSubmitted = job.getDateSubmitted();
            Date dateStarted = job.getDateStarted();
            Date dateEnded = job.getDateEnded();
            // Create message subject
            StringBuffer buffer = new StringBuffer();
            buffer.append("Job Status Update [");
            buffer.append(jobId);
            buffer.append("]");
            String emailSubject = buffer.toString();
            // Create message content
            buffer = new StringBuffer();
            // Report summary
            buffer.append("\n------------------------------------------------\n");
            buffer.append(emailSubject);
            buffer.append("\n------------------------------------------------\n");
            buffer.append("\n - User Id : ");
            buffer.append(userId);
            buffer.append("\n - Job Id : ");
            buffer.append(jobId);
            buffer.append("\n - Job Status : ");
            buffer.append(jobStatus);
            buffer.append("\n - Date Submitted : ");
            buffer.append(dateSubmitted);
            buffer.append("\n - Date Started : ");
            buffer.append(dateStarted);
            buffer.append("\n - Date Ended : ");
            buffer.append(dateEnded);
            buffer.append("\n------------------------------------------------\n");
            buffer.append("\n - Job Specification : \n\n");
            buffer.append(jobSpecification);
            String emailContent = buffer.toString();
            /***
            // Create and send message
            try {
                EmailMessage message = new EmailMessage();
                message.setTo(user);
                message.setSubject(emailSubject);
                message.setContent(emailContent);
                message.send();
            } catch (Exception e) {
                System.err.println(e);
            }
            ***/
        }
    }

    public void sendSmsMessage(Job job) {
        // Retrieve user information
        User user = job.getUser();
        String smsTo = (String)user.getAttribute("mobilePhoneNumber");
        // Attempt to sms user
        if (!smsTo.equals("")) {
            // Retrieve job information
            String jobId = job.getID();
            JobStatus jobStatus = job.getJobStatus();
            Date dateStatusChanged = job.getDateStatusChanged();
            // Create message content
            StringBuffer buffer = new StringBuffer();
            buffer.append("Job status changed for job [");
            buffer.append(jobId);
            buffer.append("] with new status [");
            buffer.append(jobStatus);
            buffer.append("] on [");
            buffer.append(dateStatusChanged);
            buffer.append("]");
            String smsContent = buffer.toString();
            /***
            // Create and send message
            try {
                SmsMessage message = new SmsMessage();
                message.setTo(user);
                message.setContent(smsContent);
                message.send();
            } catch (Exception e) {
                System.err.println(e);
            }
            ***/
        }
    }
}
