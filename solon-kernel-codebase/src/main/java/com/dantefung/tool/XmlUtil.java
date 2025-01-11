package com.dantefung.tool;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author fenghaolin
 * @date 2024/12/24 20/06
 * @since JDK1.8
 */
@Slf4j
public abstract class XmlUtil {

    private static final Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter();

    private static final XmlMapper xmlMapper = new XmlMapper();

    @SneakyThrows
    public static <T> T readJacksonXmlFromFile( Class<T> clazz,  String xmlFilePath) {
        return xmlMapper.readValue(new File(xmlFilePath), clazz);
    }

    @SneakyThrows
    public static <T> T readJacksonXmlFromString( Class<T> clazz,  String xml) {
        return xmlMapper.readValue(xml, clazz);
    }

    @SneakyThrows
    public static String toJacksonXml(Object obj) {
        ObjectWriter objectWriter = xmlMapper.writerWithDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(obj);
    }

    /**
     *
     *  使用 SpringMVC 默认的参数转换器读取
     *
     * org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor#readWithMessageConverters
     * -> org.springframework.http.converter.AbstractHttpMessageConverter#read
     *
     * @see Jaxb2RootElementHttpMessageConverter
     * @param xml
     * @return
     */
    @SneakyThrows
    public static <T> T readFromJaxbXml( Class<T> clazz,  String xml) {
        HttpInputMessage httpInputMessage = new HttpInputMessage() {

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                return new BufferedInputStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            }
        };
        return (T) jaxb2RootElementHttpMessageConverter.read(clazz, httpInputMessage);
    }

    /**
     * org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor#writeWithMessageConverters
     *
     * -> org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter#writeToResult
     */
    @SneakyThrows
    public static <T> String writeToJaxbXml(T t) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpOutputMessage httpOutputMessage = new HttpOutputMessage(){

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }

            @Override
            public OutputStream getBody() throws IOException {
                return byteArrayOutputStream;
            }
        };
        jaxb2RootElementHttpMessageConverter.write(t, MediaType.APPLICATION_XML, httpOutputMessage);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return new String(bytes, StandardCharsets.UTF_8);
    }


    /**
     * 不支持CDATA
     * 指定名称空间前缀策略来生成xml
     * @param t
     * @param namespacePrefixMapper
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> String writeToJaxbXml(T t, NamespacePrefixMapper namespacePrefixMapper) {
        StringWriter writer = new StringWriter();
        // 写入自己的 XML 声明
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(writer.toString().getBytes(StandardCharsets.UTF_8));
        HttpOutputMessage httpOutputMessage = new HttpOutputMessage(){

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }

            @Override
            public OutputStream getBody() throws IOException {
                return byteArrayOutputStream;
            }
        };

        Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter = new Jaxb2RootElementHttpMessageConverter() {
            @SneakyThrows
            @Override
            protected void customizeMarshaller(Marshaller marshaller) {
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);
            }
        };
        jaxb2RootElementHttpMessageConverter.write(t, MediaType.APPLICATION_XML, httpOutputMessage);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 支持CDATA
     * @param t
     * @param namespacePrefixMapper
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> String toJaxbXMLString( T t, NamespacePrefixMapper namespacePrefixMapper) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();

            OutputFormat of = new OutputFormat();
            of.setOmitXMLDeclaration(true);
            //            of.setPreserveSpace(true);
            //            of.setIndenting(true);

            ByteArrayOutputStream op = new ByteArrayOutputStream();
            CDataContentHandler serializer = new CDataContentHandler(op, of);
            SAXResult result = new SAXResult(serializer.asContentHandler());

            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);

            marshaller.marshal(t, result);

            StringWriter writer = new StringWriter();
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(System.lineSeparator());
            writer.append(indentFormat(op.toString("UTF-8")));

            return writer.toString();
        } catch (JAXBException e) {
            log.warn(e.getMessage() + " \r\n reason:" + e.toString(), e);
            throw e;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 不支持CDATA
     * @param t
     * @param namespacePrefixMapper
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> String toJaxbXml( T t, NamespacePrefixMapper namespacePrefixMapper) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 设置 Marshaller.JAXB_FRAGMENT 为 true，阻止生成 XML 声明
            // <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 不符合预期
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            // 再写入自己的 XML 声明
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(System.lineSeparator());
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);
            marshaller.marshal(t, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.warn(e.getMessage() + " \r\n reason:" + e.toString(), e);
            throw e;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw e;
        }
    }

    public static String indentFormat(String xml) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter formattedStringWriter = new StringWriter();
            transformer.transform(new StreamSource(new StringReader(xml)),
                    new StreamResult(formattedStringWriter));
            return formattedStringWriter.toString();
        } catch (TransformerException e) {
        }
        return null;
    }

    public static class CDataContentHandler extends XMLSerializer {
        private static final Pattern XML_CHARS = Pattern.compile( "(?<=<!\\[CDATA\\[).+(?=\\]\\]>)" );

        public CDataContentHandler( OutputStream output, OutputFormat format ) {
            super(output,format);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String input = new String(ch, start, length);
            Matcher matcher = XML_CHARS.matcher(input);
            boolean useCData = matcher.find();
            String cdataContent = null;
            char[] chars = input.toCharArray();
            if (useCData) {
                super.startCDATA();
                cdataContent = matcher.group();
                chars = cdataContent.toCharArray();
            }
            super.characters(chars, start, chars.length);
            if (useCData) {
                super.endCDATA();
            }
        }

    }



}
