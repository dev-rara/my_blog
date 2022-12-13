package com.rara.my_blog.util;

import com.rara.my_blog.entity.User;
import com.rara.my_blog.exception.CustomException;
import com.rara.my_blog.exception.ErrorCode;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;

	public User getUserInfo(HttpServletRequest httpServletRequest) {
		String token = jwtUtil.resolveToken(httpServletRequest);
		Claims claims;

		//유효한 토큰일 경우 수정 가능
		if (token != null) {
			if (jwtUtil.validateToken(token)) {
				// 토큰에서 사용자 정보 가져오기
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new CustomException(ErrorCode.INVALID_TOKEN);
			}

			// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
			return userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new CustomException(ErrorCode.USER_NOT_FOUND)
			);
		} else {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}
}
