package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

public class TeamList {
    private List<Team> teams;

    public TeamList() {
        this.teams = new ArrayList<Team>();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public int getTeamCount() {
        return this.teams.size();
    }

    public boolean isEmpty() {
        return this.teams.isEmpty();
    }

    private Map<String, Team> byName() {
        Map<String, Team> byName = new TreeMap<>();
        for (Team team : this.teams) {
            byName.put(team.getName(), team);
        }
        return byName;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Set<String> getTeamNames() {
        return byName().keySet();
    }

    public Team getTeamByName(String teamName) {
        return byName().get(teamName);
    }

}