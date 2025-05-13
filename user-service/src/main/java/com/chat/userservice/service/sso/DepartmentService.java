package com.chat.userservice.service.sso;


import com.chat.userservice.entity.sso.DepartmentEntity;
import com.chat.userservice.repository.sso.DepartmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepo;

    @Transactional
    public DepartmentEntity create(JSONObject department, DepartmentEntity parentDepartment) {
        var departmentEntity = new DepartmentEntity();
        departmentEntity.setNameUz(department.getString("nameUz"));
        departmentEntity.setNameCr(department.getString("nameCr"));
        departmentEntity.setNameRu(department.getString("nameCr"));
        departmentEntity.setNameQr(department.getString("nameQr"));
        departmentEntity.setCode(department.getString("code"));
        departmentEntity.setParent(parentDepartment);
        departmentRepo.save(departmentEntity);
        return departmentEntity;
    }

}
