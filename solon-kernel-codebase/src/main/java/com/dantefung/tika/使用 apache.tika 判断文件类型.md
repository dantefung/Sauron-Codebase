> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [www.cnblogs.com](https://www.cnblogs.com/Mr-kevin/p/12014611.html)

一. 判断文件类型一般可采用两种方式

　　1. 后缀名判断

 　　　　简单易操作，但无法准确判断类型

　　2. 文件头信息判断

　　　　通常可以判断文件类型，但有些文件类型无法判断（如 word 和 excel 头信息的前几个字节是一样的，无法判断）

　　3. 使用 apache.tika 可轻松解决以上两种方式存在的问题

二. 使用方式

　　1. maven 依赖

```
<dependency>
    <groupId>org.apache.tika</groupId>
    <artifactId>tika-core</artifactId>
    <version>1.22</version>
</dependency>

```

　　2. 具体实现

[![][img-0]; "复制代码")

```
 1 public static String getMimeType(InputStream inputStream){
 2     AutoDetectParser parser = new AutoDetectParser();
 3     parser.setParsers(new HashMap<MediaType, Parser>());
 4 
 5     Metadata metadata = new Metadata();
 6 
 7     try {
 8         parser.parse(inputStream, new DefaultHandler(), metadata, new ParseContext());
 9         inputStream.close();
10     } catch (TikaException | SAXException | IOException e) {
11         e.printStackTrace();
12     }
13 
14     return metadata.get(HttpHeaders.CONTENT_TYPE);
15 }

```

[![][img-1]; "复制代码")

　　3. 常见文件类型

<table><tbody><tr><th>MimeType</th><th>文件类型</th></tr></tbody><tbody><tr><td>application/msword</td><td>word(.doc)</td></tr><tr><td>application/vnd.ms-powerpoint</td><td>powerpoint(.ppt)</td></tr><tr><td>application/vnd.ms-excel</td><td>excel(.xls)</td></tr><tr><td>application/vnd.openxmlformats-officedocument.wordprocessingml.document</td><td>word(.docx)</td></tr><tr><td>application/vnd.openxmlformats-officedocument.presentationml.presentation</td><td>powerpoint(.pptx)</td></tr><tr><td>application/vnd.openxmlformats-officedocument.spreadsheetml.sheet</td><td>excel(.xlsx)</td></tr><tr><td>application/x-rar-compressed</td><td>rar</td></tr><tr><td>application/zip</td><td>zip</td></tr><tr><td>application/pdf</td><td>pdf</td></tr><tr><td>video/*</td><td>视频文件</td></tr><tr><td>image/*</td><td>图片文件</td></tr><tr><td>text/plain</td><td>纯文本</td></tr><tr><td>text/css</td><td>css 文件</td></tr><tr><td>text/html</td><td>html 文件</td></tr><tr><td>text/x-java-source</td><td>java 源代码</td></tr><tr><td>text/x-csrc</td><td>c 源代码</td></tr><tr><td>text/x-c++src</td><td>c++ 源代码</td></tr></tbody></table>

[img-0]:http://common.cnblogs.com/images/copycode.gifjavascript:void0

[img-1]:data: