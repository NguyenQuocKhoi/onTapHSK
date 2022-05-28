package gui;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.Database;
import dao.GiaoVien_DAO;
import dao.LopHoc_DAO;
import entity.GiaoVien;
import entity.LopHoc;

public class FrmLopHoc extends JFrame  implements ActionListener,MouseListener{
	 
	private JTextField txtMaLop ;
	private JTextField txtTenLop;
	private JTextField txtSiSo;
	private JComboBox cboGVCN;
	private JButton btnThem;
	private JButton btnLuu;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnTimGV,btnTimLop;
	private DefaultTableModel dataModel;
	private JTable table;
	LopHoc_DAO dslh = new LopHoc_DAO();
	public FrmLopHoc() {
		setTitle("Thông tin lớp học");
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Box b, b1, b2, b3, b4, b44, b5, b6, b7;
		//Dùng Box layout
		add(b = Box.createVerticalBox()); //Box theo chi�?u d�?c
		b.add(Box.createVerticalStrut(10)); //Tạo khoảng cách theo chi�?u d�?c
		b.add(b1 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); //b1 -> b7: Box theo chi�?u ngang
		b.add(b2 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 
		b.add(b3 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 
		b.add(b4 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 
		b.add(b44 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 
		b.add(b5 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 
		b.add(b6 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 
		b.add(b7 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10)); 

		JLabel lblTieuDe, lblMaLop, lblTenLop, lblGVCN, lblSiSo;
		b1.add(lblTieuDe = new JLabel("THÔNG TIN LỚP HỌC", JLabel.CENTER));
		lblTieuDe.setFont(new Font("Arial", Font.BOLD, 26));

		b2.add(lblMaLop = new JLabel("Mã lớp: ", JLabel.RIGHT)); b2.add(txtMaLop = new JTextField());
		b3.add(lblTenLop = new JLabel("Tên lớp: ", JLabel.RIGHT)); b3.add(txtTenLop = new JTextField());
		b4.add(lblGVCN = new JLabel("Giáo viên chủ nhiệm: ", JLabel.RIGHT)); 
		b4.add(cboGVCN = new JComboBox<String>());
		cboGVCN.setEditable(false);
		b44.add(lblSiSo = new JLabel("Sỉ số : ", JLabel.RIGHT)); b44.add(txtSiSo = new JTextField());
		
		lblMaLop.setPreferredSize(lblGVCN.getPreferredSize());
		lblTenLop.setPreferredSize(lblGVCN.getPreferredSize());
		lblSiSo.setPreferredSize(lblGVCN.getPreferredSize());
		
		b5.add(btnThem = new JButton("Thêm"));
		b5.add(btnLuu= new JButton("Lưu"));
		b5.add(btnSua = new JButton("Sửa"));
		b5.add(btnXoa = new JButton("Xóa"));
		b5.add(btnTimGV = new JButton("Tìm theo mã giáo viên"));
		b5.add(btnTimLop = new JButton("Tìm theo mã lớp"));

		String[] headers = {"Mã lớp", "Tên lớp", "Giáo viên CN", "Sỉ số"};
		dataModel = new DefaultTableModel(headers , 0);
		JScrollPane scroll;
		b6.add(scroll = new JScrollPane(table = new JTable(dataModel)));
		scroll.setBorder(BorderFactory.createTitledBorder("Danh sách lớp học"));

		b7.add(Box.createHorizontalStrut(600));
		
		
		btnThem.addActionListener(this);
		btnLuu.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnTimGV.addActionListener(this);
		btnTimLop.addActionListener(this);
		table.addMouseListener(this);
		
		//Khi chương trình chạy, nạp toàn bộ danh sách lớp h�?c lên bảng
		Database.getInstance().connect();
		updateComboBox();
		updateTableData();
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnThem)){
			xoaRongTextfields();
		}
		else if(o.equals(btnLuu)){
			LopHoc lh = reverSPFromTextFile();
			if(dslh.cresate(lh)){
				Object[] rowData = {txtMaLop.getText(), txtTenLop.getText(),
						cboGVCN.getSelectedItem().toString(), txtSiSo.getText()};
				dataModel.addRow(rowData);
			}
		}
		 else if(o.equals(btnSua)){
				int row = table.getSelectedRow();
				if(row >= 0){
					    LopHoc lh = reverSPFromTextFile();
						if(dslh.update(lh)){
						table.setValueAt(txtTenLop.getText(), row, 1);
						table.setValueAt(cboGVCN.getSelectedItem().toString(), row, 2);
						table.setValueAt(txtSiSo.getText(), row, 3);
						}						
				}
			}
		else if(o.equals(btnXoa)){
			int row = table.getSelectedRow();
			if(row >= 0){
					String maLop = (String) table.getValueAt(row, 0);
					if(dslh.delete(maLop)){
						dataModel.removeRow(row);
						xoaRongTextfields();
					}
			}
	    }
		else if(o.equals(btnTimLop)){
			LopHoc_DAO ds = new LopHoc_DAO();
			List<LopHoc> list = ds.getLopTheoMaLop(txtMaLop.getText());
			
			if (txtMaLop.getText().equals("")) 
				JOptionPane.showMessageDialog(this, "Vui long nhap ma lop can tim!");
			else if(list.size()==0) 
				   JOptionPane.showMessageDialog(this, "Không tìm thấy!!!");
			else 
			 {	
				dataModel.getDataVector().removeAllElements();;
				for(LopHoc s : list){
				  String [] rowData = {s.getMaLop(), s.getTenLop(), s.getGiaoVien() + "", s.getSiso() + ""};
				  dataModel.addRow(rowData);			
			}
			table.setModel(dataModel);
	   }
   }
	
 }
	private void xoaRongTextfields() {
		txtMaLop.setText("");
		txtTenLop.setText("");
		cboGVCN.setSelectedItem(null);
		txtSiSo.setText("");
		txtMaLop.requestFocus();
	}
	//đưa dữ liệu vào Combobox
	 private void updateComboBox() {
	    	GiaoVien_DAO ds = new GiaoVien_DAO();
			List<GiaoVien> list = ds.docTuBang();
			
			for(GiaoVien s : list)
			 cboGVCN.addItem(s.getMagv());			
		}
	 private LopHoc reverSPFromTextFile() {
			String malop = txtMaLop.getText().toString();
			String tenlop = txtTenLop.getText().toString();
			String gv = cboGVCN.getSelectedItem().toString();
			int siso = Integer.parseInt(txtSiSo.getText());
			//kiem tra du lieu
			
			return new LopHoc(malop, tenlop, new GiaoVien(gv),siso);
		}
	//đưa dữ liệu vào table
		 private void updateTableData() {
		    	LopHoc_DAO ds = new LopHoc_DAO();
				List<LopHoc> list = ds.docTuBang();
				for(LopHoc s : list){
					String [] rowData = {s.getMaLop(), s.getTenLop(), s.getGiaoVien() + "", s.getSiso() + ""};
					dataModel.addRow(rowData);			
				}
				table.setModel(dataModel);
			}
		 public void mouseClicked(MouseEvent e){	
				int row = table.getSelectedRow(); // lay dong dang chon tren table
				txtMaLop.setText(table.getValueAt(row,0).toString());
				txtTenLop.setText(table.getValueAt(row, 1).toString());		
				cboGVCN.setSelectedItem(table.getValueAt(row, 2).toString());
				txtSiSo.setText(table.getValueAt(row, 3).toString());		
			}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	

}
