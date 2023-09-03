package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.Settings;
import flab.project.data.enums.PublicScope;
import flab.project.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SettingService {

    private final SettingMapper settingMapper;

    public SuccessResponse getPersonalSettings(long userId) {
        Settings personalSettings = settingMapper.getPersonalSettingsByUserId(userId);

        if (personalSettings == null) {
            throw new NotExistUserException();
        }

        return new SuccessResponse(personalSettings);
    }

    public SuccessResponse updateUserPublicScope(long userId, PublicScope publicScope) {

        int numberOfAffectedRow = settingMapper.updateUserPublicScope(userId, publicScope);

        if (numberOfAffectedRow == 0) {
            throw new RuntimeException();
        }

        return new SuccessResponse();
    }
}
