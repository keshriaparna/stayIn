package com.project.learning.stayIn.dto;

import com.project.learning.stayIn.entity.User;
import com.project.learning.stayIn.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {

  private Long id;
  private User user;
  private String name;
  private Gender gender;
  private Integer age;

}
