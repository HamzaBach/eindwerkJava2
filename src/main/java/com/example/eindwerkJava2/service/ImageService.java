package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.ImageBlob;
import com.example.eindwerkJava2.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageBlob> getImages(){
        return this.imageRepository.findAll();
    }
}
