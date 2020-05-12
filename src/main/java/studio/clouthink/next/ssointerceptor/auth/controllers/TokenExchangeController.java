package studio.clouthink.next.ssointerceptor.auth.controllers;

import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import studio.clouthin.next.shared.utils.RedisUtils;
import studio.clouthink.next.ssointerceptor.auth.annotations.AnonymousAccess;
import studio.clouthink.next.ssointerceptor.auth.dtos.vo.SecurityProperties;

@RequestMapping("/token")
@RestController
public class TokenExchangeController {


    @Autowired
    private  SecurityProperties properties;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用 jwt 换取一次性临时 key
     *
     * @return
     */
    @PostMapping("/exchangeWithJWT")
    public String exchangeWithJWT() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String tempKey = UUID.fastUUID().toString();
        redisUtils.set(tempKey, authentication.getCredentials().toString(), 20000);
        return tempKey;
    }

    /**
     * 用一次性临时 key 恢复 jwt
     *
     * @return
     */
    @GetMapping("/restoreJWT/{token}")
    @AnonymousAccess
    public String restoreJWT(@PathVariable String token) {
        String jwt = redisUtils.get(token).toString();
        return properties.getTokenStartWith() + token;
    }
}
