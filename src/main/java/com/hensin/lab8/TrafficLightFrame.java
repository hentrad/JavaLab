package com.hensin.lab8;

import com.hensin.LabException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class TrafficLightFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JTextField greenField = new JTextField("", 8);
    private final JTextField lanesField = new JTextField("", 8);
    private final JTextField carsField  = new JTextField("", 8);
    private final JTextField crossField = new JTextField("", 8);

    private final JCheckBox showDetails = new JCheckBox("Показать подробности", false);

    private final JButton calculateBtn = new JButton("Рассчитать");
    private final JButton resetBtn     = new JButton("Сбросить");

    private final JLabel    resultLabel = new JLabel("Результат: —");
    private final JTextArea detailsArea = new JTextArea(7, 30);

    private final JComboBox<String>  fontFamilyCombo = new JComboBox<>();
    private final JComboBox<Integer> fontSizeCombo   = new JComboBox<>();
    private final JCheckBox         fontBoldCheck    = new JCheckBox("Жирный");

    private static final String  DEFAULT_OUTPUT_FAMILY = "Monospaced";
    private static final int     DEFAULT_OUTPUT_SIZE   = 13;
    private static final boolean DEFAULT_OUTPUT_BOLD   = false;

    private String  outputFontFamily = DEFAULT_OUTPUT_FAMILY;
    private int     outputFontSize   = DEFAULT_OUTPUT_SIZE;
    private boolean outputFontBold   = DEFAULT_OUTPUT_BOLD;

    private final Font uiFont = new Font("SansSerif", Font.PLAIN, 14);

    public TrafficLightFrame() {
        super("Светофор — лабораторная работа №8");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(620, 580);
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
                                "Выход: сколько машин останется после зелёного.\n\n" +
                                "Шрифт вывода настраивается на панели «Шрифт вывода».",
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
        inputPanel.add(new JLabel("Время зелёного света (сек):"));
        inputPanel.add(greenField);
        inputPanel.add(new JLabel("Количество полос:"));
        inputPanel.add(lanesField);
        inputPanel.add(new JLabel("Машин перед светофором:"));
        inputPanel.add(carsField);
        inputPanel.add(new JLabel("Секунд на одну машину:"));
        inputPanel.add(crossField);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Опции"));
        optionsPanel.add(showDetails);

        JPanel fontPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        fontPanel.setBorder(BorderFactory.createTitledBorder("Шрифт вывода"));

        initFontChoosers();

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

        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Результат"));
        resultPanel.add(resultLabel, BorderLayout.NORTH);

        detailsArea.setEditable(false);
        resultPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        root.add(topPanel,     BorderLayout.NORTH);
        root.add(buttonsPanel, BorderLayout.CENTER);
        root.add(resultPanel,  BorderLayout.SOUTH);

        setContentPane(root);
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
        // Здесь this::onCalculate работает,
        // потому что onCalculate не бросает checked-исключений
        calculateBtn.addActionListener(this::onCalculate);
        resetBtn.addActionListener(e -> resetToInitialState());

        showDetails.addActionListener(e ->
                detailsArea.setVisible(showDetails.isSelected()));

        fontFamilyCombo.addActionListener(e -> {
            Object sel = fontFamilyCombo.getSelectedItem();
            if (sel != null) {
                outputFontFamily = sel.toString();
                applyOutputFont();
            }
        });

        fontSizeCombo.addActionListener(e -> {
            Object sel = fontSizeCombo.getSelectedItem();
            if (sel instanceof Integer) {
                outputFontSize = (Integer) sel;
                applyOutputFont();
            }
        });

        fontBoldCheck.addActionListener(e -> {
            outputFontBold = fontBoldCheck.isSelected();
            applyOutputFont();
        });
    }

    // 5. Возврат
    private void resetToInitialState() {
        greenField.setText("");
        lanesField.setText("");
        carsField .setText("");
        crossField.setText("");

        resultLabel.setText("Результат: —");
        detailsArea.setText("");

        showDetails.setSelected(false);
        detailsArea.setVisible(true);

        outputFontFamily = DEFAULT_OUTPUT_FAMILY;
        outputFontSize   = DEFAULT_OUTPUT_SIZE;
        outputFontBold   = DEFAULT_OUTPUT_BOLD;

        fontFamilyCombo.setSelectedItem(DEFAULT_OUTPUT_FAMILY);
        fontSizeCombo.setSelectedItem(DEFAULT_OUTPUT_SIZE);
        fontBoldCheck.setSelected(DEFAULT_OUTPUT_BOLD);

        applyUiFont();
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

    // 7. Шрифт вывода поверх UI-шрифта
    private void applyOutputFont() {
        int style = outputFontBold ? Font.BOLD : Font.PLAIN;
        Font outputFont = new Font(outputFontFamily, style, outputFontSize);

        resultLabel.setFont(outputFont);
        detailsArea.setFont(outputFont);

        revalidate();
        repaint();
    }

    private static void applyRecursively(Component c, Font font) {
        c.setFont(font);
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyRecursively(child, font);
            }
        }
    }

    // 8. Логика расчёта.
    // ВАЖНО: НЕ объявляем throws — все checked-исключения обрабатываются внутри,
    // чтобы this::onCalculate работал как ActionListener.
    private void onCalculate(ActionEvent e) {
        try {
            int green = Integer.parseInt(greenField.getText().trim());
            int lanes = Integer.parseInt(lanesField.getText().trim());
            int cars  = Integer.parseInt(carsField .getText().trim());
            int cross = Integer.parseInt(crossField.getText().trim());

            TrafficLight model = new TrafficLight(green, lanes, cars, cross);

            int passed    = model.carsPassed();
            int remaining = model.carsRemaining();

            resultLabel.setText("Осталось машин: " + remaining);

            if (showDetails.isSelected()) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%-24s %d сек%n", "Время зелёного:", green));
                sb.append(String.format("%-24s %d%n",     "Полос движения:", lanes));
                sb.append(String.format("%-24s %d%n",     "Машин изначально:", cars));
                sb.append(String.format("%-24s %d сек%n", "Секунд на машину:", cross));
                sb.append(String.format("%-24s %d × (%d / %d) = %d%n",  "Пропускная способность:", lanes, green, cross, passed));
                sb.append(String.format("%-24s %d%n",     "Успело проехать:", passed));
                sb.append(String.format("%-24s %d%n",     "Осталось:", remaining));
                if (remaining > 0) {
                    sb.append(String.format("%-24s ~%d сек%n", "Нужно ещё зелёного:", model.timeToClear()));
                } else {
                    sb.append("Все машины успели проехать.\n");
                }
                detailsArea.setText(sb.toString());
            } else {
                detailsArea.setText("");
            }
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