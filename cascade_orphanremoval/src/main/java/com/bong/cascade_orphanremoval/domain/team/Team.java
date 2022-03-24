package com.bong.cascade_orphanremoval.domain.team;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "name", length = 50)
  private String name;

//  @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, orphanRemoval = true)
//  private List<Member> members = new ArrayList<>();

  public Team(String name) {
    this.name = name;
  }

//  public void addMember(Member member) {
//    members.add(member);
//    member.setTeam(this);
//  }
//
//  public void remove(Member member) {
//    members.remove(member);
//    member.setTeam(null);
//  }

}
