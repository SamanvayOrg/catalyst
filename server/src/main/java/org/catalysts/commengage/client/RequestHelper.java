package org.catalysts.commengage.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class RequestHelper {

    private RequestHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a {@link URI} from the given string representation without any parameters.
     */
    public static URI createUri(String url) {
        return createUri(url, null);
    }

    /**
     * Creates a {@link URI} from the given string representation and with the given parameters.
     */
    public static URI createUri(String url, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance().uri(URI.create(url));

        queryParams.forEach((k, v) -> {
            builder.queryParam(k,
                    UriUtils.encodeQueryParam(String.valueOf(v),
                            StandardCharsets.UTF_8.name()));
        });

        return builder.build(true).toUri();
    }

    public static <E> HttpEntity<E> createEntity(E payload, HttpHeaders headers) {
        return new HttpEntity<>(payload, headers);
    }
}
