package com.imo.backend.modules.user;

import com.imo.backend.modules.base.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("users")
@Data
public class User extends Entity {

  private String name;

  private String email;

  private String password;

  private Boolean isConfirmed;

  private String profilePicturePath;

  public User(String name, String email, String password, Boolean isConfirmed) {
    this.setName(name);
    this.email = email;
    this.password = password;
    this.isConfirmed = isConfirmed;
  }

  public void setName(String name) {
    if (name.length() < 30) {
      this.name = name;
      return;
    }

    var oldName = name;
    var splitName = oldName.split(" ");
    var formattedNamePt1 = splitName[0] + " ";
    var formattedNamePt2 = "";

    var potentialPrepositionInName = splitName[splitName.length - 2];
    if (potentialPrepositionInName.toLowerCase().matches("^(de|do|da)$")) {
      formattedNamePt2 = potentialPrepositionInName + " ";
    }

    var formattedNamePt3 = splitName[splitName.length - 1];

    this.name = formattedNamePt1.toUpperCase()
                + formattedNamePt2.toUpperCase()
                + formattedNamePt3.toUpperCase();
  }
}