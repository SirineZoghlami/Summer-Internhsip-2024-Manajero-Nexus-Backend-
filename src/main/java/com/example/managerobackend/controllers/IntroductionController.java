package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Introduction;
import com.example.managerobackend.services.IntroductionService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/introductions")
public class IntroductionController {

    private static final Logger logger = Logger.getLogger(IntroductionController.class.getName());

    @Autowired
    private IntroductionService service;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @GetMapping
    public List<Introduction> getAll() {
        logger.log(Level.INFO, "Fetching all introductions");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Introduction> getById(@PathVariable String id) {
        logger.log(Level.INFO, "Fetching introduction with ID: {0}", id);
        Introduction introduction = service.findById(id);
        return introduction != null ?
                ResponseEntity.ok(introduction) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Introduction> createWithImage(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("content") String content) throws IOException {
        logger.log(Level.INFO, "Creating introduction with image");
        Introduction introduction = new Introduction();
        introduction.setContent(content);

        if (!file.isEmpty()) {
            introduction.setImageUrl(uploadImage(file));
        }

        Introduction savedIntroduction = service.save(introduction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIntroduction);
    }

    @PutMapping("/{id}/upload")
    public ResponseEntity<Introduction> updateWithImage(@PathVariable String id,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("content") String content) throws IOException {
        logger.log(Level.INFO, "Updating introduction with ID: {0} and image", id);
        Introduction introduction = service.findById(id);
        if (introduction == null) {
            logger.log(Level.WARNING, "Introduction with ID {0} not found", id);
            return ResponseEntity.notFound().build();
        }

        introduction.setContent(content);

        if (!file.isEmpty()) {
            introduction.setImageUrl(uploadImage(file));
        }

        Introduction updatedIntroduction = service.save(introduction);
        return ResponseEntity.ok(updatedIntroduction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        logger.log(Level.INFO, "Deleting introduction with ID: {0}", id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/images/{fileId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileId) {
        logger.log(Level.INFO, "Fetching image with ID: {0}", fileId);

        GridFsResource resource = gridFsTemplate.getResource(String.valueOf(new ObjectId(fileId)));
        if (resource != null) {
            try {
                byte[] data = new byte[(int) resource.contentLength()];
                resource.getInputStream().read(data);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate content type

                return new ResponseEntity<>(data, headers, HttpStatus.OK);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error fetching image with ID: {0}", fileId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            logger.log(Level.WARNING, "Image with ID {0} not found", fileId);
            return ResponseEntity.notFound().build();
        }
    }

    private String uploadImage(MultipartFile file) throws IOException {
        logger.log(Level.INFO, "Uploading image");
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        try (InputStream inputStream = file.getInputStream()) {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();

            // Store file to MongoDB using GridFS
            ObjectId fileId = gridFsTemplate.store(inputStream, fileName, contentType);

            // Return the URL to access the uploaded file
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/introductions/images/")
                    .path(fileId.toHexString()) // Using toHexString to get ObjectId as string
                    .toUriString();
        }
    }
}
