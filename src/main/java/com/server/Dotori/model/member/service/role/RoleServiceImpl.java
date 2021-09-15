package com.server.Dotori.model.member.service.role;

import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final CurrentUserUtil currentUserUtil;

    /**
     * 현재 로그인된 유저의 권한을 조회하는 서비스로직 (모든 유저 사용가능)
     * @return List - Role (ROLE_ADMIN | ROLE_MEMBER | ROLE_COUNCILLOR | ROLE_DEVELOPER)
     */
    @Override
    public List<Role> getCurrentRole() {
        return currentUserUtil.getCurrentUser().getRoles();
    }
}
