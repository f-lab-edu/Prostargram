package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.ScreenMode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    /*
     * 2023.08.28 - 정민욱
     * Chrome은 cookie의 최대 만료 기한을 400일로 강제 하고 있으며, 그 이상의 만료 기한을 원할 시
     * 갱신 하는 형태로 구현할 것을 강제 함.
     *
     * https://developer.chrome.com/blog/cookie-max-age-expires/
     */
    public SuccessResponse updateScreenMode(long userId, ScreenMode screenMode, HttpServletResponse httpServletResponse) {
        final int COOKIE_MAX_AGE = 60 * 60 * 24 * 400;

        Cookie cookie = new Cookie("screen-mode", screenMode.name());
        cookie.setPath("/users/{userId}/settings");
        cookie.setMaxAge(COOKIE_MAX_AGE);

        httpServletResponse.addCookie(cookie);

        return new SuccessResponse();
    }
}
