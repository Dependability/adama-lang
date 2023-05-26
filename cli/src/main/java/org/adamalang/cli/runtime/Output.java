package org.adamalang.cli.runtime;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adamalang.cli.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Output {

    private boolean color = true;
    private boolean json = false;
    private ArrayList<ObjectNode> outputArray = new ArrayList<>();
    public Output(Argument args) {
        HashMap<String, ArgumentItem> arguments = args.arguments;
        ArgumentItem color = arguments.get("--no-color");
        ArgumentItem json = arguments.get("--json");
        if (json != null)
            this.json = true;
        if (color != null)
            this.color = false;
    }

    public void add(ObjectNode json) {
        outputArray.add(json);
    }
    public void out() {
        // In the case of json only
        if (json) {
            System.out.println("[");
            for (int i = 0; i < outputArray.size(); i++) {
                ObjectNode json = outputArray.get(i);
                String[] lines = json.toPrettyString().split("\n");
                for (int j = 0; j < lines.length ; j++) {
                    String outputStr = "  " + lines[j];
                    if (i < outputArray.size() - 1 && j == lines.length - 1) {
                        outputStr += ",";
                    }
                    System.out.println(outputStr);
                }
            }
            System.out.println("]");
            return;
        }
        Util.ANSI headingColor = Util.ANSI.Reset;
        Util.ANSI valueColor = Util.ANSI.Reset;
        if (color) {
            headingColor = Util.ANSI.Yellow;
            valueColor = Util.ANSI.Green;
        }

        ArrayList<StringBuilder> table = new ArrayList<>();
        for (int i = 0; i < 3 + (2*outputArray.size()); i++) {
            table.add(new StringBuilder());
        }
        int fields = 0;
        if (outputArray.size() > 0) {
            fields = outputArray.get(0).size();
        }
        int[] longestEach = new int[fields];
        String[] headers = new String[fields];
        String[][] values = new String[outputArray.size()][fields];
        // Populate the longest for Each
        for (int i = 0; i < outputArray.size() ; i++) {
            ObjectNode json = outputArray.get(i);
            Iterator<Map.Entry<String, JsonNode>> iterator = json.fields();
            int index = 0;
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> item = iterator.next();
                String header = item.getKey();
                headers[index] = header;
                JsonNode value = item.getValue();
                String textValue = value.toString();
                values[i][index] = textValue;
                int cellLength = textValue.length() > header.length() ? textValue.length() : header.length();
                if (longestEach[index] < cellLength)
                    longestEach[index] = cellLength;
                index++;
            }
        }
        // Create heading table
        table.get(0).append("\u250C");
        table.get(1).append("\u2502");
        table.get(table.size()-1).append("\u2514");
        for (int i = 0; i < longestEach.length; i++) {
            table.get(0).append("\u2500".repeat(longestEach[i]+2));
            table.get(table.size()-1).append("\u2500".repeat(longestEach[i]+2));
            if (i < longestEach.length - 1) {
                table.get(0).append("\u252C");
                table.get(table.size()-1).append("\u2534");
            }
            int spaces = (longestEach[i] + 2 - headers[i].length());
            int leftPad =  (spaces)/2;
            int rightPad = leftPad;
            if ((spaces) % 2 == 1) {
                rightPad++;
            }

            table.get(1).append(" ".repeat(leftPad)).append(Util.prefixBold(headers[i], headingColor)).append(" ".repeat(rightPad)).append("\u2502");
        }
        for (int i = 0; i < values.length ; i++) {
            for (int j = 0; j < values[i].length ; j++) {
                String value = values[i][j];
                int spaces = (longestEach[j] + 2 - value.length());
                int leftPad = (spaces)/2;
                int rightPad = leftPad;
                if ((spaces) % 2 == 1) {
                    rightPad++;
                }
                if (j == 0)
                    table.get(i*2 + 2).append("\u251C");
                table.get(i*2 + 2).append("\u2504".repeat(longestEach[j]+2));
                if (j == longestEach.length - 1)
                    table.get(i*2 + 2).append("\u2524");
                if (j < longestEach.length - 1) {
                    table.get(i*2 + 2).append("\u253C");
                }
                table.get(i*2 + 3).append("\u2502");
                table.get(i*2 + 3).append(" ".repeat(leftPad)).append(Util.prefixBold(value, valueColor)).append(" ".repeat(rightPad));
                if (j == longestEach.length - 1) {
                    table.get(i*2 + 3).append("\u2502");
                }
            }
        }
        table.get(0).append("\u2510");
        table.get(table.size()-1).append("\u2518");
        for (StringBuilder sb : table) {
            System.out.println(sb);
        }
    }


}
