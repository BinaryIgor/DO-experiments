package com.igor101.dojavamonitor.logs.model;

import java.util.List;

public record LogMapping(List<String> warningKeywords,
                         List<String> errorKeywords,
                         List<String> messagesToSwallow) {
}
