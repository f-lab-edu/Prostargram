package flab.project.service;

import flab.project.data.SocialAccountsDelta;
import flab.project.data.dto.model.Icon;
import flab.project.data.dto.model.SocialAccounts;
import flab.project.mapper.HyperLinkMapper;
import flab.project.mapper.IconMapper;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class SocialAccountService {

    private final HyperLinkMapper hyperLinkMapper;
    private final IconMapper iconMapper;
    public SocialAccountsDelta getSocialAccountsDelta(
        long userId,
        List<String> receivedSocialAccounts
    ) {
        if (CollectionUtils.isEmpty(receivedSocialAccounts)) {
            return null;
        }

        List<String> existingSocialAccounts = hyperLinkMapper.findAllByUserId(userId);

        SocialAccountsDelta socialAccountsDelta
            = new SocialAccountsDelta(existingSocialAccounts, receivedSocialAccounts);

        return socialAccountsDelta;
    }

    public void updateSocialAccounts(
        long userId,
        SocialAccountsDelta socialAccountsDelta
    ) {
        if (socialAccountsDelta == null) {
            return ;
        }

        if (socialAccountsDelta.hasToAddSocialAccounts()) {
            List<String> toAddSocialAccountUrls = socialAccountsDelta.getToAddSocialAccounts();
            insertSocialAccounts(userId, toAddSocialAccountUrls);
        }

        if (socialAccountsDelta.hasToDeleteSocialAccounts()) {
            List<String> toDeleteSocialAccountUrls = socialAccountsDelta.getToDeleteSocialAccounts();
            deleteSocialAccounts(userId, toDeleteSocialAccountUrls);
        }
    }

    private void insertSocialAccounts(
        long userId,
        List<String> toAddSocialAccountUrls
    ) {
        List<SocialAccounts> toAddSocialAccounts
            = convertSocialAccountUrlToLink(toAddSocialAccountUrls);

        initializeSocialAccounts(toAddSocialAccounts); //메서드 명 수정 필요.

        hyperLinkMapper.insertAll(toAddSocialAccounts, userId);
    }

    private static List<SocialAccounts> convertSocialAccountUrlToLink(
        List<String> socialAccountUrls) {
        return socialAccountUrls.stream()
            .map(SocialAccounts::new)
            .toList();
    }

    private void initializeSocialAccounts(List<SocialAccounts> toAddSocialAccounts) {
        List<Icon> icons = iconMapper.findAll();
        Map<String, Icon> iconMap = makeDomainNameIconMap(icons);

        setIconId(toAddSocialAccounts, iconMap);
    }

    private Map<String, Icon> makeDomainNameIconMap(List<Icon> icons) {
        return icons.stream()
            .collect(Collectors.toMap(
                Icon::getDomain,
                Function.identity()
            ));
    }

    private void setIconId(List<SocialAccounts> toAddSocialAccounts, Map<String, Icon> iconMap) {
        final Long DEFAULT_IMG_ID = 1L; // 미리 지정된 social 주소가 아니면 1로 지정.

        for (SocialAccounts account : toAddSocialAccounts) {
            String domain = account.getDomain();

            if (iconMap.containsKey(domain)) {
                long iconId = iconMap.get(domain).getIconId();
                account.setIconId(iconId);
            } else {
                account.setIconId(DEFAULT_IMG_ID);
            }
        }
    }

    private void deleteSocialAccounts(
        long userId,
        List<String> toDeleteSocialAccountUrls
    ) {
        List<SocialAccounts> toDeleteSocialAccounts = convertSocialAccountUrlToLink(
            toDeleteSocialAccountUrls);
        hyperLinkMapper.deleteAll(toDeleteSocialAccounts, userId);
    }
}
