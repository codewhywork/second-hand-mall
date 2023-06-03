package com.bistu.why.product;


import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RunWith(Runner.class)
@SpringBootTest(classes = ProductApplication.class)
public class ProductTest {
    @Value("${file.upload}")
    public String path;

    @PostMapping("/test")
    public void test(MultipartFile file, HttpServletRequest request) throws IOException {
        //获取文件名
        String oldName = file.getOriginalFilename();
        int lastIndexOf = oldName.lastIndexOf(".");
        String suffix = oldName.substring(lastIndexOf + 1);
        String newName = UUID.randomUUID().toString() + "." + suffix + oldName.substring(lastIndexOf + 1);
        File file1 = new File(path, newName);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        file.transferTo(file1);
    }
}
