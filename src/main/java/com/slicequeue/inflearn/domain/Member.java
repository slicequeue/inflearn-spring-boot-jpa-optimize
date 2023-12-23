package com.slicequeue.inflearn.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // 엔티티를 API 요청 인자에 활용하게 되는 케이스에는...
    @NotEmpty(message = "name must not be empty.") // 프레젠테이션(화면)에 대한 검증 로직이 엔티티에 들어가게됨...
    private String name; // name 이름 바꾸면 API 엔티티에 메세지로 사용하기에 모든 곳에서 문제가 발생함
    // 따라서 별도의 DTO 만들어서 맵핑하여 처리

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
