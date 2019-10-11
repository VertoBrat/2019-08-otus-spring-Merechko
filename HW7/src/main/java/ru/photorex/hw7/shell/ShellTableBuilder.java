package ru.photorex.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import ru.photorex.hw7.service.IOService;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShellTableBuilder {

    private static final int TABLE_WIDTH = 80;

    private final IOService console;

    public<T> void build(List<T> list, LinkedHashMap<String, Object> headers) {
        TableModel model = new BeanListTableModel<>(list, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        console.printString(tableBuilder.build().render(TABLE_WIDTH));
    }
}
