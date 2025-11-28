package hust.hoangson.clients.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "ecommerce.client")
public class ClientConfigProperties {

    private Boolean enabled = true;
    private String defaultProtocol = "rest";

    private Defaults defaults = new Defaults();
    private ServiceDiscoveryConfig serviceDiscovery = new ServiceDiscoveryConfig();
    private ResilienceConfig resilience = new ResilienceConfig();
    private ObservabilityConfig observability = new ObservabilityConfig();

    private Map<String, ServiceConfig> services;

    @Data
    public static class Defaults {
        private Duration connectTimeout = Duration.ofSeconds(10);
        private Duration readTimeout = Duration.ofSeconds(30);
        private Duration writeTimeout = Duration.ofSeconds(30);
        private Integer maxRetries = 3;
        private Boolean enableMetrics = true;
        private Boolean enableTracing = true;
        private Boolean followRedirects = true;
    }

    @Data
    public static class ServiceDiscoveryConfig {
        private Boolean enabled = false;
        private String type = "consul";
        private String namespace = "default";
        private Map<String, String> metadata;
    }

    @Data
    public static class ResilienceConfig {
        private CircuitBreakerConfig circuitBreaker = new CircuitBreakerConfig();
        private RetryConfig retry = new RetryConfig();
        private BulkheadConfig bulkhead = new BulkheadConfig();
        private RateLimiterConfig rateLimiter = new RateLimiterConfig();

        @Data
        public static class CircuitBreakerConfig {
            private Boolean enabled = true;
            private Integer failureRateThreshold = 50;
            private Integer slowCallRateThreshold = 100;
            private Duration slowCallDurationThreshold = Duration.ofSeconds(60);
            private Integer slidingWindowSize = 100;
            private Integer minimumNumberOfCalls = 20;
            private Duration waitDurationInOpenState = Duration.ofSeconds(60);
        }

        @Data
        public static class RetryConfig {
            private Boolean enabled = true;
            private Integer maxAttempts = 3;
            private Duration waitDuration = Duration.ofMillis(500);
            private Boolean exponentialBackoff = true;
            private Double exponentialBackoffMultiplier = 2.0;
            private Duration maxWaitDuration = Duration.ofSeconds(5);
        }

        @Data
        public static class BulkheadConfig {
            private Boolean enabled = false;
            private Integer maxConcurrentCalls = 25;
            private Duration maxWaitDuration = Duration.ZERO;
        }

        @Data
        public static class RateLimiterConfig {
            private Boolean enabled = false;
            private Integer limitForPeriod = 50;
            private Duration limitRefreshPeriod = Duration.ofSeconds(1);
            private Duration timeoutDuration = Duration.ofSeconds(5);
        }
    }

    @Data
    public static class ObservabilityConfig {
        private Boolean metricsEnabled = true;
        private Boolean tracingEnabled = true;
        private Boolean loggingEnabled = true;
        private String metricsPrefix = "ecommerce.client";
    }

    @Data
    public static class ServiceConfig {
        private String serviceId;
        private String name;
        private String protocol = "rest";
        private String url;
        private String basePath;
        private Boolean enabled = true;

        private RestConfig rest = new RestConfig();
        private GrpcConfig grpc = new GrpcConfig();
        private ResilienceConfig resilience = new ResilienceConfig();

        @Data
        public static class RestConfig {
            private Boolean logRequests = true;
            private Boolean logResponses = true;
            private String logLevel = "FULL";
            private Boolean followRedirects = true;
            private Boolean decode404 = false;
            private Boolean followRedirect = true;
        }

        @Data
        public static class GrpcConfig {
            private Boolean usePlaintext = true;
            private Integer maxInboundMessageSize = 4194304;
            private Integer maxInboundMetadataSize = 8192;
            private Duration keepAliveTime = Duration.ofMinutes(5);
            private Duration keepAliveTimeout = Duration.ofSeconds(20);
            private Boolean keepAliveWithoutCalls = false;
            private String negotiationType = "PLAINTEXT";
            private Boolean enableRetry = true;
            private Integer maxRetryAttempts = 5;
        }
    }
}
