package game;

import java.util.*;
import static game.InOutUtils.readStringsFromInputStream;
import static game.ProcessUtils.UTF_8;

/**
 * Main samplegame class.
 */
public class Main {

    public static void main(String[] args) {
        List<String> input = readStringsFromInputStream(System.in, UTF_8);
        if(!input.isEmpty()){
            Round round = new Round(input);
            printMovingGroups(makeMove(round));
        }
        System.exit(0);
    }

    private static List<MovingGroup> makeMove(Round round) {
        List<MovingGroup> movingGroups = new ArrayList<>();
        // Место для Вашего кода.

        int myId = round.getTeamId();

        int currentStep = round.getCurrentStep();

        int[][] matrix = round.getDistanceMap();

        List<Planet> listMyPlanet = round.getOwnPlanets();

        List<Planet> listNotMyPlanet = round.getAdversarysPlanets();

        List<Planet> listNoManPlanet = round.getNoMansPlanets();

        List<Planet> listAllPlanets = round.getPlanets();

        if(myId == 0) {
        listMyPlanet.stream().forEach(planet ->
                {
                    int count = (planet.getPopulation() - 1) / 3;
                    int min = 12;
                    int mini = -1;
                    int j = 0;
                    List<Integer> array = new ArrayList<>();
                    while(j < 3) {
                        min = 12;
                        for (int i = 0; i < 10; i++) {
                            if ((matrix[i][planet.getId()] <= min) && (matrix[i][planet.getId()] != 0)) {
                                min = matrix[i][planet.getId()];
                                mini = i;
                            }
                        }
                        array.add(mini);
                        matrix[mini][planet.getId()] = 12;
                        j++;
                    }
                    j--;
                    while(j >= 0) {
                        movingGroups.add(new MovingGroup(planet.getId(), array.get(j), count));
                        j--;
                    }
                }
                );}

        if(myId == 1) {
            listMyPlanet.stream().forEach(planet ->
                    {
                        int count = (planet.getPopulation() - 1) / 3;
                        int min = 12;
                        int mini = -1;
                        int j = 0;
                        List<Integer> array = new ArrayList<>();
                        while(j < 3) {
                            min = 12;
                            for (int i = 0; i < 10; i++) {
                                if ((matrix[i][planet.getId()] < min) && (matrix[i][planet.getId()] != 0)) {
                                    min = matrix[i][planet.getId()];
                                    mini = i;
                                }
                            }
                            array.add(mini);
                            matrix[mini][planet.getId()] = 12;
                            j++;
                        }
                        j--;
                        while(j >= 0) {
                            movingGroups.add(new MovingGroup(planet.getId(), array.get(j), count));
                            j--;
                        }
                    }
            );}

        return movingGroups;

    }



    private static void printMovingGroups(List<MovingGroup> moves) {
        System.out.println(moves.size());
        moves.forEach(move -> System.out.println(move.getFrom() + " " + move.getTo() + " " + move.getCount()));
    }

}
