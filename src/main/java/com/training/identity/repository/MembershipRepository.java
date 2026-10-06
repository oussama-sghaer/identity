package com.training.identity.repository;

import com.training.identity.domain.Membership;
import com.training.identity.domain.Team;
import com.training.identity.domain.TeamId;
import com.training.identity.domain.UserId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Repository
public class MembershipRepository {




    private final List<Membership> memberships;
    private final List<Team> teams;
    public MembershipRepository(ObjectMapper objectMapper, @Value("classpath:/fixtures/membership.json") Resource resource) throws IOException {
        String fileContent= resource.getContentAsString(StandardCharsets.UTF_8);
        Wrapper wrapper = objectMapper.readValue(fileContent, Wrapper.class);
        memberships = wrapper.memberships();
        teams = wrapper.teams();
    }
    private record Wrapper(List<Team> teams, List<Membership> memberships){
    }
    public List<Membership> memberships(){
        return List.copyOf(memberships);
    }
    public List<Team> teams(){
        return List.copyOf(teams);
    }
    public Optional<Team> getTeam(TeamId id){
        return teams.stream().filter(team -> team.id().equals(id)).findFirst();
    }
    public Optional<Membership> findBySubjectAndTeam(UserId subject, TeamId team){
        return memberships.stream().filter(membership -> membership.subject().equals(subject) && membership.team().equals(team)).findFirst();
    }
}
