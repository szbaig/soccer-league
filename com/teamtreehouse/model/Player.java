package com.teamtreehouse.model;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
  private static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;
  private int heightInInches;
  private boolean previousExperience;
  private boolean onTeam;

  public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.heightInInches = heightInInches;
    this.previousExperience = previousExperience;
    this.onTeam = false;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getHeightInInches() {
    return heightInInches;
  }

  public boolean isPreviousExperience() {
    return previousExperience;
  }

  public boolean isOnTeam() {
    return onTeam;
  }

  public void setOnTeam(boolean onTeam) {
    this.onTeam = onTeam;
  }

  public String getHeightInInchesText() {
    return String.format("%s inches", this.heightInInches);
  }

  @Override
  public int compareTo(Player other) {
    // We always want to sort by last name then first name
    if(equals(other)) {
      return 0;
    }
    int lastNameCmp = this.lastName.compareTo(other.lastName);
    if(lastNameCmp == 0) {
      return this.firstName.compareTo(other.firstName);
    }
    return lastNameCmp;
  }

  @Override
  public String toString() {
    return String.format(
            "%s %s (%s inches - %s) %n",
            this.getFirstName(),
            this.getLastName(),
            this.getHeightInInches(),
            this.isPreviousExperience() ? "expereinced" : "inexperienced"
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Player)) return false;

    Player player = (Player) o;

    if (heightInInches != player.heightInInches) return false;
    if (previousExperience != player.previousExperience) return false;
    if (!firstName.equals(player.firstName)) return false;
    return lastName.equals(player.lastName);

  }

  @Override
  public int hashCode() {
    int result = firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + heightInInches;
    result = 31 * result + (previousExperience ? 1 : 0);
    return result;
  }
}
