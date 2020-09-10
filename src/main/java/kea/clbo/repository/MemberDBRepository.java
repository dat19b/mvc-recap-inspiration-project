package kea.clbo.repository;

import kea.clbo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDBRepository implements IMemberRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    SqlRowSet sqlRowSet;

    private List<Member> members;

    @Override
    public boolean create(Member member) {
        try {
            int id = jdbcTemplate.update("INSERT INTO members (email, password) VALUES ('" + member.getEmail() + "','" + member.getPassword() + "')");
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Member read(String email) {

        String sql = "SELECT * FROM members WHERE email ='" + email + "'";
        sqlRowSet = jdbcTemplate.queryForRowSet(sql);

        while (sqlRowSet.next()) {
            return new Member(
                    sqlRowSet.getString("email"),
                    sqlRowSet.getString("password"));
        }
        return null;
    }

    @Override
    public List<Member> readAll() {
        members = new ArrayList<>();

        String sql = "SELECT * FROM members";
        sqlRowSet = jdbcTemplate.queryForRowSet(sql);

        while (sqlRowSet.next()) {
            members.add(new Member(sqlRowSet.getString("email"), sqlRowSet.getString("password")));
        }
        return members;

    }
}
