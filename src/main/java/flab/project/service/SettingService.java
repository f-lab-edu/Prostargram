package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.Settings;
import flab.project.data.enums.PublicScope;
import flab.project.data.enums.ScreenMode;
import flab.project.mapper.SettingMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SettingService {

    private final SettingMapper settingMapper;

    // Available maximum days are 400 days - ${https://developer.chrome.com/blog/cookie-max-age-expires/}
    public SuccessResponse updateScreenMode(long userId, ScreenMode screenMode, HttpServletResponse httpServletResponse) {
        final int COOKIE_MAX_AGE_400_DAYS = 60 * 60 * 24 * 400;

        Cookie cookie = new Cookie("screen-mode", screenMode.name());
        cookie.setMaxAge(COOKIE_MAX_AGE_400_DAYS);

        httpServletResponse.addCookie(cookie);

        return new SuccessResponse();
    }

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
