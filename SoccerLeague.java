import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.TeamList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SoccerLeague {
    private TeamList teamList;
    private BufferedReader reader;
    private Map<String, String> menu;
    private List<Player> playersList;

    public SoccerLeague(TeamList teamList) {
        this.teamList = teamList;
        this.playersList = new ArrayList<Player>();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.menu = new HashMap<String, String>();
        this.menu.put("create", "Create - add a new team");
        this.menu.put("add", "Add - add a player to a team");
        this.menu.put("remove", "Remove - remove a player from a team.");
        this.menu.put("report", "Report - view a report of a team by hieght");
        this.menu.put("balance", "Balance - view the league balance report");
        this.menu.put("roster", "Roster - View roster");
        this.menu.put("quit", "Quit - exits the program");
    }

    public void run() {
        Player[] players = Players.load();
        Arrays.sort(players);
        for (Player player : players) {
            this.playersList.add(player);
        }
        System.out.printf("There are currently %d registered players and 0 registered teams.  Please create your first team to get started. %n%n", players.length);

        String choice = "";
        int teamCount = this.teamList.getTeamCount();
        if(teamCount == 0) {
            createNewTeam();
        }
        do {
            try {
                choice = promptForAction();
                switch (choice) {
                    case "create":
                        createNewTeam();
                        break;
                    case "add":
                        List<Player> availablePlayers = getAvailablePlayers();
                        Team teamToAddPlayerTo = promptForTeam();
                        int playerToAddIndex = promptForPlayer(availablePlayers);
                        Player playerToAdd = availablePlayers.get(playerToAddIndex);
                        teamToAddPlayerTo.addPlayer(playerToAdd);
                        playerToAdd.setOnTeam(true);
                        break;
                    case "remove":
                        Team teamToRemovePlayerFrom = promptForTeam();
                        int playerToRemoveIndex = promptForPlayer(teamToRemovePlayerFrom.getPlayers());
                        Player playerToRemove = teamToRemovePlayerFrom.getPlayers().get(playerToRemoveIndex);
                        teamToRemovePlayerFrom.removePlayer(playerToRemoveIndex);
                        playerToRemove.setOnTeam(false);
                        break;
                    case "report":
                        Team teamToReportOn = promptForTeam();
                        teamToReportOn.listPlayersByHeight();
                        break;
                    case "balance":
                        getLeagueBalanceReport(playersList);
                        break;
                    case "roster":
                        Team teamToGetRosterOf = promptForTeam();
                        teamToGetRosterOf.listRoster();
                    default:
                }
            } catch (IOException ioException) {
                System.out.println("Problem with input");
                ioException.printStackTrace();
            }
        }
        while (!choice.equals("quit"));
    }

    private String promptForAction() throws IOException {
        System.out.printf("%nMenu %n");
        for (Map.Entry<String, String> option : this.menu.entrySet()) {
            System.out.printf("%s %n", option.getValue());
        }
        System.out.printf("%nSelect an option: ");
        String choice = this.reader.readLine();
        return choice.trim().toLowerCase();
    }

    private Team promptForNewTeam() throws IOException {
        System.out.println("What is the name of the team? ");
        String teamName = this.reader.readLine();
        System.out.println("Who is the team's coach? ");
        String teamCoach = this.reader.readLine();
        Team team = new Team(teamName, teamCoach);
        return team;
    }

    private void createNewTeam() {
        try {
            List<Player> availablePlayers = getAvailablePlayers();
            if(availablePlayers.size() < 1) {
                System.out.println("You cannot create a team because there are no more available players. Please remove one or more players from the other teams in the league and try again.");
                return;
            }
            Team team = promptForNewTeam();
            this.teamList.addTeam(team);

        } catch (IOException ioException) {
            System.out.println("Problem with input");
            ioException.printStackTrace();
        }
    }

    private Team promptForTeam() throws IOException {
        Set<String> teamNames = this.teamList.getTeamNames();
        Set<String> sortedTeams = new TreeSet<>(teamNames);
        List<String> teamArray = new ArrayList<>();
        int i = 0;
        for (String teamName : sortedTeams) {
            i++;
            Team team = this.teamList.getTeamByName(teamName);
            teamArray.add(teamName);
            System.out.printf("%s.) %s %n", i, team.toString());
        }
        System.out.println("Select a team: ");
        int teamIndex = Integer.parseInt(this.reader.readLine()) - 1;
        String teamName = teamArray.get(teamIndex);
        Team team = this.teamList.getTeamByName(teamName);
        return team;
    }

    private int promptForPlayer(List<Player> players) throws IOException {
        int i = 0;
        for (Player player : players) {
            i++;
            String playerDetails = player.toString();
            System.out.printf("%s.) %s", i, playerDetails);
        }
        System.out.println("Select a player: ");
        String playerNumber = this.reader.readLine();
        int playerIndex = (Integer.parseInt(playerNumber) - 1);
        return playerIndex;
    }

    private void getLeagueBalanceReport(List<Player> players) {
        int totalPlayers = players.size();
        Long experiencedPlayerCount = players.stream().filter(p -> p.isPreviousExperience()).count();
        Long inexperiencedPlayerCount = (long) (totalPlayers - experiencedPlayerCount);
        System.out.printf("There are %s experienced players in the league. %n", experiencedPlayerCount);
        System.out.printf("There are %s inexperienced players in the league. %n", inexperiencedPlayerCount);

        for (Team team : this.teamList.getTeams()) {
            team.listPlayersByHeight();
        };
    }

    private List<Player> getAvailablePlayers() {
        List<Player> availablePlayers = new ArrayList<>();
        this.playersList.stream().filter(p -> p.isOnTeam() == false).forEach(availablePlayers::add);
        return availablePlayers;
    }

}