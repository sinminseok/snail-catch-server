package com.snailcatch.server.global.formatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExecutionPlanFormatter {

    private static final String DEFAULT_VALUE = "-";

    private static final String[] EXPLAIN_COLUMNS = {
            "id", "select_type", "table", "partitions", "type", "possible_keys",
            "key", "key_len", "ref", "rows", "filtered", "Extra"
    };

    public static String formatExecutionPlan(List<Map<String, String>> rowsData) {
        Map<String, Integer> columnWidths = calculateColumnWidths(rowsData);
        StringBuilder sb = new StringBuilder();
        appendHeader(sb, columnWidths);
        appendSeparator(sb, columnWidths);
        appendRows(sb, rowsData, columnWidths);
        return sb.toString();
    }

    private static Map<String, Integer> calculateColumnWidths(List<Map<String, String>> rowsData) {
        Map<String, Integer> columnWidths = new LinkedHashMap<>();
        for (String col : EXPLAIN_COLUMNS) {
            columnWidths.put(col, col.length());
        }

        for (Map<String, String> row : rowsData) {
            for (String col : EXPLAIN_COLUMNS) {
                String value = row.getOrDefault(col, DEFAULT_VALUE);
                columnWidths.put(col, Math.max(columnWidths.get(col), value.length()));
            }
        }

        return columnWidths;
    }

    private static void appendHeader(StringBuilder sb, Map<String, Integer> columnWidths) {
        sb.append("| ");
        for (String col : EXPLAIN_COLUMNS) {
            sb.append(padRight(col, columnWidths.get(col))).append(" | ");
        }
        sb.append("\n");
    }

    private static void appendSeparator(StringBuilder sb, Map<String, Integer> columnWidths) {
        sb.append("|");
        for (String col : EXPLAIN_COLUMNS) {
            int width = columnWidths.get(col);
            sb.append("-".repeat(width + 2)).append("|");
        }
        sb.append("\n");
    }

    private static void appendRows(StringBuilder sb, List<Map<String, String>> rowsData, Map<String, Integer> columnWidths) {
        for (Map<String, String> row : rowsData) {
            sb.append("| ");
            for (String col : EXPLAIN_COLUMNS) {
                String value = row.getOrDefault(col, DEFAULT_VALUE);
                sb.append(padRight(value, columnWidths.get(col))).append(" | ");
            }
            sb.append("\n");
        }
    }

    private static String padRight(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
