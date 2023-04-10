package com.urise.webapp.util;

import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.TestData.R1;

public class JsonParserTest {
    @Test
    public void testResume() throws Exception {

        String json = JsonParser.write((CompanySection) R1.getSection(SectionType.EXPERIENCE), CompanySection.class);
        System.out.println(json);
        CompanySection companySection = JsonParser.read(json, CompanySection.class);
        Assert.assertEquals((CompanySection) R1.getSection(SectionType.EXPERIENCE), companySection);
    }

    @Test
    public void write() throws Exception {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}
