package kea.clbo.repository;

import kea.clbo.model.Member;

import java.util.List;
import java.util.Map;

public interface IMemberRepository {
    boolean create(Member member);
    Member read(String email);
    List<Member> readAll();
}
