package flab.project.service;

import flab.project.mapper.PostImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostImageService {

    private final PostImageMapper postImageMapper;

    public void saveAll(long postId, List<String> uploadedFileUrls) {
        int numberOfAffectedRow = postImageMapper.saveAll(postId, uploadedFileUrls);

        if (numberOfAffectedRow != uploadedFileUrls.size()) {
            throw new RuntimeException();
        }
    }
}
