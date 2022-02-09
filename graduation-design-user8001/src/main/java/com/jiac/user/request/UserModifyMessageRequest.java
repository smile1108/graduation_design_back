package com.jiac.user.request;

import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import lombok.Data;

/**
 * FileName: UserModifyMessageRequest
 * Author: Jiac
 * Date: 2022/2/5 12:25
 */
@Data
public class UserModifyMessageRequest {
    private String username;
    private String nickname;
    private String school;
    private String college;
    private String specialty;
    private String gender;
    private String resume;

    public static UserModifyMessageRequest of(String username, String nickname, String school, String college,
                                                String specialty, String gender, String resume) {
        if(nickname.length() > 20) {
            throw new MyException(ErrorEnum.NICKNAME_TOO_LENGTH);
        }
        if(school.length() > 20) {
            throw new MyException(ErrorEnum.SCHOOL_TOO_LENGTH);
        }
        if(college.length() > 20) {
            throw new MyException(ErrorEnum.COLLEGE_TOO_LENGTH);
        }
        if(specialty.length() > 20) {
            throw new MyException(ErrorEnum.SPECIALTY_TOO_LENGTH);
        }
        if(!"male".equals(gender) && !"female".equals(gender)) {
            throw new MyException(ErrorEnum.ILLEGAL_GENDER);
        }
        if(resume.length() > 50) {
            throw new MyException(ErrorEnum.RESUME_TOO_LENGTH);
        }

        UserModifyMessageRequest request = new UserModifyMessageRequest();
        request.setUsername(username);
        request.setNickname(nickname);
        request.setSchool(school);
        request.setCollege(college);
        request.setSpecialty(specialty);
        request.setGender(gender);
        request.setResume(resume);

        return request;
    }
}
