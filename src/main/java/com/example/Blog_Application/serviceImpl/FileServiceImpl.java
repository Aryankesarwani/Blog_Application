package com.example.Blog_Application.serviceImpl;

import com.example.Blog_Application.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //file name
        String name = file.getOriginalFilename();

        //random name generate file
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
        System.out.println(fileName1);
        //Full path of file
        String filePath = path+ File.separator+fileName1;


        // create folder if not created
        File f = new File(path);
        if(!f.exists()){
            boolean mkd = f.mkdir();
            System.out.println(mkd);
        }



        //file copy/upload
        Files.copy(file.getInputStream(), Paths.get(filePath));




        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);
        return  is;
    }
}
