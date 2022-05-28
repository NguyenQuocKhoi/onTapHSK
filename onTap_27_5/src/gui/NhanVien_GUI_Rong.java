package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
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
import javax.swing.JCheckBox;
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
import dao.Dao_LopHoc;
import dao.Dao_SinhVien;
import entity.LopHoc;
import entity.SinhVien;

public class NhanVien_GUI_Rong extends JFrame implements MouseListener, ActionListener {

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
	private JCheckBox cbxPhai;
	private Dao_SinhVien dssv = new Dao_SinhVien();

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
		b1.add(lblMaNV = new JLabel("Mã sinh viên: "));
		b1.add(txtMaNV = new JTextField());

		b.add(b2 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b2.add(lblhoTen = new JLabel("Họ tên: "));
		b2.add(txthoTen = new JTextField());

		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b3.add(new JLabel("Phái:"));
		b3.add(cbxPhai = new JCheckBox("Nữ"));
		b3.add(lblTuoi = new JLabel("Tuổi: "));
		b3.add(txtTuoi = new JTextField());
		b.add(b4 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));

		b4.add(lblPhong = new JLabel("Mã giáo viên: "));

		// Tạo và đổ dữ liệu vào comboBox
		b4.add(cboPhong = new JComboBox<String>());
		cboPhong.setEditable(true);

		b4.add(lblTienLuong = new JLabel("Email: "));
		b4.add(txtTienLuong = new JTextField());

		lblhoTen.setPreferredSize(lblMaNV.getPreferredSize());
		lblPhong.setPreferredSize(lblMaNV.getPreferredSize());
		lblTuoi.setPreferredSize(lblMaNV.getPreferredSize());

		b.add(b5 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		String[] headers = "MaSV;Họ tên;Phái;Tuổi;GiaoVien;Email".split(";");
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
		pnlRight.add(btnXoaTrang = new JButton("Xóa trắng"));

		ConnectDB.getInstance().connect();
		updateComboBox();
		updateTableData();

		table.addMouseListener(this);

		btnSua.addActionListener(this);
		btnThem.addActionListener(this);
		btnTim.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnXoa.addActionListener(this);
	}

	public static void main(String[] args) throws SQLException {
		new NhanVien_GUI_Rong().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnXoaTrang)) {
			txtMaNV.setText("");
			txthoTen.setText("");
			cbxPhai.setSelected(false);
			txtTuoi.setText("");
			cboPhong.setSelectedItem(null);
			txtTienLuong.setText("");
		} else if (o.equals(btnThem)) {
			if (vaildData()) {
				SinhVien sv = rever();
				if (dssv.themSinhVien(sv)) {
					tableModel.addRow(new Object[] { sv.getMaSV(), sv.getHoTen(), sv.isPhai(), sv.getTuoi(),
							sv.getLopHoc().getMaLop(), sv.getEmail() });
				}
				xoaTrang();
			}
		} else if (o.equals(btnXoa)) {
			int r = table.getSelectedRow();
			if (r >= 0) {
				String ma = (String) table.getValueAt(r, 0);
				if (dssv.xoaSinhVien(ma)) {
					tableModel.removeRow(r);
					xoaTrang();
				}
			}
		} else if (o.equals(btnSua)) {
			int r = table.getSelectedRow();
			if (r >= 0) {
				SinhVien sv = rever();
				if (dssv.suaSinhVien(sv)) {
					table.setValueAt(txthoTen.getText(), r, 1);
//					table.setValueAt(cbxPhai.getSelectedIcon(), r, 2);
					table.setValueAt(txtTuoi.getText(), r, 3);
					table.setValueAt(cboPhong.getSelectedItem(), r, 4);
					table.setValueAt(txtTienLuong.getText(), r, 5);
				}
			}
		} else if (o.equals(btnTim)) {
			Dao_SinhVien dssv = new Dao_SinhVien();
			List<SinhVien> list;
			list = dssv.getSinhVienByMa(txtTim.getText());
			if (txtTim.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập mã cần tìm");
			} else if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "Khong tim thay");
			} else {
				tableModel.getDataVector().removeAllElements();
				for (SinhVien sinhVien : list) {
					tableModel.addRow(new Object[] { sinhVien.getMaSV(), sinhVien.getHoTen(), sinhVien.isPhai(),
							sinhVien.getTuoi(), sinhVien.getLopHoc().getMaLop(), sinhVien.getEmail() });
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int r = table.getSelectedRow();
		txtMaNV.setText(table.getValueAt(r, 0).toString());
		txthoTen.setText(table.getValueAt(r, 1).toString());
		cbxPhai.setSelected(table.getValueAt(r, 2).toString() == "true" ? true : false);
		txtTuoi.setText(table.getValueAt(r, 3).toString());
		cboPhong.setSelectedItem(table.getValueAt(r, 4).toString());
		txtTienLuong.setText(table.getValueAt(r, 5).toString());
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
		Dao_LopHoc dslh = new Dao_LopHoc();
		List<LopHoc> list = dslh.getAllLopHoc();
		for (LopHoc lopHoc : list) {
			cboPhong.addItem(lopHoc.getMaLop());
		}
	}

	private void updateTableData() {
		Dao_SinhVien dssv = new Dao_SinhVien();
		List<SinhVien> list = dssv.getAllSinhVien();
		for (SinhVien sinhVien : list) {
			tableModel.addRow(new Object[] { sinhVien.getMaSV(), sinhVien.getHoTen(), sinhVien.isPhai(),
					sinhVien.getTuoi(), sinhVien.getLopHoc().getMaLop(), sinhVien.getEmail() });
		}
	}

	private SinhVien rever() {
		String masv = txtMaNV.getText().toString();
		String hoten = txthoTen.getText().toString();
		boolean phai = Boolean.parseBoolean(cbxPhai.getText());
		int tuoi = Integer.parseInt(txtTuoi.getText());
		String gV = cboPhong.getSelectedItem().toString();
		String email = txtTienLuong.getText().toString();
		return new SinhVien(masv, hoten, phai, tuoi, new LopHoc(gV), email);

	}

	private void xoaTrang() {
		txtMaNV.setText("");
		txthoTen.setText("");
		cbxPhai.setSelected(false);
		txtTuoi.setText("");
		cboPhong.setSelectedItem(null);
		txtTienLuong.setText("");
	}

	private boolean vaildData() {
		String ma = txtMaNV.getText().trim();
		String hoten = txthoTen.getText().trim();
		String tuoi = txtTuoi.getText().trim();
		String email = txtTienLuong.getText().trim();

		if (ma.equals("") || hoten.equals("") || tuoi.equals("") || cboPhong.getSelectedItem().toString() == null
				|| email.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
			return false;
		}
		if (!(ma.length() > 0 && ma.matches("^20[0-9]{3}$"))) {
			JOptionPane.showMessageDialog(null, "Mã sinh viên gồm 5 số bắt đầu là số 20");
			return false;
		}
		if (tuoi.length() > 0) {
			try {
				int x = Integer.parseInt(txtTuoi.getText());
				if (x <= 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập tuổi lớn hơn 0");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên");
				return false;
			}

		}
		if (!(email.length() > 0 && email.matches("^(\\w+[-./]{0,}\\w+)*@gmail.com"))) {
			JOptionPane.showMessageDialog(null, "Đúng định dạng email");
			return false;
		}

		return true;
	}
}
