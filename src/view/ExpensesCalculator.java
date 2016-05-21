package view;

import controller.Controller;
import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class ExpensesCalculator extends javax.swing.JFrame {

    /**
     * Creates new form ExpensesCalculator
     */
    private Controller controller;

    public ExpensesCalculator() {
        controller = new Controller();
        initComponents();
        initMonths();
        initCategories();
        initTypes();
        fillTransactionsTable( controller.getMonthTransactions() );
        fillMonthsTable( controller.getMonths() );
        fillCategoriesTable( controller.getCategories() );
        this.setLocationRelativeTo( null );
        //jLayeredPaneViewTransactions.setVisible( false );
        jLayeredPaneAddTransaction.setVisible( false );
        jLayeredPaneViewMonths.setVisible( false );
        jLayeredPaneViewCategories.setVisible( false );
        jLabelTransactionId.setVisible( false );
        jLabelAddMonthId.setVisible( false );
    }

    private void initMonths() {
        List<Month> allMonths = controller.getMonths();
        String[] months = new String[ allMonths.size() + 1 ];
        months[ 0 ] = "-ALL-";
        for ( int i = 1; i < allMonths.size() + 1; i++ ) {
            months[ i ] = allMonths.get( i - 1 ).getName();
        }

        String[] monthsForAddTransactionFrame = Arrays.copyOfRange( months, 1, months.length );

        jComboBoxMonths.setModel( new javax.swing.DefaultComboBoxModel( months ) );
        jComboBoxMonthsAddTransaction.setModel( new javax.swing.DefaultComboBoxModel( monthsForAddTransactionFrame ) );
    }

    private void initCategories() {
        List<Category> allCategories = controller.getCategories();
        String[] categories = new String[ allCategories.size() + 1 ];
        categories[ 0 ] = "-ALL-";
        for ( int i = 1; i < allCategories.size() + 1; i++ ) {
            categories[ i ] = allCategories.get( i - 1 ).getName();
        }
        String[] categoriesForAddTransactionFrame = Arrays.copyOfRange( categories, 1, categories.length );

        jComboBoxCategory.setModel( new javax.swing.DefaultComboBoxModel( categories ) );
        jComboBoxCategoryAddTransaction.setModel( new javax.swing.DefaultComboBoxModel( categoriesForAddTransactionFrame ) );
    }

    private void initTypes() {

        String[] types = new String[ 3 ];

        types[ 0 ] = "-ALL-";
        types[ 1 ] = "income";
        types[ 2 ] = "expense";

        jComboBoType.setModel( new javax.swing.DefaultComboBoxModel( types ) );
    }

    class CustomModel extends AbstractTableModel {

        String[] COLUMN_NAMES = new String[]{ "ID", "Name", "Type", "Month", "Category", "Amount", "Edit", "Delete" };

        private Object[][] result;
        private String[] columnNames;

        public CustomModel( Object[][] result, String[] columnNames ) {
            this.result = result;
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return result.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName( int col ) {
            return columnNames[ col ];
        }

        @Override
        public Object getValueAt( final int rowIndex, final int columnIndex ) {
            //return result[ rowIndex ][ columnIndex ];
            JButton button;
            switch ( columnIndex ) {
                case 6:
                    //Edit
                    button = new JButton( COLUMN_NAMES[ columnIndex ] );
                    button.addActionListener( new ActionListener() {
                        public void actionPerformed( ActionEvent arg0 ) {
//                            JOptionPane.showMessageDialog( JOptionPane.getFrameForComponent( button ),
//                                                           "Edit Button clicked for row " + rowIndex );
                            String transactionId = jTableMonthTransactions.getValueAt( rowIndex, 0 ).toString();
                            String transactionName = jTableMonthTransactions.getValueAt( rowIndex, 1 ).toString();
                            String transactionType = jTableMonthTransactions.getValueAt( rowIndex, 2 ).toString();
                            String transactionMonthName = jTableMonthTransactions.getValueAt( rowIndex, 3 ).toString();
                            String transactionCategoryName = jTableMonthTransactions.getValueAt( rowIndex, 4 ).toString();
                            String transactionAmount = jTableMonthTransactions.getValueAt( rowIndex, 5 ).toString();
                            jLayeredPaneViewTransactions.setVisible( false );
                            jLayeredPaneAddTransaction.setVisible( true );
                            jTextFieldTransactionName.setText( transactionName );
                            jComboBoxTypeAddTransaction.setSelectedItem( transactionType );
                            jComboBoxMonthsAddTransaction.setSelectedItem( transactionMonthName );
                            jComboBoxCategoryAddTransaction.setSelectedItem( transactionCategoryName );
                            jTextFieldAmount.setText( transactionAmount );
                            jLabelTransactionId.setText( transactionId );
                            jLabelTransactionId.setVisible( false );
                            jButtonAddTransaction.setText( "Edit" );
                        }
                    } );
                    return button;
                case 7:
                    //Delete
                    button = new JButton( COLUMN_NAMES[ columnIndex ] );
                    button.addActionListener( new ActionListener() {
                        public void actionPerformed( ActionEvent arg0 ) {
                            JOptionPane.showMessageDialog( JOptionPane.getFrameForComponent( button ),
                                                           "Delete Button clicked for row " + rowIndex
                                                           + " : " + jTableMonthTransactions.getValueAt( rowIndex, 1 ) );
                            controller.deleteMonthTransaction( Integer.parseInt(
                                    jTableMonthTransactions.getValueAt( rowIndex, 0 ).toString() ) );
                            fillTransactionsTable( controller.getMonthTransactions(
                                    jComboBoxMonths.getSelectedItem().toString(),
                                    jComboBoxCategory.getSelectedItem().toString(),
                                    jComboBoType.getSelectedItem().toString() ) );
                        }
                    } );
                    return button;
                default:
                    return result[ rowIndex ][ columnIndex ];
            }
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {
            return false;
        }
    }

    class CustomModelMonthsTable extends AbstractTableModel {

        String[] COLUMN_NAMES_MONTHS_TABLE = new String[]{ "ID", "Name", "Edit", "Delete" };

        private Object[][] result;
        private String[] columnNames;
        private boolean[][] editable_cells;

        public CustomModelMonthsTable( Object[][] result, String[] columnNames ) {
            this.result = result;
            this.columnNames = columnNames;
            this.editable_cells = new boolean[ getRowCount() ][ getColumnCount() ];
        }

        @Override
        public int getRowCount() {
            return result.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName( int col ) {
            return columnNames[ col ];
        }

        @Override
        public Object getValueAt( final int rowIndex, final int columnIndex ) {
            JButton button;
            switch ( columnIndex ) {
                case 3: //delete
                    button = new JButton( COLUMN_NAMES_MONTHS_TABLE[ columnIndex ] );
//                    Edit month
                    button.addActionListener( new ActionListener() {
                        public void actionPerformed( ActionEvent arg0 ) {
                            String monthId = jTableMonths.getValueAt( rowIndex, 0 ).toString();
                            String monthName = jTableMonths.getValueAt( rowIndex, 1 ).toString();
                            JOptionPane.showMessageDialog( JOptionPane.getFrameForComponent( button ),
                                                           "Delete Button clicked for row " + rowIndex
                                                           + " : " + jTableMonths.getValueAt( rowIndex, 1 ) );
                            controller.deleteMonth( Integer.parseInt(
                                    jTableMonths.getValueAt( rowIndex, 0 ).toString() ) );
                            fillMonthsTable( controller.getMonths() );

                        }
                    } );
                    return button;
                case 2: //update
                    button = new JButton( COLUMN_NAMES_MONTHS_TABLE[ columnIndex ] );
                    button.addActionListener( new ActionListener() {
                        public void actionPerformed( ActionEvent arg0 ) {
                            setCellEditable( rowIndex, 1, true );
                            jLabelAddMonthId.setText(
                                    jTableMonths.getValueAt( rowIndex, 0 ).toString() );
                            jTextFieldAddMonthName.setText(
                                    jTableMonths.getValueAt( rowIndex, 1 ).toString() );
                            jButtonAddMonth.setText( "Save changes" );
                        }
                    } );
                    return button;
                default:
                    return result[ rowIndex ][ columnIndex ];
            }
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {
            return this.editable_cells[ rowIndex ][ columnIndex ];
        }

        public void setCellEditable( int row, int col, boolean value ) {
            for ( int i = 0; i < getRowCount(); i++ ) {
                for ( int j = 0; j < getColumnCount(); j++ ) {
                    if ( i != row || j != col ) {
                        editable_cells[ i ][ j ] = !value;
                    } else {
                        editable_cells[ row ][ col ] = value;
                    }
                }
            }
//            this.fireTableCellUpdated( row, col );
        }
    }

    class CustomModelCategoriesTable extends AbstractTableModel {

        String[] COLUMN_NAMES_CATEGORIES_TABLE = new String[]{ "ID", "Name", "Edit", "Delete" };

        private Object[][] result;
        private String[] columnNames;

        public CustomModelCategoriesTable( Object[][] result, String[] columnNames ) {
            this.result = result;
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() {
            return result.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName( int col ) {
            return columnNames[ col ];
        }

        @Override
        public Object getValueAt( final int rowIndex, final int columnIndex ) {
            JButton button;
            switch ( columnIndex ) {
                case 3: //delete
                    button = new JButton( COLUMN_NAMES_CATEGORIES_TABLE[ columnIndex ] );
//                    Edit month
                    button.addActionListener( new ActionListener() {
                        public void actionPerformed( ActionEvent arg0 ) {
                            String categoryId = jTableCategories.getValueAt( rowIndex, 0 ).toString();
                            String categoryName = jTableCategories.getValueAt( rowIndex, 1 ).toString();
                            JOptionPane.showMessageDialog( JOptionPane.getFrameForComponent( button ),
                                                           "Delete Button clicked for row " + rowIndex
                                                           + " : " + jTableCategories.getValueAt( rowIndex, 1 ) );
                            controller.deleteCategory( Integer.parseInt(
                                    jTableCategories.getValueAt( rowIndex, 0 ).toString() ) );
                            fillCategoriesTable( controller.getCategories() );
                        }
                    } );
                    return button;
//                case 2: //update
//                    button = new JButton( COLUMN_NAMES_CATEGORIES_TABLE[ columnIndex ] );
//                    button.addActionListener( new ActionListener() {
//                        public void actionPerformed( ActionEvent arg0 ) {
//                            jLabelAddMonthId.setText(
//                                    jTableMonths.getValueAt( rowIndex, 0 ).toString() );
//                            jTextFieldAddMonthName.setText(
//                                    jTableMonths.getValueAt( rowIndex, 1 ).toString() );
//                            jButtonAddMonth.setText( "Save changes" );
//                        }
//                    } );
//                    return button;
                default:
                    return result[ rowIndex ][ columnIndex ];
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jLayeredPaneViewTransactions = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxMonths = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox();
        jComboBoType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableMonthTransactions = new javax.swing.JTable();
        jLayeredPaneAddTransaction = new javax.swing.JLayeredPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTransactionName = new javax.swing.JTextField();
        jComboBoxTypeAddTransaction = new javax.swing.JComboBox();
        jComboBoxMonthsAddTransaction = new javax.swing.JComboBox();
        jComboBoxCategoryAddTransaction = new javax.swing.JComboBox();
        jTextFieldAmount = new javax.swing.JTextField();
        jButtonAddTransaction = new javax.swing.JButton();
        jLabelInsertTransactionStatus = new javax.swing.JLabel();
        jLabelTransactionId = new javax.swing.JLabel();
        jLayeredPaneViewMonths = new javax.swing.JLayeredPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMonths = new javax.swing.JTable();
        jButtonAddMonth = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldAddMonthName = new javax.swing.JTextField();
        jLabelAddMonthId = new javax.swing.JLabel();
        jLayeredPaneViewCategories = new javax.swing.JLayeredPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCategories = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldAddCategoryName = new javax.swing.JTextField();
        jButtonAddCategory = new javax.swing.JButton();
        jLabelCategoryId = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemViewTransactions = new javax.swing.JMenuItem();
        jMenuItemAddTransaction = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemViewCategories = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPaneViewTransactions.setPreferredSize(new java.awt.Dimension(540, 360));

        jLabel1.setText("Month/Year");

        jComboBoxMonths.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Month1", "Month2", "Month3" }));
        jComboBoxMonths.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxMonthsItemStateChanged(evt);
            }
        });
        jComboBoxMonths.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jComboBoxMonthsMouseReleased(evt);
            }
        });
        jComboBoxMonths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMonthsActionPerformed(evt);
            }
        });
        jComboBoxMonths.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxMonthsPropertyChange(evt);
            }
        });

        jLabel2.setText("Transaction Category");

        jComboBoxCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Food", "Drinks", "Soviet" }));
        jComboBoxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoryActionPerformed(evt);
            }
        });

        jComboBoType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoTypeActionPerformed(evt);
            }
        });

        jLabel3.setText("Transaction Type");

        jTableMonthTransactions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Type", "Month", "Category", "Amount", "Edit/Delete"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableMonthTransactions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMonthTransactionsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableMonthTransactions);
        if (jTableMonthTransactions.getColumnModel().getColumnCount() > 0) {
            jTableMonthTransactions.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jLayeredPaneViewTransactionsLayout = new javax.swing.GroupLayout(jLayeredPaneViewTransactions);
        jLayeredPaneViewTransactions.setLayout(jLayeredPaneViewTransactionsLayout);
        jLayeredPaneViewTransactionsLayout.setHorizontalGroup(
            jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneViewTransactionsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPaneViewTransactionsLayout.createSequentialGroup()
                        .addGroup(jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jComboBoxMonths, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jLayeredPaneViewTransactionsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(84, 84, 84)
                                .addComponent(jLabel3))
                            .addGroup(jLayeredPaneViewTransactionsLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jComboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPaneViewTransactionsLayout.setVerticalGroup(
            jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneViewTransactionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(22, 22, 22)
                .addGroup(jLayeredPaneViewTransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMonths, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jLayeredPaneViewTransactions.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewTransactions.setLayer(jComboBoxMonths, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewTransactions.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewTransactions.setLayer(jComboBoxCategory, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewTransactions.setLayer(jComboBoType, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewTransactions.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewTransactions.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel4.setText("Name");

        jLabel5.setText("Type");

        jLabel6.setText("Month");

        jLabel7.setText("Category");

        jLabel8.setText("Amount");

        jTextFieldTransactionName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTransactionNameActionPerformed(evt);
            }
        });

        jComboBoxTypeAddTransaction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "income", "expense" }));
        jComboBoxTypeAddTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTypeAddTransactionActionPerformed(evt);
            }
        });

        jComboBoxMonthsAddTransaction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxCategoryAddTransaction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextFieldAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAmountActionPerformed(evt);
            }
        });

        jButtonAddTransaction.setText("Add");
        jButtonAddTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddTransactionActionPerformed(evt);
            }
        });

        jLabelInsertTransactionStatus.setText("Status :");

        jLabelTransactionId.setText("hidden; for id;  do not delete");

        javax.swing.GroupLayout jLayeredPaneAddTransactionLayout = new javax.swing.GroupLayout(jLayeredPaneAddTransaction);
        jLayeredPaneAddTransaction.setLayout(jLayeredPaneAddTransactionLayout);
        jLayeredPaneAddTransactionLayout.setHorizontalGroup(
            jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneAddTransactionLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jLayeredPaneAddTransactionLayout.createSequentialGroup()
                        .addComponent(jButtonAddTransaction)
                        .addGap(28, 28, 28)
                        .addComponent(jLabelInsertTransactionStatus))
                    .addGroup(jLayeredPaneAddTransactionLayout.createSequentialGroup()
                        .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(116, 116, 116)
                        .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxMonthsAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxTypeAddTransaction, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxCategoryAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAmount)))
                    .addGroup(jLayeredPaneAddTransactionLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(134, 134, 134)
                        .addComponent(jTextFieldTransactionName)))
                .addGap(38, 38, 38)
                .addComponent(jLabelTransactionId)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPaneAddTransactionLayout.setVerticalGroup(
            jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneAddTransactionLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldTransactionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTransactionId))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxTypeAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxMonthsAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxCategoryAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jLayeredPaneAddTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddTransaction)
                    .addComponent(jLabelInsertTransactionStatus))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jLayeredPaneAddTransaction.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jTextFieldTransactionName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jComboBoxTypeAddTransaction, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jComboBoxMonthsAddTransaction, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jComboBoxCategoryAddTransaction, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jTextFieldAmount, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jButtonAddTransaction, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jLabelInsertTransactionStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneAddTransaction.setLayer(jLabelTransactionId, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPaneViewMonths.setPreferredSize(new java.awt.Dimension(540, 360));

        jTableMonths.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Edit", "Delete"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMonths.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMonthsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableMonths);

        jButtonAddMonth.setText("Add month");
        jButtonAddMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddMonthActionPerformed(evt);
            }
        });

        jLabel9.setText("Month name");

        jTextFieldAddMonthName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddMonthNameActionPerformed(evt);
            }
        });

        jLabelAddMonthId.setText("for id; do not delete it");

        javax.swing.GroupLayout jLayeredPaneViewMonthsLayout = new javax.swing.GroupLayout(jLayeredPaneViewMonths);
        jLayeredPaneViewMonths.setLayout(jLayeredPaneViewMonthsLayout);
        jLayeredPaneViewMonthsLayout.setHorizontalGroup(
            jLayeredPaneViewMonthsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneViewMonthsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jLayeredPaneViewMonthsLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel9)
                .addGap(100, 100, 100)
                .addComponent(jTextFieldAddMonthName, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabelAddMonthId)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneViewMonthsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAddMonth)
                .addGap(34, 34, 34))
        );
        jLayeredPaneViewMonthsLayout.setVerticalGroup(
            jLayeredPaneViewMonthsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneViewMonthsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jLayeredPaneViewMonthsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldAddMonthName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAddMonthId))
                .addGap(27, 27, 27)
                .addComponent(jButtonAddMonth)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jLayeredPaneViewMonths.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewMonths.setLayer(jButtonAddMonth, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewMonths.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewMonths.setLayer(jTextFieldAddMonthName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewMonths.setLayer(jLabelAddMonthId, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCategoriesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableCategories);

        jLabel10.setText("Category name");

        jButtonAddCategory.setText("Add category");

        jLabelCategoryId.setText("hidden; do not delete it");

        javax.swing.GroupLayout jLayeredPaneViewCategoriesLayout = new javax.swing.GroupLayout(jLayeredPaneViewCategories);
        jLayeredPaneViewCategories.setLayout(jLayeredPaneViewCategoriesLayout);
        jLayeredPaneViewCategoriesLayout.setHorizontalGroup(
            jLayeredPaneViewCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneViewCategoriesLayout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jLayeredPaneViewCategoriesLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel10)
                .addGap(86, 86, 86)
                .addComponent(jTextFieldAddCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jLabelCategoryId)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneViewCategoriesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAddCategory)
                .addGap(37, 37, 37))
        );
        jLayeredPaneViewCategoriesLayout.setVerticalGroup(
            jLayeredPaneViewCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPaneViewCategoriesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jLayeredPaneViewCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldAddCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCategoryId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jButtonAddCategory)
                .addGap(29, 29, 29))
        );
        jLayeredPaneViewCategories.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewCategories.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewCategories.setLayer(jTextFieldAddCategoryName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewCategories.setLayer(jButtonAddCategory, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPaneViewCategories.setLayer(jLabelCategoryId, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jMenuBar1.setPreferredSize(new java.awt.Dimension(228, 25));

        jMenu1.setText("Transactions");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItemViewTransactions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemViewTransactions.setText("View");
        jMenuItemViewTransactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewTransactionsActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemViewTransactions);

        jMenuItemAddTransaction.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemAddTransaction.setText("Add");
        jMenuItemAddTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddTransactionActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemAddTransaction);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Months");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("View");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Categories");

        jMenuItemViewCategories.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemViewCategories.setText("View");
        jMenuItemViewCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemViewCategoriesActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemViewCategories);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPaneViewTransactions, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addComponent(jLayeredPaneAddTransaction)
            .addComponent(jLayeredPaneViewMonths, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
            .addComponent(jLayeredPaneViewCategories)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPaneViewTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPaneAddTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPaneViewMonths, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPaneViewCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxMonthsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxMonthsMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMonthsMouseReleased

    private void jComboBoxMonthsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxMonthsItemStateChanged

    }//GEN-LAST:event_jComboBoxMonthsItemStateChanged

    private void jComboBoxMonthsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBoxMonthsPropertyChange

    }//GEN-LAST:event_jComboBoxMonthsPropertyChange

    private void jComboBoxMonthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMonthsActionPerformed
        System.out.println( "Month Clicked : " + jComboBoxMonths.getSelectedItem() );

        List<MonthTransaction> monthTransactions;
        monthTransactions = controller.getMonthTransactions(
                jComboBoxMonths.getSelectedItem().toString(),
                jComboBoxCategory.getSelectedItem().toString(),
                jComboBoType.getSelectedItem().toString() );
        fillTransactionsTable( monthTransactions );
    }//GEN-LAST:event_jComboBoxMonthsActionPerformed

    private void jComboBoxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoryActionPerformed
        System.out.println( "Category Clicked : " + jComboBoxCategory.getSelectedItem().toString() );

        List<MonthTransaction> monthTransactions;
        monthTransactions = controller.getMonthTransactions(
                jComboBoxMonths.getSelectedItem().toString(),
                jComboBoxCategory.getSelectedItem().toString(),
                jComboBoType.getSelectedItem().toString() );
        fillTransactionsTable( monthTransactions );
    }//GEN-LAST:event_jComboBoxCategoryActionPerformed

    private void jComboBoTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoTypeActionPerformed
        System.out.println( "Type Clicked : " + jComboBoType.getSelectedItem() );

        List<MonthTransaction> monthTransactions;
        monthTransactions = controller.getMonthTransactions(
                jComboBoxMonths.getSelectedItem().toString(),
                jComboBoxCategory.getSelectedItem().toString(),
                jComboBoType.getSelectedItem().toString() );
        fillTransactionsTable( monthTransactions );
    }//GEN-LAST:event_jComboBoTypeActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jTextFieldTransactionNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTransactionNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTransactionNameActionPerformed

    private void jComboBoxTypeAddTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTypeAddTransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTypeAddTransactionActionPerformed

    private void jTextFieldAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAmountActionPerformed

    private void jMenuItemViewTransactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewTransactionsActionPerformed
        jLayeredPaneViewTransactions.setVisible( true );
        jLayeredPaneAddTransaction.setVisible( false );
        jLayeredPaneViewMonths.setVisible( false );
        jLayeredPaneViewCategories.setVisible( false );
        fillTransactionsTable( controller.getMonthTransactions() );
    }//GEN-LAST:event_jMenuItemViewTransactionsActionPerformed

    private void jMenuItemAddTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddTransactionActionPerformed
        jLayeredPaneViewTransactions.setVisible( false );
        jLayeredPaneAddTransaction.setVisible( true );
        jLayeredPaneViewMonths.setVisible( false );
        jLayeredPaneViewCategories.setVisible( false );
        jTextFieldTransactionName.setText( "" );
        jTextFieldAmount.setText( "" );
        jComboBoxCategoryAddTransaction.setSelectedIndex( 0 );
        jComboBoxMonthsAddTransaction.setSelectedIndex( 0 );
        jComboBoxTypeAddTransaction.setSelectedIndex( 0 );
    }//GEN-LAST:event_jMenuItemAddTransactionActionPerformed

    private void jButtonAddTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddTransactionActionPerformed
        boolean status = false;
        if ( jButtonAddTransaction.getText().equals( "Add" ) ) {
            status = controller.
                    addMonthTransactions( jTextFieldTransactionName.getText(),
                                          jComboBoxMonthsAddTransaction.getSelectedItem().toString(),
                                          jComboBoxCategoryAddTransaction.getSelectedItem().toString(),
                                          jComboBoxTypeAddTransaction.getSelectedItem().toString(),
                                          Double.parseDouble( jTextFieldAmount.getText() ) );
            jLabelInsertTransactionStatus.setText(
                    status
                            ? "Month Transaction was inserted successfully!"
                            : "Internal error while inserting month transaction"
            );
        } else if ( jButtonAddTransaction.getText().equals( "Edit" ) ) {
            status = controller.
                    updateMonthTransactions( jLabelTransactionId.getText(),
                                             jTextFieldTransactionName.getText(),
                                             jComboBoxMonthsAddTransaction.getSelectedItem().toString(),
                                             jComboBoxCategoryAddTransaction.getSelectedItem().toString(),
                                             jComboBoxTypeAddTransaction.getSelectedItem().toString(),
                                             jTextFieldAmount.getText() );
            jLabelInsertTransactionStatus.setText(
                    status
                            ? "Month Transaction was updated successfully!"
                            : "Internal error while updating month transaction"
            );
        }
    }//GEN-LAST:event_jButtonAddTransactionActionPerformed

    private void jTableMonthTransactionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMonthTransactionsMouseClicked
        int column = jTableMonthTransactions.getColumnModel().getColumnIndexAtX( evt.getX() ); // get the coloum of the button
        int row = evt.getY() / jTableMonthTransactions.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if ( row < jTableMonthTransactions.getRowCount() && row >= 0 && column < jTableMonthTransactions.getColumnCount() && column >= 0 ) {
            Object value = jTableMonthTransactions.getValueAt( row, column );
            if ( value instanceof JButton ) {
                /*perform a click event*/
                (( JButton ) value).doClick();
            }
        }
    }//GEN-LAST:event_jTableMonthTransactionsMouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        jLayeredPaneAddTransaction.setVisible( false );
        jLayeredPaneViewMonths.setVisible( true );
        jLayeredPaneViewTransactions.setVisible( false );
        jLayeredPaneViewCategories.setVisible( false );
        jTextFieldAddMonthName.setText( "" );
        jButtonAddMonth.setText( "Add month" );
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTableMonthsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMonthsMouseClicked
        int column = jTableMonths.getColumnModel().getColumnIndexAtX( evt.getX() ); // get the coloum of the button
        int row = evt.getY() / jTableMonths.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if ( row < jTableMonths.getRowCount() && row >= 0 && column < jTableMonths.getColumnCount() && column >= 0 ) {
            Object value = jTableMonths.getValueAt( row, column );
            if ( value instanceof JButton ) {
                /*perform a click event*/
                (( JButton ) value).doClick();
            }
        }
    }//GEN-LAST:event_jTableMonthsMouseClicked

    private void jButtonAddMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddMonthActionPerformed
        if ( (jButtonAddMonth).getText().equals( "Add month" ) ) {
            controller.addMonth( jTextFieldAddMonthName.getText() );
            fillMonthsTable( controller.getMonths() );
            jTextFieldAddMonthName.setText( "" );
        } else if ( (jButtonAddMonth).getText().equals( "Save changes" ) ) {
            controller.updateMonth( jLabelAddMonthId.getText(), jTextFieldAddMonthName.getText() );
            fillMonthsTable( controller.getMonths() );
            jTextFieldAddMonthName.setText( "" );
        }
    }//GEN-LAST:event_jButtonAddMonthActionPerformed

    private void jTextFieldAddMonthNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddMonthNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddMonthNameActionPerformed

    private void jMenuItemViewCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemViewCategoriesActionPerformed
        jLayeredPaneViewCategories.setVisible( true );
        jLayeredPaneAddTransaction.setVisible( false );
        jLayeredPaneViewMonths.setVisible( false );
        jLayeredPaneViewTransactions.setVisible( false );
    }//GEN-LAST:event_jMenuItemViewCategoriesActionPerformed

    private void jTableCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCategoriesMouseClicked
        int column = jTableCategories.getColumnModel().getColumnIndexAtX( evt.getX() ); // get the column of the button
        int row = evt.getY() / jTableCategories.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if ( row < jTableCategories.getRowCount() && row >= 0 && column < jTableCategories.getColumnCount() && column >= 0 ) {
            Object value = jTableCategories.getValueAt( row, column );
            if ( value instanceof JButton ) {
                /*perform a click event*/
                (( JButton ) value).doClick();
            }
        }
    }//GEN-LAST:event_jTableCategoriesMouseClicked

    private void fillTransactionsTable( List<MonthTransaction> monthTransactions ) {
        Object[][] twoDimensionalArrayForTables = new Object[ monthTransactions.size() ][ 8 ];
        for ( int i = 0; i < monthTransactions.size(); i++ ) {
            twoDimensionalArrayForTables[ i ][ 0 ] = monthTransactions.get( i ).getId();
            twoDimensionalArrayForTables[ i ][ 1 ] = monthTransactions.get( i ).getName();
            twoDimensionalArrayForTables[ i ][ 2 ] = monthTransactions.get( i ).getType();
            twoDimensionalArrayForTables[ i ][ 3 ] = controller.getMonthByID( monthTransactions.get( i ).getMonthId() ).getName();
            twoDimensionalArrayForTables[ i ][ 4 ] = controller.getCategoryByID( monthTransactions.get( i ).getCategoryId() ).getName();
            twoDimensionalArrayForTables[ i ][ 5 ] = monthTransactions.get( i ).getAmount();
            twoDimensionalArrayForTables[ i ][ 6 ] = new JButton( "Edit #" + monthTransactions.get( i ).getId() );
            twoDimensionalArrayForTables[ i ][ 7 ] = new JButton( "Delete #" + monthTransactions.get( i ).getId() );

        }

        String[] rowNames = new String[]{ "ID", "Name", "Type", "Month", "Category", "Amount", "Edit", "Delete" };
        CustomModel cm = new CustomModel( twoDimensionalArrayForTables, rowNames );

        jTableMonthTransactions.setModel( cm );
        jTableMonthTransactions.getTableHeader().setReorderingAllowed( false );

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        jTableMonthTransactions.getColumn( cm.getColumnName( 6 ) ).setCellRenderer( buttonRenderer );
        jTableMonthTransactions.getColumn( cm.getColumnName( 7 ) ).setCellRenderer( buttonRenderer );
    }

    private void fillMonthsTable( List<Month> months ) {
        Object[][] twoDimensionalArrayForTables = new Object[ months.size() ][ 4 ];
        for ( int i = 0; i < months.size(); i++ ) {
            twoDimensionalArrayForTables[ i ][ 0 ] = months.get( i ).getId();
            twoDimensionalArrayForTables[ i ][ 1 ] = months.get( i ).getName();
            twoDimensionalArrayForTables[ i ][ 2 ] = new JButton( "Edit #" + months.get( i ).getId() );
            twoDimensionalArrayForTables[ i ][ 3 ] = new JButton( "Delete #" + months.get( i ).getId() );

        }

        String[] rowNames = new String[]{ "ID", "Name", "Edit", "Delete" };
        CustomModelMonthsTable cm = new CustomModelMonthsTable( twoDimensionalArrayForTables, rowNames );

        jTableMonths.setModel( cm );
        jTableMonths.getTableHeader().setReorderingAllowed( false );

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        jTableMonths.getColumn( cm.getColumnName( 2 ) ).setCellRenderer( buttonRenderer );
        jTableMonths.getColumn( cm.getColumnName( 3 ) ).setCellRenderer( buttonRenderer );
    }

    private void fillCategoriesTable( List<Category> categories ) {
        Object[][] twoDimensionalArrayForTables = new Object[ categories.size() ][ 4 ];
        for ( int i = 0; i < categories.size(); i++ ) {
            twoDimensionalArrayForTables[ i ][ 0 ] = categories.get( i ).getId();
            twoDimensionalArrayForTables[ i ][ 1 ] = categories.get( i ).getName();
            twoDimensionalArrayForTables[ i ][ 2 ] = new JButton( "Edit #" + categories.get( i ).getId() );
            twoDimensionalArrayForTables[ i ][ 3 ] = new JButton( "Delete #" + categories.get( i ).getId() );

        }

        String[] rowNames = new String[]{ "ID", "Name", "Edit", "Delete" };
        CustomModelCategoriesTable cm = new CustomModelCategoriesTable( twoDimensionalArrayForTables, rowNames );

        jTableCategories.setModel( cm );
        jTableCategories.getTableHeader().setReorderingAllowed( false );

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        jTableCategories.getColumn( cm.getColumnName( 2 ) ).setCellRenderer( buttonRenderer );
        jTableCategories.getColumn( cm.getColumnName( 3 ) ).setCellRenderer( buttonRenderer );
    }

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if ( "Nimbus".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( ClassNotFoundException ex ) {
            java.util.logging.Logger.getLogger( ExpensesCalculator.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( InstantiationException ex ) {
            java.util.logging.Logger.getLogger( ExpensesCalculator.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( IllegalAccessException ex ) {
            java.util.logging.Logger.getLogger( ExpensesCalculator.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( ExpensesCalculator.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                new ExpensesCalculator().setVisible( true );
            }
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddCategory;
    private javax.swing.JButton jButtonAddMonth;
    private javax.swing.JButton jButtonAddTransaction;
    private javax.swing.JComboBox jComboBoType;
    private javax.swing.JComboBox jComboBoxCategory;
    private javax.swing.JComboBox jComboBoxCategoryAddTransaction;
    private javax.swing.JComboBox jComboBoxMonths;
    private javax.swing.JComboBox jComboBoxMonthsAddTransaction;
    private javax.swing.JComboBox jComboBoxTypeAddTransaction;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAddMonthId;
    private javax.swing.JLabel jLabelCategoryId;
    private javax.swing.JLabel jLabelInsertTransactionStatus;
    private javax.swing.JLabel jLabelTransactionId;
    private javax.swing.JLayeredPane jLayeredPaneAddTransaction;
    private javax.swing.JLayeredPane jLayeredPaneViewCategories;
    private javax.swing.JLayeredPane jLayeredPaneViewMonths;
    private javax.swing.JLayeredPane jLayeredPaneViewTransactions;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemAddTransaction;
    private javax.swing.JMenuItem jMenuItemViewCategories;
    private javax.swing.JMenuItem jMenuItemViewTransactions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableCategories;
    private javax.swing.JTable jTableMonthTransactions;
    private javax.swing.JTable jTableMonths;
    private javax.swing.JTextField jTextFieldAddCategoryName;
    private javax.swing.JTextField jTextFieldAddMonthName;
    private javax.swing.JTextField jTextFieldAmount;
    private javax.swing.JTextField jTextFieldTransactionName;
    // End of variables declaration//GEN-END:variables
    private static class JTableButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
            JButton button = ( JButton ) value;
            return button;
        }
    }

}
