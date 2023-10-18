package proeend.hittable;

import proeend.material.Material;
import proeend.math.Vector;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Deze klasse is verantwoordelijk voor het inladen van geometrische objecten vanuit OBJ-bestanden
 * en het converteren ervan naar een driehoeken genaamd TriangleMesh.
 */
public class ObjectLoader {

    /**
     * Laadt een geometrisch object vanuit een OBJ-bestand en retourneert een {@link  TriangleMesh}.
     *
     * @param filepath Het pad naar het OBJ-bestand.
     * @param material Het materiaal dat aan het geladen object moet worden toegewezen.
     * @return Een {@link TriangleMesh} dat het geladen object representeert.
     * @throws IOException Als er een fout optreedt bij het lezen van het bestand.
     */
    public static TriangleMesh loadObj(String filepath, Material material) throws IOException {

        List<Vector> vertexes = new ArrayList<>();
        List<Integer> vertexIndexes = new ArrayList<>();
        List<Integer> faces = new ArrayList<>();

        Scanner scanner = new Scanner(new File(filepath));
        parseVerticesAndFaces(scanner, vertexes, vertexIndexes, faces);

        System.out.println("Object \"" + filepath + "\" loaded from file");
        return new TriangleMesh(faces.toArray(new Integer[0]), vertexIndexes.toArray(new Integer[0]), vertexes.toArray(new Vector[0]), material);
    }

    /**
     * Parsed de vertex- en face gegevens van het OBJ-bestand.
     *
     * @param scanner De scanner voor het lezen van het bestand.
     * @param vertexes De lijst met vertices.
     * @param vertexIndexes De lijst met vertex-indices.
     * @param faces De lijst met face-gegevens.
     */
    private static void parseVerticesAndFaces(Scanner scanner, List<Vector> vertexes, List<Integer> vertexIndexes, List<Integer> faces) {

        while (scanner.hasNextLine()) {
            String[] lineParts = scanner.nextLine().split(" ");

            boolean lineIsVertex = Objects.equals(lineParts[0], "v");
            boolean lineIsFace = Objects.equals(lineParts[0], "f");

            if (lineIsVertex) {
                parseVertex(lineParts, vertexes);
            }
            else if (lineIsFace) {
                parseFace(lineParts, vertexIndexes, faces);
            }
        }
    }

    /**
     * Parsed de gegevens van een vertex uit een regel in een OBJ-bestand en voegt deze toe aan de lijst met vertexes.
     *
     * @param vertexData De array met gegevens van een vertex-regel.
     * @param vertexes De lijst met vertices waar de nieuwe vertex aan wordt toegevoegd.
     */
    private static void parseVertex(String[] vertexData, List<Vector> vertexes) {
        // Controleer of de vertex-data de verwachte structuur heeft (x, y, z)
        if (vertexData.length >= 4) {
            double x = Double.parseDouble(vertexData[1]);
            double y = Double.parseDouble(vertexData[2]);
            double z = Double.parseDouble(vertexData[3]);
            vertexes.add(new Vector(x, y, z));
        }
        else {
            System.out.println("Ongeldige vertex-gegevens: " + String.join(" ", vertexData));
        }
    }

    /**
     * Parsed de gegevens van een face uit een regel in een OBJ-bestand.
     *
     * @param faceData De array met gegevens van een face-regel.
     * @param vertexIndexList De lijst met vertex-indices waar de nieuwe indices aan worden toegevoegd.
     * @param faces De lijst met face-gegevens waar het aantal vertices van de face wordt toegevoegd.
     */
    private static void parseFace(String[] faceData, List<Integer> vertexIndexList, List<Integer> faces) {
        // Controleer of de face-data te lang is
        if (faceData.length > 5) {
            System.out.println("Te veel gegevens in een face-regel (5+).");
        }

        // Voeg het aantal vertices in de face toe aan de lijst met faces
        faces.add(faceData.length - 1);

        // Verwerk de vertex-indices in de face-data
        for (int j = 1; j < faceData.length; j++) {
            String[] vertexIndexParts = faceData[j].split("/");
            vertexIndexList.add(Integer.parseInt(vertexIndexParts[0]));
        }
    }
}