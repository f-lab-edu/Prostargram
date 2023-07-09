package flab.project.data.dto.response;

import flab.project.data.dto.common.CommonProfileInfo;
import flab.project.data.dto.common.HashTag;

import java.util.List;

public class UpdateProfileDTO extends CommonProfileInfo {
    private List<HashTag> hashTagList;
}
