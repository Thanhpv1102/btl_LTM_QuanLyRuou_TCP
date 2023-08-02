/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ClientCtr;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Nuocsx;
import model.Ruou;

/**
 *
 * @author phamv
 */
public class ClientView extends javax.swing.JFrame {

    private List<Nuocsx> listNSX;
    private List<Ruou> listR;
    private List<String> listItem;
    private DefaultTableModel tmN, tmR;
    private String duongdananh = "E:\\asset\\default.png";

    /**
     * Creates new form Main
     */
    public ClientView() {
        initComponents();
        setLocationRelativeTo(null);
        tmN = (DefaultTableModel) tbNuocsx.getModel();
        tmR = (DefaultTableModel) tbRuou.getModel();
        getItem();
        eventNuocsx();
        eventRuou();
    }

    private void viewAllN() {
        ClientCtr ctr = new ClientCtr();
        Nuocsx n = new Nuocsx();
        ctr.openSocket();
        ctr.sendCode("1");
        n.setAction("viewallN");
        ctr.sendNuocsx(n);
        listNSX = ctr.getListNSX();
        tmN.setRowCount(0);
        for (Nuocsx nn : listNSX) {
            tmN.addRow(nn.toObject());
        }
        ctr.closeConnection();
    }

    private void viewAllR() {
        ClientCtr ctr = new ClientCtr();
        Ruou r = new Ruou();
        ctr.openSocket();
        ctr.sendCode("2");
        r.setAction("viewallR");
        ctr.sendRuou(r);
        listR = ctr.getListRuou();
        tmR.setRowCount(0);
        for (Ruou rr : listR) {
            tmR.addRow(rr.toObject());
        }
        ctr.closeConnection();
    }

    private void reset() {
        maN.setText("");
        tenN.setText("");
        motaN.setText("");
    }

    private void eventNuocsx() {
        themNBT.addActionListener(e -> {
            Nuocsx n = new Nuocsx();
            if (maN.getText().length() != 0 && tenN.getText().length() != 0 && motaN.getText().length() != 0) {
                n.setMa(Integer.parseInt(maN.getText()));
                n.setTen(tenN.getText());
                n.setMoTa(motaN.getText());
                n.setAction("addN");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("1");
                ctr.sendNuocsx(n);
                String res = ctr.getResult();
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Thêm nước sản xuất thành công!");
                    reset();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Thêm thất bại! Mã hoặc tên đã tồn tại.");
                }
                ctr.closeConnection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            }
            getItem();
        });

        xemNBT.addActionListener(e -> {
            viewAllN();
        });

