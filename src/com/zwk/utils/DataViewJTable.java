package com.zwk.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class DataViewJTable extends JTable {
	public DataViewJTable() {
		super();
		initTable();
	}

	public DataViewJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		initTable();
	}

	private void initTable() {
		this.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.setForeground(new Color(43, 43, 43));
		this.setSelectionBackground(new Color(54, 148, 117));
		// 设置间隔色
		this.setGridColor(new Color(242, 242, 242));
		// 显示网格
		this.setShowGrid(true);
		// 设置水平线
		this.setShowHorizontalLines(true);
		// 设置垂直线
		this.setShowVerticalLines(true);
		this.setBorder(null);
		// 设置单元格内容居中显示
		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.setDefaultRenderer(Object.class, tableCellRenderer);

		// 设置表头样式
		JTableHeader tableHeader = this.getTableHeader();
		tableHeader.setFont(new Font("微软雅黑", Font.BOLD, 14));
		// 允许拉动宽度
		tableHeader.setResizingAllowed(true);
		// 设置颜色
		tableHeader.setBackground(new Color(21, 116, 147));
		tableHeader.setForeground(new Color(255, 255, 255));
		TableCellRenderer d = tableHeader.getDefaultRenderer();
		// 设置表头行高
		Dimension dimension = tableHeader.getSize();
		dimension.height = 30;
		tableHeader.setPreferredSize(dimension);
	}

	// 设置不可编辑
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (getFillsViewportHeight()) {
			paintEmptyRows(g);
		}
	}

	/**
	 * Paints the backgrounds of the implied empty rows when the table model is
	 * insufficient to fill all the visible area available to us. We don't
	 * involve cell renderers, because we have no data.
	 */
	protected void paintEmptyRows(Graphics g) {
		final int rowCount = getRowCount();
		final Rectangle clip = g.getClipBounds();
		if (rowCount * rowHeight < clip.height) {
			for (int i = rowCount; i <= clip.height / rowHeight; ++i) {
				g.setColor(colorForRow(i));
				g.fillRect(clip.x, i * rowHeight, clip.width, rowHeight);
			}
		}
	}

	/**
	 * Returns the appropriate background color for the given row.
	 */
	protected Color colorForRow(int row) {
		return (row % 3 == 0) ? new Color(238, 238, 238) : Color.white;
	}

	/**
	 * Shades alternate rows in different colors.
	 */
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		if (!isCellSelected(row, column)) {
			c.setBackground(colorForRow(row));
			c.setForeground(UIManager.getColor("Table.foreground"));
		} else {
			c.setBackground(new Color(54, 148, 117));
			c.setForeground(new Color(43, 43, 43));
		}
		return c;
	}
}
