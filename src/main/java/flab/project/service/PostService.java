package flab.project.service;

import flab.project.common.FileStorage.FileStorage;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.enums.FileType;
import flab.project.mapper.HashTagMapper;
import flab.project.mapper.ImageMapper;
import flab.project.mapper.PostHashTagMapper;
import flab.project.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;

    public void addBasicPost(BasicPost basicPost) {
        int NumberOfAffectedRow = postMapper.addBasicPost(basicPost);

        if (NumberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }
}
