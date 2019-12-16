package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootApplication
//@MapperScan(value="com.example.demo.mapper")
public class DemoApplication implements CommandLineRunner {
    //    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        jdk();
//        Spring();
//        jdbcTemplate.update(insert("exception","Throwable","java.lang","","","JDK1.0"));
//        String UU = "https://docs.oracle.com/javase/8/docs/api/";
//        String s=restTemplate.getForEntity(UU + "allclasses-frame.html", String.class).getBody();
//        Document document=Jsoup.parse(s);
//        Elements li=document.select("[class=indexContainer]").select("li");
//        li.stream().filter(element -> element.text().endsWith("Error") || element.text().endsWith("Exception")).forEach(element -> {
//            String kk=restTemplate.getForEntity(UU + element.select("a").attr("href"), String.class).getBody();
//            Document doc=Jsoup.parse(kk);
//            Element block=doc.select("[class=block]").first();
//            String desc=block.html();
//            String pclz=doc.select("pre").first().text();
//            String since = "";
//            Element ele=block.nextElementSibling();
//            if (!Objects.isNull(ele)&&!Objects.isNull(ele.select("dd"))&&!Objects.isNull(ele.select("dd").first())) {
//                since=ele.select("dd").first().text();
//            }
//            if (since.length()>10){
//                since = "";
//            }
//            String sql = insert(
//                "exception",
//                element.text(),
//                element.select("a").attr("title").replace("class in ", "").replace("interface in ","").trim(),
//                pclz.substring(pclz.indexOf("extends")+ "extends".length()).trim(),
//                desc.replaceAll("'","\""),
//                since
//            );
//            jdbcTemplate.update(sql);
//        });
//        System.out.println("oooooooook!!!!");
    }

    private void jdk(){
        String UU = "https://docs.oracle.com/javase/8/docs/api/";
        String s=restTemplate.getForEntity(UU + "allclasses-frame.html", String.class).getBody();
        Document document=Jsoup.parse(s);
        Elements li=document.select("[class=indexContainer]").select("li");
        li.stream().forEach(element -> {
            String pck = element.select("a").attr("title").replace("class in ", "").replace("interface in ", "").trim();
            if(pck.startsWith("java.io")
                    ||pck.startsWith("java.lang")
                    ||pck.startsWith("java.nio")
                    ||pck.startsWith("java.rmi")
                    ||pck.startsWith("java.security")
                    ||pck.startsWith("java.sql")
                    ||pck.startsWith("java.text")
                    ||pck.startsWith("java.time")
                    ||pck.startsWith("java.util")
                    ||pck.startsWith("java.math")
                    ||pck.startsWith("java.net")) {
                String kk=restTemplate.getForEntity(UU + element.select("a").attr("href"), String.class).getBody();
                Document doc=Jsoup.parse(kk);
                Element block=doc.select("[class=block]").first();
                if (block==null){
                    System.out.println(element.text());
                } else {
                    String desc=block.html();
                    String pclz=doc.select("pre").first().text();
                    String since="";
                    Element ele=block.nextElementSibling();
                    if (!Objects.isNull(ele) && !Objects.isNull(ele.select("dd")) && !Objects.isNull(ele.select("dd").first())) {
                        since=ele.select("dd").first().text();
                    }
                    if (since.length() > 10) {
                        since="";
                    }

                    String sql=insert(
                        "jdk",
                        element.text(),
                        pck,
                        pclz.substring(pclz.indexOf("extends") + "extends".length()).trim(),
                        desc.replaceAll("'", "\""),
                        since
                    );
                    try {
                        jdbcTemplate.update(sql);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println("oooooooook!!!!");
    }

    private void Spring(){
        String UU = "https://docs.spring.io/spring/docs/5.2.2.RELEASE/javadoc-api/";
        String s=restTemplate.getForEntity(UU + "allclasses-frame.html", String.class).getBody();
        Document document=Jsoup.parse(s);
        Elements li=document.select("[class=indexContainer]").select("li");
        li.stream().forEach(element -> {
            String kk=restTemplate.getForEntity(UU + element.select("a").attr("href"), String.class).getBody();
            Document doc=Jsoup.parse(kk);
            Element block=doc.select("[class=block]").first();
            if (block==null){
                System.out.println(element.text());
            } else {
                String desc=block.html();
                String pclz=doc.select("pre").first().text();
                String since = "";
                Element ele=block.nextElementSibling();
                if (!Objects.isNull(ele)&&!Objects.isNull(ele.select("dd"))&&!Objects.isNull(ele.select("dd").first())) {
                    since=ele.select("dd").first().text();
                }
                if (since.length()>10){
                    since = "";
                }
                String sql = insert(
                        "spring",
                        element.text(),
                        element.select("a").attr("title").replace("class in ", "").replace("interface in ","").trim(),
                        pclz,
                        desc.replaceAll("'","\""),
                        since
                );
                jdbcTemplate.update(sql);
            }
        });
        System.out.println("oooooooook!!!!");
    }

    private String insert(String tb,String clz,String pa,String pclz, String desc,String since){
        StringBuilder stringBuilder = new StringBuilder("Insert into "+tb+"(clz,package,pclz,desc_en,version) values(");
        stringBuilder.append("'"+clz+"',");
        stringBuilder.append("'"+pa+"',");
        stringBuilder.append("'"+pclz+"',");
        stringBuilder.append("'"+desc+"',");
        stringBuilder.append("'"+since+"'");
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    private String deal(String s) {
        String s1=s
//                .replaceAll("`test`.", "")
                .replaceAll("`aid`,", "");
        int i=s1.indexOf(") VALUES (");
        String s2=s1.substring(i);
        String[] split=s2.split(",");
        String s3="";
        for (int j=1; j < split.length; j++) {
            s3+="," + split[j].trim();
        }
        return s1.substring(0, i) + ") VALUES (" + s3.substring(1);

    }

    private String fy(String s) {


        Map<String,String> map =new HashMap<>();
        map.put("q",s);
        map.put("f","en");
        map.put("t","zh");
        map.put("a","20191215000366322");
        map.put("s","1435660288");
        map.put("g","");
        String body=restTemplate.getForEntity("http://api.fanyi.baidu.com/api/trans/vip/translate?q={q}&from={f}&to={t}&appid={a}&salt={s}&sign={g}", String.class, map).getBody();
        Document document=Jsoup.parse(body);
        return "";
    }
}
