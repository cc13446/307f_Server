package MyGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomStateUI extends JFrame {
    // 创建表头
    private final Object[] columnNames = {"房间状态", "总费用", "当前温度", "目标温度", "当前费率", "总服务时间", "风速"};

    // 获取表格数据
    private Object[][] roomState = {
            {"11111", 1, 1, "", "", "", ""},
            {"11111", 1, 1, 1, 1, 1, "LOW"},
            {"11111", 1, 1, 1, 1, 1, "LOW"},
            {"11111", 1, 1, 1, 1, 1, "LOW"},
            {"11111", 1, 1, 1, 1, 1, "MID"}
    };
    JTable roomStateTable = null;

    public RoomStateUI(JFrame relativeWindow) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(relativeWindow);
        setTitle("Room State");
        setSize(600, 400);

        JPanel jp = new JPanel(new BorderLayout());

        // 创建表格
        roomStateTable = new JTable(roomState, columnNames);

        JButton flush = new JButton("刷新");
        flush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 刷新数据
                System.out.println("点击刷新数据按钮");

                roomStateTable = new JTable(roomState, columnNames);
            }
        });

        // 表格的显示
        jp.add(roomStateTable.getTableHeader(), BorderLayout.NORTH);
        jp.add(roomStateTable, BorderLayout.CENTER);
        jp.add(flush, BorderLayout.SOUTH);

        setContentPane(jp);
        pack();
        setLocationRelativeTo(null);
    }
}
