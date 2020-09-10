package kea.clbo.repository;

import kea.clbo.model.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository
public class MemberArrayRepository implements IMemberRepository {

    // Storage - Here a Map for fast and easy lookup
    Map<String, Member> members = new HashMap<>();

    @Override
    public boolean create(Member member) {
        // check if key already exists to prevent override of value
        Member mem = members.get(member.getEmail());

        // if a member (email as key) is already in the Map return false, if a new member is created return true
        if(mem == null){ // member does not exists
            members.put(member.getEmail(), member);
            return true;
        }
        return false; // if member already exists
    }

    @Override
    public Member read(String email) {
        return members.get(email);
    }

    @Override
    public List<Member> readAll() {
        return new ArrayList<Member>(members.values());
    }
}
