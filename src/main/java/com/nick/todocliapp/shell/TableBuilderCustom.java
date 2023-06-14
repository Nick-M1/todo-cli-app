package com.nick.todocliapp.shell;

import com.nick.todocliapp.exception.BadRequestCommandException;
import com.nick.todocliapp.model.TableableModel;
import org.springframework.shell.table.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TableBuilderCustom {
    public <T extends TableableModel> Table buildTable(List<T> items) {
        if (items.isEmpty())
            throw new BadRequestCommandException("No items matched this query\n\n");

        final var tableSize = items.size() + 1;
        String[][] data = new String[tableSize][];
        data[0] = items.get(0).tableHeader();

        for (int i = 1; i < tableSize; i++)
            data[i] = items.get(i - 1).asStringArray();

        return createTableBoundaries(data);
    }


    public <T extends TableableModel> Table buildTable(T item) {
        String[][] data = new String[][] {
            item.tableHeader(),
            item.asStringArray()
        };

        return createTableBoundaries(data);
    }


    private Table createTableBoundaries(String[][] data) {
        TableModel model = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(model);

        return tableBuilder
                .addFullBorder(BorderStyle.fancy_light)
                .addHeaderBorder(BorderStyle.fancy_heavy)
                .build();
    }
}
