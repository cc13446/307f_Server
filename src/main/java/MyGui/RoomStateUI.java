package MyGui;

import javax.swing.*;
import java.awt.*;

public class RoomStateUI extends JFrame {
    public RoomStateUI(JFrame relativeWindow) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(relativeWindow);
        setTitle("Room State");
        setSize(600, 400);

        JPanel jp = new JPanel(new BorderLayout());

        // 创建表头
        Object[] columnNames = {"房间状态", "总费用", "当前温度", "目标温度", "当前费率", "总服务时间", "风速"};

        // 获取表格数据
        Object[][] roomState = {
                {"11111", 1, 1, "", "", "", ""},
                {"11111", 1, 1, 1, 1, 1, "LOW"},
                {"11111", 1, 1, 1, 1, 1, "LOW"},
                {"11111", 1, 1, 1, 1, 1, "LOW"},
                {"11111", 1, 1, 1, 1, 1, "MID"}
        };

        // 创建表格
        JTable roomStateTable = new JTable(roomState, columnNames);

        // 表格的显示
        jp.add(roomStateTable.getTableHeader(), BorderLayout.NORTH);
        jp.add(roomStateTable, BorderLayout.CENTER);

        setContentPane(jp);
        setLocationRelativeTo(null);
    }
}
