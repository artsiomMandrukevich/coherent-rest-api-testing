package HTTP;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import static HTTP.HTTPVariables.*;

public class HTTPBasicAuthClient extends HTTPClient {

    public CloseableHttpClient getHttpClientWithBasicAuthCredentials() {
        return HttpClientBuilder.create().setDefaultCredentialsProvider(getBasiAuthCredentials()).build();
    }

    private BasicCredentialsProvider getBasiAuthCredentials() {
        final HttpHost targetHost = new HttpHost(HOSTNAME, PORT, SCHEME);
        final BasicCredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope authScope = new AuthScope(targetHost);
        provider.setCredentials(authScope, new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS_ARRAY));
        return provider;
    }

}
