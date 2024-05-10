package dut.stage.sfe.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Entity;
 
public class Mail {
    private String to;
    private String from;
    private String subject;
    private String template;
    private Map<String, Object> properties;

    public Mail(String to, String from, String subject, String template, HashMap<String, Object> properties) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.template = template;
        this.properties = properties;
    }

    public Mail() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
