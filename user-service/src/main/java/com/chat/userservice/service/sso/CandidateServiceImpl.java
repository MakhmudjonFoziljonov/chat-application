package com.chat.userservice.service.sso;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.entity.sso.CandidateEntity;
import com.chat.userservice.entity.sso.DepartmentEntity;
import com.chat.userservice.entity.sso.RegionEntity;
import com.chat.userservice.entity.sso.StateEntity;
import com.chat.userservice.enums.AppLang;
import com.chat.userservice.repository.sso.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final StateService stateService;
    private final RegionService regionService;
    private final PositionService positionService;
    private final FileInfoServiceImpl fileInfoService;
    private final DepartmentService departmentService;
    private final MilitaryDegreeService militaryDegreeService;
    private final MilitaryCertificateService militaryCertificateService;

    private final CandidateRepository candidateRepository;

    @Override
    public void create(JSONObject jsonObject, RegisterDto registerDto, AppLang lang) throws ParseException {
        JSONObject employee = jsonObject.getJSONObject("employee");

        JSONObject passport = jsonObject.getJSONObject("passport");

        JSONObject militaryCertificate = jsonObject.getJSONObject("militaryCertificate");
        JSONObject state = jsonObject.getJSONObject("birthAddress");
        JSONObject militaryDegree = jsonObject.getJSONObject("militaryDegree");

        JSONObject organizationObject = jsonObject.getJSONObject("organization");
        JSONObject organization = organizationObject.getJSONObject("organization");

        JSONObject departmentObject = jsonObject.getJSONObject("department");
        JSONObject department = departmentObject.getJSONObject("department");

        JSONObject positionObject = jsonObject.getJSONObject("position");
        JSONObject position = positionObject.getJSONObject("position");

        String militaryPhoto = jsonObject.getString("militaryPhoto");


        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        var candidateEntity = new CandidateEntity();

        String lastName = employee.getString("lastNameUz");
        String firstName = employee.getString("firstNameUz");
        String parentName = employee.getString("parentNameUz");

        String lastNameCr = employee.getString("lastNameCr");
        String firstNameCr = employee.getString("firstNameCr");
        String parentNameCr = employee.getString("parentNameCr");
        String pin = employee.getString("pinfl");
        String birthDate = employee.getString("birthDate");
        String passportBeginDate = passport.getString("beginDate");
        String passportEndDate = passport.getString("endDate");


        candidateEntity.setPinfl(pin);
        candidateEntity.setLastName(lastName);
        candidateEntity.setFirstName(firstName);
        candidateEntity.setParentName(parentName);
        candidateEntity.setParentNameCr(lastNameCr);
        candidateEntity.setLastNameCr(firstNameCr);
        candidateEntity.setFirstNameCr(parentNameCr);
        candidateEntity.setFullName(firstName + " " + lastName + " " + parentName);
        candidateEntity.setPhoneNumber(registerDto.getPhoneNumber());
        candidateEntity.setBirthDate(sdf.parse(birthDate));

        candidateEntity.setPassportSeries(passport.getString("series"));
        candidateEntity.setPassportNumber(passport.getString("number"));
        candidateEntity.setPassportGivenBy(passport.getString("givenBy"));
        candidateEntity.setPassportBeginDate(sdf.parse(passportBeginDate));
        candidateEntity.setPassportEndDate(sdf.parse(passportEndDate));
        candidateEntity.setCertificateSeries(militaryCertificate.getString("series"));
        candidateEntity.setCertificateNumber(militaryCertificate.getString("number"));


        DepartmentEntity org = departmentService.create(organization, null);
        departmentService.create(department, org);

        StateEntity state1 = stateService.create(state.getJSONObject("state"));
        RegionEntity region = regionService.create(state.getJSONObject("region"));
        positionService.create(position);
        militaryDegreeService.create(militaryDegree);
        militaryCertificateService.create(militaryCertificate, sdf);


        candidateEntity.setAddress(state.getString("address"));

        String file = fileInfoService.saveFromBase64(militaryPhoto, "user_photos", "user_photo");
        candidateEntity.setUploadPath(file);
        if (file != null) {
            candidateEntity.setPhotoExist(true);
        }
        candidateEntity.setState(state1);
        candidateEntity.setRegion(region);


        candidateRepository.save(candidateEntity);
    }
}
