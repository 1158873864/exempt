package njurestaurant.njutakeout.security.jwt;

import io.jsonwebtoken.Claims;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.company.Staff;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    Claims getClaimsFromToken(String token);

    String getUsernameFromToken(String token);

    JwtUser convertUserToJwtUser(User user);

    Date generateExpirationDate(long expiration);

    boolean validateToken(String authToken);

    String generateToken(UserDetails userDetails, long expiration);

//    JwtUser converStaffToJwtUser(Staff staff);
}
