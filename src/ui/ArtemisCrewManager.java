package ui;

import graph.GraphNode;
import model.ArtemisCrewController;
import model.Map;
import model.Planet;
import model.Spaceship;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ArtemisCrewManager {

    public static Scanner reader;
    private static ArtemisCrewController<Planet> artemisCrewController;
    private static Map map;
    private static String implementation;

    public static void main(String[] args) throws FileNotFoundException {
        showMainMenu();
    }

    public static void init(int implementation) throws FileNotFoundException {
        if(implementation==1){
            artemisCrewController = new ArtemisCrewController<>("adjacency_list");
            ArtemisCrewManager.implementation = "adjacency_list";
        } else {
            artemisCrewController = new ArtemisCrewController<>("adjacency_matrix");
            ArtemisCrewManager.implementation = "adjacency_matrix";
        }
        map = new Map();
        load();

        Planet planetOn = artemisCrewController.searchPlanet("Earth");
        artemisCrewController.getSpaceship().setPlanetOn(planetOn);
        artemisCrewController.getSpaceship().setFuel(200000);
    } // init

    public static void showMainMenu() throws FileNotFoundException {

        JOptionPane.showMessageDialog(null, "\n|-------------------------------------------|\n| ARTEMIS CREW SOFTWARE  |\n|-------------------------------------------|");
        int implementation = Integer.parseInt(JOptionPane.showInputDialog("Which implementation of the graph do you want?\n1) Adjacency list. \n2) Adjacency matrix"));
        init(implementation);

        boolean runFlag = true;

        while (runFlag) {
            String menu = "";

            menu+=("\n\n      WHAT DO YOU WANT TO DO?");
            menu+=("\n|----------------------------------------------------");
            menu+=("|\n| 1. See in which planet Artemis is.\n|" +
                    "| 2. Show info. of a planet.\n|" +
                    "| 3. Can Artemis go to a planet.\n|" +
                    "| 4. Minimum path that Artemis can travel to go to a planet.\n|" +
                    "| 5. Travel to a planet.\n|"+
                    "| 0. Exit.\n|"+
                    "|--------------------------------------"
            );
            int decision0 = Integer.parseInt(JOptionPane.showInputDialog(menu)); // I ask the user what he/she wants to do.

            switch (decision0) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Artemis is in: "+ artemisCrewController.whereIsArtemis());
                    break;

                case 2:
                    String name = JOptionPane.showInputDialog("Type the name of the planet:");
                    if(artemisCrewController.planetInfo(name).equals("")){
                        JOptionPane.showMessageDialog(null, "That planet doesn't exists :/");
                    } else {
                        JOptionPane.showMessageDialog(null, artemisCrewController.planetInfo(name));
                    }
                    break;

                case 3:
                    String planetFrom = JOptionPane.showInputDialog("Type the name of the planet where you are departing:");
                    String planetTo = JOptionPane.showInputDialog("Type the name of the planet where you want to arrive:");

                    if(artemisCrewController.searchPlanet(planetFrom)!=null && artemisCrewController.searchPlanet(planetTo)!=null){
                        Planet from = artemisCrewController.searchPlanet(planetFrom);
                        Planet to = artemisCrewController.searchPlanet(planetTo);

                        if(artemisCrewController.canWeGo(from, to).equals("")){
                            JOptionPane.showMessageDialog(null, "Sorry, we're having errors.");
                        } else {
                            JOptionPane.showMessageDialog(null, artemisCrewController.canWeGo(from, to));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "At least one of those planets doesn't exist :/");
                    }

                    break;

                case 4:
                    String planetInit = JOptionPane.showInputDialog("Type the name of the planet where you are departing:");
                    String planetFinish = JOptionPane.showInputDialog("Type the name of the planet where you want to arrive:");

                    if(artemisCrewController.searchPlanet(planetInit)!=null && artemisCrewController.searchPlanet(planetFinish)!=null){
                        Planet from = artemisCrewController.searchPlanet(planetInit);
                        Planet to = artemisCrewController.searchPlanet(planetFinish);

                        if(artemisCrewController.canWeGo(from, to).equals("")){
                            JOptionPane.showMessageDialog(null, "Sorry, we're having errors.");
                        } else {
                            ArrayList<GraphNode<Planet>> path = artemisCrewController.minimumPathFromOnePlanetToAnother(from, to);
                            String msg = "";

                            for (int i = 0; i < path.size(); i++) {
                                msg += path.get(i).getValue().getName()+" ";
                            }

                            JOptionPane.showMessageDialog(null, "You have to follow the next path: "+msg);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "At least one of those planets doesn't exist :/");
                    }
                    break;

                case 5:
                    String finish = JOptionPane.showInputDialog("Type the name of the planet where you will arrive:");

                    if(artemisCrewController.searchPlanet(finish)!=null){
                        Planet from = artemisCrewController.getSpaceship().getPlanetOn();
                        System.out.println(artemisCrewController.getSpaceship().getPlanetOn());
                        Planet to = artemisCrewController.searchPlanet(finish);

                        if(artemisCrewController.canWeGo(from, to).equals("")){
                            JOptionPane.showMessageDialog(null, "Sorry, we're having errors.");
                        } else {
                            if(artemisCrewController.canWeGo(from, to).contains("YES! You can go from the planet")){
                                if(artemisCrewController.travel(from, to, artemisCrewController.getSpaceship().getFuel())>=0){
                                    JOptionPane.showMessageDialog(null, "OK! You traveled to that planet.\n" +
                                            "Your fuel is: "+artemisCrewController.travel(from, to, artemisCrewController.getSpaceship().getFuel()));
                                    artemisCrewController.getSpaceship().setFuel(artemisCrewController.travel(from, to, artemisCrewController.getSpaceship().getFuel()));
                                    artemisCrewController.getSpaceship().setPlanetOn(to);
                                } else {
                                    JOptionPane.showMessageDialog(null,"NO! You don't have the enough fuel.\n" +
                                            "If you do the travel, then your fuel is going to be: "+artemisCrewController.travel(from, to, artemisCrewController.getSpaceship().getFuel()));
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "At least one of those planets doesn't exist :/");
                    }

                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Thank you for using our system. Come back soon!");
                    runFlag = false;
                    break;

                default:
                    break;
            } // switch
        } // while
    } // main menu

    public static void load() throws FileNotFoundException {
        File file = new File("planets.temp");
        Map map = new Map();

        if(!file.exists()){
            return;
        } else {
            try{
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line;

                while((line=reader.readLine())!=null){
                    String[] parts = line.split("#");
                    Planet planet;

                    if(parts[1].equalsIgnoreCase("false")){
                        planet = new Planet(parts[0], false);
                    } else {
                        planet = new Planet(parts[0], true);
                    }
                    map.addPlanet(planet);
                }
                artemisCrewController.setMap(map);
                fis.close();
                addNodesToGraph();
                addEdgesToGraph();
                return;

            } catch (FileNotFoundException fileNotFoundException){
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    } // load

    public static void addNodesToGraph(){
        for (int i = 0; i < artemisCrewController.getMap().getPlanets().size(); i++) {
            artemisCrewController.addVertex(artemisCrewController.getMap().getPlanets().get(i));
        }
    } // add nodes to graph

    public static void addEdgesToGraph() throws FileNotFoundException {
        File file = new File("edges.temp");

        if(!file.exists()){
            return;
        } else {
            try{
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line;

                while((line=reader.readLine())!=null){
                    String[] parts = new String[3];
                    boolean hasNumeral = false;

                    if(line.contains("#")){
                        parts = line.split("#");
                        hasNumeral = true;
                    }

                    GraphNode<Planet> nodeFrom = null;
                    GraphNode<Planet> nodeTo = null;
                    Planet planetFrom = null;
                    Planet planetTo = null;

                    if(implementation.equalsIgnoreCase("adjacency_list") && hasNumeral){
                        for (int i = 0; i < artemisCrewController.getGraph().graphNodesAdjacencyList.size(); i++) {
                            if(artemisCrewController.getGraph().graphNodesAdjacencyList.get(i).getValue().getName().equalsIgnoreCase(parts[1])){
                                nodeFrom = artemisCrewController.getGraph().graphNodesAdjacencyList.get(i);
                                planetFrom = artemisCrewController.getGraph().graphNodesAdjacencyList.get(i).getValue();
                            }

                            if(artemisCrewController.getGraph().graphNodesAdjacencyList.get(i).getValue().getName().equalsIgnoreCase(parts[2])){
                                nodeTo = artemisCrewController.getGraph().graphNodesAdjacencyList.get(i);
                                planetTo = artemisCrewController.getGraph().graphNodesAdjacencyList.get(i).getValue();
                            }

                            if(nodeFrom != null && nodeTo != null){
                                break;
                            }
                        } // for

                    } else if(implementation.equalsIgnoreCase("adjacency_matrix") && hasNumeral){
                        ArrayList<GraphNode<Planet>> matrix = artemisCrewController.matrixToList();

                        for (int i = 0; i < matrix.size(); i++) {
                            if(matrix.get(i).getValue().getName().equalsIgnoreCase(parts[1])){
                                nodeFrom = matrix.get(i);
                                planetFrom = matrix.get(i).getValue();
                            }

                            if(matrix.get(i).getValue().getName().equalsIgnoreCase(parts[2])){
                                nodeTo = matrix.get(i);
                                planetTo = matrix.get(i).getValue();
                            }

                            if(nodeFrom != null && nodeTo != null){
                                break;
                            }
                        } // for
                    }

                    if(hasNumeral){
                        System.out.println(planetFrom.getName()+planetTo.getName());
                        artemisCrewController.addEdge(Integer.parseInt(parts[0]), planetFrom, planetTo);
                    }
                }
                fis.close();
                return;

            } catch (FileNotFoundException fileNotFoundException){
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    } // add edges to graph
}
