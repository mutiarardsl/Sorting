import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.Random;

class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        super();
        this.radius = radius;
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }
}

public class SortingAppGUI extends JFrame {
    private JTextField arraySize; // Deklarasi yang benar
    private JTextArea resultTextArea; // Deklarasi untuk JTextArea

    public SortingAppGUI() {
        setTitle("Aplikasi Sorting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(186, 26, 95));

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout(0, 20));
        contentPane.setBackground(new Color(186,26,95));
        setContentPane(contentPane);

        RoundedPanel inputPanel = new RoundedPanel(20); // Menggunakan RoundedPanel dengan radius 20
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(241, 211, 214));

        JLabel sizeLabel = new JLabel("Ukuran Array:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Mengubah font
        arraySize = new JTextField(20);
        JButton sortButton = new JButton("Urutkan");
        sortButton.setBackground(new Color(67, 188, 168)); // Warna tombol
        sortButton.setForeground(Color.BLACK);
        sortButton.setFont(new Font("Arial", Font.BOLD, 12)); // Mengubah font tombol

        inputPanel.add(sizeLabel);
        inputPanel.add(arraySize);
        inputPanel.add(sortButton);

        contentPane.add(inputPanel, BorderLayout.NORTH);

        RoundedPanel resultPanel = new RoundedPanel(20); 
        resultTextArea = new JTextArea(10, 50);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        resultPanel.setBackground(new Color(241, 211, 214)); // Warna panel hasil

        contentPane.add(resultPanel, BorderLayout.CENTER);

        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int size = Integer.parseInt(arraySize.getText());
                    int[] unsortedArray = generateRandomArray(size);
                    resultTextArea.setText("");

                    resultTextArea.append("Array sebelum diurutkan: " + Arrays.toString(unsortedArray) + "\n\n");

                    int[] bubbleSortedArray = unsortedArray.clone();
                    bubbleSort(bubbleSortedArray);
                    resultTextArea.append("Array setelah diurutkan dengan Bubble Sort: " + Arrays.toString(bubbleSortedArray) + "\n\n");

                    int[] shellSortedArray = unsortedArray.clone();
                    shellSort(shellSortedArray);
                    resultTextArea.append("Array setelah diurutkan dengan Shell Sort: " + Arrays.toString(shellSortedArray) + "\n\n");
                } catch (NumberFormatException nfe) {
                    resultTextArea.setText("Silakan masukkan ukuran array yang valid.");
                }
            }
        });
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Menukar elemen
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SortingAppGUI frame = new SortingAppGUI();
                frame.setVisible(true);
            }
        });
    }
}