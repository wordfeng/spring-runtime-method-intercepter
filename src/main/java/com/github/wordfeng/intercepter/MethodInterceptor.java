package com.github.wordfeng.intercepter;

import com.github.wordfeng.service.TestService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.Callable;

@Slf4j
@Component
public class MethodInterceptor implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始拦截初始化");
        ByteBuddyAgent.install();
        new AgentBuilder.Default()
//                .type(ElementMatchers.any()) // 拦截任意类
                .type(ElementMatchers.isAnnotatedWith(RequestMapping.class))
                .transform((builder, type, classLoader, module) -> {
                    log.info("builder:{}, type:{}, classLoader:{}, module:{}", builder, type, classLoader, module);
                    return builder.method(ElementMatchers.any()) // 拦截任意方法
                            .intercept(MethodDelegation.to(MyInterceptor.class));
                }) // 使用自定义的拦截器
                .type(ElementMatchers.isAnnotatedWith(LogParamsAndReturn.class))
                .transform(((builder, typeDescription, classLoader, javaModule) -> {
                    log.info("注解拦截");
                    return builder.method(ElementMatchers.isAnnotatedWith(LogParamsAndReturn.class)).intercept(MethodDelegation.to(MyInterceptor.class));
                }))
                .installOnByteBuddyAgent();
    }

    @Component
    public static class MyInterceptor {
        public static void intercept(@SuperCall Callable<?> zuper) throws Exception {
            System.out.println("方法拦截前");
            try {
                zuper.call(); // 调用原始方法
            } finally {
                System.out.println("方法拦截后");
            }
        }
    }
}