        suaNBT.addActionListener(e -> {
            Nuocsx n = new Nuocsx();
            if (maN.getText().matches("\\d+") && tenN.getText().length() != 0 && motaN.getText().length() != 0) {
                n.setMa(Integer.parseInt(maN.getText()));
                n.setTen(tenN.getText());
                n.setMoTa(motaN.getText());
                n.setAction("updateN");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("1");
                ctr.sendNuocsx(n);
                String res = ctr.getResult();
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Sửa thành công!");
                    reset();
                    viewAllN();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sửa thất bại! Mã hoặc tên bị trùng.");
                }
                ctr.closeConnection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            }
            getItem();
        });

        xoaNBT.addActionListener(e -> {
            int row = tbNuocsx.getSelectedRow();
            if (row >= 0 && row < tbNuocsx.getRowCount()) {
                Nuocsx n = new Nuocsx();
                n.setMa(Integer.parseInt(maN.getText()));
                n.setAction("deleteN");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("1");
                ctr.sendNuocsx(n);
                String res = ctr.getResult();
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
                    reset();
                    viewAllN();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Xóa thất bại!");
                }
                ctr.closeConnection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Chọn nước sản xuất cần xóa!");
            }
            getItem();
        });

        boquaNBT.addActionListener(e -> {
            reset();
        });
        timNBT.addActionListener(e -> {
            Nuocsx n = new Nuocsx();
            try {
                if (timN.getText().length() != 0) {
                    n.setTen(timN.getText());
                    n.setAction("searchN");
                    ClientCtr ctr = new ClientCtr();
                    ctr.openSocket();
                    ctr.sendCode("1");
                    ctr.sendNuocsx(n);
                    listNSX = ctr.getListNSX();
                    tmN.setRowCount(0);
                    for (Nuocsx nn : listNSX) {
                        tmN.addRow(nn.toObject());
                    }
                    ctr.closeConnection();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tên nước sản xuất cần tìm!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }

    private void getItem() {
        Ruou r = new Ruou();
        r.setAction("getItem");
        ClientCtr ctr = new ClientCtr();
        ctr.openSocket();
        ctr.sendCode("2");
        ctr.sendRuou(r);
        listItem = ctr.getItem();
        nuocsxCB.removeAllItems();
        nuocCB.removeAllItems();
        nuocCB.addItem("all");
        for (String i : listItem) {
            nuocsxCB.addItem(i);
            nuocCB.addItem(i);
        }
        ctr.closeConnection();
    }

    private void refresh() {
        maR.setText("");
        tenR.setText("");
        nongdoR.setText("");
        namR.setText("");
        nuocsxCB.setSelectedIndex(0);
        anhR.setIcon(ResizeImage("E:\\asset\\default.png"));
    }

    private void eventRuou() {
        boquaRBT.addActionListener(e -> {
            refresh();
        });
        themRBT.addActionListener(e -> {
            Ruou r = new Ruou();
            if (maR.getText().matches("\\d+") && tenR.getText().length() != 0 && nongdoR.getText().matches("\\d+(\\.\\d*)?") && namR.getText().matches("\\d+")) {
                r.setMa(Integer.parseInt(maR.getText()));
                r.setTen(tenR.getText());
                r.setNongDo(Double.parseDouble(nongdoR.getText()));
                r.setSoNam(Integer.parseInt(namR.getText()));
                r.setNuocSanXuat(nuocsxCB.getSelectedItem().toString());
                r.setHinhAnh(duongdananh);
                r.setAction("addR");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("2");
                ctr.sendRuou(r);
                String res = ctr.getResult();
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Thêm rượu thành công!");
                    refresh();

                } else {
                    JOptionPane.showMessageDialog(rootPane, "Thông tin chưa chính xác, vui lòng kiểm tra lại!");
                }
                ctr.closeConnection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            }
            getItem();
        });

        xemRBT.addActionListener(e -> {
            viewAllR();
        });
        suaRBT.addActionListener(e -> {
            Ruou r = new Ruou();
            if (maR.getText().matches("\\d+") && tenR.getText().length() != 0 && nongdoR.getText().matches("\\d+(\\.\\d*)?") && namR.getText().matches("\\d+")) {
                r.setMa(Integer.parseInt(maR.getText()));
                r.setTen(tenR.getText());
                r.setNongDo(Double.parseDouble(nongdoR.getText()));
                r.setSoNam(Integer.parseInt(namR.getText()));
                r.setNuocSanXuat(nuocsxCB.getSelectedItem().toString());
                r.setHinhAnh(duongdananh);
                r.setAction("updateR");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("2");
                ctr.sendRuou(r);
                String res = ctr.getResult();
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Sửa thành công!");
                    refresh();
                    viewAllR();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sửa thất bại!");
                }
                ctr.closeConnection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Chọn loại rượu cần sửa!");
            }

            getItem();
        });

        xoaRBT.addActionListener(e -> {
            int row = tbRuou.getSelectedRow();
            if (row >= 0 && row < tbRuou.getRowCount()) {
                Ruou r = new Ruou();
                r.setMa(Integer.parseInt(maR.getText()));
                r.setAction("deleteR");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("2");
                ctr.sendRuou(r);
                String res = ctr.getResult();
                if (res.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
                    refresh();
                    viewAllR();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Xóa thất bại!");
                }
                ctr.closeConnection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Chọn loại rượu cần xóa!");
            }

            getItem();
        });
        timRBT.addActionListener(e -> {
            Ruou r = new Ruou();
            try {
                r.setNongdoSearch(nongdoCB.getSelectedItem().toString());
                r.setNamsxSearch(namsxCB.getSelectedItem().toString());
                r.setNuocSanXuat(nuocCB.getSelectedItem().toString());
                r.setAction("searchR");
                ClientCtr ctr = new ClientCtr();
                ctr.openSocket();
                ctr.sendCode("2");
                ctr.sendRuou(r);
                listR = ctr.getListRuou();
                tmR.setRowCount(0);
                for (Ruou rr : listR) {
                    tmR.addRow(rr.toObject());
                }
                ctr.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        maN = new javax.swing.JTextField();
        tenN = new javax.swing.JTextField();
        motaN = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNuocsx = new javax.swing.JTable();
        timN = new javax.swing.JTextField();
        timNBT = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        themNBT = new javax.swing.JButton();
        suaNBT = new javax.swing.JButton();
        xoaNBT = new javax.swing.JButton();
        boquaNBT = new javax.swing.JButton();
        xemNBT = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nuocsxCB = new javax.swing.JComboBox<>();
        anhRBT = new javax.swing.JButton();
        maR = new javax.swing.JTextField();
        tenR = new javax.swing.JTextField();
        nongdoR = new javax.swing.JTextField();
        namR = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        anhR = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        themRBT = new javax.swing.JButton();
        suaRBT = new javax.swing.JButton();
        xoaRBT = new javax.swing.JButton();
        xemRBT = new javax.swing.JButton();
        boquaRBT = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbRuou = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        timRBT = new javax.swing.JButton();
        nongdoCB = new javax.swing.JComboBox<>();
        namsxCB = new javax.swing.JComboBox<>();
        nuocCB = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("QUAN LY RUOU");

        jLabel2.setText("Ma:");

        jLabel3.setText("Ten:");

        jLabel4.setText("Mo ta:");

        maN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maNActionPerformed(evt);
            }
        });

        tenN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenNActionPerformed(evt);
            }
        });

        motaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                motaNActionPerformed(evt);
            }
        });

        tbNuocsx.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Ma", "Ten", "Mo ta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbNuocsx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNuocsxMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbNuocsx);

        timN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timNActionPerformed(evt);
            }
        });

        timNBT.setText("Tim kiem");

        themNBT.setText("Them");
        themNBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNBTActionPerformed(evt);
            }
        });

        suaNBT.setText("Sua");
        suaNBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaNBTActionPerformed(evt);
            }
        });

        xoaNBT.setText("Xoa");
        xoaNBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaNBTActionPerformed(evt);
            }
        });

        boquaNBT.setText("Bo qua");
        boquaNBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boquaNBTActionPerformed(evt);
            }
        });

        xemNBT.setText("Xem CSDL");
        xemNBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xemNBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(themNBT)
                    .addComponent(boquaNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(suaNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xoaNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(xemNBT, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themNBT)
                    .addComponent(suaNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boquaNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(xemNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jLabel5.setText("Nhap ten nuoc san xuat:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(maN, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(tenN)
                            .addComponent(motaN)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(timN, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(timNBT)
                        .addContainerGap(182, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(timN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(198, 198, 198))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(maN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tenN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(motaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Quan ly nuoc san xuat", jPanel2);

        jLabel6.setText("Ma:");

        jLabel7.setText("Ten:");

        jLabel8.setText("Nong do:");

        jLabel9.setText("So nam:");

        nuocsxCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        anhRBT.setText("Chon anh:");
        anhRBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anhRBTActionPerformed(evt);
            }
        });

        tenR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenRActionPerformed(evt);
            }
        });

        nongdoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nongdoRActionPerformed(evt);
            }
        });

        namR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namRActionPerformed(evt);
            }
        });

        jLabel10.setText("Nuoc san xuat:");

        anhR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/default.png"))); // NOI18N

        themRBT.setText("Them");
        themRBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themRBTActionPerformed(evt);
            }
        });

        suaRBT.setText("Sua");

        xoaRBT.setText("Xoa");

        xemRBT.setText("Xem CSDL");

        boquaRBT.setText("bo qua");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(themRBT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(suaRBT))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(boquaRBT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xoaRBT)))
                .addGap(14, 14, 14))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(xemRBT)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themRBT)
                    .addComponent(suaRBT))
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaRBT)
                    .addComponent(boquaRBT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(xemRBT)
                .addGap(20, 20, 20))
        );

        tbRuou.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Ma", "Ten", "Nong do", "So nam", "Nuoc sx", "hinh anh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbRuou.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRuouMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbRuou);

        jLabel12.setText("---Tim kiem Ruou---");

        jLabel13.setText("Chon nong do:");

        jLabel14.setText("Chon nam sx:");

        timRBT.setText("Tim kiem");

        nongdoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "all", "duoi 5%", "tu 5% den 15%", "tren 15%", " " }));

        namsxCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "all", "truoc nam 2000", "tu nam 2000 den 2015", "tu 2015 den nay" }));

        nuocCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Chon nuoc sx:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(nongdoCB, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(namsxCB, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuocCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(82, 82, 82)
                .addComponent(timRBT)
                .addContainerGap(221, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(nongdoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(namsxCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timRBT))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuocCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(anhRBT)
                                .addGap(36, 36, 36)
                                .addComponent(anhR))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tenR)
                                    .addComponent(maR)
                                    .addComponent(nongdoR)
                                    .addComponent(namR)
                                    .addComponent(nuocsxCB, 0, 182, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(311, 311, 311))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(maR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tenR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(nongdoR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(namR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nuocsxCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(anhR, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(anhRBT)))
                        .addGap(26, 26, 26)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Quan ly ruou", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(457, 457, 457)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1079, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void motaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_motaNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_motaNActionPerformed

    private void tenNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenNActionPerformed

    private void maNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maNActionPerformed

    private void suaNBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaNBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suaNBTActionPerformed

    private void timNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timNActionPerformed

    private void xoaNBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaNBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xoaNBTActionPerformed

    private void themNBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themNBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_themNBTActionPerformed

    private void boquaNBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boquaNBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boquaNBTActionPerformed

    private void xemNBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xemNBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xemNBTActionPerformed

    private void tbNuocsxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNuocsxMouseClicked
        // TODO add your handling code here:
        int row = tbNuocsx.getSelectedRow();
        if (row >= 0 && row < tbNuocsx.getRowCount()) {
            maN.setText(tmN.getValueAt(row, 0).toString());
            tenN.setText(tmN.getValueAt(row, 1).toString());
            motaN.setText(tmN.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_tbNuocsxMouseClicked

    private void tenRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenRActionPerformed

    private void nongdoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nongdoRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nongdoRActionPerformed

    private void namRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namRActionPerformed

    private void themRBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themRBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_themRBTActionPerformed

    private void anhRBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anhRBTActionPerformed
        try {
            JFileChooser f = new JFileChooser("E:\\asset");
            f.setDialogTitle("Chon anh");
            f.showOpenDialog(null);
            File ftenanh = f.getSelectedFile();
            duongdananh = ftenanh.getAbsolutePath();
            if (duongdananh != null) {
                anhR.setIcon(ResizeImage(String.valueOf(duongdananh)));
            } else {
                JOptionPane.showMessageDialog(rootPane, "Ban chua chon anh");
            }
        } catch (Exception e) {
            System.out.println("Chua chon anh");
        }
    }//GEN-LAST:event_anhRBTActionPerformed

    private void tbRuouMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRuouMouseClicked
        // TODO add your handling code here:
        int row = tbRuou.getSelectedRow();
        if (row >= 0 && row < tbRuou.getRowCount()) {
            maR.setText(tmR.getValueAt(row, 0).toString());
            tenR.setText(tmR.getValueAt(row, 1).toString());
            nongdoR.setText(tmR.getValueAt(row, 2).toString());
            namR.setText(tmR.getValueAt(row, 3).toString());
            for (int i = 0; i < nuocsxCB.getItemCount(); i++) {
                if (nuocsxCB.getItemAt(i).equals(tmR.getValueAt(row, 4).toString())) {
                    nuocsxCB.setSelectedIndex(i);
                }
            }
            anhR.setIcon(ResizeImage(tmR.getValueAt(row, 5).toString()));
        }
    }//GEN-LAST:event_tbRuouMouseClicked
    public ImageIcon ResizeImage(String ImagePath) {
        ImageIcon myImage = new ImageIcon(ImagePath);
        Image img = myImage.getImage();
        Image newImg = img.getScaledInstance(anhR.getWidth(), anhR.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anhR;
    private javax.swing.JButton anhRBT;
    private javax.swing.JButton boquaNBT;
    private javax.swing.JButton boquaRBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField maN;
    private javax.swing.JTextField maR;
    private javax.swing.JTextField motaN;
    private javax.swing.JTextField namR;
    private javax.swing.JComboBox<String> namsxCB;
    private javax.swing.JComboBox<String> nongdoCB;
    private javax.swing.JTextField nongdoR;
    private javax.swing.JComboBox<String> nuocCB;
    private javax.swing.JComboBox<String> nuocsxCB;
    private javax.swing.JButton suaNBT;
    private javax.swing.JButton suaRBT;
    private javax.swing.JTable tbNuocsx;
    private javax.swing.JTable tbRuou;
    private javax.swing.JTextField tenN;
    private javax.swing.JTextField tenR;
    private javax.swing.JButton themNBT;
    private javax.swing.JButton themRBT;
    private javax.swing.JTextField timN;
    private javax.swing.JButton timNBT;
    private javax.swing.JButton timRBT;
    private javax.swing.JButton xemNBT;
    private javax.swing.JButton xemRBT;
    private javax.swing.JButton xoaNBT;
    private javax.swing.JButton xoaRBT;
    // End of variables declaration//GEN-END:variables
}
