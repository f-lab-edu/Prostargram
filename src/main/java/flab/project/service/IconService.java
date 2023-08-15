package flab.project.service;

import flab.project.data.dto.model.SocialAccount;
import flab.project.mapper.IconMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IconService {
    private final IconMapper iconMapper;

    public void setIconId(SocialAccount socialAccount) {
        String domain = socialAccount.getDomain();
        Long iconId = iconMapper.findByDomain(domain);
        socialAccount.setIconId(iconId);
    }
}
