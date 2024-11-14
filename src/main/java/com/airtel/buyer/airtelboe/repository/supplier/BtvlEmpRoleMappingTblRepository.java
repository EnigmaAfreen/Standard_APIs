package com.airtel.buyer.airtelboe.repository.supplier;

import com.airtel.buyer.airtelboe.model.supplier.BTVL_EMP_ROLE_MAPPING_TBL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BtvlEmpRoleMappingTblRepository extends JpaRepository<BTVL_EMP_ROLE_MAPPING_TBL, BigDecimal> {

    List<BTVL_EMP_ROLE_MAPPING_TBL> findByEmpOlmIdAndEmpRoleIdAllIgnoreCase(String empOlmId, Integer empRoleId);

    List<BTVL_EMP_ROLE_MAPPING_TBL> findByEmpRoleId(Integer empRoleId);

    List<BTVL_EMP_ROLE_MAPPING_TBL> findByEmpRoleIdNotAndEmpOlmIdIgnoreCase(Integer empRoleId, String empOlmId);

    List<BTVL_EMP_ROLE_MAPPING_TBL> findByEmpRoleIdInAndLob(List<Integer> empRoleIds, String lob);
}
