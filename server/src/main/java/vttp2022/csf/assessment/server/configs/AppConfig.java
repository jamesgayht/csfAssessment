package vttp2022.csf.assessment.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
    
    @Value("${REDISHOST}")
	private String redisHost;
	@Value("${REDISPORT}")
	private int redisPort;
	@Value("${REDISUSER}")
	private String redisUser;
	@Value("${REDISPASSWORD}")
	private String redisPassword;

	@Value("${spring.mongo.url}")
	private String mongoUrl;

	@Value("${spaces.secret.key}")
	private String spacesSecretKey;
	@Value("${spaces.access.key}")
	private String spacesAccessKey;

	@Value("${spaces.endpoint.url}")
	private String spacesEndpointUrl;

	@Value("${spaces.endpoint.region}")
	private String spacesRegion;

	@Bean
    @Scope("singleton")
	public RedisTemplate<String, String> createRedisTemplate() {
		final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setUsername(redisUser);
        config.setPassword(redisPassword);

		// config.setDatabase(redisDatabase);

		final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder()
				.build();
		final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
		jedisFac.afterPropertiesSet();

		final RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisFac);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		return template;
	}

	@Bean
	public MongoTemplate createMongoTemplate() {
		MongoClient client = MongoClients.create(mongoUrl);
		return new MongoTemplate(client, "csfassessment");
	}

	@Bean 
	public AmazonS3 createS3Client() {
		BasicAWSCredentials cred = new BasicAWSCredentials(spacesAccessKey, spacesSecretKey);
		EndpointConfiguration epConfig = new EndpointConfiguration(spacesEndpointUrl, spacesRegion);

		return AmazonS3ClientBuilder.standard()
				.withEndpointConfiguration(epConfig)
				.withCredentials(new AWSStaticCredentialsProvider(cred))
				.build();
	}

}

