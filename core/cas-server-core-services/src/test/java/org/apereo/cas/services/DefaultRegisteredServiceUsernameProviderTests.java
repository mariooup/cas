package org.apereo.cas.services;

import org.apereo.cas.authentication.principal.Principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Misagh Moayyed
 * @since 4.1.0
 */
public class DefaultRegisteredServiceUsernameProviderTests {
    private static final File JSON_FILE = new File(FileUtils.getTempDirectoryPath(), "defaultRegisteredServiceUsernameProvider.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void verifyRegServiceUsernameUpper() {
        val provider = new DefaultRegisteredServiceUsernameProvider();
        provider.setCanonicalizationMode(CaseCanonicalizationMode.UPPER.name());
        val principal = mock(Principal.class);
        when(principal.getId()).thenReturn("id");
        val id = provider.resolveUsername(principal, RegisteredServiceTestUtils.getService(),
            RegisteredServiceTestUtils.getRegisteredService("usernameAttributeProviderService"));
        assertEquals(id, principal.getId().toUpperCase());
    }

    @Test
    public void verifyRegServiceUsername() {
        val provider = new DefaultRegisteredServiceUsernameProvider();

        val principal = mock(Principal.class);
        when(principal.getId()).thenReturn("id");
        val id = provider.resolveUsername(principal, RegisteredServiceTestUtils.getService(),
            RegisteredServiceTestUtils.getRegisteredService("id"));
        assertEquals(id, principal.getId());
    }

    @Test
    public void verifyEquality() {
        val provider =
            new DefaultRegisteredServiceUsernameProvider();

        val provider2 =
            new DefaultRegisteredServiceUsernameProvider();

        assertEquals(provider, provider2);
    }

    @Test
    public void verifySerializeADefaultRegisteredServiceUsernameProviderToJson() throws IOException {
        val providerWritten = new DefaultRegisteredServiceUsernameProvider();

        MAPPER.writeValue(JSON_FILE, providerWritten);

        val providerRead = MAPPER.readValue(JSON_FILE, DefaultRegisteredServiceUsernameProvider.class);

        assertEquals(providerWritten, providerRead);
    }
}
