import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.TeamList;

public class LeagueManager {

    public static void main(String[] args) {
        TeamList teamList = new TeamList();
        SoccerLeague soccerLeague = new SoccerLeague(teamList);
        soccerLeague.run();
    }

}
