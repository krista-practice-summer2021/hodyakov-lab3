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
        if (!input.isEmpty()) {
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

        listMyPlanet.stream().forEach(planet -> {
            int count = (planet.getPopulation() - 1) / 3;
            int j = 0;
            List<Integer> array = new ArrayList<>();

            List<Planet> attackPlanets = new ArrayList<>();
            attackPlanets.addAll(listNoManPlanet);
            attackPlanets.addAll(listNotMyPlanet);

            while (j < 3) {
                final int[] min = {20};
                final int[] minj = {-1};
                final int[] index = {0};
                final int[] i = {0};
                attackPlanets.stream().forEach(attackPl -> {
                    if(matrix[planet.getId()][attackPl.getId()] < min[0]) {
                                min[0] = matrix[planet.getId()][attackPl.getId()];
                                minj[0] = attackPl.getId();
                                index[0] = i[0];
                            }
                    i[0]++;
                });
                array.add(minj[0]);
                if(attackPlanets.size() > 0) attackPlanets.remove(index[0]);
                j++;
            }

            j--;

            while (j >= 0) {
                movingGroups.add(new MovingGroup(planet.getId(), array.get(j), count));
                j--;
            }
        });

        return movingGroups;

    }


    private static void printMovingGroups(List<MovingGroup> moves) {
        System.out.println(moves.size());
        moves.forEach(move -> System.out.println(move.getFrom() + " " + move.getTo() + " " + move.getCount()));
    }

}
