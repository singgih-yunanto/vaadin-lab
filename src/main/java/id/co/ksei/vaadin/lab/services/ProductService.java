package id.co.ksei.vaadin.lab.services;

import id.co.ksei.vaadin.lab.model.Category;
import id.co.ksei.vaadin.lab.model.Product;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final WebClient webClient;

    public ProductService() {
        webClient = WebClient.builder()
                .baseUrl("https://dummyjson.com")
                .filter((clientRequest, next) -> {
                    log.info("Request: [{}] {}", clientRequest.method(), clientRequest.url());
                    return next.exchange(clientRequest);
                })
                .build();
    }

    public Mono<ProductsResponse> getProducts(String search, int limit, int offset) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/products")
                            .queryParam("limit", limit)
                            .queryParam("skip", offset);

                    if (StringUtils.isNotBlank(search)) {
                        uriBuilder.path("/search")
                                .queryParam("q", search);
                    }

                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(ProductsResponse.class);
    }

    public Mono<List<Category>> getCategories() {
        return webClient.get()
                .uri("/product/categories")
                .retrieve()
                .bodyToFlux(Category.class)
                .collectList();
    }

    @Data
    public static class ProductsResponse {
        private List<Product> products;
        private Integer total;
        private Integer skip;
        private Integer limit;
    }
}
