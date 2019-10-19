package ru.photorex.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Component;
import ru.photorex.hw8.service.IOService;

import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor
public class ShellTableBuilder {

    private static final int TABLE_WIDTH = 80;

    private final IOService console;

    public<T> void build(Iterable<T> list, LinkedHashMap<String, Object> headers) {
        TableModel model = new BeanListTableModel<>(list, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        console.printString(tableBuilder.build().render(TABLE_WIDTH));
    }
}
