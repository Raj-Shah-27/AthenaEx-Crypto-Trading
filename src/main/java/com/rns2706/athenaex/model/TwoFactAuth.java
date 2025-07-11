package com.rns2706.athenaex.model;

import com.rns2706.athenaex.domain.VerificationType;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;

@Data
public class TwoFactAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
