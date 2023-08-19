package flab.project.service;

import flab.project.data.dto.model.SocialAccount;
import flab.project.mapper.IconMapper;
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

        Long iconId = iconMapper.findByDomain(domain);

        //Service와 Mapper사이에 클래스를 두고, iconId를 분기문으로 가져오게 하고싶은데..
        if (iconId == null) {
            iconId = DEFAULT_ICON_ID;
        }

        return iconId;
    }
}
