package flab.project.service;

import flab.project.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageMapper imageMapper;

    public void saveAll(long userId, List<String> uploadedFileUrls) {
        int NumberOfAffectedRow = imageMapper.saveAll(uploadedFileUrls);

        if (NumberOfAffectedRow != uploadedFileUrls.size()) {
            throw new RuntimeException();
        }
    }
}
