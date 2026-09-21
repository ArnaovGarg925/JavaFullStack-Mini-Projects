package com.campus.lostfound;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
            AppService service = context.getBean(AppService.class);
            new LoginFrame(service, context);
        });
    }

    static class LoginFrame extends JFrame {
        private final AppService service;
        private final AnnotationConfigApplicationContext context;
        private final JTextField email = new JTextField();
        private final JPasswordField password = new JPasswordField();

        LoginFrame(AppService service, AnnotationConfigApplicationContext context) {
            this.service = service;
            this.context = context;
            setTitle("Campus Lost & Found - Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(480, 360);
            setLocationRelativeTo(null);
            build();
            setVisible(true);
        }

        private void build() {
            JPanel root = new JPanel(new BorderLayout(15, 15));
            root.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

            JLabel title = new JLabel("Campus Lost & Found", SwingConstants.CENTER);
            title.setFont(new Font("SansSerif", Font.BOLD, 24));
            root.add(title, BorderLayout.NORTH);

            JPanel form = new JPanel(new GridLayout(5, 1, 8, 8));
            form.add(new JLabel("Email"));
            form.add(email);
            form.add(new JLabel("Password"));
            form.add(password);

            JButton login = new JButton("Login");
            JButton register = new JButton("Create Account");
            JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0));
            buttons.add(login);
            buttons.add(register);
            form.add(buttons);

            root.add(form, BorderLayout.CENTER);

            JLabel hint = new JLabel("Admin demo: admin@campus.com / admin123",
                    SwingConstants.CENTER);
            root.add(hint, BorderLayout.SOUTH);
            setContentPane(root);

            login.addActionListener(e -> doLogin());
            register.addActionListener(e -> new RegisterDialog(this, service));
        }

        private void doLogin() {
            User user = service.login(email.getText(), new String(password.getPassword()));
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
                return;
            }
            new Dashboard(this, service, context, user);
            setVisible(false);
        }
    }

    static class RegisterDialog extends JDialog {
        RegisterDialog(JFrame parent, AppService service) {
            super(parent, "Create Account", true);
            setSize(420, 330);
            setLocationRelativeTo(parent);

            JTextField name = new JTextField();
            JTextField email = new JTextField();
            JPasswordField pass = new JPasswordField();
            JButton create = new JButton("Create Account");

            JPanel p = new JPanel(new GridLayout(7, 1, 8, 8));
            p.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
            p.add(new JLabel("Full Name")); p.add(name);
            p.add(new JLabel("Email")); p.add(email);
            p.add(new JLabel("Password")); p.add(pass);
            p.add(create);
            setContentPane(p);

            create.addActionListener(e -> {
                try {
                    service.register(name.getText(), email.getText(),
                            new String(pass.getPassword()));
                    JOptionPane.showMessageDialog(this, "Account created successfully.");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            setVisible(true);
        }
    }

    static class Dashboard extends JFrame {
        private final AppService service;
        private final AnnotationConfigApplicationContext context;
        private final User user;
        private final DefaultListModel<Item> lostModel = new DefaultListModel<>();
        private final DefaultListModel<Item> foundModel = new DefaultListModel<>();
        private final JList<Item> lostList = new JList<>(lostModel);
        private final JList<Item> foundList = new JList<>(foundModel);
        private final JLabel welcome = new JLabel();

        Dashboard(JFrame login, AppService service,
                  AnnotationConfigApplicationContext context, User user) {
            this.service = service;
            this.context = context;
            this.user = user;

            setTitle("Campus Lost & Found - Dashboard");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 650);
            setLocationRelativeTo(null);
            build();
            refresh();
            setVisible(true);

            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override public void windowClosing(java.awt.event.WindowEvent e) {
                    context.close();
                }
            });
        }

        private void build() {
            JPanel root = new JPanel(new BorderLayout(10, 10));
            root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel top = new JPanel(new BorderLayout());
            welcome.setText("Welcome, " + user.getName()
                    + (user.isAdmin() ? "  [ADMIN]" : ""));
            welcome.setFont(new Font("SansSerif", Font.BOLD, 18));
            top.add(welcome, BorderLayout.WEST);

            JButton logout = new JButton("Logout");
            top.add(logout, BorderLayout.EAST);
            logout.addActionListener(e -> {
                dispose();
                login.setVisible(true);
            });
            root.add(top, BorderLayout.NORTH);

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("🔴 LOST ITEMS", buildItemPanel(ReportType.LOST, lostList));
            tabs.addTab("🟢 FOUND ITEMS", buildItemPanel(ReportType.FOUND, foundList));
            tabs.addTab("My Reports", buildMyReportsPanel());
            if (user.isAdmin()) tabs.addTab("Admin", buildAdminPanel());
            root.add(tabs, BorderLayout.CENTER);

            setContentPane(root);
        }

        private JPanel buildItemPanel(ReportType type, JList<Item> list) {
            JPanel panel = new JPanel(new BorderLayout(8, 8));
            JTextField search = new JTextField();
            JButton searchBtn = new JButton("Search");
            JButton add = new JButton(type == ReportType.LOST ? "Report Lost Item" : "Report Found Item");
            JButton edit = new JButton("Edit Selected");
            JButton delete = new JButton("Delete Selected");
            JButton claim = new JButton("Claim Selected");

            JPanel controls = new JPanel(new BorderLayout(8, 8));
            controls.add(search, BorderLayout.CENTER);
            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttons.add(searchBtn); buttons.add(add); buttons.add(edit); buttons.add(delete);
            if (type == ReportType.FOUND) buttons.add(claim);
            controls.add(buttons, BorderLayout.SOUTH);
            panel.add(controls, BorderLayout.NORTH);

            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setFont(new Font("Monospaced", Font.PLAIN, 13));
            panel.add(new JScrollPane(list), BorderLayout.CENTER);

            searchBtn.addActionListener(e -> refresh(type, search.getText()));
            search.addActionListener(e -> refresh(type, search.getText()));
            add.addActionListener(e -> addItem(type));
            edit.addActionListener(e -> editSelected(list));
            delete.addActionListener(e -> deleteSelected(list));
            claim.addActionListener(e -> claimSelected(list));

            return panel;
        }

        private JPanel buildMyReportsPanel() {
            JPanel p = new JPanel(new BorderLayout());
            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.setFont(new Font("Monospaced", Font.PLAIN, 13));
            p.add(new JScrollPane(area), BorderLayout.CENTER);
            JButton refresh = new JButton("Refresh My Reports");
            p.add(refresh, BorderLayout.SOUTH);

            Runnable update = () -> {
                StringBuilder s = new StringBuilder();
                for (Item i : service.items()) {
                    if (i.getOwnerId().equals(user.getId())) s.append(i).append("\n");
                }
                area.setText(s.length() == 0 ? "No reports yet." : s.toString());
            };
            refresh.addActionListener(e -> update.run());
            SwingUtilities.invokeLater(update);
            return p;
        }

        private JPanel buildAdminPanel() {
            JPanel p = new JPanel(new BorderLayout(8, 8));
            JTextArea area = new JTextArea();
            area.setEditable(false);
            JButton refresh = new JButton("Refresh Users & Items");
            p.add(new JScrollPane(area), BorderLayout.CENTER);
            p.add(refresh, BorderLayout.SOUTH);

            Runnable update = () -> {
                StringBuilder s = new StringBuilder("USERS\n");
                s.append("========================================\n");
                for (User u : service.users())
                    s.append(u.getId()).append(" | ").append(u.getName())
                     .append(" | ").append(u.getEmail()).append("\n");
                s.append("\nITEMS\n========================================\n");
                for (Item i : service.items()) s.append(i).append("\n");
                area.setText(s.toString());
            };
            refresh.addActionListener(e -> update.run());
            SwingUtilities.invokeLater(update);
            return p;
        }

        private void addItem(ReportType type) {
            ItemDialog dialog = new ItemDialog(this, type, null);
            dialog.setVisible(true);
            if (dialog.ok) {
                try {
                    service.addItem(dialog.title.getText(), dialog.category.getText(),
                        dialog.location.getText(), dialog.date.getText(),
                        dialog.description.getText(), type, user.getId());
                    refresh();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }

        private void editSelected(JList<Item> list) {
            Item item = list.getSelectedValue();
            if (item == null) { JOptionPane.showMessageDialog(this, "Select an item first."); return; }
            if (!user.isAdmin() && !item.getOwnerId().equals(user.getId())) {
                JOptionPane.showMessageDialog(this, "You can edit only your own report.");
                return;
            }
            ItemDialog dialog = new ItemDialog(this, item.getReportType(), item);
            dialog.setVisible(true);
            if (dialog.ok) {
                item.setTitle(dialog.title.getText());
                item.setCategory(dialog.category.getText());
                item.setLocation(dialog.location.getText());
                item.setDate(dialog.date.getText());
                item.setDescription(dialog.description.getText());
                service.updateItem(item);
                refresh();
            }
        }

        private void deleteSelected(JList<Item> list) {
            Item item = list.getSelectedValue();
            if (item == null) { JOptionPane.showMessageDialog(this, "Select an item first."); return; }
            int yes = JOptionPane.showConfirmDialog(this,
                    "Delete " + item.getTitle() + "?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (yes == JOptionPane.YES_OPTION) {
                try {
                    service.deleteItem(item.getId(), user);
                    refresh();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }

        private void claimSelected(JList<Item> list) {
            Item item = list.getSelectedValue();
            if (item == null) { JOptionPane.showMessageDialog(this, "Select an item first."); return; }
            try {
                service.claimItem(item);
                JOptionPane.showMessageDialog(this, "Claim/request submitted.");
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        private void refresh() {
            refresh(ReportType.LOST, "");
            refresh(ReportType.FOUND, "");
        }

        private void refresh(ReportType type, String query) {
            DefaultListModel<Item> model = type == ReportType.LOST ? lostModel : foundModel;
            model.clear();
            List<Item> items = service.items(type, query);
            for (Item item : items) model.addElement(item);
        }
    }

    static class ItemDialog extends JDialog {
        final JTextField title = new JTextField();
        final JTextField category = new JTextField();
        final JTextField location = new JTextField();
        final JTextField date = new JTextField();
        final JTextField description = new JTextField();
        boolean ok = false;

        ItemDialog(JFrame parent, ReportType type, Item item) {
            super(parent, type + " Item", true);
            setSize(500, 400);
            setLocationRelativeTo(parent);

            if (item != null) {
                title.setText(item.getTitle());
                category.setText(item.getCategory());
                location.setText(item.getLocation());
                date.setText(item.getDate());
                description.setText(item.getDescription());
            }

            JPanel p = new JPanel(new GridLayout(11, 1, 7, 7));
            p.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));
            p.add(new JLabel("Item Title")); p.add(title);
            p.add(new JLabel("Category")); p.add(category);
            p.add(new JLabel("Location")); p.add(location);
            p.add(new JLabel("Date (YYYY-MM-DD)")); p.add(date);
            p.add(new JLabel("Description")); p.add(description);

            JButton save = new JButton(item == null ? "Add Report" : "Save Changes");
            p.add(save);
            setContentPane(p);

            save.addActionListener(e -> {
                if (title.getText().isBlank() || category.getText().isBlank()
                        || location.getText().isBlank()) {
                    JOptionPane.showMessageDialog(this, "Fill title, category and location.");
                    return;
                }
                ok = true;
                dispose();
            });
        }
    }
}
