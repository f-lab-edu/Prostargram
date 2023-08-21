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

        int reflectedRowCount = settingMapper.updateUserPublicScope(userId, publicScope);

        if (reflectedRowCount != 1) {
            throw new RuntimeException();
        }

        return new SuccessResponse();
    }
}
