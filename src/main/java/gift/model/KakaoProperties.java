package gift.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private final String clientId;
    private final String redirectUri;
    private final String kakaoAuthUrl;
    private final String userInfoUrl;

    public KakaoProperties(String clientId, String redirectUri, String kakaoAuthUrl,String userInfoUrl) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.kakaoAuthUrl = kakaoAuthUrl;
        this.userInfoUrl = userInfoUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getKakaoAuthUrl() {
        return kakaoAuthUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
