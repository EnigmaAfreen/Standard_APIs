package com.airtel.buyer.airtelboe.user;


import com.airtel.buyer.airtelboe.model.Role;
import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import com.airtel.buyer.airtelboe.repository.supplier.BtvlEmpRoleMappingTblRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    BtvlEmpRoleMappingTblRepository btvlEmpRoleMappingTblRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {

        List<BTVL_EMP_ROLE_MAPPING_TBL> userList = this.btvlEmpRoleMappingTblRepository.findByEmpRoleIdNotAndEmpOlmIdIgnoreCase(-1, username);

        if (userList != null && !userList.isEmpty()) {

            List<Integer> roles = new ArrayList<>();

            for (BTVL_EMP_ROLE_MAPPING_TBL obj : userList) {
                roles.add(obj.getEmpRoleId());
            }

            BTVL_EMP_ROLE_MAPPING_TBL bTVL_EMP_ROLE_MAPPING_TBL = userList.get(0);
            return getCustomUserFromBtvlUser(bTVL_EMP_ROLE_MAPPING_TBL, username, roles);
        }

        return null;
    }

    private UserDetailsImpl getCustomUserFromBtvlUser(BTVL_EMP_ROLE_MAPPING_TBL bTVL_EMP_ROLE_MAPPING_TBL, String username, List<Integer> roles) {

        UserDetailsImpl user = new UserDetailsImpl();
        user.setUsername(username);
//        user.setPassword(bTVL_PARTNER_USER_TBL.getPasswd());
        user.setEmail(bTVL_EMP_ROLE_MAPPING_TBL.getEmpMail());
//        List<Role> rolesList = new ArrayList<>();
//        rolesList.add(Role.values()[bTVL_EMP_ROLE_MAPPING_TBL.getEmpRoleId().intValue() - 1]);
//        user.setRoles(rolesList);

        List<Role> rolesList = new ArrayList<>();
        for (Integer role : roles) {
            rolesList.add(Role.values()[role - 1]);
        }
        user.setRoles(rolesList);

        return user;
    }

}