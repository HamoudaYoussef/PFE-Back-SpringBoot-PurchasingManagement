package biz.picosoft.demo.config;

import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component
public class JWTAutorizationFilter extends OncePerRequestFilter {

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)

            throws ServletException, IOException {

        String securityKey = BeanUtil.getBean(Environment.class).getProperty("spring.security.key");


        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,PUT,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers",
                "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Contol-Request-Headers,authorization");
        response.addHeader("Access-Control-Expose-Headers",
                "Access-Controle-Allow-Origin, Access-Controle-Allow-Credentials, authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        }


        String jwt = request.getHeader(SecurityConstants.HEADER_STRING_AUTHORIZATION);
        String username = "system";
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (jwt == null) {
            String api = request.getHeader(SecurityConstants.HEADER_API_STRING);
            if (api == null) {
                chain.doFilter(request, response);
                return;
            }
            else {
                jwt=BeanUtil.getBean(KernelService.class).getToken(api);
                System.out.println("MM Token//"+jwt+"From apikey"+api);
            }

        }
        else if (!jwt.startsWith(SecurityConstants.TOCKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        } else {
            Claims claims = Jwts.parser().setSigningKey(securityKey)
                    .parseClaimsJws(jwt.replace(SecurityConstants.TOCKEN_PREFIX, "")).getBody();

            username = claims.getSubject();

            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

            roles.forEach((r -> {
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
            }));
            authorities.add(new SimpleGrantedAuthority("Admin_Acl"));
        }
        UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(username, null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        chain.doFilter(request, response);

    }


}