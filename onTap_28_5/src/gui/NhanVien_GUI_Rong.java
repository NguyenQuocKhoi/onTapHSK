package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import connectDB.ConnectDB;
import dao.Dao_NhanVien;
import dao.Dao_PhongBan;
import entity.NhanVien;
import entity.PhongBan;

public class NhanVien_GUI_Rong extends JFrame implements ActionListener, MouseListener {

	private JTable table;
	private JTextField txtMaNV;
	private JTextField txthoTen;
	private JTextField txtTuoi;
	private JTextField txtTienLuong;
	private JTextField txtTim;
	private JButton btnTim;
	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnLuu;
	private JButton btnXoaTrang;
	private DefaultTableModel tableModel;
	private JLabel lblPhong;
	private JTextField txtPhong;
	private JComboBox cboPhong;
	private JButton btnSua;
	private Dao_NhanVien dsnv = new Dao_NhanVien();

	public NhanVien_GUI_Rong() throws SQLException {

		// khởi tạo kết nối đến CSDL

		setTitle("^-^");
		setSize(700, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel pnlNorth;
		add(pnlNorth = new JPanel(), BorderLayout.NORTH);
		JLabel lblTieuDe;
		pnlNorth.add(lblTieuDe = new JLabel("THÔNG TIN NHÂN VIÊN"));
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
		lblTieuDe.setForeground(Color.BLUE);

		Box b = Box.createVerticalBox();

		Box b1, b2, b3, b4, b5;
		JLabel lblMaNV, lblhoTen, lblTuoi, lblTienLuong;

		b.add(b1 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b1.add(lblMaNV = new JLabel("Mã nhân viên: "));
		b1.add(txtMaNV = new JTextField());

		b.add(b2 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b2.add(lblhoTen = new JLabel("Họ tên: "));
		b2.add(txthoTen = new JTextField());

		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b3.add(lblTuoi = new JLabel("Tuổi: "));
		b3.add(txtTuoi = new JTextField());
		b.add(b4 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));

		b4.add(lblPhong = new JLabel("Mã phòng: "));

		// Tạo và đổ dữ liệu vào comboBox
		b4.add(cboPhong = new JComboBox<String>());
		cboPhong.setEditable(true);

		b4.add(lblTienLuong = new JLabel("Tiền lương: "));
		b4.add(txtTienLuong = new JTextField());

		lblhoTen.setPreferredSize(lblMaNV.getPreferredSize());
		lblPhong.setPreferredSize(lblMaNV.getPreferredSize());
		lblTuoi.setPreferredSize(lblMaNV.getPreferredSize());

		b.add(b5 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		String[] headers = "MaNV;Họ tên;Tuổi;Phòng;Tiền lương".split(";");
		tableModel = new DefaultTableModel(headers, 0);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table = new JTable(tableModel));
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

		b5.add(scroll);
		add(b, BorderLayout.CENTER);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		add(split, BorderLayout.SOUTH);
		JPanel pnlLeft, pnlRight;
		split.add(pnlLeft = new JPanel());
		split.add(pnlRight = new JPanel());

		pnlLeft.add(new JLabel("Nhập mã số cần tìm: "));
		pnlLeft.add(txtTim = new JTextField(10));
		pnlLeft.add(btnTim = new JButton("Tìm"));

		pnlRight.add(btnThem = new JButton("Thêm"));
		pnlRight.add(btnSua = new JButton("Sửa"));
		pnlRight.add(btnXoa = new JButton("Xóa"));
		pnlRight.add(btnLuu = new JButton("Lưu"));

		ConnectDB.getInstance().connect();
		updateComboBox();
		updateTableData();

		table.addMouseListener(this);
		btnThem.addActionListener(this);
		btnLuu.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnTim.addActionListener(this);
	}

	public static void main(String[] args) throws SQLException {
		new NhanVien_GUI_Rong().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();
		if (o.equals(btnThem)) {
			xoaRong();
		} else if (o.equals(btnLuu)) {
			if (vaildData()) {
				NhanVien nhanVien = rever();
				if (dsnv.themNhanVien(nhanVien)) {
					tableModel.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getTuoi(),
							nhanVien.getPhonBan().getMaPhong(), nhanVien.getTienLuong() });
				}
			}
		} else if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r >= 0) {
				String ma = (String) table.getValueAt(r, 0);
				if (dsnv.xoaNhanVien(ma)) {
					tableModel.removeRow(r);
					xoaRong();
				}
			}
		} else if (o.equals(btnSua)) {
			int r = table.getSelectedRow();
			if (r >= 0) {
				NhanVien nv = rever();
				table.setValueAt(txthoTen.getText(), r, 1);
				table.setValueAt(txtTuoi.getText(), r, 2);
				table.setValueAt(cboPhong.getSelectedItem(), r, 3);
				table.setValueAt(txtTienLuong.getText(), r, 4);
			}
		} else if (o.equals(btnTim)) {
			Dao_NhanVien dsnv = new Dao_NhanVien();
			List<NhanVien> list;
			list = dsnv.getNhanVienByMa(txtTim.getText());
			if (txtTim.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Vui long nhap ma can tim");
			} else if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "Khong tim thay");
			} else {
				tableModel.getDataVector().removeAllElements();
				for (NhanVien nhanVien : list) {
					tableModel.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getTuoi(),
							nhanVien.getPhonBan().getMaPhong(), nhanVien.getTienLuong() });
				}
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int r = table.getSelectedRow();
		txtMaNV.setText(table.getValueAt(r, 0).toString());
		txthoTen.setText(table.getValueAt(r, 1).toString());
		txtTuoi.setText(table.getValueAt(r, 2).toString());
		cboPhong.setSelectedItem(table.getValueAt(r, 3).toString());
		txtTienLuong.setText(table.getValueAt(r, 4).toString());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void updateComboBox() {
		Dao_PhongBan dspv = new Dao_PhongBan();
		List<PhongBan> list = dspv.getAllPhongBan();
		for (PhongBan phongBan : list) {
			cboPhong.addItem(phongBan.getMaPhong());
		}
	}

	private void updateTableData() {
		Dao_NhanVien dsnv = new Dao_NhanVien();
		List<NhanVien> list = dsnv.getALLNhanVien();
		for (NhanVien nhanVien : list) {
			tableModel.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getTuoi(),
					nhanVien.getPhonBan().getMaPhong(), nhanVien.getTienLuong() });
		}
	}

	public void xoaRong() {
		txtMaNV.setText("");
		txthoTen.setText("");
		txtTuoi.setText("");
		cboPhong.setSelectedItem(null);
		txtTienLuong.setText("");
	}

	public NhanVien rever() {
		String ma = txtMaNV.getText().toString();
		String hoten = txthoTen.getText().toString();
		int tuoi = Integer.parseInt(txtTuoi.getText());
		String maPhong = cboPhong.getSelectedItem().toString();
		float tienLuong = Float.parseFloat(txtTienLuong.getText());
		return new NhanVien(ma, hoten, tuoi, new PhongBan(maPhong), tienLuong);
	}

	public boolean vaildData() {
		String ma = txtMaNV.getText().trim();
		String hoTen = txthoTen.getText().trim();
		String tuoi = txtTuoi.getText().trim();
		String tienLuong = txtTienLuong.getText().trim();
		if (ma.equals("") || hoTen.equals("") || tuoi.equals("") || cboPhong.getSelectedItem().toString() == null // loi
				|| tienLuong.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
			return false;
		}

		if (!(ma.length() > 0 && ma.matches("^21[0-9]{6}$"))) {
			JOptionPane.showMessageDialog(null, "Ma khong 8 so bat dau bang so 21");
			return false;
		}

		if (tuoi.length() > 0) {
			try {
				int x = Integer.parseInt(txtTuoi.getText());
				if (x <= 18) {
					JOptionPane.showMessageDialog(null, "Tuoi lon hon 18");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "vui long nhap so nguyen");
				return false;
			}
		}

		if (tienLuong.length() > 0) {
			try {
				float x = Float.parseFloat(txtTienLuong.getText());
				if (x <= 0) {
					JOptionPane.showMessageDialog(null, "Tien luong lon hon 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "vui long nhap so");
				return false;
			}
		}
		return true;
	}
}
