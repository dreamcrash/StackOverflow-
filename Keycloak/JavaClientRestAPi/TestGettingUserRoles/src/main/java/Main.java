import org.keycloak.OAuth2Constants;
import org.keycloak.TokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.resource.AuthorizationResource;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.*;
import org.keycloak.representations.idm.authorization.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

// Original Question
// https://stackoverflow.com/questions/73671590/how-to-get-the-roles-and-attributes-of-a-keycloak-user/73678225#73678225

public class Main {

    public static final String SERVER_URL = "http://localhost:8080/auth";
    public static final String REALM_NAME = "foo";
    public static final String CLIENT_NAME = "foo";
    public static final String CLIENT_SECRET = "p7GnL5hfqVm0lnkiR7Xxn5y04UiKVrUu";
    public static final String USER_PASSWORD = "john.doe";
    public static final String USERNAME = "john.doe";

    public static final Keycloak keycloak = KeycloakBuilder.builder() //
            .serverUrl(SERVER_URL) //
            .realm("master") //
            .grantType(OAuth2Constants.PASSWORD) //
            .clientId("admin-cli") //
            .username("admin") //
            .password("admin") //
            .build();

    public static void setTestData() {
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(REALM_NAME);
        realm.setEnabled(true);
        RoleRepresentation role = new RoleRepresentation();
        role.setName("TestRealmRole");
        RolesRepresentation roles = new RolesRepresentation();
        roles.setRealm(List.of(role));
        realm.setRoles(roles);

        ClientRepresentation client = new ClientRepresentation();
        client.setName(CLIENT_NAME);
        client.setClientId(CLIENT_NAME);
        client.setBearerOnly(true);
        ResourceServerRepresentation resourceServerRepresentation = new ResourceServerRepresentation();
        resourceServerRepresentation.setPolicyEnforcementMode(PolicyEnforcementMode.DISABLED);
        resourceServerRepresentation.setClientId(CLIENT_NAME);

        ResourceRepresentation resource = new ResourceRepresentation();
        resource.setName("Default Resource");
        resource.setUris(Set.of("/*"));
        resource.setType("urn:foo:resources:default");
        resourceServerRepresentation.setResources(List.of(resource));
        resourceServerRepresentation.setAllowRemoteResourceManagement(true);

        client.setAuthorizationSettings(resourceServerRepresentation);
        client.setAuthorizationServicesEnabled(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setStandardFlowEnabled(false);
        client.setServiceAccountsEnabled(true);
        client.setSecret(CLIENT_SECRET);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(USER_PASSWORD);

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setUsername(USERNAME);
        user.setCredentials(List.of(passwordCred));
        user.setRealmRoles(List.of(role.getName()));
        user.setClientRoles(Map.of("broker", List.of("read-token")));


        realm.setClients(List.of(client));
        realm.setUsers(List.of(user));
        keycloak.realms().create(realm);
    }

    public static void deleteTestData() {
        keycloak.realm(REALM_NAME).remove();
    }

    public static void main(String[] args) {
        setTestData();
        try {
            Configuration configuration = new Configuration();
            configuration.setRealm(REALM_NAME);
            configuration.setResource(CLIENT_NAME);
            configuration.setBearerOnly(Boolean.TRUE);
            configuration.setAuthServerUrl(SERVER_URL);
            configuration.setCredentials(Map.of("secret", CLIENT_SECRET));
            
            AuthzClient authzClient = AuthzClient.create(configuration);
            AuthorizationRequest request = new AuthorizationRequest();

            AuthorizationResource authorization = authzClient.authorization(USERNAME, USER_PASSWORD);
            AuthorizationResponse authorize = authorization.authorize(request);

            String tokenString = authorize.getToken();

            AccessToken token = TokenVerifier.create(tokenString, AccessToken.class).getToken();
            Set<String> realmRoles = token.getRealmAccess().getRoles();
            Map<String, AccessToken.Access> clientRoles = token.getResourceAccess();

            assert realmRoles.contains("TestRealmRole");
            assert clientRoles.containsKey("broker");
            assert clientRoles.get("broker").getRoles().contains("read-token");

            System.out.printf("Realm 'foo' = Roles %s%n", realmRoles);
            clientRoles.forEach(Main::printClientRoles);
        } catch (VerificationException e) {
            e.printStackTrace();
        } finally {
            deleteTestData();
        }
    }

    public static void printClientRoles(String resource, AccessToken.Access access) {
        System.out.printf("Client '%s' = Roles %s%n", resource, access.getRoles());
    }
}
