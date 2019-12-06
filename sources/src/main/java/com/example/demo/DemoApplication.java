package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class).bannerMode(Banner.Mode.OFF).run(args);
//        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try(BufferedWriter writer=Files.newBufferedWriter(Paths.get("b.sql"));
            BufferedReader reader =Files.newBufferedReader(Paths.get("a.sql"));
        ) {
            reader
                .lines()
                .forEach(s -> {
                    try {
                        writer.write(deal(s));
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
        System.out.println("ok");
    }

    private String deal(String s){
        String s1=s
//                .replaceAll("`test`.", "")
                .replaceAll("`aid`,", "");
        int i=s1.indexOf(") VALUES (");
        String s2=s1.substring(i);
        String[] split=s2.split(",");
        String s3 = "";
        for (int j=1;j<split.length;j++) {
            s3 += "," + split[j].trim();
        }
        return s1.substring(0,i) + ") VALUES (" + s3.substring(1);

    }
}
