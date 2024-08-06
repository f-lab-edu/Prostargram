package flab.project.domain.user.service;

import flab.project.domain.user.model.Icon;
import flab.project.domain.user.model.SocialAccount;
import flab.project.domain.user.mapper.IconMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IconService {

    private static final long DEFAULT_ICON_ID = 1L;

    private final IconMapper iconMapper;

    public void setIconId(SocialAccount socialAccount) {
        String domain = socialAccount.getDomain();

        long iconId = getIconIdByDomain(domain);

        socialAccount.setIconId(iconId);
    }

    private long getIconIdByDomain(String domain) {
        Icon icon = iconMapper.findByDomain(domain);

        if (icon == null) {
            return DEFAULT_ICON_ID;
        }

        return icon.getIconId();
    }
}