package cn.edu.nbpt.facenet.singin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import cn.edu.nbpt.facenet.singin.entity.User;

public class JwtUtil {
    /**
     * 30秒过期时间，单位毫秒
     */
    private static final long EXPIRE = 60 * 1000 * 30;

    /**
     * 加密密钥
     */
    private static final String SECRET = "pouaownkls";

    /**
     * token前缀，可以不添加，主要用来区分业务
     */
    private static final String TOKEN_PREFIX = "Meeting";

    /**
     * subject，颁发者
     */
    private static final String SUBJECT = "nbpt";


    public static String createToken(User user){
        /**
         * 设置token携带的参数
         */
        String token = Jwts.builder()
                .claim("UID",user.getuId())
                .claim("Phone",user.getPhone())
                .claim("Admin",user.getAdmin())
                .claim("userName",user.getUsername())
                .claim("avatar",user.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
        // TOKEN_PREFIX可以不加，更具业务需求
        token = TOKEN_PREFIX + token;
        return token;
    }

    public static Claims checkTOKEN(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }
}