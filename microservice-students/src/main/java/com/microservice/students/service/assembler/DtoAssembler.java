package com.microservice.students.service.assembler;

import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoAssembler {

    private final ModelMapper modelMapper;

    public StudentResponse toDto(Student student){
        return modelMapper.map(student, StudentResponse.class);
    }

    public Student toEntity(StudentRequest studentRequest){
        return modelMapper.map(studentRequest, Student.class);
    }
}
