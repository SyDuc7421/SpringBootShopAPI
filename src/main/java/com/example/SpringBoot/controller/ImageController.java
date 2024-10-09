package com.example.SpringBoot.controller;

import com.example.SpringBoot.dto.ImageDto;
import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Image;
import com.example.SpringBoot.response.APIResponse;
import com.example.SpringBoot.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Upload success!", imageDtos));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse( "Upload failed!", null));
        }

    }

    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws SQLException {
        Image image = imageService.getImageById(id);
        ByteArrayResource resource= new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName()+"\"")
                .body(resource);
    }

    @PutMapping("/image/{id}")
    public ResponseEntity<APIResponse> updateImage(@PathVariable Long id, @RequestBody MultipartFile file) {
            try{
                Image image = imageService.getImageById(id);
                if (image != null) {
                    imageService.updateImage(file, id);
                    return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Update success!", image));
                }
            }
            catch (ResourceNotFoundException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Update failed!", null));
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<APIResponse> deleteImage(@PathVariable Long id) {
        try{
            Image image = imageService.getImageById(id);
            if (image != null) {
                imageService.deleteImageById(id);
                return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Delete success!", image));
            }
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Delete failed!", null));
    }


}
