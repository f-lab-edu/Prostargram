package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PublicScope;
import flab.project.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SettingService {

    private final SettingMapper settingMapper;

    public SuccessResponse updateUserPublicScope(long userId, PublicScope publicScope) {

        int numberOfAffectedRow = settingMapper.updateUserPublicScope(userId, publicScope);

        if (numberOfAffectedRow != 1) {
            throw new RuntimeException();
        }

        return new SuccessResponse();
    }
}
