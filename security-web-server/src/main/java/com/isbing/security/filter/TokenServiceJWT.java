//package com.isbing.security.filter;
//
//import com.isbing.security.bean.CurrentUser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.core.token.Token;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class TokenServiceJWT{
//
//	@Resource
//	private StringRedisTemplate stringRedisTemplate;
//
//	/**
//	 * 私钥
//	 */
//	@Value("${token.jwtSecret}")
//	private String jwtSecret;
//
//	private static Key KEY = null;
//	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
//
//	public String saveToken(CurrentUser loginUser) {
//		loginUser.setToken(UUID.randomUUID().toString());
//		cacheLoginUser(loginUser);
//		String jwtToken = createJWTToken(loginUser);
//		//return new Token(jwtToken, loginUser.getLoginTime());
//		return jwtToken;
//	}
//
//	/**
//	 * 生成jwt
//	 *
//	 * @param loginUser
//	 * @return
//	 */
//	private String createJWTToken(CurrentUser loginUser) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户
//		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
//				.compact();
//		return jwtToken;
//	}
//
//	private void cacheLoginUser(CurrentUser loginUser) {
//		//loginUser.setLoginTime(System.currentTimeMillis());
//		//loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
//		// 根据uuid将loginUser缓存
//		stringRedisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, 1111, TimeUnit.SECONDS);
//	}
//
//	/**
//	 * 更新缓存的用户信息
//	 */
//	public void refresh(CurrentUser loginUser) {
//		cacheLoginUser(loginUser);
//	}
//
//	public CurrentUser getLoginUser(String jwtToken) {
//		String uuid = getUUIDFromJWT(jwtToken);
//		if (uuid != null) {
//			return stringRedisTemplate.boundValueOps(getTokenKey(uuid)).get();
//		}
//
//		return null;
//	}
//
//
//	private String getTokenKey(String uuid) {
//		return "tokens:" + uuid;
//	}
//
//	private Key getKeyInstance() {
//		if (KEY == null) {
//			synchronized (TokenServiceJWT.class) {
//				if (KEY == null) {// 双重锁
//					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
//					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
//				}
//			}
//		}
//
//		return KEY;
//	}
//
//	private String getUUIDFromJWT(String jwtToken) {
//		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
//			return null;
//		}
//
//		try {
//			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
//			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
//		}  catch (Exception e) {
//
//		}
//
//		return null;
//	}
//}
