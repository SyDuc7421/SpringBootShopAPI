package com.example.SpringBoot.service.image;

import com.example.SpringBoot.dto.ImageDto;
import com.example.SpringBoot.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService  {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long id);
}
