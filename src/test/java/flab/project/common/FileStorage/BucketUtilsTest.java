package flab.project.common.FileStorage;

import flab.project.domain.file.enums.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BucketUtilsTest {

    @DisplayName("FileType이 PROFILE_IMAGE이면, profileImage를 반환한다.")
    @Test
    void getBaseBucektName_profileImage(){
        // when
        String baseBucketName = BucketUtils.getBaseBucketName(FileType.PROFILE_IMAGE);

        // then
        assertThat(baseBucketName).isEqualTo("profileimage");
    }

    @DisplayName("FileType이 POST_IMAGE이면, postimage를 반환한다.")
    @Test
    void getBaseBucektName_postImage(){
        // when
        String baseBucketName = BucketUtils.getBaseBucketName(FileType.POST_IMAGE);

        // then
        assertThat(baseBucketName).isEqualTo("postimage");
    }
}