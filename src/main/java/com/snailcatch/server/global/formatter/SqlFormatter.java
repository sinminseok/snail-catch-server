package com.snailcatch.server.global.formatter;

import java.util.List;
import java.util.stream.Collectors;

public class SqlFormatter {

    public static String formatSql(String sql) {
        return com.github.vertical_blank.sqlformatter.SqlFormatter.of("mysql").format(sql);
    }

    public static String formatSqls(List<String> sqls) {
        return sqls.stream()
                .map(SqlFormatter::formatSql)
                .collect(Collectors.joining("\n\n")); // 두 줄 간격으로 구분
    }

}
