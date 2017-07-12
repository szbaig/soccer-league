package com.teamtreehouse.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Map;

public class Team {
    private String name;
    private String coach;
    private List<Player> players;

    public Team(String name, String coach) {
        this.name = name;
        this.coach = coach;
        this.players = new ArrayList<Player>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(int playerIndex) {
        this.players.remove(playerIndex);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Long getPercentOfExperiencedPlayers() {
        Long numberOfExperiencedPlayers = this.players.stream().filter(p -> p.isPreviousExperience()).count();
        int totalPlayers = this.players.size();
        Long percentOfExperiencedPlayers = (long)((float) numberOfExperiencedPlayers/totalPlayers*100);
        return percentOfExperiencedPlayers;
    }

    public List<Player> getPlayersByHeight() {
        List<Player> teamPlayers = this.players;
        teamPlayers.sort((Player p1, Player p2) -> Integer.toString(p1.getHeightInInches()).compareTo(Integer.toString(p2.getHeightInInches())));
        return teamPlayers;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return String.format("Team %s coached by %s", this.getName(), this.getCoach());
    }

    public void listRoster() {
        List<Player> teamPlayers = this.getPlayers();
        for (Player player : teamPlayers) {
            System.out.printf("%s", player.toString());
        }
    }

    public void listPlayersByHeight() {
        List<Player> teamPlayers = this.getPlayersByHeight();
        System.out.printf("%s %n", this.toString());
        for (Player player : teamPlayers) {
            System.out.printf("%s", player.toString());
        }
        Long percentOfExperiencedPlayers = this.getPercentOfExperiencedPlayers();
        System.out.println("The average experience level for this team is " + percentOfExperiencedPlayers +"%");
    }

}
