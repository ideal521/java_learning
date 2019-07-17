package com.sxt.server.basic;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 熟悉SAX的解析流程
 *
 * @author lyl3878
 * @date 7/2/2019
 */
public class XmlTest {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        //SAX解析
        // 1、获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 2、从解析工厂获取解析器
        SAXParser parse = factory.newSAXParser();
        // 3、编写处理器
        // 4、加载文档 Document注册处理器
        PHandler handler = new PHandler();
        // 5、解析
        parse.parse(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("com/sxt/server/basic/p.xml"), handler);

        List<Person> persons = handler.getPersons();
        for (Person p : persons) {
            System.out.println(p.getName() + "-->" + p.getAge());
        }

    }
}

class PHandler extends DefaultHandler {

    private List<Person> persons;
    private Person person;
    private String tag; // 存储操作标签


    @Override
    public void startDocument() throws SAXException {
        persons = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (null != qName) {
            tag = qName;

            if (tag.equals("person")) {
                person = new Person();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if (null != tag) { //处理空问题
            if (tag.equals("name")) {
                person.setName(contents);
            } else if (tag.equals("age")) {
                if (contents.length() > 0) {
                    person.setAge(Integer.valueOf(contents));
                }
            }
        }

    }

    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (null != qName) { //处理空问题
            if (qName.equals("person")) {
                persons.add(person);
            }
        }
        tag = null; //tag丢弃掉
    }

    @Override
    public void endDocument() throws SAXException {

    }
}
