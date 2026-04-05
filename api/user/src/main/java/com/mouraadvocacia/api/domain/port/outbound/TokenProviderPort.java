package com.mouraadvocacia.api.domain.port.outbound;

import java.util.UUID;

public interface TokenProviderPort {

    String generateToken(UUID userId, String email);
}
