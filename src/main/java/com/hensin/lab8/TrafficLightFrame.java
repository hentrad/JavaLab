package com.hensin.lab8;

import com.hensin.LabException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class TrafficLightFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final String  DEFAULT_OUTPUT_FAMILY = "Monospaced";
    private static final int     DEFAULT_OUTPUT_SIZE   = 13;
    private static final boolean DEFAULT_OUTPUT_BOLD   = false;

    private final JTextField greenField = new JTextField(8);
    private final JTextField lanesField = new JTextField(8);
    private final JTextField carsField  = new JTextField(8);
    private final JTextField crossField = new JTextField(8);

    private final JCheckBox showDetails = new JCheckBox("Показать подробности", false);

    private final JButton calculateBtn = new JButton("Рассчитать");
    private final JButton resetBtn     = new JButton("Сбросить");

    private final JLabel    resultLabel = new JLabel("Результат: —");
    private final JTextArea detailsArea = new JTextArea(7, 30);

    private final JComboBox<String>  fontFamilyCombo = new JComboBox<>();
    private final JComboBox<Integer> fontSizeCombo   = new JComboBox<>();
    private final JCheckBox          fontBoldCheck   = new JCheckBox("Жирный");

    private final Font uiFont = new Font("SansSerif", Font.PLAIN, 14);

    public TrafficLightFrame() {
        super("Светофор — лабораторная работа №8");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(620, 680);
        setLocationRelativeTo(null);

        setupMenu();
        setupLayout();
        setupEvents();

        applyUiFont();
        applyOutputFont();
    }

    // 1. Меню
    private void setupMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Лабораторная работа №8\nПриложение-светофор\n\n" +
                                "Вход: время зелёного, полосы, машины, сек/машина.\n" +
                                "Выход: сколько машин останется после зелёного.\n\n",
                        "О программе", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        bar.add(fileMenu);
        bar.add(helpMenu);
        setJMenuBar(bar);
    }

    // 2. Размещение компонентов
    private void setupLayout() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 6, 6));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Входные данные"));
        addRow(inputPanel, "Время зелёного света (сек):", greenField);
        addRow(inputPanel, "Количество полос:",           lanesField);
        addRow(inputPanel, "Машин перед светофором:",     carsField);
        addRow(inputPanel, "Секунд на одну машину:",      crossField);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("опции"));
        optionsPanel.add(showDetails);

        initFontChoosers();

        JPanel fontPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        fontPanel.setBorder(BorderFactory.createTitledBorder("Шрифт вывода"));
        fontPanel.add(fontFamilyCombo);
        fontPanel.add(fontSizeCombo);
        fontPanel.add(fontBoldCheck);

        JPanel topPanel = new JPanel(new BorderLayout(6, 6));
        topPanel.add(inputPanel,   BorderLayout.NORTH);
        topPanel.add(optionsPanel, BorderLayout.CENTER);
        topPanel.add(fontPanel,    BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        buttonsPanel.add(calculateBtn);
        buttonsPanel.add(resetBtn);

        detailsArea.setEditable(false);

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Результат"));
        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        root.add(topPanel,     BorderLayout.NORTH);
        root.add(buttonsPanel, BorderLayout.CENTER);
        root.add(resultPanel,  BorderLayout.SOUTH);

        setContentPane(root);
    }

    private static void addRow(JPanel panel, String text, JComponent field) {
        panel.add(new JLabel(text));
        panel.add(field);
    }

    // 3. Наполняем списки шрифтов и размеров
    private void initFontChoosers() {
        String[] families = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        Arrays.sort(families);
        for (String family : families) {
            fontFamilyCombo.addItem(family);
        }
        fontFamilyCombo.setSelectedItem(DEFAULT_OUTPUT_FAMILY);

        for (int size = 8; size <= 36; size += 2) {
            fontSizeCombo.addItem(size);
        }
        fontSizeCombo.setSelectedItem(DEFAULT_OUTPUT_SIZE);

        fontFamilyCombo.setPreferredSize(new Dimension(180, 24));
        fontSizeCombo.setPreferredSize(new Dimension(60, 24));
    }

    // 4. Обработчики событий
    private void setupEvents() {
        calculateBtn.addActionListener(e -> onCalculate());
        resetBtn.addActionListener(e -> resetToInitialState());

        showDetails.addActionListener(e ->
                detailsArea.setVisible(showDetails.isSelected()));

        // Один слушатель на все три элемента управления шрифтом
        ActionListener fontListener = e -> applyOutputFont();
        fontFamilyCombo.addActionListener(fontListener);
        fontSizeCombo.addActionListener(fontListener);
        fontBoldCheck.addActionListener(fontListener);
    }

    // 5. Возврат к начальному состоянию
    private void resetToInitialState() {
        greenField.setText("");
        lanesField.setText("");
        carsField.setText("");
        crossField.setText("");

        resultLabel.setText("Результат: —");
        detailsArea.setText("");

        showDetails.setSelected(false);
        detailsArea.setVisible(true);

        fontFamilyCombo.setSelectedItem(DEFAULT_OUTPUT_FAMILY);
        fontSizeCombo.setSelectedItem(DEFAULT_OUTPUT_SIZE);
        fontBoldCheck.setSelected(DEFAULT_OUTPUT_BOLD);
        applyOutputFont();

        greenField.requestFocusInWindow();
    }

    // 6. Единый шрифт интерфейса
    private void applyUiFont() {
        applyRecursively(getContentPane(), uiFont);
        if (getJMenuBar() != null) {
            applyRecursively(getJMenuBar(), uiFont);
        }
    }

    // 7. Шрифт вывода поверх UI-шрифта (берётся напрямую из элементов управления)
    private void applyOutputFont() {
        int style = fontBoldCheck.isSelected() ? Font.BOLD : Font.PLAIN;
        Font outputFont = new Font((String) fontFamilyCombo.getSelectedItem(),
                                   style,
                                   (Integer) fontSizeCombo.getSelectedItem());
        resultLabel.setFont(outputFont);
        detailsArea.setFont(outputFont);
    }

    private static void applyRecursively(Component c, Font font) {
        c.setFont(font);
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyRecursively(child, font);
            }
        }
    }

    private static int readInt(JTextField field) {
        return Integer.parseInt(field.getText().trim());
    }

    private static void row(StringBuilder sb, String label, Object value) {
        sb.append(String.format("%-24s %s%n", label, value));
    }

    // 8. Логика расчёта
    private void onCalculate() {
        try {
            TrafficLight model = new TrafficLight(
                    readInt(greenField), readInt(lanesField),
                    readInt(carsField),  readInt(crossField));

            int passed    = model.carsPassed();
            int remaining = model.carsRemaining();

            resultLabel.setText("Осталось машин: " + remaining);

            if (!showDetails.isSelected()) {
                detailsArea.setText("");
                return;
            }

            StringBuilder sb = new StringBuilder();
            row(sb, "Время зелёного:",   model.getGreenTime() + " сек");
            row(sb, "Полос движения:",   model.getLanes());
            row(sb, "Машин изначально:", model.getCars());
            row(sb, "Секунд на машину:", model.getAvgCrossTime() + " сек");
            row(sb, "Пропускная способность:",
                    model.getLanes() + " × (" + model.getGreenTime() + " / "
                            + model.getAvgCrossTime() + ") = " + passed);
            row(sb, "Успело проехать:",  passed);
            row(sb, "Осталось:",         remaining);
            if (remaining > 0) {
                row(sb, "Нужно ещё зелёного:", "~" + model.timeToClear() + " сек");
            } else {
                sb.append("Все машины успели проехать.\n");
            }
            detailsArea.setText(sb.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Все поля должны содержать целые числа.\n",
                    "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        } catch (LabException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Ошибка lab8", JOptionPane.WARNING_MESSAGE);
        }
    }
}