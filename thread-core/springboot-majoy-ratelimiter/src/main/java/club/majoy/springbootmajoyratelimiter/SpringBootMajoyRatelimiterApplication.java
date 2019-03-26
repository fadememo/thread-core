package club.majoy.springbootmajoyratelimiter;

import club.majoy.springbootmajoyratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * whats wrong with you?
 * registry.addInterceptor(new HandlerInterceptor()).addPathPaterns("/*)
 * 奇怪了,为什么无法拦截到http请求呢?
 */
@SpringBootApplication
@Slf4j
public class SpringBootMajoyRatelimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMajoyRatelimiterApplication.class, args);
	}
	@Resource
	private JedisPool jedisPool;
	//注册拦截器
	public WebMvcConfigurer webMvcConfigurer(){
		//
		return new WebMvcConfigurer() {
			@Override
			public void configurePathMatch(PathMatchConfigurer configurer) {

			}

			@Override
			public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

			}

			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

			}

			@Override
			public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

			}

			@Override
			public void addFormatters(FormatterRegistry registry) {

			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				//
				registry.addInterceptor(new HandlerInterceptor() {

					@Override
					public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
						HandlerMethod handlerMethod = (HandlerMethod) handler;
						//拦截到处理方法 t1 t2
						Method method = handlerMethod.getMethod();
						//获取注解
						RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
						if(null!= rateLimiter){
							int limit = rateLimiter.limit();
							int timeout = rateLimiter.timeout();
							Jedis jedis = jedisPool.getResource();
							String token = RedisRateLimter.getTokenFromBucket(jedis,limit,timeout );

							if(null == token){
								response.sendError(500);
								return false;
							}
							log.info("token:{}",token);
						}
						return true;
					}

					@Override
					public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

					}

					@Override
					public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

					}
				}).addPathPatterns("/*");//拦截/* 方法
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {

			}

			@Override
			public void addCorsMappings(CorsRegistry registry) {

			}

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {

			}

			@Override
			public void configureViewResolvers(ViewResolverRegistry registry) {

			}

			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

			}

			@Override
			public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

			}

			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

			}

			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

			}

			@Override
			public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

			}

			@Override
			public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

			}

			@Override
			public Validator getValidator() {
				return null;
			}

			@Override
			public MessageCodesResolver getMessageCodesResolver() {
				return null;
			}
		};
	}
}
