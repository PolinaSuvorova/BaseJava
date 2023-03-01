package com.urise.webapp.storage.stream;

import com.urise.webapp.exception.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializerStrategy {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Company.class, CompanySection.class, Period.class, TextSection.class,
                ListTextSection.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream oStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(oStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream iStream) throws IOException {
        try (Reader resume = new InputStreamReader(iStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(resume);
        }
    }
}
