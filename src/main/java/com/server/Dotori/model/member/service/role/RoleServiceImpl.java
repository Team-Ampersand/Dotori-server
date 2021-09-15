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

    @Override
    public List<Role> getCurrentRole() {
        return currentUserUtil.getCurrentUser().getRoles();
    }
}
