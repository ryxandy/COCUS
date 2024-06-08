package com.cocus.fileuploader.util;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class LineResponseXML {
    private String line;

    public LineResponseXML() {}

    public LineResponseXML(String line) {
        this.line = line;
    }

    @XmlElement
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}