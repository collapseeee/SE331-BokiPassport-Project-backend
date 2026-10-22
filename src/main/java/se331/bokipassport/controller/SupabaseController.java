package se331.bokipassport.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import se331.bokipassport.util.LabMapper;
import se331.bokipassport.util.StorageFileDto;
import se331.bokipassport.util.SupabaseStorageService;

@Controller
@RequiredArgsConstructor
public class SupabaseController {
    final SupabaseStorageService supabaseStorageService;

    @PostMapping("/uploadImage")
    public ResponseEntity<StorageFileDto> uploadImage(@RequestParam("image")MultipartFile image) {
        try {
            StorageFileDto imageUrl = supabaseStorageService.uploadImage(image);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(LabMapper.INSTANCE.getStorageFileDto("Error uploading image: " + e.getMessage()));
        }
    }

    @GetMapping("/presignedUrl")
    public ResponseEntity<String> getPresignedUrl(@RequestParam("fileName") String fileName) {
        try {
            String presignedUrl = supabaseStorageService.getPresignedUrl(fileName);
            return ResponseEntity.ok(presignedUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating presigned url: " + e.getMessage());
        }
    }
}
